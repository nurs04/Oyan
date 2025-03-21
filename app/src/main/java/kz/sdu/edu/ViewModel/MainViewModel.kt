package kz.sdu.edu.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kz.sdu.edu.models.BookItem
import kz.sdu.edu.models.CommentItem
import kz.sdu.edu.models.CommentRequest
import kz.sdu.edu.repository.MainRepository

class MainViewModel(
    private val repository: MainRepository
) : ViewModel(){
    private val _recommendedBooks = MutableStateFlow<List<BookItem>>(emptyList())
    val recommendedBooks  : StateFlow<List<BookItem>> = _recommendedBooks
    private val _popularBooks = MutableStateFlow<List<BookItem>>(emptyList())
    val popularBooks  : StateFlow<List<BookItem>> = _popularBooks
    private val _newBooks = MutableStateFlow<List<BookItem>>(emptyList())
    val newBooks  : StateFlow<List<BookItem>> = _newBooks
    private val _bookDetails = MutableStateFlow<BookItem?>(null)
    val bookDetails: StateFlow<BookItem?> = _bookDetails
    private val _comments = MutableStateFlow<List<CommentItem>>(emptyList())
    val comments : StateFlow<List<CommentItem>> = _comments
    init{
        fetchBooks()
    }

    private fun fetchBooks(){
        viewModelScope.launch {
            val recommended = repository.fetchRecommendedBooks() ?: emptyList()
            _recommendedBooks.value = recommended.take(3)

            val popular = repository.fetchPopularBooks() ?: emptyList()
            _popularBooks.value = popular.take(3)

            val new = repository.fetchNewBooks() ?: emptyList()
            _newBooks.value = new.take(3)

        }
    }

    fun loadBookDetails(bookId : Int){
        viewModelScope.launch {
            _bookDetails.value = repository.getBookDetails(bookId)
        }
    }

    fun getComments(bookId: Int){
        viewModelScope.launch {
            val comments = repository.getComments(bookId) ?: emptyList()
            _comments.value = comments
        }
    }

    fun sendComments(bookId: Int, commentRequest: CommentRequest) {
        viewModelScope.launch {
            val response = repository.sendComments(bookId, commentRequest)
            if (response != null && response.status == "success") {
                _bookDetails.value = _bookDetails.value?.copy(rating = response.newRating.toString())
            }
        }
    }
}