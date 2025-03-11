package kz.sdu.edu.View

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import kz.sdu.edu.R
import kz.sdu.edu.ui.theme.components.CustomCard

@Composable
fun ContinueScreen(
    onContinueWithEmail : () -> Unit
){
    Column (
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        CustomCard(
            icon = R.drawable.google_foreground,
            text = stringResource(R.string.continue_with_google),
            onClick = {}
        )
        Spacer(modifier = Modifier.height(20.dp))
        CustomCard(
            icon = R.drawable.email_foreground,
            text = stringResource(R.string.continue_with_email),
            onClick = {onContinueWithEmail()}
        )
        Spacer(modifier = Modifier.height(30.dp))
    }
}