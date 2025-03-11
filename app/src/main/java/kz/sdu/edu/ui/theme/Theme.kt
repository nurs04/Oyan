package kz.sdu.edu.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val AppColors = darkColorScheme(
    primary = Black900,
    secondary = Black900,
    tertiary = Black900,
    onPrimary = Color.White,
    onSecondary = Black500,
)

@Composable
fun OyanTheme(content: @Composable () -> Unit ){
    MaterialTheme(
        colorScheme = AppColors,
        typography = Typography,
        content = content
    )
}