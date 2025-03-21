package kz.sdu.edu.repository

import android.content.SharedPreferences
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kz.sdu.edu.Util.CookieHelper
import kz.sdu.edu.data.ApiService
import kz.sdu.edu.models.AuthResponse
import kz.sdu.edu.network.NetworkResult


class AuthRepositoryImpl(
    private val api: ApiService,
    private val sharedPreferences: SharedPreferences
) : AuthRepository {

    private var csrfToken: String? = sharedPreferences.getString("csrfToken", null)
    private var cookies: String? = sharedPreferences.getString("cookies", null)


    override suspend fun fetchCsrfToken(): NetworkResult<Unit> = withContext(Dispatchers.IO) {
        try {
            val response = api.getCsrfToken()
            if (response.isSuccessful) {
                val newCookies = response.headers()["Set-Cookie"] ?: ""
                if (newCookies.contains("csrftoken=")) {
                    val currentCookies = sharedPreferences.getString("cookies", "").orEmpty()
                    val mergedCookies = CookieHelper.mergeCookies(currentCookies, newCookies)
                    cookies = mergedCookies.ifEmpty { newCookies }
                    csrfToken = CookieHelper.extractCsrfToken(mergedCookies.ifEmpty { newCookies })
                    if (csrfToken != null && cookies != null) {
                        saveToken(csrfToken!!, cookies!!)
                        return@withContext NetworkResult.Success(Unit)
                    } else {
                        return@withContext NetworkResult.Error("CSRF token extraction failed")
                    }
                } else {
                    Log.d("CSRF_TOKEN", "No valid csrftoken in newCookies: $newCookies")
                    return@withContext NetworkResult.Error("No valid CSRF token in response")
                }
            } else {
                return@withContext NetworkResult.Error("HTTP error: ${response.code()}")
            }
        } catch (e: Exception) {
            Log.e("CSRF_TOKEN", "Error fetching CSRF token", e)
            return@withContext NetworkResult.Error("Exception fetching CSRF token", e)
        }
    }

    override fun saveToken(token: String, cookies : String){
        sharedPreferences.edit()
            .putString("csrfToken", token)
            .putString("cookies", cookies)
            .apply()
    }

    override suspend fun register(username: String, email: String, password1: String, password2: String): NetworkResult<AuthResponse> = withContext(Dispatchers.IO) {
        when (val csrfResult = fetchCsrfToken()) {
            is NetworkResult.Success -> Unit // proceed
            is NetworkResult.Error -> return@withContext NetworkResult.Error("CSRF token fetch failed", csrfResult.exception)
        }
        try {
            val response = api.register(username, email, password1, password2)
            if (response.isSuccessful) {
                response.headers()["Set-Cookie"]?.let { newCookies ->
                    val currentCookies = sharedPreferences.getString("cookies", "").orEmpty()
                    val mergedCookies = CookieHelper.mergeCookies(currentCookies, newCookies)
                    cookies = mergedCookies.ifEmpty { newCookies }
                    sharedPreferences.edit().putString("cookies", cookies).apply()
                }
                val body = response.body()
                if (body?.status == "success") {
                    setLoggedIn(true)
                }
                if (body != null) {
                    return@withContext NetworkResult.Success(body)
                } else {
                    return@withContext NetworkResult.Error("Empty registration response")
                }
            } else {
                return@withContext NetworkResult.Error("HTTP error: ${response.code()}")
            }
        } catch (e: Exception) {
            Log.e("REGISTER", "Network error", e)
            return@withContext NetworkResult.Error("Exception during registration", e)
        }
    }

    override suspend fun login(identifier: String, password: String): NetworkResult<AuthResponse> = withContext(Dispatchers.IO) {
        if (csrfToken.isNullOrEmpty()) {
            when (val csrfResult = fetchCsrfToken()) {
                is NetworkResult.Success -> Unit
                is NetworkResult.Error -> return@withContext NetworkResult.Error("CSRF token fetch failed", csrfResult.exception)
            }
        }
        try {
            val response = api.login(identifier, password)
            if (response.isSuccessful) {
                response.headers()["Set-Cookie"]?.let { newCookies ->
                    // Only update if valid cookie values are returned.
                    if (newCookies.contains("sessionid=") || newCookies.contains("csrftoken=")) {
                        val currentCookies = sharedPreferences.getString("cookies", "").orEmpty()
                        val mergedCookies = CookieHelper.mergeCookies(currentCookies, newCookies)
                        cookies = mergedCookies.ifEmpty { newCookies }
                        sharedPreferences.edit().putString("cookies", cookies).apply()
                    } else {
                        Log.d("LOGIN", "Received invalid cookie header: $newCookies")
                    }
                }
                val body = response.body()
                if (body?.status == "success") {
                    setLoggedIn(true)
                }
                if (body != null) {
                    return@withContext NetworkResult.Success(body)
                } else {
                    return@withContext NetworkResult.Error("Empty login response")
                }
            } else {
                return@withContext NetworkResult.Error("HTTP error: ${response.code()}")
            }
        } catch (e: Exception) {
            return@withContext NetworkResult.Error("Exception during login", e)
        }
    }

    override suspend fun logout() {
        sharedPreferences.edit()
            .clear()
            .apply()
        fetchCsrfToken()
    }

    override fun isLoggedIn() = sharedPreferences.getBoolean("is_logged_in", false)

    override fun setLoggedIn(value: Boolean) {
        sharedPreferences.edit().putBoolean("is_logged_in", value).apply()
    }

    override fun setGenresSelected(value: Boolean) {
        sharedPreferences.edit().putBoolean("genres_selected", value).apply()
    }
    override fun areGenresSelected() = sharedPreferences.getBoolean("genres_selected", false)

    override fun getCsrfToken() = csrfToken
    override fun getCookies() = cookies
}
