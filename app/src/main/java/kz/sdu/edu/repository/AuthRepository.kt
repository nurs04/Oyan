package kz.sdu.edu.repository

import android.content.SharedPreferences
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import kz.sdu.edu.data.ApiService
import kz.sdu.edu.models.AuthResponse
import kz.sdu.edu.models.GenresResponse


class AuthRepository(
    private val api: ApiService,
    private val sharedPreferences: SharedPreferences
) {

    private var csrfToken: String? = null
    private var cookies: String? = null

    init{
        csrfToken = sharedPreferences.getString("csrfToken", null)
        cookies = sharedPreferences.getString("cookies", null)

    }

    private suspend fun fetchCsrfToken() = withContext(Dispatchers.IO) {
        try {
            val response = api.getCsrfToken()
            if (response.isSuccessful) {
                val headers = response.headers()
                val cookieHeader = headers["Set-Cookie"]
                cookies = cookieHeader
                    ?.split(";")
                    ?.firstOrNull { it.trim().startsWith("csrftoken=") }
                    ?.trim()
                Log.d("GENRE", "COOKIE $cookies")
                csrfToken = cookies?.substringAfter("csrftoken=")
                if (csrfToken != null && cookies != null){
                    saveToken(csrfToken!!, cookies!!)
                }
                Log.d("CSRF_TOKEN", "Полученный CSRF-токен: $csrfToken, Cookies: $cookies")
            } else {
                Log.e("CSRF_TOKEN", "Ошибка получения CSRF-токена: ${response.code()}")
            }
        } catch (e: Exception) {
            Log.e("CSRF_TOKEN", "Ошибка во время запроса CSRF-токена", e)
        }
    }

    private fun saveToken(token: String, cookies : String){
        sharedPreferences.edit()
            .putString("csrfToken", token)
            .putString("cookies", cookies)
            .apply()
    }

    suspend fun register(username: String, email: String, password1: String, password2: String): AuthResponse? {
        if (csrfToken == null || cookies == null) {
            Log.e("REGISTER", "CSRF-токен отсутствует, получаем новый")
            fetchCsrfToken() // get a token
            delay(100)
        }
        csrfToken ?: return null
        cookies ?: return null

        val headers = mapOf(
            "Content-Type" to "application/x-www-form-urlencoded",
            "X-CSRFToken" to csrfToken!!,
            "Cookie" to cookies!!
        )

        Log.d("REGISTER", "Register with token: $csrfToken")

        return try {
            val response = api.register(headers, username, email, password1, password2)
            Log.d("REGISTER", "Server Response: $response")
            response
        } catch (e: Exception) {
            Log.e("REGISTER", "Error SignUp: ", e)
            null
        }
    }

    suspend fun fetchGenres(): List<GenresResponse>? {
        if (csrfToken == null || cookies == null) {
            Log.e("GENRES", "CSRF-токен отсутствует, получаем новый")
            fetchCsrfToken()
        }
        if (csrfToken == null || cookies == null) {
            Log.e("GENRES", "Не удалось получить CSRF-токен")
            return null
        }

        val headers = mapOf(
            "X-CSRFToken" to csrfToken!!,
            "Cookie" to cookies!!
        )

        Log.d("GENRES", "Отправляем запрос с заголовками:{$csrfToken $cookies}")

        return try {
            val response = api.genres(headers)
            Log.d("GENRES", "Код ответа: ${response.code()}, Тело: ${response.body()}")

            if (response.isSuccessful) {
                response.body()
            } else {
                Log.e("GENRES", "Ошибка загрузки жанров: ${response.code()} ${response.message()}")
                null
            }
        } catch (e: Exception) {
            Log.e("GENRES", "Ошибка при запросе жанров", e)
            null
        }
    }

    suspend fun login(
        identifier: String,
        password: String): AuthResponse? {
        if (csrfToken == null || cookies == null) {
            Log.e("LOGIN", "CSRF-токен отсутствует, получаем новый")
            fetchCsrfToken()
        }
        csrfToken ?: return null
        cookies ?: return null

        val headers = mapOf(
            "Content-Type" to "application/x-www-form-urlencoded",
            "X-CSRFToken" to csrfToken!!,
            "Cookie" to cookies!!
        )

        Log.d("LOGIN", "Отправка запроса входа с header: $headers")
        return try {
            val response = api.login(headers, identifier, password)
            Log.d("LOGIN", "Ответ сервера: $response")
            response
        } catch (e: Exception) {
            Log.e("LOGIN", "Ошибка входа", e)
            null
        }
    }
}
