package kz.sdu.edu

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import kz.sdu.edu.View.WelcomeScreen
import kz.sdu.edu.navigation.AppNavHost
import kz.sdu.edu.ui.theme.OyanTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent{
            val navController = rememberNavController()
            OyanTheme {
                AppNavHost(navController = navController)
            }
        }
    }
}
