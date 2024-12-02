package com.example.v

import android.content.Context
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
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
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
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
        BackgroundMusicPlayer.start(this, R.raw.liod)
        SoundManager.initialize(this)
        enableEdgeToEdge()
        lifecycle.addObserver(object : LifecycleEventObserver {
            override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
                when (event) {
                    Lifecycle.Event.ON_PAUSE -> BackgroundMusicPlayer.pause()
                    Lifecycle.Event.ON_RESUME -> BackgroundMusicPlayer.resume()

                    else -> Unit
                }
            }
        })
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
                        composable(Screen.GameEnd.route) {
                            GameEnd(navController = navController)
                        }
                    }
                }
            }
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        BackgroundMusicPlayer.stop()
    }
}

enum class Screen(val route: String) {
    MainMenu("MainMenu"),
    CategoryScreen("CategoryScreen"),
    MovieEasy("MovieEasy"),
    MovieDisneyMedium("MovieDisneyMedium"),
    MovieDisneyHard("MovieDisneyHard"),
    GenreScreen("GenreScreen"),
    MovieSuperHeroEasy("MovieSuperHeroEasy"),
    MovieScifiEasy("MovieScifiEasy"),
    GameEnd("GameEnd")
}

object BackgroundMusicPlayer {
    private var mediaPlayer: MediaPlayer? = null

    fun start(context: Context, resId: Int) {
        try {
            if (mediaPlayer == null) {
                mediaPlayer = MediaPlayer.create(context, resId).apply {
                    isLooping = true
                    start()
                }
                Log.d("BackgroundMusicPlayer", "MediaPlayer started")
            }
        } catch (e: Exception) {
            Log.e("BackgroundMusicPlayer", "Error starting MediaPlayer", e)
        }
    }

    fun stop() {
        mediaPlayer?.apply {
            stop()
            release()
        }
        mediaPlayer = null
        Log.d("BackgroundMusicPlayer", "MediaPlayer stopped")
    }

    fun pause() {
        mediaPlayer?.pause()
        Log.d("BackgroundMusicPlayer", "MediaPlayer paused")
    }

    fun resume() {
        mediaPlayer?.start()
        Log.d("BackgroundMusicPlayer", "MediaPlayer resumed")
    }

    fun setVolume(volume: Float) {
        mediaPlayer?.setVolume(volume, volume)
    }
}

