package kz.sdu.edu.data

import kz.sdu.edu.models.AuthResponse
import kz.sdu.edu.models.BookListResponse
import kz.sdu.edu.models.CommentListResponse
import kz.sdu.edu.models.CommentRequest
import kz.sdu.edu.models.CommentsResponse
import kz.sdu.edu.models.GenresResponse
import kz.sdu.edu.models.SelectedGenresRequest
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface ApiService{
    @GET("csrf/")
    suspend fun getCsrfToken(): Response<ResponseBody>

    @FormUrlEncoded
    @POST("signup/")
    suspend fun register(
        @Field("username") username: String,
        @Field("email") email: String,
        @Field("password1") password1: String,
        @Field("password2") password2: String
    ) : Response<AuthResponse>

    @FormUrlEncoded
    @POST("login/")
    suspend fun login(
        @Field("login") identifier : String,
        @Field("password") password : String
    ) : Response<AuthResponse>

    @GET("genres/")
    suspend fun genres() : Response<List<GenresResponse>>

    @POST("genres/")
    suspend fun saveSelectedGenres(
        @Body genres: SelectedGenresRequest
    ) : Response<Unit>

    @GET("newbooks/")
    suspend fun getNewBooks(
        @Query("page") page : Int
    ) : Response<BookListResponse>

    @GET("popularbooks/")
    suspend fun getPopularBooks(
        @Query("page") page : Int
    ): Response<BookListResponse>

    @GET("recommendedbooks/")
    suspend fun getRecommendationBooks(
        @Query("page") page : Int
    ) : Response<BookListResponse>

    @GET("books/{book_id}/comments/")
    suspend fun getComments(
        @Path("book_id") bookId: Int
    ): Response<CommentListResponse>

    @POST("books/{book_id}/comments/")
    suspend fun sendComments(
        @Path("book_id") bookId: Int,
        @Body commentRequest: CommentRequest
    ): Response<CommentsResponse>


}