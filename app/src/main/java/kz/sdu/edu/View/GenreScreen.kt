package kz.sdu.edu.View

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import kz.sdu.edu.ViewModel.GenreViewModel
import kz.sdu.edu.ui.theme.components.GenreComponents
import org.koin.androidx.compose.koinViewModel

@Composable
fun GenreScreen(
    genreViewModel: GenreViewModel = koinViewModel(),
    onContinue : () -> Unit
){
    val genres by genreViewModel.filteredGenres.collectAsState()
    val selectedGenres by genreViewModel.selectedGenres.collectAsState()
    var searchQuery by remember { mutableStateOf("") }


    LaunchedEffect(Unit){
        genreViewModel.fetchGenres()
    }

    GenreComponents(
        genres = genres,
        searchQuery = searchQuery,
        selectedGenres = selectedGenres,
        onSearch = {
            searchQuery = it
            genreViewModel.onSearchQueryChanged(it)
        },
        onGenreSelected = {genre ->
            genreViewModel.toogleGenreSelected(genre)
        },
        onContinue = { genreViewModel.saveSelectedGenres { onContinue() } }
    )

}