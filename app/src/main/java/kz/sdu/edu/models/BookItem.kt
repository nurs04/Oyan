package kz.sdu.edu.models

import com.google.gson.annotations.SerializedName

data class BookItem(
    val id: Int,
    val title: String,
    val author: String,
    val description: String,
    @SerializedName("cover_image_url")
    val coverImageUrl: String,
    val rating: String,
    val genres: List<Int>,
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

