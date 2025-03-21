package kz.sdu.edu.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "books")
data class BookEntity(
    @PrimaryKey val id: Int,
    val category: String,
    val title: String,
    val author: String,
    val description: String,
    @SerializedName("cover_image_url")
    val coverImageUrl: String,
    val rating: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("published_year")
    val publishedYear: Int,
    val plan: String,
    val price: String,
    @SerializedName("page_count")
    val pageCount : Int,
    @SerializedName("age_limit")
    val ageLimit : String,
    @SerializedName("read_url")
    val readUrl: String
)
