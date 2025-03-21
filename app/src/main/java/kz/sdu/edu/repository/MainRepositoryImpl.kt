package kz.sdu.edu.repository

import android.util.Log
import kz.sdu.edu.data.ApiService
import kz.sdu.edu.data.BookDao
import kz.sdu.edu.data.toBookItem
import kz.sdu.edu.data.toEntity
import kz.sdu.edu.models.BookItem
import kz.sdu.edu.models.BookListResponse
import kz.sdu.edu.models.CommentItem
import kz.sdu.edu.models.CommentListResponse
import kz.sdu.edu.models.CommentRequest
import kz.sdu.edu.models.CommentsResponse
import retrofit2.Response

class MainRepositoryImpl(
    private val api : ApiService,
    private val bookDao: BookDao
) : MainRepository{
    override suspend fun fetchRecommendedBooks(): List<BookItem> {
        return fetchBooksWithCache("recommended") { api.getRecommendationBooks(1) }
    }

    override suspend fun fetchPopularBooks(): List<BookItem> {
        return fetchBooksWithCache("popular") { api.getPopularBooks(1) }
    }

    override suspend fun fetchNewBooks(): List<BookItem> {
        return fetchBooksWithCache("new") { api.getNewBooks(1) }
    }

    override suspend fun getComments(bookId: Int): List<CommentItem>? {
        val response = api.getComments(bookId)
        return if (response.isSuccessful) {
            response.body()?.results  // extract the list from the response body
        } else {
            null
        }
    }

    override suspend fun sendComments(bookId: Int, commentRequest: CommentRequest): CommentsResponse? {
        val response = api.sendComments(bookId, commentRequest)
        return if (response.isSuccessful) {
            response.body()
        } else {
            null
        }
    }


    override suspend fun fetchBooksByCategory(
        category: String,
        page: Int,
        forceRefresh: Boolean
    ): List<BookItem>? {
        return try {
            if (page == 1) {
                // When page 1 is requested
                if (!forceRefresh) {
                    // If not forcing a refresh, try to get cached books.
                    val localBooks = bookDao.getBooksByCategory(category)
                    if (localBooks.isNotEmpty()) {
                        return localBooks.map { it.toBookItem() }
                    }
                }
                // Either forceRefresh is true or cache is empty. Do a network call.
                val response = getApiCallForCategory(category, page)
                if (response.isSuccessful) {
                    val books = response.body()?.results ?: emptyList()
                    // Store into Room so next time you can get these 20 books
                    bookDao.insertBooks(books.map { it.toEntity(category) })
                    books
                } else {
                    emptyList()
                }
            } else {
                // For pages beyond the first page, always use a network call.
                fetchFromNetworkOnly(category, page)
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    override suspend fun getBookDetails(bookId: Int): BookItem? {
        return try {
            bookDao.getBookById(bookId)?.toBookItem()
        } catch (e: Exception) {
            Log.e("Repository", "Error getting book from DB", e)
            null
        }
    }

    private suspend fun fetchBooksWithCache(
        category: String,
        apiCall: suspend () -> Response<BookListResponse>
    ): List<BookItem> {
        return try {
            val localBooks = bookDao.getBooksByCategory(category)
            if (localBooks.isNotEmpty()) {
                return localBooks.map { it.toBookItem() }
            }

            val response = apiCall()
            if (response.isSuccessful) {
                val books = response.body()?.results ?: emptyList()
                bookDao.insertBooks(books.map { it.toEntity(category) })
                books
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    private suspend fun fetchFromNetworkOnly(category: String, page: Int): List<BookItem> {
        return try {
            val response = getApiCallForCategory(category, page)
            if (response.isSuccessful) {
                response.body()?.results ?: emptyList()
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    private suspend fun getApiCallForCategory(category: String, page: Int): Response<BookListResponse> {
        return when (category.lowercase()) {
            "recommended" -> api.getRecommendationBooks(page)
            "popular" -> api.getPopularBooks(page)
            "new" -> api.getNewBooks(page)
            else -> throw IllegalArgumentException("Invalid category")
        }
    }


}

