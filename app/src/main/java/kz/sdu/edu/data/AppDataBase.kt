package kz.sdu.edu.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import kz.sdu.edu.models.BookEntity
import kz.sdu.edu.models.BookItem



class Converters {
    @TypeConverter
    fun fromIntList(list: List<Int>?): String? {
        return list?.joinToString(separator = ",")
    }

    @TypeConverter
    fun toIntList(data: String?): List<Int>? {
        return data?.split(",")?.mapNotNull { it.toIntOrNull() }
    }
}


@Database(entities = [BookEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun bookDao(): BookDao
}

fun BookItem.toEntity(category: String): BookEntity {
    return BookEntity(
        id = this.id,
        category = category,
        title = this.title,
        author = this.author,
        description = this.description,
        coverImageUrl = this.coverImageUrl,
        rating = this.rating,
        createdAt = this.createdAt,
        publishedYear = this.publishedYear,
        plan = this.plan,
        price = this.price,
        pageCount = this.pageCount,
        ageLimit = this.ageLimit,
        readUrl = this.readUrl

    )
}

fun BookEntity.toBookItem(): BookItem {
    return BookItem(
        id = this.id,
        title = this.title,
        author = this.author,
        description = this.description,
        coverImageUrl = this.coverImageUrl,
        rating = this.rating,
        genres = emptyList(),
        createdAt = this.createdAt,
        publishedYear = this.publishedYear,
        plan = this.plan,
        price = this.price,
        pageCount = this.pageCount,
        ageLimit = this.ageLimit,
        readUrl = this.readUrl
    )
}
