package kz.sdu.edu.ui.theme.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import kz.sdu.edu.R
import kz.sdu.edu.models.BookItem
import kz.sdu.edu.ui.theme.DarkText
import kz.sdu.edu.ui.theme.OpenSans
import kz.sdu.edu.ui.theme.PrimaryViolet
import kz.sdu.edu.ui.theme.PrimaryVioletLight

@Composable
fun CategorySection(
    title: String,
    navController: NavHostController,
    books: List<BookItem>,
    onSeeAllClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = stringResource(R.string.see_all),
                style = MaterialTheme.typography.bodyMedium,
                color = PrimaryViolet,
                modifier = Modifier.clickable { onSeeAllClick() }
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        LazyRow(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            items(books) { book ->
                BooksCard(
                    book = book,
                    navController = navController,
                    modifier = Modifier.aspectRatio(2f / 3f)
                )
            }
        }
    }
}


@Composable
fun BooksCard(
    book : BookItem,
    navController: NavHostController,
    modifier: Modifier = Modifier
){
    Box(
        modifier = modifier
            .widthIn(min = 75.dp, max = 115.dp)
            .clip(RoundedCornerShape(8.dp))
            .clickable { navController.navigate("book_details/${book.id}")  }
    ) {
        AsyncImage(
            model = book.coverImageUrl,
            contentDescription = book.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
fun TopButtons(
    onClickBooks : () -> Unit,
    onClickAudio : () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
    ) {
        ChooseButton(
            text = stringResource(R.string.book),
            onClicked = onClickBooks,
            color = ButtonDefaults.buttonColors(
                contentColor = Color.White,
                containerColor = PrimaryViolet
            ),
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.width(12.dp))
        ChooseButton(
            text = stringResource(R.string.audio),
            onClicked = onClickAudio,
            color = ButtonDefaults.buttonColors(
                contentColor = DarkText,
                containerColor = PrimaryVioletLight
            ),
            modifier = Modifier.weight(1f)
        )
    }

}

@Composable
fun TopBarStories(
    onImageClick: () -> Unit
){
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        TopContainer(
            image = R.drawable.daily,
            text = R.string.daily,
            onImageClick = onImageClick,
            modifier = Modifier.weight(1f).aspectRatio(1f)

        )
        TopContainer(
            image = R.drawable.comp,
            text = R.string.compt,
            onImageClick = onImageClick,
            modifier = Modifier.weight(1f).aspectRatio(1f)
        )
        TopContainer(
            image = R.drawable.hit,
            text = R.string.hit,
            onImageClick = onImageClick,
            modifier = Modifier.weight(1f).aspectRatio(1f)

        )

    }
}

@Composable
private fun TopContainer(
    image : Int,
    text : Int,
    modifier: Modifier = Modifier,
    onImageClick : () -> Unit
){
    Box(
        modifier = modifier.clickable { onImageClick() },
        contentAlignment = Alignment.BottomStart,
    ){
        Image(
            painter = painterResource(image),
            contentDescription = "",
            modifier = Modifier
                .fillMaxSize()
        )
        Text(
            text = stringResource(text),
            fontFamily = OpenSans,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            color = Color.White,
            modifier = Modifier.padding(start = 12.dp, bottom = 14.dp)
        )
    }
}