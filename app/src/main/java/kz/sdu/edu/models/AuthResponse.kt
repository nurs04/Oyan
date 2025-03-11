package kz.sdu.edu.models

data class AuthResponse(
    val status : String,
    val username : String,
    val preferred_genres : Boolean
)
