package kz.sdu.edu.View

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignBottomSheet(
    onDismiss : () -> Unit,
    onNavigateSignIn : () -> Unit
){
    var currentScreen by remember { mutableStateOf<Screens>(Screens.Continue)}
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    LaunchedEffect(sheetState) {
        sheetState.show()
    }
    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = {
            onDismiss()
            currentScreen = Screens.Continue },
        shape = RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp),
        containerColor = Color.White,
    ) {
        when(currentScreen){
            is Screens.Continue -> {
                ContinueScreen(onContinueWithEmail = {currentScreen = Screens.SignIn})
            }
            is Screens.SignIn -> {
                SignInScreen(
                    onSignIn = {
                        onDismiss()
                        onNavigateSignIn()
                    },
                    onSignUp = {currentScreen = Screens.SignUp},
                )
            }
            is Screens.SignUp -> {
                SignUpScreen(onNavigateToSignIn = {currentScreen = Screens.SignIn})
            }
        }
    }
}

sealed class Screens{
    object Continue: Screens()
    object SignIn: Screens()
    object SignUp: Screens()
}

