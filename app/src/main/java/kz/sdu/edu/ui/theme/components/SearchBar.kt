package kz.sdu.edu.ui.theme.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kz.sdu.edu.R
import kz.sdu.edu.ui.theme.DarkText
import kz.sdu.edu.ui.theme.OpenSans
import kz.sdu.edu.ui.theme.PrimaryGrayLight
import kz.sdu.edu.ui.theme.PrimaryVioletLight

@Preview
@Composable
fun FunPrev(){
    var value by remember { mutableStateOf("") }
    SearchBar(
        value = value,
        onValueChanged = {value = it},
        placeholder = "Search"
    )
}


@Composable
fun SearchBar(
    value : String,
    onValueChanged : (String) -> Unit,
    placeholder : String,
) {
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 50.dp),
        textStyle = MaterialTheme.typography.bodyLarge,
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
            ),
        shape = RoundedCornerShape(10.dp),
        value = value,
        onValueChange = onValueChanged,
        placeholder = {
                Text(
                    text = placeholder,
                    style = MaterialTheme.typography.bodyLarge,
                    color = PrimaryGrayLight,
                    fontWeight = FontWeight.Medium
                )
        },
        singleLine = true,
        trailingIcon = {
                Icon(
                    painter = painterResource(R.drawable.search),
                    contentDescription = "Search icon",
                    tint = DarkText
                )
            }
    )
}
