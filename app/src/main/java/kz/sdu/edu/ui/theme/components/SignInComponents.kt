package kz.sdu.edu.ui.theme.components

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kz.sdu.edu.R
import kz.sdu.edu.View.GenreScreen
import kz.sdu.edu.ViewModel.AuthViewModel
import kz.sdu.edu.ui.theme.GrayDarkText
import kz.sdu.edu.ui.theme.PrimaryViolet

@Composable
fun SignInComponents(
    onSignUp: () -> Unit,
    onSignIn: () -> Unit,
    email : String,
    password : String,
    identifierError: String?,
    passwordError: String?,
    onEmailChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit
){
    Column (
        modifier = Modifier
            .fillMaxWidth()
            .systemBarsPadding()
            .padding(20.dp)
    ) {
        SignTextField(
            value = email,
            onValueChange = onEmailChanged,
            placeholder = stringResource(R.string.email_placeholder),
            isPasswordField = false,
            errorMessage = identifierError
        )
        Spacer(modifier = Modifier.height(20.dp))
        SignTextField(
            value = password,
            onValueChange = onPasswordChanged,
            placeholder = stringResource(R.string.password_placeholder),
            isPasswordField = true,
            errorMessage = passwordError
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = stringResource(R.string.forgot_password),
            style = MaterialTheme.typography.bodyLarge,
            color = PrimaryViolet
        )
        Spacer(modifier = Modifier.height(20.dp))
        ContinueButton(
            text = stringResource(R.string.sign_in),
            onClicked = {
                onSignIn()
                        },
            color = ButtonDefaults.buttonColors(
                contentColor = Color.White,
                containerColor = PrimaryViolet
            )
        )
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(R.string.need_account),
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Normal,
                color = GrayDarkText
            )
            Spacer(modifier = Modifier.width(14.dp))
            Text(
                text = stringResource(R.string.sign_up),
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold,
                color = PrimaryViolet,
                modifier = Modifier.clickable { onSignUp() }
            )
        }
    }
}