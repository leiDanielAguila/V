package com.example.v.view.movie

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.v.R
import com.example.v.Screen
import com.example.v.data.AppDatabase
import com.example.v.model.MovieViewModel
import com.example.v.service.SoundManager
import com.example.v.ui.theme.Lalezar
import com.example.v.ui.theme.Spenbeb
import com.example.v.ui.theme.darkRed
import com.example.v.ui.theme.disnep
import com.example.v.ui.theme.lightGreen
import com.example.v.ui.theme.lightRed
import com.example.v.ui.theme.onyx
import com.example.v.view.GameHearts
import com.example.v.view.GameOver
import com.example.v.view.GameTiles
import com.example.v.view.HintNotes
import com.example.v.view.MovieTicketHeader
import com.example.v.view.ReusableNavigationButton
import com.example.v.view.ScoreCard
import com.example.v.view.Settings
import com.example.v.view.TextFieldInput
import kotlinx.coroutines.delay

@Composable
fun MovieEasyMainScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
) {
    val context = LocalContext.current
    val movieDao = remember { AppDatabase.getDatabase(context).newMovieDao() }
    val movieViewModel = remember { MovieViewModel(movieDao) }
    val movieUiState by movieViewModel.movieUiState.collectAsState()
    val movieState by movieViewModel.movieState.collectAsState()
    var isHintVisible by remember { mutableStateOf(false) }
    var isSettingVisible by remember { mutableStateOf(false) }

    var isGameOver by remember { mutableStateOf(false) }
    var gameOverText by remember { mutableStateOf("") }
    var gameOverColor by remember { mutableStateOf(lightGreen) }
    var isWin by remember { mutableStateOf(false) }
    var isLose by remember { mutableStateOf(false) }

    val boxColor by remember { mutableStateOf(Color.White) }
    var showGameOver by remember { mutableStateOf(false) }

    val confetti by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.confetti))

    var gameFinished = movieState.disneyEasyFinished

    val gameOverStatus = movieViewModel.checkIfGameIsOver(movieViewModel.movieEasyWords, 1)

    Log.d("GameState", "Game Over Status: $gameOverStatus")

