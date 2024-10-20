package com.example.v

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.animateValue
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import com.example.v.ui.theme.Lalezar
import com.example.v.ui.theme.Spenbeb
import com.example.v.ui.theme.VTheme
import com.example.v.ui.theme.darkGreen
import com.example.v.ui.theme.darkRed
import com.example.v.ui.theme.darkYellow
import com.example.v.ui.theme.heartRed
import com.example.v.ui.theme.lightBlue
import com.example.v.ui.theme.lightGreen
import com.example.v.ui.theme.lightRed
import com.example.v.ui.theme.navyBlue
import com.example.v.ui.theme.onyx
import com.example.v.ui.theme.pastelGreen
import kotlinx.coroutines.delay

// Main screen for Movie easy difficulty
@Composable
fun MovieScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    movieViewModel: MovieEasyViewModel = viewModel()
) {

    val movieUiState by movieViewModel.movieUiState.collectAsState() // connects to app logic

    val confetti by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.confetti))
    //val correct by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.correctword))
    
//    val correctProgress by animateLottieCompositionAsState(
//        composition = correct,
//        speed = 0.7F,
//        iterations = 1,
//    )

    var isHintVisible by remember { // boolean for hint note
        mutableStateOf(false)
    }

    var backgroundColor by remember { // value for background color
        mutableIntStateOf(2)
    }

    var isCorrectVisible by remember { // TBA
        mutableStateOf(false)
    }
    
    val colorChanger = when(backgroundColor){ // background changer app logic
        1 -> darkRed
        2 -> navyBlue
        else -> lightBlue
    }

    Box( // main screen box
        modifier
            .fillMaxSize()
            .background(colorChanger)
    ){
        Image(
            painterResource(R.drawable.movietape),
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier.matchParentSize()
        )
        Column( // Button Bar Column
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.End,
        ) {
            ButtonBar(
                backgroundColor = backgroundColor, // for changing background color
                onBackgroundChange = {
                    onClickChange -> backgroundColor = onClickChange
                },

                isHintVisible = isHintVisible, // for hint notes
                isHintVisibleChange = {
                    isHintVisibleChange -> isHintVisible = isHintVisibleChange
                },

                navController = navController, // for game exit
                movieViewModel = movieViewModel,
            )
        }// Button Bar Column

        Column( // MOVIE CARD DISPLAY ONLY
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painterResource(R.drawable.movie_card) ,
                contentDescription = "",
                modifier = Modifier
                    .padding(horizontal = 0.dp, vertical = 30.dp)
            )
        } // MOVIE CARD DISPLAY ONLY

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 230.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            UserLives(movieUiState = movieUiState)
        } // GAME USER LIFE

        Column( // MOVIE DIFFICULTY CARD DISPLAY ONLY
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painterResource(R.drawable.moviedifficultyeasy),
                contentDescription = "",
                modifier = Modifier
                    .padding(horizontal = 0.dp, vertical = 150.dp)
            )
        } // MOVIE DIFFICULTY CARD DISPLAY ONLY

        Column( // SCORE CARD
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 110.dp),
        ) {
            Box(
                modifier = Modifier
                    .size(width = 109.dp, height = 93.01.dp)
                    .fillMaxSize(),
            ){
                Image ( // SCORE CARD IMAGE
                    painterResource(R.drawable.score_movie),
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .matchParentSize(),
                )

                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Bottom
                ) {
                    Text(
                        text = movieUiState.userScore.toString(),
                        fontSize = 32.sp,
                        color = Color.White,
                        fontFamily = Lalezar,
                    )
                }
            }
        } // SCORE CARD

        Column( // GAME TILES
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 134.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            GameTiles(movieViewModel = movieViewModel)
        } // GAME TILES

        Column( // USER INPUT FIELD
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 560.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            TextField(
                value = movieViewModel.userInput,
                onValueChange = { newUserInput ->
                    movieViewModel.updateUserInput(newUserInput)
                },
                keyboardActions = KeyboardActions(
                    onDone = {
                        movieViewModel.ifUserInputCorrect()
                        movieViewModel.ifGameFinished()
                    }
                ),
                singleLine = true,
                textStyle = TextStyle(textAlign = TextAlign.Center),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done,
                ),
                label = {Text(text = "Enter Guess here")},
                leadingIcon = {
                    Icon(imageVector = Icons.AutoMirrored.Filled.Send, contentDescription = "", tint = Color.LightGray)
                }

            )
        } // USER INPUT FIELD

        
        Column( // DISPLAY WHEN GAME OVER AND WIN
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {

            AnimatedVisibility(
                visible = movieUiState.isGameOverAndWin,
            ) {
                LevelComplete(movieViewModel = movieViewModel)
            }
        } // DISPLAY WHEN GAME OVER AND WIN

        Column( // LOTTIE ANIMATION
            modifier = Modifier.fillMaxSize()
        ) {
            if (movieUiState.isGameOverAndWin) {
                SoundManager.win()
                LottieAnimation(composition = confetti, iterations = 10)
            }
        } // lottie animation for game ends

        LaunchedEffect(movieUiState.isCorrect) {
            if (movieUiState.isCorrect) {
                isCorrectVisible = true
                delay(3000)
                isCorrectVisible = false
            }
        }

        Column( // LOTTIE ANIMATION
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
//            if () {
//                AnimatedVisibility(visible = isCorrectVisible) {
//                    LottieAnimation(composition = correct)
//                }
//            }
        } // lottie animation for correct

        Column( // HINT NOTE
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 50.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
        ) {
            HintNotes(isHintVisible = isHintVisible, movieViewModel = movieViewModel)
        } // HINT NOTES

        Column( // game over and lose
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            AnimatedVisibility(
                visible = movieUiState.isGameOverAndLose
            ) {
                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    LevelFailed(movieViewModel = movieViewModel, navController = navController)
                }
            }
        } //  game over and lose

    }
}

