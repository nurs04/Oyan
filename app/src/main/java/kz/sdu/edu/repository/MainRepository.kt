package kz.sdu.edu.repository

import kz.sdu.edu.models.BookItem
import kz.sdu.edu.models.CommentItem
import kz.sdu.edu.models.CommentRequest
import kz.sdu.edu.models.CommentsResponse

interface MainRepository {
    suspend fun fetchRecommendedBooks() : List<BookItem>?
    suspend fun fetchPopularBooks() : List<BookItem>?
    suspend fun fetchNewBooks() : List<BookItem>?
    suspend fun fetchBooksByCategory(category: String, page : Int, forceRefresh : Boolean = false): List<BookItem>?
    suspend fun getBookDetails(bookId : Int) : BookItem?
    suspend fun getComments(bookId: Int) : List<CommentItem>?
    suspend fun sendComments(bookId: Int, commentRequest: CommentRequest) : CommentsResponse?
}