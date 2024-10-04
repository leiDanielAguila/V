package com.example.v

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview

import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.v.ui.theme.BackgroundScreenColor
import com.example.v.ui.theme.VTheme
import com.example.v.ui.theme.lighterBrown
import kotlinx.serialization.Serializable

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
                        startDestination = MovieEasy, // change when done testing
                        enterTransition = { fadeIn(animationSpec = tween(2000, easing = LinearOutSlowInEasing)) },
                        exitTransition = { fadeOut(animationSpec = tween(2000, easing = LinearOutSlowInEasing)) }
                    ) {
                        composable<MainMenu> {
                            MainScreen(navController = navController)
                        }
                        composable<CategoryScreen> {
                            CategoryScreen(navController = navController)
                        }
                        composable<MovieEasy> {
                            MovieScreen(navController = navController)
                        }
                    }
                }
            }
        }
    }
}

@Serializable
object MainMenu

@Serializable
object CategoryScreen

@Serializable
object MovieEasy