@Composable
fun ButtonBar(
    modifier: Modifier = Modifier,
    movieViewModel: MovieEasyViewModel,
    backgroundColor: Int,
    onBackgroundChange: (Int) -> Unit,
    isHintVisible: Boolean,
    isHintVisibleChange: (Boolean) -> Unit,
    navController: NavController
) {
    Column(
        Modifier.padding(vertical = 32.dp, horizontal = 12.dp)
    ) {
        Surface(
            modifier
                .size(width = 44.dp, height = 210.dp)
                .fillMaxSize(),
            shape = RoundedCornerShape(15.dp),
            border = BorderStroke(width = 1.dp, color = onyx),
            color = pastelGreen,

            ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                IconButton(onClick = { // APP EXIT
                    SoundManager.clickSound()
                    navController.navigate(CategoryScreen)
                }) {
                    Icon(
                        painterResource(R.drawable.baseline_exit_to_app_24),
                        contentDescription = "",
                        tint = onyx,
                        modifier = Modifier.size(32.dp)
                    )
                } // EXIT
                IconButton(onClick = { // HINT NOTES
                    isHintVisibleChange(!isHintVisible)
                    SoundManager.clickSound()
                }) {
                    if (isHintVisible) {
                        Icon(
                            painterResource(R.drawable.baseline_menu_book_24),
                            contentDescription = "",
                            tint = Color.Yellow,
                            modifier = Modifier.size(32.dp)
                        )
                    } else {
                        Icon(
                            painterResource(R.drawable.baseline_menu_book_24),
                            contentDescription = "",
                            tint = onyx,
                            modifier = Modifier.size(32.dp)
                        )
                    }


                } // HINT BOOK
                IconButton(onClick = { // FUTURE IN GAME MENU
                /*TODO*/
                    // temporary function to reset the game
                    movieViewModel.resetGame()
                    SoundManager.clickSound()
                }) {
                    Icon(
                        painterResource(R.drawable.baseline_lightbulb_24),
                        contentDescription = "",
                        tint = onyx,
                        modifier = Modifier.size(32.dp)
                    )
                } // LIGHT BULB
                IconButton(onClick = { // BACKGROUND CHANGER
                    SoundManager.clickSound()
                    val onClickChange = if (backgroundColor == 3)
                        1 else backgroundColor+1
                    onBackgroundChange(onClickChange)
                }) {
                    Icon(
                        painterResource(R.drawable.baseline_repeat_24),
                        contentDescription = "",
                        tint = onyx,
                        modifier = Modifier.size(32.dp)
                    )
                } // CHANGE BG
            }
        }
    }
}

