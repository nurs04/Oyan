package kz.sdu.edu.models

import androidx.compose.foundation.lazy.grid.LazyGridState

data class SeeAllUiState(
    val category: String = "",
    val books: List<BookItem> = emptyList(),
    val isLoading: Boolean = false,
    val listState: LazyGridState = LazyGridState(),
    val endReached : Boolean = false
)
