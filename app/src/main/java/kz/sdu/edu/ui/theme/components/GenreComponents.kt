package kz.sdu.edu.ui.theme.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kz.sdu.edu.R
import kz.sdu.edu.models.GenresResponse
import kz.sdu.edu.ui.theme.DarkText
import kz.sdu.edu.ui.theme.PrimaryViolet
import kz.sdu.edu.ui.theme.PrimaryVioletLight


@Composable
fun GenreComponents(
    genres : List<GenresResponse>,
    selectedGenres : List<String>,
    searchQuery : String,
    onSearch : (String) -> Unit,
    onGenreSelected : (String) -> Unit,
) {

    Spacer(modifier = Modifier.height(8.dp))
    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
            .padding(16.dp)
    ) {
        SearchBar(
            value = searchQuery,
            onValueChanged = { query ->
                onSearch(query)
            },
            placeholder = stringResource(R.string.search_genre)
        )
        Spacer(modifier = Modifier.height(20.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(genres) { genre ->
                val isSelected = selectedGenres.contains(genre.genre)
                val isDisabled = selectedGenres.size >= 5 && !isSelected
                GenreChip(
                    text = genre.genre,
                    isSelected = isSelected,
                    isDisabled = isDisabled,
                    onClick = {onGenreSelected(genre.genre)}
                )
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        ContinueButton(
            text = stringResource(R.string.continue_b),
            onClicked = {  },
            color = ButtonDefaults.buttonColors(
                contentColor = Color.White,
                containerColor = PrimaryViolet
            )
        )
        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Composable
fun GenreChip(
    text: String,
    isSelected: Boolean,
    isDisabled: Boolean,
    onClick: () -> Unit
) {
    val backgroundColor = when {
        isSelected -> PrimaryViolet
        isDisabled -> Color.Black
        else -> PrimaryVioletLight
    }
    val textColor = if (isSelected) Color.White else DarkText

    Button(
        onClick = onClick,
        enabled = !isDisabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor,
            contentColor = textColor,
            disabledContainerColor = PrimaryVioletLight.copy(alpha = 0.5f),
            disabledContentColor = DarkText.copy(alpha = 0.5f)
        ),
        shape = RoundedCornerShape(10.dp),
        contentPadding = PaddingValues(vertical = 10.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium
        )
    }
}