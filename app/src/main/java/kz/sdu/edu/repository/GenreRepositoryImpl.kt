package kz.sdu.edu.repository

import android.content.SharedPreferences
import android.util.Log
import kz.sdu.edu.data.ApiService
import kz.sdu.edu.models.GenresResponse
import kz.sdu.edu.models.SelectedGenresRequest

class GenreRepositoryImpl(
    private val api: ApiService,
    private val sharedPreferences: SharedPreferences
) : GenreRepository {
    override suspend fun fetchGenres(): List<GenresResponse>? {
        return try {
            val response = api.genres()
            if (response.isSuccessful) {
                response.body()
            } else {
                Log.e("GenreRepository", "Error fetching genres: ${response.code()} ${response.message()}")
                null
            }
        } catch (e: Exception) {
            Log.e("GenreRepository", "Exception fetching genres", e)
            null
        }
    }

    override suspend fun saveSelectedGenresToServer(genreIds: List<Int>): Boolean {
        return try {
            val response = api.saveSelectedGenres(SelectedGenresRequest(genreIds))
            response.isSuccessful
        } catch (e: Exception) {
            Log.e("GenreRepository", "Error saving selected genres", e)
            false
        }
    }

    override fun saveSelectedGenres(genres: Set<String>) {
        sharedPreferences.edit()
            .putStringSet("selected_genres", genres)
            .apply()
    }

    override fun getSelectedGenres(): Set<String> {
        return sharedPreferences.getStringSet("selected_genres", emptySet()) ?: emptySet()
    }
}