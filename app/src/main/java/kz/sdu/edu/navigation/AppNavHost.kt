package kz.sdu.edu.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import kz.sdu.edu.View.GenreScreen
import kz.sdu.edu.View.SignBottomSheet
import kz.sdu.edu.View.SignUpScreen
import kz.sdu.edu.View.WelcomeScreen

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "welcome") {
        composable("welcome") {
            WelcomeScreen(navController = navController)
        }
        composable("sign_in_bottom_sheet") {
            SignBottomSheet(
                onDismiss = {navController.popBackStack()},
                onNavigateSignIn = {
                    navController.popBackStack()
                    navController.navigate("genres"){
                        popUpTo("welcome"){
                            inclusive = false
                        }
                    }
                }
            )
        }
        composable("sign_up"){
            SignUpScreen(onNavigateToSignIn = {navController.navigate("sign_in_bottom_sheet")})
        }
        composable("genres") {
            GenreScreen()
        }
    }
}