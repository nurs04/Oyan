package kz.sdu.edu.ui.theme.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kz.sdu.edu.R
import kz.sdu.edu.ViewModel.AuthViewModel
import kz.sdu.edu.ui.theme.PrimaryGrayLight
import kz.sdu.edu.ui.theme.PrimaryViolet

@Composable
fun SignUpComponents(
    username: String,
    onUsernameChange: (String) -> Unit,
    email: String,
    password: String,
    repeatPassword: String,
    usernameError: String?,
    emailError: String?,
    passwordError: String?,
    repeatPasswordError: String?,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onRepeatPasswordChange: (String) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        SignTextField(
            value = username,
            onValueChange = onUsernameChange,
            placeholder = stringResource(R.string.username),
            isPasswordField = false,
            errorMessage = usernameError
        )
        Spacer(modifier = Modifier.height(20.dp))
        SignTextField(
            value = email,
            onValueChange = onEmailChange,
            placeholder = stringResource(R.string.email_sign),
            isPasswordField = false,
            errorMessage = emailError
        )
        Spacer(modifier = Modifier.height(20.dp))
        SignTextField(
            value = password,
            onValueChange = onPasswordChange,
            placeholder = stringResource(R.string.password_placeholder),
            isPasswordField = true,
            errorMessage = passwordError
        )
        Spacer(modifier = Modifier.height(20.dp))
        SignTextField(
            value = repeatPassword,
            onValueChange = onRepeatPasswordChange,
            placeholder = stringResource(R.string.repeat_password),
            isPasswordField = true,
            errorMessage = repeatPasswordError
        )
    }
}