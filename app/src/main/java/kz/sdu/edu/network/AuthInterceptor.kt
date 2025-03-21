package kz.sdu.edu.network

import android.content.SharedPreferences
import android.util.Log

import okhttp3.Interceptor
import okhttp3.Response
import okio.Buffer

class AuthInterceptor(private val sharedPreferences: SharedPreferences) : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val originalRequest = chain.request()
            val url = originalRequest.url.toString()
            val requestBuilder = originalRequest.newBuilder()

            /* 1. Получение токенов */
            val cookies = sharedPreferences.getString("cookies", "").orEmpty()
            val csrfToken = sharedPreferences.getString("csrfToken", "").orEmpty()
            val contentType = if(url.endsWith("login/") || url.endsWith("signup/")){
                "application/x-www-form-urlencoded"
            } else  {
                "application/json"
            }
            // 2. Логирование для дебага
            Log.d("AUTH", "Using CSRF: ${csrfToken.take(5)}...")
            Log.d("AUTH", "Using Cookies: ${cookies.take(20)}...")


            // 3. Добавление заголовков
            requestBuilder
                .addHeader("Content-Type", contentType)
                .addHeader("X-CSRFToken", csrfToken)
                .addHeader("Cookie", cookies)

            // 4. Логирование тела запроса
            val request = requestBuilder.build()
            request.body?.let { body ->
                val buffer = Buffer()
                body.writeTo(buffer)
                Log.d("AUTH", "Request Body: ${buffer.readUtf8()}")
            }

            val response = chain.proceed(request)

            return response
        }
    }
