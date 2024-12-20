package com.example.v.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.v.R
import com.example.v.Screen
import com.example.v.data.AppDatabase
import com.example.v.model.MovieViewModel
import com.example.v.service.SoundManager
import com.example.v.ui.theme.BackgroundScreenColor

@Composable
fun CategoryScreen(modifier: Modifier = Modifier, navController: NavController) {

    val context = LocalContext.current
    val movieDao = remember { AppDatabase.getDatabase(context).newMovieDao() }
    val movieViewModel = remember { MovieViewModel(movieDao) }
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(BackgroundScreenColor),
        contentAlignment = Alignment.TopCenter
    ) {
        Image(
            painter = painterResource(R.drawable.letters),
            contentDescription = "background letters",
            contentScale = ContentScale.Crop,
            modifier = Modifier.matchParentSize()
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 26.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment =  Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                ReusableNavigationButton(
                    Screen.MainMenu,
                    textInButton = "Back",
                    fontSize = 32.sp,
                    navController
                )

                ScoreCard(movieViewModel = movieViewModel)
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 100.dp)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
           // verticalArrangement = Arrangement.SpaceEvenly,
        ) {
            MovieCard(
                navController = navController,
                navigate = Screen.GenreScreen,
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            // verticalArrangement = Arrangement.SpaceEvenly,
        ) {
            TechnologyCard(navController)
        }
    }
}

@Composable
fun MovieCard(
    navigate: Screen,
    navController: NavController,
) {
    Box(
        modifier = Modifier
            .clickable {
                navController.navigate(navigate.route)
                SoundManager.clickSound()
            }
            .size(width = 268.dp, height = 181.dp)
    ) {
        Image(
            painterResource(id = R.drawable.movie_category),
            contentDescription = "Movie Card",
            contentScale = ContentScale.Crop,
            modifier = Modifier.matchParentSize()
        )
//        Row(
//            modifier = Modifier
//                .fillMaxSize(),
//            verticalAlignment = Alignment.Bottom,
//        ) {
//            Icon(
//                painterResource(R.drawable.baseline_star_24),
//                contentDescription = "Star",
//                modifier = Modifier.size(54.dp),
//                tint = Color.DarkGray
//            )
//            Icon(
//                painterResource(R.drawable.baseline_star_24),
//                contentDescription = "Star",
//                modifier = Modifier.size(54.dp),
//                tint = Color.DarkGray
//            )
//            Icon(
//                painterResource(R.drawable.baseline_star_24),
//                contentDescription = "Star",
//                modifier = Modifier.size(54.dp),
//                tint = Color.DarkGray
//            )
//        }
    }
}


@Composable
fun TechnologyCard(navController: NavController) {

    val context = LocalContext.current
    val movieDao = remember { AppDatabase.getDatabase(context).newMovieDao() }
    val movieViewModel = remember { MovieViewModel(movieDao) }
    val movieUiState by movieViewModel.movieUiState.collectAsState()
    val movieState by movieViewModel.movieState.collectAsState()

    val lock by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.lock))

    val isGameCompleted: Boolean = when {
        movieState.disneyEasyFinished && movieState.disneyMediumFinished &&
                movieState.disneyHardFinished && movieState.superheroEasyFinished -> true
        else -> false
    }

    var isPlaying by remember {mutableStateOf(false)}

    val progress by animateLottieCompositionAsState(
        composition = lock,
        isPlaying = isPlaying
    )

    LaunchedEffect(key1 = progress) {
        if (progress == 0f) {
            isPlaying = true
        }
        if (progress == 1f) {
            isPlaying = false
        }
    }

    Box(
        modifier = Modifier
            .clickable {
                if (!isGameCompleted) {
                    isPlaying = true
                } else {
                    navController.navigate(Screen.GameEnd.route)
                }
            }
            .size(width = 294.dp, height = 140.dp)
    ) {

        Image(
            painterResource(id = R.drawable.tech_card),
            contentDescription = "Movie Card",
            modifier = Modifier.matchParentSize()
        )

        Column(
            modifier = Modifier.matchParentSize(),
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.Center
        ){
            AnimatedVisibility(!isGameCompleted) {
                LottieAnimation(
                    composition = lock,
                    // iterations = Int.MAX_VALUE
                    progress = {progress}
                )
            }
        }
//        Row(
//            modifier = Modifier
//                .fillMaxSize(),
//            verticalAlignment = Alignment.Bottom,
//        ) {
//            Icon(
//                painterResource(R.drawable.baseline_star_24),
//                contentDescription = "Star",
//                modifier = Modifier.size(54.dp),
//                tint = Color.DarkGray
//            )
//            Icon(
//                painterResource(R.drawable.baseline_star_24),
//                contentDescription = "Star",
//                modifier = Modifier.size(54.dp),
//                tint = Color.DarkGray
//            )
//            Icon(
//                painterResource(R.drawable.baseline_star_24),
//                contentDescription = "Star",
//                modifier = Modifier.size(54.dp),
//                tint = Color.DarkGray
//            )
//        } // ROW ENDING - TECHNOLOGY CARD
    } // BOX ENDING - TECHNOLOGY CARD
}

@Preview(showBackground = true)
@Composable
fun CategoryScreenPreview(modifier: Modifier = Modifier) {
    CategoryScreen(navController = rememberNavController())
}

//m
