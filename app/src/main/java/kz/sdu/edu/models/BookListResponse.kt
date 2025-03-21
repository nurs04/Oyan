package kz.sdu.edu.models

data class BookListResponse(
    val count : Int,
    val next: String?,
    val previous: String?,
    val results: List<BookItem>
)
