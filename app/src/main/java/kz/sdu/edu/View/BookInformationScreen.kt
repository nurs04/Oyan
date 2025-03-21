package kz.sdu.edu.View

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import kz.sdu.edu.ViewModel.MainViewModel
import kz.sdu.edu.ui.theme.components.BookInformationComponents
import org.koin.androidx.compose.koinViewModel

@Composable
fun BookInformationScreen(
    navController: NavHostController,
    bookId : Int,
    viewModel: MainViewModel = koinViewModel()
){
    val book by viewModel.bookDetails.collectAsState()

    LaunchedEffect(bookId) {
        viewModel.loadBookDetails(bookId)
    }

    book?.let {
        BookInformationComponents(
            navController = navController,
            book = it,
            onReadBook = {}
        )

    }
}