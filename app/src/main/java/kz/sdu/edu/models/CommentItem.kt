package kz.sdu.edu.models

import com.google.gson.annotations.SerializedName

data class CommentItem(
    val id : Int,
    val user : String,
    val content : String,
    val rate : Int,
    @SerializedName("created_at")
    val createdAt : String
)
