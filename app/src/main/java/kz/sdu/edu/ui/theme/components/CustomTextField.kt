package kz.sdu.edu.ui.theme.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import kz.sdu.edu.R
import kz.sdu.edu.ui.theme.DarkText
import kz.sdu.edu.ui.theme.OpenSans
import kz.sdu.edu.ui.theme.PrimaryGrayLight
import kz.sdu.edu.ui.theme.PrimaryVioletLight


@Composable
fun SignTextField(
    value: String,
    onValueChange : (String) -> Unit,
    placeholder: String,
    isPasswordField: Boolean,
    errorMessage : String? = null
){
    var passwordVisible by remember { mutableStateOf(false) }
    Column {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp),
            value = value,
            onValueChange = onValueChange,
            isError = errorMessage != null,
            placeholder = {
                Text(
                    text = placeholder,
                    fontFamily = OpenSans,
                    fontWeight = FontWeight.Normal,
                    color = PrimaryGrayLight
                )
            },
            singleLine = true,
            shape = RoundedCornerShape(10.dp),
            visualTransformation =
            if (isPasswordField && !passwordVisible) PasswordVisualTransformation()
            else VisualTransformation.None,
            trailingIcon = {
                if (isPasswordField) {
                    IconButton(
                        onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            painter = painterResource(
                                if (passwordVisible) R.drawable.eye_open else R.drawable.eye_closed
                            ),
                            contentDescription = if (passwordVisible) "Hide Password" else "Show Password",
                            modifier = Modifier.size(24.dp),
                            tint = DarkText,
                        )
                    }
                }
            },
            colors = TextFieldDefaults
                .colors(
                    unfocusedContainerColor = PrimaryVioletLight,
                    focusedContainerColor = PrimaryVioletLight,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color(0xff7F87F6),
                    cursorColor = DarkText,
                    unfocusedTextColor = DarkText,
                    focusedTextColor = DarkText,
                    disabledTextColor = DarkText,
                    errorContainerColor = PrimaryVioletLight,
                    errorIndicatorColor = Color.Red,
                    errorSupportingTextColor = Color.Red,
                    errorTextColor = PrimaryGrayLight

                ),

        )
        Spacer(modifier = Modifier.height(5.dp))
        if (errorMessage != null) {
            Text(
                text = errorMessage,
                color = Color.Red,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 8.dp)
            )
        }
    }
}