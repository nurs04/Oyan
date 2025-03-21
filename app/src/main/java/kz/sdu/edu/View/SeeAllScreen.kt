package kz.sdu.edu.View

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kz.sdu.edu.R
import kz.sdu.edu.ViewModel.SeeAllViewModel
import kz.sdu.edu.ui.theme.DarkText
import kz.sdu.edu.ui.theme.PrimaryViolet
import kz.sdu.edu.ui.theme.components.BooksCard
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SeeAllScreen(
    navController: NavHostController,
    category: String,
    seeAllViewModel: SeeAllViewModel = koinViewModel()
) {
    val uiState by seeAllViewModel.uiState.collectAsState()
    val listState = uiState.listState

    LaunchedEffect(category) {
        seeAllViewModel.setCategory(category)
    }
    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo }
            .collect { visibleItems ->
                if (visibleItems.isNotEmpty() &&
                    visibleItems.last().index >= uiState.books.size - 5
                ) {
                    seeAllViewModel.loadNextPage()
                }
            }
    }

    Scaffold(
        containerColor = Color.White,
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    titleContentColor = DarkText,
                    containerColor = Color.White),
                title = { Text(text = getTopBarCategory(category), style = MaterialTheme.typography.titleMedium) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(painterResource(R.drawable.back_arrow), contentDescription = "Back", tint = DarkText)
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                state = listState,
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                items(uiState.books) { book ->
                    BooksCard(
                        book = book,
                        navController = navController,
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(0.8f)
                    )
                }

                if (uiState.isLoading) {
                    item(span = { GridItemSpan(3) }) {
                        CircularProgressIndicator(
                            color = PrimaryViolet,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                                .wrapContentWidth(Alignment.CenterHorizontally)
                        )
                    }
                }
            }
        }
    }
}

fun getTopBarCategory(category : String) : String{
    when(category){
        "recommended" -> return "Recommendation"
        "popular" -> return "Popular books"
        "new" -> return "New books"
    }
    return ""
}