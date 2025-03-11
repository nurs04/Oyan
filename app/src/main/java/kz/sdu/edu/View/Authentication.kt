package kz.sdu.edu.View

import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetValue
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import kz.sdu.edu.ui.theme.components.GenreComponents

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
        onDismissRequest = { onDismiss() },
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

