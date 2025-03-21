package kz.sdu.edu.ui.theme.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import kz.sdu.edu.R
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

@Composable
fun SignUpInfo(){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ){
        Text(
            text = stringResource(R.string.info),
            style = MaterialTheme.typography.bodySmall,
            color = PrimaryGrayLight,
            textAlign = TextAlign.Center
        )
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(R.string.terms_of_policy),
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Medium,
                color = PrimaryViolet,
                textDecoration = TextDecoration.Underline
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = stringResource(R.string.and),
                style = MaterialTheme.typography.bodySmall,
                color = PrimaryGrayLight,
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = stringResource(R.string.privaci_policy),
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Medium,
                color = PrimaryViolet,
                textDecoration = TextDecoration.Underline
            )
        }
    }
}