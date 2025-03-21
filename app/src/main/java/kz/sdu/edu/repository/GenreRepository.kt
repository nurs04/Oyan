package kz.sdu.edu.repository

import kz.sdu.edu.models.GenresResponse

interface GenreRepository {
    suspend fun fetchGenres(): List<GenresResponse>?
    suspend fun saveSelectedGenresToServer(genreIds: List<Int>): Boolean
    fun getSelectedGenres(): Set<String>
    fun saveSelectedGenres(genres: Set<String>)
}