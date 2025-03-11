package kz.sdu.edu.View

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalView
import kz.sdu.edu.Util.Validator
import kz.sdu.edu.ViewModel.AuthViewModel
import kz.sdu.edu.ViewModel.GenreViewModel
import kz.sdu.edu.ui.theme.components.SignInComponents
import org.koin.androidx.compose.koinViewModel


@Composable
fun SignInScreen(
    authViewModel: AuthViewModel = koinViewModel(),
    genreViewModel: GenreViewModel = koinViewModel(),
    onSignIn : () -> Unit,
    onSignUp : () -> Unit,
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var identifierError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }
    var hasClickedSignIn by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current
    val view = LocalView.current
    val authState by authViewModel.authState.collectAsState()


    LaunchedEffect(authState, hasClickedSignIn) {
        authState?.let { response ->
            if (hasClickedSignIn && response.status == "success") {
                genreViewModel.fetchGenres()
                onSignIn()
            } else if (response.status == "error") {
                identifierError = "Invalid credentials"
                passwordError = "Invalid credentials"
            } else {
                Log.e("SignIn", "Ошибка входа: ${response.status}")
            }
        }
    }
    fun validateInputs(): Boolean {
        val emailValid = Validator.validateEmail(email).also { identifierError = it } == null
        val passwordValid = Validator.validatePassword(password).also { passwordError = it } == null
        return emailValid && passwordValid
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                focusManager.clearFocus()
                authViewModel.hideKeyboard(view)
            }
    ) {
        SignInComponents(
            onSignIn = {
                hasClickedSignIn = true
                if (validateInputs()) {
                    authViewModel.login(email, password)
                }
            },
            email = email,
            password = password,
            identifierError = identifierError,
            passwordError = passwordError,
            onEmailChanged = { email = it },
            onPasswordChanged = { password = it },
            onSignUp = onSignUp

        )
    }
}
