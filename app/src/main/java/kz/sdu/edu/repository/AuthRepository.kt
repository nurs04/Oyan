package kz.sdu.edu.repository

import kz.sdu.edu.models.AuthResponse
import kz.sdu.edu.network.NetworkResult

interface AuthRepository {

    suspend fun fetchCsrfToken() : NetworkResult<Unit>

    suspend fun register(
        username : String,
        email : String,
        password1 : String,
        password2: String,
    ): NetworkResult<AuthResponse>

    suspend fun login(
        identifier: String,
        password : String
    ): NetworkResult<AuthResponse>

    suspend fun logout()
    fun isLoggedIn(): Boolean
    fun setLoggedIn(value : Boolean)
    fun saveToken(token : String, cookies : String)
    fun setGenresSelected(value: Boolean)
    fun areGenresSelected(): Boolean
    fun getCsrfToken() : String?
    fun getCookies() : String?

}