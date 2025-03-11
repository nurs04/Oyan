package kz.sdu.edu.View

import android.service.autofill.Validators
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import kz.sdu.edu.R
import kz.sdu.edu.Util.Validator
import kz.sdu.edu.ViewModel.AuthViewModel
import kz.sdu.edu.ui.theme.PrimaryViolet
import kz.sdu.edu.ui.theme.components.ContinueButton
import kz.sdu.edu.ui.theme.components.SignUpComponents
import kz.sdu.edu.ui.theme.components.SignUpInfo
import org.koin.androidx.compose.koinViewModel


@Composable
fun SignUpScreen(
    authViewModel: AuthViewModel = koinViewModel(),
    onNavigateToSignIn: () -> Unit
) {

    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var repeatPassword by remember { mutableStateOf("") }
    var usernameError by remember { mutableStateOf<String?>(null) }
    var emailError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }
    var repeatPasswordError by remember { mutableStateOf<String?>(null) }
    val authState by authViewModel.authState.collectAsState()

    LaunchedEffect(authState) {
        authState?.let { response ->
            if (response.status == "success") {
                onNavigateToSignIn()
            } else {
                when (response.status) {
                    "username_exists" -> usernameError = "Username already exists"
                    "email_exists" -> emailError = "Email already exists"
                    else -> Log.e("SignUp", "Ошибка регистрации: ${response.status}")
                }
            }
        }
    }

    fun validateAll(){
        usernameError = Validator.validateUserName(username)
        emailError = Validator.validateEmail(email)
        passwordError = Validator.validatePassword(password)
        repeatPasswordError = if (password != repeatPassword) "Passwords don't match" else null
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
    ) {
        SignUpComponents(
            username = username,
            onUsernameChange = {username = it},
            email = email,
            password = password,
            repeatPassword = repeatPassword,
            onEmailChange = {email = it},
            onPasswordChange = {password = it},
            onRepeatPasswordChange = {repeatPassword = it},
            usernameError = usernameError,
            emailError = emailError,
            passwordError = passwordError,
            repeatPasswordError = repeatPasswordError,
        )
        Spacer(modifier = Modifier.height(20.dp))
        ContinueButton(
            text = stringResource(R.string.create_account),
            onClicked = {
                validateAll()
                if(username.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && repeatPassword.isNotEmpty() &&listOf(usernameError, emailError, passwordError, repeatPasswordError).all{
                    it == null
                    }){
                    authViewModel.register(username, email, password, repeatPassword)
                }else {
                    Log.e("SignUp", "Please fill all fields correctly")
                }
            },
            color = ButtonDefaults.buttonColors(
                contentColor = Color.White,
                containerColor = PrimaryViolet
            )
        )
        Spacer(modifier = Modifier.height(10.dp))
        SignUpInfo()
    }
}