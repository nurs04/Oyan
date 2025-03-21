package kz.sdu.edu.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import kz.sdu.edu.View.BookInformationScreen
import kz.sdu.edu.View.GenreScreen
import kz.sdu.edu.View.MainScreen
import kz.sdu.edu.View.SeeAllScreen
import kz.sdu.edu.View.SignBottomSheet
import kz.sdu.edu.View.SignUpScreen
import kz.sdu.edu.View.WelcomeScreen
import kz.sdu.edu.ViewModel.AuthViewModel
import kz.sdu.edu.repository.AuthRepository
import kz.sdu.edu.ui.theme.components.BookInformationComponents
import org.koin.androidx.compose.koinViewModel

@Composable
fun AppNavHost(navController: NavHostController) {
    val authRepository: AuthRepository = koinViewModel<AuthViewModel>().repository
    val startDestination = remember {
        when {
            !authRepository.isLoggedIn() -> "welcome"
            authRepository.areGenresSelected() -> "main"
            else -> "genres"
        }
    }


    NavHost(navController = navController, startDestination = startDestination) {
        composable("welcome") { WelcomeScreen(navController = navController) }
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
            GenreScreen(
                onContinue = {
                      navController.navigate("main"){
                          popUpTo("genres"){inclusive = true}
                  }
                }
            )
        }
        composable("main"){
            MainScreen(navController = navController)
        }
        composable("seeAll/{category}"){ backStackEntry ->
            val category = backStackEntry.arguments?.getString("category") ?: "recommended"
            SeeAllScreen(
                navController = navController,
                category = category
            )
        }
        composable("book_details/{bookId}"){ backStackEntry ->
            val bookId = backStackEntry.arguments?.getString("bookId")?.toIntOrNull() ?: 0
            BookInformationScreen(navController, bookId)
        }
    }
}