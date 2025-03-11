package kz.sdu.edu.data

import kz.sdu.edu.Util.RetrofitInterface
import kz.sdu.edu.models.AuthResponse
import kz.sdu.edu.models.GenresResponse
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.POST

interface ApiService{
    @GET("csrf/")
    suspend fun getCsrfToken(): Response<ResponseBody>

    @FormUrlEncoded
    @POST("signup/")
    suspend fun register(
        @HeaderMap headers: Map<String, String?>,
        @Field("username") username: String,
        @Field("email") email: String,
        @Field("password1") password1: String,
        @Field("password2") password2: String
    ) : AuthResponse

    @FormUrlEncoded
    @POST("login/")
    suspend fun login(
        @HeaderMap headers: Map<String, String?>,
        @Field("login") identifier : String,
        @Field("password") password : String
    ) : AuthResponse

    @GET("genres/")
    suspend fun genres(
        @HeaderMap headers: Map<String, String?>
    ) : Response<List<GenresResponse>>

    companion object{
        fun create(): ApiService{
            return RetrofitInterface.api
        }
    }
}