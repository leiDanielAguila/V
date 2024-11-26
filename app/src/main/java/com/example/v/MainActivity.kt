package com.example.v

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.v.data.AppDatabase
import com.example.v.service.SoundManager
import com.example.v.ui.theme.BackgroundScreenColor
import com.example.v.ui.theme.VTheme
import com.example.v.view.CategoryScreen
import com.example.v.view.GenreScreen
import com.example.v.view.MainScreen
import com.example.v.view.movie.MovieDisneyHardMainScreen
import com.example.v.view.movie.MovieDisneyMediumMainScreen
import com.example.v.view.movie.MovieEasyMainScreen
import com.example.v.view.superhero.MovieSuperHeroEasyMainScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SoundManager.initialize(this)
        enableEdgeToEdge()
        setContent {
            VTheme {
                val navController = rememberNavController()
                val database = Room.databaseBuilder(
                    applicationContext,
                    AppDatabase::class.java, "app-database"
                ).build()
                Box(modifier = Modifier.background(BackgroundScreenColor)) {
                    NavHost(
                        navController = navController ,
                        startDestination = Screen.MainMenu.route, // change when done testing
                        enterTransition = { fadeIn(animationSpec = tween(2000, easing = LinearOutSlowInEasing)) },
                        exitTransition = { fadeOut(animationSpec = tween(2000, easing = LinearOutSlowInEasing)) }
                    ) {
                        composable(Screen.MainMenu.route) {
                            MainScreen(navController = navController)
                        }
                        composable(Screen.CategoryScreen.route){
                            CategoryScreen(navController = navController)
                        }
                        composable(Screen.MovieEasy.route) {
                            MovieEasyMainScreen(navController = navController)
                        }
                        composable(Screen.MovieDisneyMedium.route) {
                            MovieDisneyMediumMainScreen(navController = navController)
                        }
                        composable(Screen.MovieDisneyHard.route) {
                            MovieDisneyHardMainScreen(navController = navController)
                        }
                        composable(Screen.GenreScreen.route) {
                            GenreScreen(navController = navController)
                        }
                        composable(Screen.MovieSuperHeroEasy.route) {
                            MovieSuperHeroEasyMainScreen(navController = navController)
                        }
                    }
                }
            }
        }
    }
}

//@Serializable
//object MainMenu
//
//@Serializable
//object CategoryScreen
//
//@Serializable
//object MovieEasy


enum class Screen(val route: String) {
    MainMenu("MainMenu"),
    CategoryScreen("CategoryScreen"),
    MovieEasy("MovieEasy"),
    MovieDisneyMedium("MovieDisneyMedium"),
    MovieDisneyHard("MovieDisneyHard"),
    GenreScreen("GenreScreen"),
    MovieSuperHeroEasy("MovieSuperHeroEasy")
}