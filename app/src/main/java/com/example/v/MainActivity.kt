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
import com.example.v.model.MovieViewModel
import com.example.v.service.SoundManager
import com.example.v.ui.theme.BackgroundScreenColor
import com.example.v.ui.theme.VTheme
import com.example.v.view.CategoryScreen
import com.example.v.view.MainScreen
import com.example.v.view.movie.MovieEasyMainScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SoundManager.initialize(this)
        enableEdgeToEdge()
        setContent {
            VTheme {
                val navController = rememberNavController()
                Box(modifier = Modifier.background(BackgroundScreenColor)) {
                    NavHost(
                        navController = navController ,
                        startDestination = Screen.MovieEasy.route, // change when done testing
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
                            MovieEasyMainScreen(navController = navController, movieViewModel = MovieViewModel())
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
    MovieEasy("MovieEasy")
}