// game over logic change .movieEasyWords to its appropriate difficulty level
    if (gameOverStatus == 1) {
        isGameOver = true
        gameOverText = "Game Over"
        gameOverColor = darkRed
        isLose = true
    } else if (gameOverStatus == 2) {
        movieViewModel.changeFinishedState(movieID = 1)
        isGameOver = true
        gameOverText = "Level Complete!"
        gameOverColor = lightGreen
        isWin = true
    }

    LaunchedEffect(isGameOver) {
        if (isGameOver) {
            delay(2000)
            showGameOver = true
            delay(9000)
            showGameOver = false
        } else {
            showGameOver = false
        }
    }



    Box(
        modifier
            .fillMaxSize()
            .background(darkRed)
    ) {
        Image(
            painterResource(R.drawable.movietape),
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier.matchParentSize()
        )

        Box (
            Modifier.padding(horizontal = 8.dp,  vertical = 16.dp),
        ) {
            Column {
                ReusableNavigationButton(
                    Screen.CategoryScreen,
                    textInButton = "Exit",
                    20.sp,
                    navController = navController
                )
                Spacer(modifier.height(12.dp))
                ScoreCard(
                    movieViewModel = movieViewModel
                )
            }
        }

        Box(
            modifier
                .fillMaxSize()
                .padding(12.dp),
            Alignment.TopCenter
        ) {
            MovieTicketHeader(header = "Disney", font = disnep) // change font
        }

        Box(
            modifier
                .fillMaxSize()
                .padding(top = 140.dp),
            Alignment.TopCenter
        ) {
            GameHearts(
                movieID = 1,
                movieViewModel = movieViewModel
            )
        }

        Box(
            modifier
                .fillMaxSize()
                .padding(12.dp),
            Alignment.TopEnd
        ) {
            SelectionBars(
                isHintVisible = isHintVisible,
                isSettingVisible = isSettingVisible,
                isHintVisibleChange = {
                    isHintVisible = it
                },
                isSettingVisibleChange = {
                    isSettingVisible = it
                }
            )
        }

        Box(
            modifier.fillMaxSize().padding(bottom = 120.dp),
            Alignment.BottomCenter
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                GameTiles( // change movies according to its appropriate genre when copied to other screens.
                    movieViewModel = movieViewModel,
                    outerBoxWidth = 400.dp,
                    outerBoxHeight = 460.dp,
                    textBoxWidth = 50.dp,
                    textBoxHeight = 30.dp,
                    movieTiles = movieViewModel.movieEasyTiles,
                    tilesCount = movieViewModel.gameTilesCount,
                    movieNumberTiles = movieViewModel.movieEasyNumberTiles,
                    gridCount = movieViewModel.movieDisneyEasyGridCount,
                    boxColor = boxColor,
                    movieID = 1
                )
                Spacer(modifier.height(6.dp))
                TextFieldInput(
                    movieViewModel = movieViewModel,
                    onDone = {
                        movieViewModel.checkUserInput(movieWords = movieViewModel.movieEasyWords,1)
                        movieViewModel.checkIfGameIsOver(movieViewModel.movieDisneyMediumWords, 1)
                    },
                )
            }
        }
        var  onLevelDone by remember { mutableStateOf(false) }
        Box(
            modifier.fillMaxSize(),
            Alignment.Center
        ) {
            AnimatedVisibility(
                visible = showGameOver
            ) {
                GameOver(
                    text = gameOverText,
                    navController = navController,
                    font = Spenbeb,
                    color = gameOverColor,
                    onClick = onLevelDone,
                    onClickChange = {onLevelDone = it},
                    screen = Screen.MovieEasy,
                    lose = isLose,
                    movieID = 1
                )
            }
        }

        Box (
            modifier.fillMaxSize(),
            Alignment.Center
        ) {
            AnimatedVisibility(
                visible = showGameOver && isWin
            ) {
                LottieAnimation(composition = confetti, iterations = 10)
            }
        }


        Box(
            modifier
                .fillMaxSize()
                .padding(16.dp),
            Alignment.TopCenter
        ) {
            AnimatedVisibility(
                modifier = Modifier
                    .size(width = 270.dp, height = 254.dp),
                visible = isHintVisible
            ) {
                HintNotes(
                    category = "Disney",
                    hintCount = movieViewModel.hintNoteCount, // remove 10 before applying to other composable
                    headerFontFamily = disnep,
                    bodyFontFamily = Lalezar,
                    movieViewModel = movieViewModel,
                    movieHint = movieViewModel.movieEasyHints, // change depending on the difficulty level
                    movieYear = movieViewModel.movieEasyYears // change depending on movies
                )
            }
        }

        Box(
            modifier.fillMaxSize(),
            Alignment.Center
        ) {
            AnimatedVisibility(visible = isSettingVisible) {
                Settings (
                    isSettingOpen = isSettingVisible,
                    isSettingOpenChange = ({isSettingVisible = it})
                )
            }
        }
    }
}

@Composable
fun SelectionBars(
    isHintVisible: Boolean,
    isHintVisibleChange: (Boolean) -> Unit,
    isSettingVisible: Boolean,
    isSettingVisibleChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier
            .size(width = 54.dp, height = 180.dp),
        border = BorderStroke(3.dp, onyx),
        shape = RoundedCornerShape(15.dp),
        color = lightRed
    ) {
        Column(
            modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            IconButton(
                onClick = {
                    isHintVisibleChange(!isHintVisible)
                    SoundManager.clickSound()
                }
            ) {
                if (isHintVisible) {
                    Icon(
                        painterResource(R.drawable.baseline_library_books_24),
                        contentDescription = "",
                        tint = lightGreen,
                        modifier = Modifier.size(38.dp)
                    )
                } else {
                    Icon(
                        painterResource(R.drawable.baseline_library_books_24),
                        contentDescription = "",
                        tint = onyx,
                        modifier = Modifier.size(32.dp)
                    )
                }
            }

            IconButton(
                onClick = {
                    isSettingVisibleChange(!isSettingVisible)
                    SoundManager.clickSound()
                }
            ) {
                if (isSettingVisible) {
                    Icon(
                        painterResource(R.drawable.baseline_settings_24),
                        contentDescription = "Settings",
                        tint = lightGreen,
                        modifier = Modifier.size(38.dp)
                    )
                } else {
                    Icon(
                        painterResource(R.drawable.baseline_settings_24),
                        contentDescription = "Settings",
                        tint = onyx
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun MovieEasyPreview(modifier: Modifier = Modifier) {
    MovieEasyMainScreen(navController = rememberNavController())
}





