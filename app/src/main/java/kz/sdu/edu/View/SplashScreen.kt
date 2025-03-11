package kz.sdu.edu.View

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kz.sdu.edu.R
import kz.sdu.edu.ui.theme.PrimaryViolet

@Composable
fun SplashScreen(){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(PrimaryViolet),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(R.drawable.oyan_foreground),
            contentDescription = null,
            modifier = Modifier.size(150.dp)
        )
    }
}

@Preview
@Composable
fun FunPreview(){
    SplashScreen()
}