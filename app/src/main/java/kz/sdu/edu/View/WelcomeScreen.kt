package kz.sdu.edu.View

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kz.sdu.edu.R
import kz.sdu.edu.ui.theme.DarkText
import kz.sdu.edu.ui.theme.GrayText
import kz.sdu.edu.ui.theme.PrimaryViolet
import kz.sdu.edu.ui.theme.components.ActionButton


@Composable
fun WelcomeScreen(
    modifier: Modifier = Modifier,
    navController: NavController
){
    var showBottomSheet by remember { mutableStateOf(false) }
    Box(
        modifier = modifier
            .fillMaxSize()
            .systemBarsPadding(),
        contentAlignment = Alignment.Center
    ){
        Column (
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Image(
                painter = painterResource(R.drawable.greeting_foreground),
                contentDescription = null,
                modifier = modifier.size(320.dp)
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = stringResource(R.string.welcome_text),
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = DarkText
            )
            Spacer(modifier = Modifier.height(14.dp))
            Text(
                text = stringResource(R.string.welcom_subset_text),
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Normal,
                color = GrayText

            )
            Spacer(modifier = modifier.height(30.dp))
            ActionButton(
                text = stringResource(R.string.get_started),
                onClicked = {showBottomSheet = true},
                color = ButtonDefaults.buttonColors(
                    contentColor = Color.White,
                    containerColor = PrimaryViolet
                )
            )
        }
    }
    if(showBottomSheet){
        SignBottomSheet(
            onDismiss = { showBottomSheet = false },
            onNavigateSignIn = {
                navController.navigate("genres") {
                    popUpTo("welcome") { inclusive = true }
                }
            }
        )
    }

}
