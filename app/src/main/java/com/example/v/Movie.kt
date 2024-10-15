package com.example.v

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieAnimatable
import com.airbnb.lottie.compose.rememberLottieComposition
import androidx.compose.material3.TextField
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import com.example.v.ui.theme.Lalezar
import com.example.v.ui.theme.Spenbeb
import com.example.v.ui.theme.VTheme
import com.example.v.ui.theme.darkRed
import com.example.v.ui.theme.lightBlue
import com.example.v.ui.theme.lightGreen
import com.example.v.ui.theme.navyBlue
import com.example.v.ui.theme.onyx
import com.example.v.ui.theme.pastelGreen
import kotlinx.coroutines.delay
import kotlin.math.roundToInt

// Main screen for Movie easy difficulty
@Composable
fun MovieScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    movieViewModel: MovieViewModel = viewModel()
) {

    val movieUiState by movieViewModel.movieUiState.collectAsState() // connects to app logic

    val confetti by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.confetti))
    val correct by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.correctword))
    
    val correctProgress by animateLottieCompositionAsState(
        composition = correct,
        speed = 0.7F,
        iterations = 1,
    )

    var isHintVisible by remember { // boolean for hint note
        mutableStateOf(false)
    }

    var backgroundColor by remember { // value for background color
        mutableIntStateOf(1)
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
                .padding(top = 220.dp),
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
                    Icon(imageVector = Icons.Default.Send, contentDescription = "", tint = Color.LightGray)
                }

            )
        } // USER INPUT FIELD

        
        Column( // DISPLAY WHEN GAME OVER
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {

            AnimatedVisibility(
                visible = movieUiState.isGameOver
            ) {

                Surface(
                    modifier = Modifier
                        .size(width = 303.dp, height = 263.dp),
                    color = lightGreen,
                    shape = RoundedCornerShape(15.dp),
                    border = BorderStroke(3.dp, Color.Black)
                ) {

                }
            }
        } // DISPLAY WHEN GAME OVER

        Column( // LOTTIE ANIMATION
            modifier = Modifier.fillMaxSize()
        ) {
            if (movieUiState.isGameOver) {
                SoundManager.win()
                LottieAnimation(composition = confetti, iterations = 5)
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
            HintNotes(isHintVisible = isHintVisible)
        } // HINT NOTES

    }
}

@Composable
fun ButtonBar(
    modifier: Modifier = Modifier,
    movieViewModel: MovieViewModel,
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
                    Icon(
                        painterResource(R.drawable.baseline_menu_book_24),
                        contentDescription = "",
                        tint = onyx,
                        modifier = Modifier.size(32.dp)

                    )
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

//@Composable
//fun HintBox(
//    isVisible: Boolean,
//    isVisibleChange:(Boolean) -> Unit
//) {
//    AnimatedVisibility(
//        visible = isVisible,
//        modifier = Modifier.size(width = 337.dp, height = 601.dp)
//    ) {
//        Box {
//            Image(
//                painterResource(id = R.drawable.hintbook),
//                contentDescription = "",
//                contentScale = ContentScale.Crop,
//                modifier = Modifier.matchParentSize()
//            )
//
//            Divider(
//                thickness = 3.dp,
//                color = Color.DarkGray,
//                modifier = Modifier.padding(vertical = 145.dp, horizontal = 55.dp)
//            )
//
//            Column(
//                modifier = Modifier.fillMaxSize(),
//                horizontalAlignment = Alignment.End,
//                verticalArrangement = Arrangement.Top
//            ) {
//                Surface(
//                    color = Color.Transparent,
//                    modifier = Modifier
//                        .padding(horizontal = 18.dp, vertical = 50.dp)
//                        .clickable {
//                            SoundManager.clickSound()
//                            isVisibleChange(!isVisible)
//                        }
//                ) {
//                    Icon(
//                        painterResource(R.drawable.baseline_close_24),
//                        contentDescription = "",
//                        tint = darkRed,
//                        modifier = Modifier.size(54.dp)
//                    )
//                }
//            }
//
//        }
//    }
//}

@Composable
fun GameTiles(movieViewModel: MovieViewModel) {

    val movieUiState by movieViewModel.movieUiState.collectAsState()
    Surface(
        modifier = Modifier
            .size(width = 320.dp, height = 350.dp),
        color = onyx,
        shape = RoundedCornerShape(15.dp)
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(9),
            verticalArrangement = Arrangement.spacedBy(6.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
        ) {
            items(count = boxCountMovie1) {
                if (it in movieEasyTiles.keys) { // for words
                    Surface(
                        modifier = Modifier.size(width = 20.dp, height = 30.dp),
                        color = Color.White,
                        shape = RoundedCornerShape(3.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxSize(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            if (it in movieUiState.wordTileStorage) {
                                Text(
                                    text = movieEasyTiles[it].toString(),
                                    color = Color.Black,
                                    fontFamily = Spenbeb
                                )
                            }
                        }
                    }
                } // for words


                if (it in numberingSystem.keys) { // for numbers
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
                                text = numberingSystem[it].toString(),
                                color = Color.White,
                                fontSize = 27.sp,
                                fontFamily = Spenbeb
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
    modifier: Modifier = Modifier,
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
            items(count = easyMovieHintCount) {
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
                        Text(
                            text = "#2",
                            fontSize = 18.sp,
                            fontFamily = Spenbeb
                        )
                    }

                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "A magical ice queen",
                            fontSize = 18.sp,
                            fontFamily = Lalezar
                        )
                    }
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
fun HintNotePreview() {
    VTheme {
        HintNotes(isHintVisible = true)
    }
}

