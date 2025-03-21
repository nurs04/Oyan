package kz.sdu.edu.models

import com.google.gson.annotations.SerializedName

data class SelectedGenresRequest(
    @SerializedName("genres")
    val genres: List<Int>
)
