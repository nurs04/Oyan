package kz.sdu.edu.View

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import kotlinx.coroutines.delay
import kz.sdu.edu.R
import kz.sdu.edu.ViewModel.MainViewModel
import kz.sdu.edu.ui.theme.components.CategorySection
import kz.sdu.edu.ui.theme.components.SearchBar
import kz.sdu.edu.ui.theme.components.TopBarStories
import kz.sdu.edu.ui.theme.components.TopButtons
import org.koin.androidx.compose.koinViewModel

@Composable
fun MainScreen(
    navController: NavHostController,
    mainViewModel: MainViewModel = koinViewModel()
) {
    val recommendedBooks by mainViewModel.recommendedBooks.collectAsState()
    val popularBooks by mainViewModel.popularBooks.collectAsState()
    val newBooks by mainViewModel.newBooks.collectAsState()
    var searchQuery by remember { mutableStateOf("") }
    var debounceQuery by remember { mutableStateOf("") }
    val listState = rememberLazyListState()

    LaunchedEffect(searchQuery) {
        delay(300)
        debounceQuery = searchQuery
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        // Search
        SearchBar(
            value = searchQuery,
            onValueChanged = {searchQuery = it},
            placeholder = stringResource(R.string.search_main)
        )
        Spacer(modifier = Modifier.height(14.dp))
        //Main Container
        LazyColumn (
            state = listState,
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(14.dp),

        ){

            item{
                //Book and Audio buttons
                TopButtons(
                    onClickBooks = {},
                    onClickAudio = {}
                )
            }

            item {
                // Stories : [Daily excerpt, Competition, Hit]
                TopBarStories(
                    onImageClick = {}
                )
            }
            item {
                // Recommendation : in screen shows only 3
                CategorySection(
                    title = stringResource(R.string.recomendation),
                    navController = navController,
                    onSeeAllClick = { navController.navigate("seeAll/recommended") },
                    books = recommendedBooks
                )
            }
            item {
                // Popular Books : in screen shows only 3
                CategorySection(
                    title = stringResource(R.string.popular_books),
                    navController = navController,
                    onSeeAllClick = { navController.navigate("seeAll/popular") },
                    books = popularBooks
                )
            }
            item {
                // New Books : in screen shows only 3
                CategorySection(
                    title = stringResource(R.string.new_books),
                    navController = navController,
                    onSeeAllClick = { navController.navigate("seeAll/new") },
                    books = newBooks
                )
            }
        }
        Spacer(modifier = Modifier.height(24.dp))
    }
}