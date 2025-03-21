package kz.sdu.edu.ViewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kz.sdu.edu.models.GenresResponse
import kz.sdu.edu.repository.AuthRepository
import kz.sdu.edu.repository.AuthRepositoryImpl
import kz.sdu.edu.repository.GenreRepository

class GenreViewModel(
    val repository: GenreRepository
) : ViewModel() {
    private val _genres = MutableStateFlow<List<GenresResponse>>(emptyList())
    private val _searchQuery = MutableStateFlow("")
    private val _selectedGenres = MutableStateFlow<List<String>>(emptyList())
    val selectedGenres : StateFlow<List<String>> = _selectedGenres.asStateFlow()
    val filteredGenres = combine(_genres, _searchQuery, _selectedGenres){ genres, query, selected ->
        if (query.isBlank()){
            val initial = genres.take(10)
            val extra = genres.filter { selected.contains(it.id.toString()) && !initial.contains(it) }
            initial + extra
        }else{
            genres.filter { it.genre.startsWith(query, ignoreCase = true) }
        }
    }.stateIn(viewModelScope, SharingStarted.Lazily , emptyList())

    init {
        val savedGenres = repository.getSelectedGenres()
        Log.d("GENRES_DEBUG", "Загружены сохраненные ID: $savedGenres")
        _selectedGenres.value = savedGenres.toList()

    }
    fun fetchGenres(){
        viewModelScope.launch {
            try {
                val genreList = repository.fetchGenres()
                _genres.value = genreList ?: emptyList()
            } catch (e: Exception) {
                Log.e("GENRES", "Ошибка при загрузке жанров", e)
            }
        }
    }
    fun onSearchQueryChanged(query: String){
        _searchQuery.value = query
    }

    fun toogleGenreSelected(genreId: Int){
        val genreIdStr = genreId.toString()
        Log.d("GENRES_DEBUG", "Нажат жанр ID: $genreIdStr")

        val current = _selectedGenres.value.toMutableList()
        if(current.contains(genreIdStr)){
            current.remove(genreIdStr)
            Log.d("GENRES_DEBUG", "Удален жанр ID: $genreIdStr")
        }else{
            if(current.size < 5){
                current.add(genreIdStr)
                Log.d("GENRES_DEBUG", "Добавлен жанр ID: $genreIdStr")
            }
        }
        _selectedGenres.value = current
        repository.saveSelectedGenres(current.toSet())
    }

    fun saveSelectedGenres(onSuccess: () -> Unit) {
        viewModelScope.launch {
            Log.d("GENRES_DEBUG", "Сохранение жанров. Выбранные ID: ${repository.getSelectedGenres()}")
            val genreIds = repository.getSelectedGenres().mapNotNull{ it.toIntOrNull() }
            if (genreIds.isEmpty()) {
                Log.e("GENRES", "Нет выбранных жанров")
                return@launch
            }
            val success = repository.saveSelectedGenresToServer(genreIds)
            Log.d("GENRES_DEBUG", "Результат сохранения: $success")
            if (success) {
                onSuccess()
            }
        }
    }
}
