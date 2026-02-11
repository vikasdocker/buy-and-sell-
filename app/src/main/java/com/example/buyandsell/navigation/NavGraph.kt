package com.example.buyandsell.navigation

import androidx.compose.runtime.Composable
import androidx.compose.material3.Text
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.buyandsell.ui.auth.LoginScreen
import com.example.buyandsell.ui.chat.ChatScreen
import com.example.buyandsell.ui.chat.InboxScreen
import com.example.buyandsell.ui.home.HomeScreen

@Composable
fun NavGraph() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginScreen(navController)
        }
        composable("signup") {
            // Placeholder for SignupScreen
            Text("Sign Up Screen")
        }
        composable("home") {
            HomeScreen(navController)
        }
        composable("inbox") {
            InboxScreen(navController)
        }
        composable(
            "chat/{chatId}",
            arguments = listOf(navArgument("chatId") { type = NavType.StringType })
        ) { backStackEntry ->
            val chatId = backStackEntry.arguments?.getString("chatId") ?: ""
            ChatScreen(navController, chatId)
        }
    }
}