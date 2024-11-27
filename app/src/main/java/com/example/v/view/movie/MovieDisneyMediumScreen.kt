package com.example.v.view.movie
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
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
import com.example.v.ui.theme.Lalezar
import com.example.v.ui.theme.Spenbeb
import com.example.v.ui.theme.darkRed
import com.example.v.ui.theme.disnep
import com.example.v.ui.theme.lightGreen
import com.example.v.view.GameHearts
import com.example.v.view.GameOver
import com.example.v.view.GameTiles
import com.example.v.view.HintNotes
import com.example.v.view.MovieTicketHeader
import com.example.v.view.ReusableNavigationButton
import com.example.v.view.ScoreCard
import com.example.v.view.TextFieldInput
import kotlinx.coroutines.delay

@Composable
fun MovieDisneyMediumMainScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
) {
    val context = LocalContext.current
    val movieDao = remember { AppDatabase.getDatabase(context).newMovieDao() }
    val movieViewModel = remember { MovieViewModel(movieDao) }
    val movieUiState by movieViewModel.movieUiState.collectAsState()

    var isHintVisible by remember { mutableStateOf(false) }
    var isSettingVisible by remember { mutableStateOf(false) }

    var isGameOver by remember { mutableStateOf(false) }
    var gameOverText by remember { mutableStateOf("") }
    var gameOverColor by remember { mutableStateOf(lightGreen) }
    var isWin by remember { mutableStateOf(false) }

    val boxColor by remember { mutableStateOf(Color.White) }
    var showGameOver by remember { mutableStateOf(false) }

    val confetti by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.confetti))


    if (movieViewModel.checkIfGameIsOver(movieViewModel.movieDisneyMediumWords, 2) == 1) {
        isGameOver = true
        gameOverText = "Game Over"
        gameOverColor = darkRed
    } else if (movieViewModel.checkIfGameIsOver(movieViewModel.movieDisneyMediumWords, 2) == 2) {
        isGameOver = true
        gameOverText = "Level Complete!"
        gameOverColor = lightGreen
        isWin = true
    }

    LaunchedEffect(isGameOver) {
        if (isGameOver) {
            delay(2000)
            showGameOver = true
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
            MovieTicketHeader(header = "Disney", font = disnep)
        }

        Box(
            modifier
                .fillMaxSize()
                .padding(top = 140.dp),
            Alignment.TopCenter
        ) {
            GameHearts(
                movieUiState = movieUiState
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
            modifier.fillMaxSize().padding(bottom = 60.dp),
            Alignment.BottomCenter
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                GameTiles(
                    movieViewModel = movieViewModel,
                    outerBoxWidth = 340.dp,
                    outerBoxHeight = 550.dp,
                    textBoxWidth = 30.dp,
                    textBoxHeight = 30.dp,
                    tilesCount = movieViewModel.gameTilesDisneyMediumCount,
                    movieTiles = movieViewModel.movieDisneyMediumTiles,
                    movieNumberTiles = movieViewModel.movieDisneyMediumNumberTiles,
                    gridCount = movieViewModel.movieDisneyMediumGridCount,
                    boxColor = boxColor,
                    movieID = 2
                )
                Spacer(modifier.height(22.dp))
                TextFieldInput(
                    movieViewModel = movieViewModel,
                    onDone = {
                        movieViewModel.checkUserInput(movieWords = movieViewModel.movieDisneyMediumWords,2)
                        movieViewModel.checkIfGameIsOver(movieViewModel.movieDisneyMediumWords, 2)
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
                    onClickChange = {
                        onLevelDone = it
                    },
                    screen = Screen.MovieDisneyMedium
                )
            }
        }

        Box (
            modifier.fillMaxSize(),
            Alignment.Center
        ) {
            AnimatedVisibility(
                visible = showGameOver
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
                    hintCount = movieViewModel.hintNoteDisneyMedium, // remove 10 before applying to other composable
                    headerFontFamily = disnep,
                    bodyFontFamily = Lalezar,
                    movieViewModel = movieViewModel,
                    movieHint = movieViewModel.movieDisneyMediumHints, // change depending on the difficulty level
                    movieYear = movieViewModel.movieDisneyMediumYears // change depending on movies
                )
            }
        }
    }
}



@Preview
@Composable
fun MovieDisneyMediumPreview(modifier: Modifier = Modifier) {
    MovieDisneyMediumMainScreen(navController = rememberNavController())
}





