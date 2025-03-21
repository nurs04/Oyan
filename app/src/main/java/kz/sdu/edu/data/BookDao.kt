package kz.sdu.edu.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kz.sdu.edu.models.BookEntity

@Dao
interface BookDao {
    @Query("SELECT * FROM books WHERE category = :category")
    suspend fun getBooksByCategory(category: String): List<BookEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBooks(books: List<BookEntity>)

    @Query("SELECT * FROM books WHERE id = :bookId")
    suspend fun getBookById(bookId: Int): BookEntity?
}