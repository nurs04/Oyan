package kz.sdu.edu.models

data class User(
    val id: Int,
    val username: String,
    val email: String,
    val avatar: String?,
    val dateOfBirth: String?,
    val bio: String?,
    val preferredGenres: List<String>,
    val createdAt: String
)

