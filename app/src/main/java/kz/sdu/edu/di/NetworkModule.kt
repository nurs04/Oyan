package kz.sdu.edu.di

import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kz.sdu.edu.Util.Util
import kz.sdu.edu.data.ApiService
import kz.sdu.edu.network.AuthInterceptor
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    single { AuthInterceptor(get()) }
    single { provideOkHttpClient(get()) }
    single { provideRetrofit(get()) }
    single { get<Retrofit>().create(ApiService::class.java) }
}

private fun provideOkHttpClient(sharedPreferences: SharedPreferences): OkHttpClient {
    return OkHttpClient.Builder()
        .addInterceptor(AuthInterceptor(sharedPreferences))
        .build()
}

private fun provideGson(): Gson = GsonBuilder()
    .setLenient()
    .create()

private fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl(Util.BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(provideGson()))
        .build()
}