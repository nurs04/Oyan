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

class GenreViewModel(
    private val repository: AuthRepository
) : ViewModel() {
    private val _genres = MutableStateFlow<List<GenresResponse>>(emptyList())
    private val _searchQuery = MutableStateFlow("")
    private val _selectedGenres = MutableStateFlow<List<String>>(emptyList())

    val selectedGenres : StateFlow<List<String>> = _selectedGenres.asStateFlow()

    val filteredGenres = combine(_genres, _searchQuery, _selectedGenres){ genres, query, selected ->
        if (query.isBlank()){
            val initial = genres.take(10)
            val extra = genres.filter { selected.contains(it.genre) && !initial.contains(it) }
            initial + extra
        }else{
            genres.filter { it.genre.startsWith(query, ignoreCase = true) }
        }
    }.stateIn(viewModelScope, SharingStarted.Lazily , emptyList())

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

    fun toogleGenreSelected(genre: String){
        var current = _selectedGenres.value.toMutableList()
        if(current.contains(genre)){
            current.remove(genre)
        }else{
            if(current.size < 5){
                current.add(genre)
            }
        }
        _selectedGenres.value = current
    }
}