@Composable
fun LevelComplete(
    modifier: Modifier = Modifier,
    movieViewModel: MovieEasyViewModel
) {
    Surface(
        modifier.fillMaxSize(),
        color = Color(0xFFF8DE7E),
        shape = RoundedCornerShape(15.dp),
        border = BorderStroke(3.dp, Color.Black)
    ) {
        Column(
            modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Level Complete!",
                fontSize = 52.sp,
                fontFamily = Lalezar,
                color = Color(0xFFF5F5F5),
                fontWeight = FontWeight.Bold,
            )

            Row(
                modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextButton(
                    onClick = {
                        movieViewModel.resetGame()
                        SoundManager.clickSound()
                    },
                ) {
                    Text(
                        "Repeat level",
                        fontSize = 18.sp,
                        fontFamily = Spenbeb,
                        color = darkRed,
                        fontWeight = FontWeight.Bold,
                    )
                    Icon(
                        painterResource(R.drawable.baseline_repeat_24),
                        contentDescription = "",
                        tint = darkRed,
                        modifier = Modifier.size(32.dp)
                    )
                }

                TextButton(
                    onClick = {},
                    ) {
                    Text(
                        "Next level",
                        fontSize = 18.sp,
                        fontFamily = Spenbeb,
                        color = lightGreen,
                        fontWeight = FontWeight.Bold,
                    )
                }
            }
        }
    }
}

@Composable
fun LevelFailed(
    modifier: Modifier = Modifier,
    movieViewModel: MovieEasyViewModel,
    navController: NavController
) {
    Surface(
        modifier.fillMaxSize(),
        color = Color.DarkGray,
        shape = RoundedCornerShape(15.dp),
        border = BorderStroke(3.dp, Color.Black)
    ) {
        Column(
            modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Level Failed!",
                fontSize = 52.sp,
                fontFamily = Lalezar,
                color = Color(0xFFF5F5F5),
                fontWeight = FontWeight.Bold,
            )

            Row(
                modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextButton(
                    onClick = {
                        movieViewModel.resetGame()
                        SoundManager.clickSound()
                    },
                ) {
                    Text(
                        "Try Again?",
                        fontSize = 18.sp,
                        fontFamily = Spenbeb,
                        color = lightGreen,
                        fontWeight = FontWeight.Bold,
                    )
                    Icon(
                        painterResource(R.drawable.baseline_replay_24),
                        contentDescription = "",
                        tint = lightGreen,
                        modifier = Modifier.size(32.dp)
                    )
                }
                TextButton(
                    onClick = {
                        navController.navigate(MainMenu)
                        SoundManager.clickSound()
                    },
                ) {
                    Text(
                        "Exit to Menu?",
                        fontSize = 18.sp,
                        fontFamily = Spenbeb,
                        color = Color.Red,
                        fontWeight = FontWeight.Bold,
                    )

                }
            }
        }
    }
}

@Composable
fun GameTiles(movieViewModel: MovieEasyViewModel) {

    val movieUiState by movieViewModel.movieUiState.collectAsState()
    Surface(
        modifier = Modifier
            .size(width = 330.dp, height = 360.dp),
        color = onyx,
        shape = RoundedCornerShape(10.dp)
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(9),
            verticalArrangement = Arrangement.spacedBy(6.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
        ) {
            items(count = movieViewModel.boxCountMovie1) {
                if (it in movieViewModel.movieEasyTiles.keys) { // for words
                    Surface(
                        modifier = Modifier.size(width = 20.dp, height = 30.dp),
                        color = Color.White,
                        shape = RoundedCornerShape(5.dp),
                        border = BorderStroke(2.dp, Color.Black)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxSize(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            if (it in movieUiState.wordTileStorage) {
                                Text(
                                    text = movieViewModel.movieEasyTiles[it].toString(),
                                    color = Color.Black,
                                    fontFamily = Spenbeb
                                )
                            }
                        }
                    }
                } // for words


                if (it in movieViewModel.numberingSystem.keys) { // for numbers
                    Surface(
                        modifier = Modifier.size(width = 20.dp, height = 30.dp),
                        color = Color.Transparent,
                        shape = RoundedCornerShape(3.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxSize(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = movieViewModel.numberingSystem[it].toString(),
                                color = Color.White,
                                fontSize = 27.sp,
                                fontFamily = Spenbeb,
                            )
                        }
                    }
                } // for numbers scope
            }
        }
    } // Game Tiles scope
}

