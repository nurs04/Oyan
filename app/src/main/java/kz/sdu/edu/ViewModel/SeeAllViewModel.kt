package kz.sdu.edu.ViewModel

import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kz.sdu.edu.models.SeeAllUiState
import kz.sdu.edu.repository.MainRepository

class SeeAllViewModel (
    private val repository: MainRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(SeeAllUiState())
    val uiState: StateFlow<SeeAllUiState> = _uiState.asStateFlow()

    private var currentPage = 1
    private var endReached = false
    private val PAGE_SIZE = 20

    fun setCategory(category: String) {
        if (_uiState.value.category != category) {
            currentPage = 1
            endReached = false
            _uiState.update { it.copy(category = category, books = emptyList()) }
            loadFirstPage(category)
        }
    }

    private fun loadFirstPage(category: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            // Force a network call for page 1 to get the full 20 books and store them in Room.
            val books = repository.fetchBooksByCategory(category, 1, forceRefresh = true) ?: emptyList()
            _uiState.update {
                it.copy(
                    books = books,
                    isLoading = false,
                    listState = LazyGridState(0, 0)
                )
            }
            currentPage = 1
            endReached = books.size < PAGE_SIZE
        }
    }

    fun loadNextPage() {
        if (endReached || _uiState.value.isLoading) return

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val nextPage = currentPage + 1
            val newBooks = repository.fetchBooksByCategory(_uiState.value.category, nextPage) ?: emptyList()
            endReached = newBooks.isEmpty() || newBooks.size < PAGE_SIZE
            _uiState.update {
                it.copy(
                    books = it.books + newBooks,
                    isLoading = false
                )
            }
            currentPage = nextPage
        }
    }
}