package com.example.apiboutiqueexame

import BookDetailViewModel
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.apiboutiqueexame.api.provideRetrofit
import com.example.apiboutiqueexame.escrever.EscreverScreen
import com.example.apiboutiqueexame.escrever.EscreverViewModel
import com.example.apiboutiqueexame.perfil.PerfilScreen
import com.example.apiboutiqueexame.perfil.PerfilViewModel
import com.example.apiboutiqueexame.produto.BibliotecaScreen
import com.example.apiboutiqueexame.produto.BookWorldApp
import com.example.apiboutiqueexame.room.AppDatabase
import com.example.apiboutiqueexame.room.login.LoginScreen
import com.example.apiboutiqueexame.room.login.UserViewModel
import com.example.apiboutiqueexame.ui.theme.ApiBoutiqueExameTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val database = AppDatabase.getDatabase(this)
        val apiService = provideRetrofit()

        val userViewModel = UserViewModel(database.userDao())
        val bookDetailViewModel = BookDetailViewModel(apiService, database.userDao())
        val perfilViewModel = PerfilViewModel(apiService, database.userDao())
        val escreverViewModel = EscreverViewModel(apiService, database.userDao())
        setContent {
            ApiBoutiqueExameTheme {
                val navController = rememberNavController()

                // Inicializando o UserViewModel
                val isLogged by userViewModel.user.collectAsState()
                val startScreen = if (isLogged != null) "home" else "login"

                NavHost(navController = navController, startDestination = startScreen) {
                    composable("login") {
                        LoginScreen(viewModel = userViewModel, navController = navController)
                    }
                    composable("home") {
                        BookWorldApp(bookDetailViewModel, navController)
                    }
                    composable("book") {
                        BibliotecaScreen(bookDetailViewModel, navController)
                    }
                    composable("write") {
                        EscreverScreen(escreverViewModel)
                    }
                    composable("perfil") {
                        PerfilScreen(viewModel = perfilViewModel, navController = navController)
                    }

                }
            }
        }
    }
}