@Composable
fun HintNotes(
    movieViewModel: MovieEasyViewModel,
    isHintVisible: Boolean
) {
    val lazyListState = rememberLazyListState()
    val snapBehavior = rememberSnapFlingBehavior(lazyListState = lazyListState)
    AnimatedVisibility(
        modifier = Modifier
            .size(width = 270.dp, height = 254.dp),
        visible = isHintVisible
    ) {
        LazyRow(
            state = lazyListState,
            modifier = Modifier
                .fillMaxSize(),
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            flingBehavior = snapBehavior
        ) {
            items(count = movieViewModel.easyMovieHintCount) {
                Box(
                    modifier = Modifier
                        .size(width = 270.dp, height = 254.dp)
                ) {
                    Image(
                        painter = painterResource(R.drawable.hint_note),
                        contentDescription = "",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.matchParentSize()
                    )

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 30.dp, vertical = 70.dp),
                    ) {
                        if (it in movieViewModel.movieEasyHint.keys) {
                            Text(
                                text = "#".plus(it+1),
                                fontSize = 18.sp,
                                fontFamily = Spenbeb,
                                color = onyx
                            )
                        }
                    } // for number

                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        if (it in movieViewModel.movieEasyHint.keys) {
                            movieViewModel.movieEasyHint[it]?.let { it1 ->
                                Text(
                                    text = it1,
                                    fontSize = 22.sp,
                                    fontFamily = Lalezar,
                                    color = onyx
                                )
                            }
                        }
                    } // for the description
                }
            }
        }
    }
}

@Composable
fun UserLives(
    modifier: Modifier = Modifier,
    movieUiState: MovieUiState
) {

    val infiniteTransition = rememberInfiniteTransition(label = "")

    
    val heartSize by infiniteTransition.animateValue(
        initialValue = 30.dp,
        targetValue = 43.dp,
        typeConverter = Dp.VectorConverter,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 700,
                easing = FastOutSlowInEasing
            ),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )

    Box(
        modifier
            .size(width = 150.dp, height = 62.dp)
            .background(darkGreen)
            .border(3.dp, Color.Black)
    ) {
        Row(
            modifier = Modifier.matchParentSize(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            when (movieUiState.gameLives) {
                3 -> {
                    Image(
                        painterResource(R.drawable.heart),
                        contentDescription = "User life",
                        modifier = Modifier.size(heartSize),
                    )
                    Image(
                        painterResource(R.drawable.heart),
                        contentDescription = "User life",
                        modifier = Modifier.size(heartSize),
                    )
                    Image(
                        painterResource(R.drawable.heart),
                        contentDescription = "User life",
                        modifier = Modifier.size(heartSize),
                    )
                }
                2 -> {
                    Icon(
                        painterResource(R.drawable.baseline_heart_broken_24),
                        contentDescription = "Broken heart",
                        tint = heartRed
                    )
                    Image(
                        painterResource(R.drawable.heart),
                        contentDescription = "User life",
                        modifier = Modifier.size(heartSize),
                    )
                    Image(
                        painterResource(R.drawable.heart),
                        contentDescription = "User life",
                        modifier = Modifier.size(heartSize),
                    )
                }
                1 -> {
                    Icon(
                        painterResource(R.drawable.baseline_heart_broken_24),
                        contentDescription = "Broken heart",
                        tint = heartRed
                    )
                    Icon(
                        painterResource(R.drawable.baseline_heart_broken_24),
                        contentDescription = "Broken heart",
                        tint = heartRed
                    )
                    Image(
                        painterResource(R.drawable.heart),
                        contentDescription = "User life",
                        modifier = Modifier.size(heartSize),
                    )
                }
                else -> {
                    Icon(
                        painterResource(R.drawable.baseline_heart_broken_24),
                        contentDescription = "Broken heart",
                        tint = heartRed
                    )
                    Icon(
                        painterResource(R.drawable.baseline_heart_broken_24),
                        contentDescription = "Broken heart",
                        tint = heartRed
                    )
                    Icon(
                        painterResource(R.drawable.baseline_heart_broken_24),
                        contentDescription = "Broken heart",
                        tint = heartRed
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun MoviePreview() {
    MovieScreen(navController = rememberNavController())
}

@Preview(showBackground = true)
@Composable
fun UserLivesPreview() {
    VTheme {
        UserLives(movieUiState = MovieUiState())
    }
}

@Preview(showBackground = true)
@Composable
fun HintNotePreview() {
    VTheme {
        HintNotes(isHintVisible = true, movieViewModel = MovieEasyViewModel())
    }
}

@Preview(showBackground = true)
@Composable
fun LevelCompletePreview(modifier: Modifier = Modifier) {
    LevelComplete(movieViewModel = MovieEasyViewModel())
}

@Preview
@Composable
fun LevelFailedPreview(modifier: Modifier = Modifier) {
    LevelFailed(movieViewModel = MovieEasyViewModel(), navController = rememberNavController())
}



