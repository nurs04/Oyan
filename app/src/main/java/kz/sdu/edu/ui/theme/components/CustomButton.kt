package kz.sdu.edu.ui.theme.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import kz.sdu.edu.R
import kz.sdu.edu.ui.theme.OpenSans
import kz.sdu.edu.ui.theme.PrimaryViolet
import kz.sdu.edu.ui.theme.PrimaryVioletLight




@Composable
fun ActionButton(
     modifier : Modifier = Modifier,
     text : String,
     onClicked: () -> Unit,
     color: ButtonColors,
){
    Button(
        modifier = modifier
            .width(250.dp)
            .height(55.dp),
        onClick = onClicked,
        colors = color,
        shape = RoundedCornerShape(10.dp)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

    }
}

@Composable
fun ContinueButton(
    modifier : Modifier = Modifier,
    text : String,
    onClicked: () -> Unit,
    color: ButtonColors,
){
    Button(
        modifier = modifier
            .fillMaxWidth()
            .height(55.dp),
        onClick = onClicked,
        colors = color,
        shape = RoundedCornerShape(10.dp)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
        )

    }
}

@Composable
fun ChooseButton(
    modifier : Modifier = Modifier,
    text : String,
    onClicked: () -> Unit,
    color: ButtonColors,
){
    Button(
        modifier = modifier
            .height(45.dp),
        onClick = onClicked,
        colors = color,
        shape = RoundedCornerShape(10.dp),
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Normal,
        )

    }
}

@Composable
fun BackButton(
    img : Int,
    onClicked: () -> Unit
){
    Box(
        modifier = Modifier
            .size(50.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(PrimaryVioletLight)
            .clickable { onClicked() },
        contentAlignment = Alignment.Center,
    ) {
        Image(
            painter = painterResource(img),
            contentDescription = "Icon",
            modifier = Modifier
        )
    }
}