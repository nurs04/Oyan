package kz.sdu.edu.models

import com.google.gson.annotations.SerializedName

data class CommentRequest(
    val content : String,
    val rate : Int
)

data class CommentsResponse(
    val status : String,
    @SerializedName("new_rating")
    val newRating : Float
)

data class CommentListResponse(
    val count : Int,
    val next: String?,
    val previous: String?,
    val results: List<CommentItem>
)
