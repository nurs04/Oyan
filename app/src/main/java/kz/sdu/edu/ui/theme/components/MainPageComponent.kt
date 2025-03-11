package kz.sdu.edu.ui.theme.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kz.sdu.edu.R
import kz.sdu.edu.ui.theme.DarkText
import kz.sdu.edu.ui.theme.PrimaryViolet
import kz.sdu.edu.ui.theme.PrimaryVioletLight

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MainPageComponent() {
    var searchQuery by remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
            .padding(16.dp)
    ){
        SearchBar(
            value = searchQuery,
            onValueChanged = {},
            placeholder = stringResource(R.string.search_main)
        )
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            ChooseButton(
                text = stringResource(R.string.book),
                onClicked = {},
                color = ButtonDefaults.buttonColors(
                    contentColor = Color.White,
                    containerColor = PrimaryViolet
                )
            )
            Spacer(modifier = Modifier.width(10.dp))
            ChooseButton(
                text = stringResource(R.string.audio),
                onClicked = {},
                color = ButtonDefaults.buttonColors(
                    contentColor = DarkText,
                    containerColor = PrimaryVioletLight
                )
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
        TopBarStories()
        Spacer(modifier = Modifier.height(24.dp))
        MainBookContainer()
    }
}