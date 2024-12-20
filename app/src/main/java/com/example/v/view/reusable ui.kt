package com.example.v.view

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.animateValue
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.v.R
import com.example.v.Screen
import com.example.v.data.AppDatabase
import com.example.v.model.MovieViewModel
import com.example.v.service.SoundManager
import com.example.v.ui.theme.BackgroundScreenColor
import com.example.v.ui.theme.Lalezar
import com.example.v.ui.theme.Spenbeb
import com.example.v.ui.theme.Yellow
import com.example.v.ui.theme.anotherWhite
import com.example.v.ui.theme.cream
import com.example.v.ui.theme.darkGreen
import com.example.v.ui.theme.darkYellow
import com.example.v.ui.theme.lightGreen
import com.example.v.ui.theme.lightRed
import com.example.v.ui.theme.onyx
import kotlinx.coroutines.delay

@Composable
fun ReusableNavigationButton(
    destination: Screen,
    textInButton: String,
    fontSize: TextUnit,
    navController: NavController,
    font: FontFamily = Spenbeb
) {
    Button(
        onClick = {
            navController.navigate(destination.route)
            SoundManager.clickSound()
        },
        elevation = ButtonDefaults.elevatedButtonElevation(30.dp),
        colors = ButtonDefaults.buttonColors(lightRed)
    ) {
        Text(
            text = textInButton,
            color = onyx,
            fontSize = fontSize,
            fontFamily = font
        )
    }
}

@Composable
fun BuyScreen(
    modifier: Modifier = Modifier,
    openBuyScreen: Boolean,
    openBuyScreenChange: (Boolean) -> Unit,
    screenToBuy: Screen?,
    thankYouModule: Boolean,
    thankYouModuleChange: (Boolean) -> Unit,
    notEnoughUserScore: Boolean,
    notEnoughUserScoreChange: (Boolean) -> Unit
) {

    val context = LocalContext.current
    val movieDao = remember { AppDatabase.getDatabase(context).newMovieDao() }
    val movieViewModel = remember { MovieViewModel(movieDao) }
    val movieState by movieViewModel.movieState.collectAsState()

    var movieValue by remember { mutableIntStateOf(0) }

    movieValue = when (screenToBuy)  {
        Screen.MovieDisneyMedium -> 60
        Screen.MovieSuperHeroEasy -> 100
        Screen.MovieDisneyHard -> 40
        // for scifi
        else -> throw IllegalArgumentException("Invalid screen")
    }

    Box(
        modifier
            .size(320.dp)
            .clip(shape = RoundedCornerShape(15.dp))
            .background(BackgroundScreenColor)
    ) {
        Column(
            modifier
                .fillMaxSize()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                text = "Unlock difficulty?",
                color = cream,
                fontFamily = Spenbeb,
                fontSize = 22.sp
            )

            Text(
                text = "$movieValue user score required for this difficulty",
                color = Color.White,
                fontFamily = Lalezar,
                fontSize = 16.sp
            )

            Row(
                modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = {
                        //movieViewModel.buyDifficulty(screenToBuy = screenToBuy)
                        if (movieViewModel.buyDifficulty(screenToBuy)) { // purchased
                            thankYouModuleChange(true)
                            notEnoughUserScoreChange(false)
                            Log.w("BuyScreen", "Purchased")
                        } else { // !purchased
                            notEnoughUserScoreChange(true)
                            thankYouModuleChange(false)
                            Log.w("BuyScreen", "Not purchased")
                        }
                        openBuyScreenChange(!openBuyScreen) // to exit after buying
                    },
                    colors = ButtonDefaults.buttonColors(lightGreen)
                ) {
                    Text(
                        text = "Yes",
                        fontSize = 18.sp,
                        fontFamily = Spenbeb
                    )
                }
                Button(
                    onClick = {
                        openBuyScreenChange(!openBuyScreen)
                    },
                    colors = ButtonDefaults.buttonColors(Color.Red)
                ) {
                    Text(
                        text = "No",
                        fontSize = 18.sp,
                        fontFamily = Spenbeb
                    )
                }
            }
        }
    }
}

@Composable
fun DifficultySelector(
    modifier: Modifier = Modifier,
    onClick: Boolean,
    onClickChange: (Boolean) -> Unit,
    openBuyClick: Boolean,
    openBuyClickChange: (Boolean) -> Unit,
    navController: NavController,
    screenToBuy: Screen?,
    screenToBuyChange: (Screen?) -> Unit,
    easyScreen: Screen?,
    mediumScreen: Screen?,
    hardScreen: Screen?
) {

    val context = LocalContext.current
    val movieDao = remember { AppDatabase.getDatabase(context).newMovieDao() }
    val movieViewModel = remember { MovieViewModel(movieDao) }
    val movieState by movieViewModel.movieState.collectAsState()

    val isDisneyMediumUnlocked = movieState.disneyMediumUnlocked
    val isDisneyHardUnlocked = movieState.disneyHardUnlocked
    val isSuperheroEasyUnlocked = movieState.superheroEasyUnlocked

    AnimatedVisibility(
        visible = onClick,
        enter = slideInHorizontally{ it },
        exit = slideOutHorizontally { it },
    ) {
        Box(
            modifier
                .fillMaxSize()
                .background(anotherWhite)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.End,
            ) {
                // exit button
                IconButton(
                    onClick = {
                        onClickChange(!onClick)
                        SoundManager.clickSound()
                    },
                ) {
                    Icon(
                        painterResource(id = R.drawable.baseline_close_24),
                        contentDescription = "Close",
                        modifier = Modifier.size(78.dp),
                        tint = Color.Red
                    )
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly,

                ) {
                // text for difficulty selector
                Text(
                    "Select Difficulty",
                    fontSize = 48.sp,
                    fontFamily = Lalezar,
                    color = Color.Black
                )

                Button(
                    onClick = {
                        SoundManager.clickSound()
                        if (!isSuperheroEasyUnlocked && easyScreen != Screen.MovieEasy) {
                            openBuyClickChange(!openBuyClick)
                            screenToBuyChange(easyScreen)
                        } else if (easyScreen != null) {
                            navController.navigate(easyScreen.route)
                        } else {
                            throw IllegalArgumentException("Invalid mediumScreen value or buy screen error")
                        }
                    },
                    elevation = ButtonDefaults.elevatedButtonElevation(20.dp),
                    colors = ButtonDefaults.buttonColors(lightGreen)
                ) {
                    // easy button
                    Text("Easy", fontSize = 32.sp, fontFamily = Spenbeb)

                }

                Button(
                    onClick = {
                        SoundManager.clickSound()
                        if (!isDisneyMediumUnlocked) {
                            openBuyClickChange(!openBuyClick)
                            screenToBuyChange(mediumScreen)
                        } else if (mediumScreen != null) {
                            navController.navigate(mediumScreen.route)
                        } else {
                            throw IllegalArgumentException("Invalid mediumScreen value or buy screen error")
                        }
                    },
                    elevation = ButtonDefaults.elevatedButtonElevation(20.dp),
                    colors = ButtonDefaults.buttonColors(darkYellow)
                ) {
                    // medium button
                    Text("Medium", fontSize = 32.sp, fontFamily = Spenbeb)
//                    AnimatedVisibility(visible = movieState.disneyMediumUnlocked) {
//                        Icon(
//                            painterResource(R.drawable.baseline_lock_24),
//                            contentDescription = ""
//                        )
//                    }
                }

                Button(
                    onClick = {
                        SoundManager.clickSound()
                        if (!isDisneyHardUnlocked) {
                            openBuyClickChange(!openBuyClick)
                            screenToBuyChange(hardScreen)
                        } else if (hardScreen != null) {
                            navController.navigate(hardScreen.route)
                        } else {
                            throw IllegalArgumentException("Invalid mediumScreen value or buy screen error")
                        }
                    },
                    elevation = ButtonDefaults.elevatedButtonElevation(20.dp),
                    colors = ButtonDefaults.buttonColors(Color.Red)
                ) {
                    // hard button
                    Text("Hard", fontSize = 32.sp, fontFamily = Spenbeb)
                    AnimatedVisibility(visible = isDisneyHardUnlocked) {

                    }
                }
            }
        }
    }
}


@Composable
fun GameOver(
    screen: Screen,
    onClick: Boolean,
    onClickChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    text: String,
    color: Color,
    font: FontFamily,
    navController: NavController,
    lose: Boolean,
    movieID: Int
) {
    Box(
        modifier
            .size(width = 250.dp, height = 241.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(color)

    ) {
        Column(
            modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = text,
                fontSize = 22.sp,
                color = cream,
                fontFamily = font
            )

            ShowLevelButton(
                onClickLevelDone = onClick,
                onClickLevelDoneChange = onClickChange,
                navController = navController,
                screen = screen,
                lose = lose,
                movieID = movieID
            )


            ReusableNavigationButton(
                Screen.CategoryScreen,
                textInButton = "Menu",
                20.sp,
                navController = navController
            )
        }
    }
}

@Composable
fun ShowLevelButton(
    screen: Screen,
    onClickLevelDone: Boolean,
    onClickLevelDoneChange: (Boolean) -> Unit,
    navController: NavController,
    font: FontFamily = Spenbeb,
    lose: Boolean,
    movieID: Int
) {
    val context = LocalContext.current
    val movieDao = remember { AppDatabase.getDatabase(context).newMovieDao() }
    val movieViewModel = remember { MovieViewModel(movieDao) }
    // State to track the countdown
    var countdown by remember { mutableIntStateOf(9) }
    var countdownColor by remember { mutableStateOf(onyx) }
    var countdownFontSize by remember { mutableStateOf(18.sp) }

    // timer
    LaunchedEffect(key1 = countdown) {
        if (countdown > 0) {
            if(countdown == 3) {
                countdownColor = Color.Red
                countdownFontSize = 25.sp
            }
            delay(1000)
            countdown -= 1
        }
    }

    Button(
        onClick = {
            navController.navigate(screen.route)
            onClickLevelDoneChange(!onClickLevelDone)
            movieViewModel.tryAgain(movieID)
        },
        elevation = ButtonDefaults.elevatedButtonElevation(30.dp),
        colors = ButtonDefaults.buttonColors(lightRed)
    ) {
        // Row for placing countdown and text side by side
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "$countdown", // Display countdown number
                color = countdownColor,
                fontSize = countdownFontSize,
                fontFamily = font
            )
            if (lose) {
                Spacer(modifier = Modifier.padding(8.dp))
                Text(
                    text = "Try Again?",
                    color = onyx,
                    fontSize = 18.sp,
                    fontFamily = font
                )
            }
        }
    }
}


@Composable
fun GameHearts(
    modifier: Modifier = Modifier,
    movieViewModel: MovieViewModel,
    movieID: Int
) {
    val infiniteTransition = rememberInfiniteTransition(label = "")

    val movieState by movieViewModel.movieState.collectAsState()


    val lives = when (movieID) {
        1 -> movieState.disneyEasyGameLives
        2 -> movieState.disneyMediumGameLives
        3 -> movieState.disneyHardGameLives
        4 -> movieState.superheroEasyGameLives
        else -> throw IllegalArgumentException("Invalid movieID: $movieID")
    }

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
            val totalLives = 3
            (0 until totalLives).forEach { index ->
                if (index < lives) {
                    // Display a full heart for remaining lives
                    Image(
                        painter = painterResource(R.drawable.heart),
                        contentDescription = "User life",
                        modifier = Modifier.size(heartSize)
                    )
                } else {
                    // Display a broken heart for lost lives
                    Icon(
                        painter = painterResource(R.drawable.baseline_heart_broken_24),
                        contentDescription = "Broken heart",
                        tint = Color.LightGray
                    )
                }
            }
        }
    }
}


@Composable
fun ScoreCard(
    modifier: Modifier = Modifier,
    movieViewModel: MovieViewModel
) {
    val movieState by movieViewModel.movieState.collectAsState()
    Box(
        modifier
            .size(width = 109.dp, height = 93.01.dp)
            .background(Color.Transparent)
    ) {
        Image ( // SCORE CARD IMAGE
            painterResource(R.drawable.score_movie),
            contentDescription = "score card across the game",
            contentScale = ContentScale.Fit,
        )

        Column(
            modifier
                .matchParentSize()
                .padding(top = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = movieState.userScore.toString(),
                fontSize = 28.sp,
                fontFamily = Spenbeb,
                color = Color.White
            )
        }
    }
}

@Composable
fun MovieTicketHeader(
    modifier: Modifier = Modifier,
    header: String,
    font: FontFamily
) {
    Box(
        modifier.size(width = 168.dp, height = 113.dp),
        Alignment.Center
    ) {
        Image(
            painterResource(R.drawable.newmovieticket),
            contentDescription = "Movie Ticket",
            contentScale = ContentScale.Crop,
            modifier = Modifier.matchParentSize()
        )

        Text(
            text = header,
            fontFamily = font,
            fontSize = 32.sp,
            color = Color.White
        )
    }
}

@Composable
fun GameTiles(
    modifier: Modifier = Modifier,
    movieViewModel: MovieViewModel,
    tilesCount: Int, // the overall tiles count
    movieTiles: Map<Int, Char>, // for movie tiles refer to viewmodel and the actual tiles
    movieNumberTiles: Map<Int, Any>, // for number tiles
    outerBoxWidth: Dp = 400.dp,
    outerBoxHeight: Dp = 400.dp,
    textBoxHeight: Dp = 30.dp,
    textBoxWidth: Dp = 20.dp,
    gridCount: Int, // for the grid count inside the box {the width}
    boxColor: Color,
    movieID: Int,
) {
    // change later to movieState
    // for debugging remove if app crashes
    val movieState by movieViewModel.movieState.collectAsState()

    val set = when(movieID) {
        1 -> movieState.disneyEasyTileStorage
        2 -> movieState.disneyMediumTileStorage
        3 -> movieState.disneyHardTileStorage
        4 -> movieState.superheroEasyTileStorage
        else -> throw IllegalArgumentException("Invalid movieID: $movieID")
    }
    Surface(
        modifier
            .size(width = outerBoxWidth, height = outerBoxHeight),
        color = onyx,
        shape = RoundedCornerShape(10.dp)
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(gridCount),
            verticalArrangement = Arrangement.spacedBy(2.dp),
            horizontalArrangement = Arrangement.spacedBy(2.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
        ) {
            items(count = tilesCount) {
                if (it in movieTiles && it !in set) {
                    Surface(
                        modifier = Modifier.size(width = textBoxWidth, height = textBoxHeight),
                        color = boxColor,
                        shape = RoundedCornerShape(5.dp),
                        border = BorderStroke(2.dp, Color.Black)
                    ) { // for text
                        Row(
                            modifier = Modifier.fillMaxSize(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
//                            if (it in movieUiState.wordTileStorage) {
//                                Text(
//                                    text = "${movieTiles[it]}",
//                                    color = Color.Black,
//                                    fontFamily = Spenbeb
//                                )
//                            }
                        }

                    }
                } else if (it in movieNumberTiles.keys) { // for numbers
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
                                text = movieNumberTiles[it].toString(),
                                color = Color.White,
                                fontSize = 18.sp,
                                fontFamily = Spenbeb,
                            )
                        }
                    }
                } else if (it in movieTiles && (it in set)) {
                    Surface(
                        modifier = Modifier.size(width = textBoxWidth, height = textBoxHeight),
                        color = Color.Green,
                        shape = RoundedCornerShape(5.dp),
                        border = BorderStroke(2.dp, Color.Black)
                    ) { // for text
                        Row(
                            modifier = Modifier.fillMaxSize(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            if (it in set) {
                                Text(
                                    text = "${movieTiles[it]}",
                                    color = Color.Black,
                                    fontFamily = Spenbeb
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TextFieldInput(
    movieViewModel: MovieViewModel,
    onDone: () -> Unit,
    labelText: String = "Enter Guess here...",
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    TextField(
        value = movieViewModel.userInput,
        onValueChange = { newUserInput ->
            movieViewModel.updateUserInput(newUserInput)
        },
        keyboardActions = KeyboardActions(
            onDone = {
                onDone()
                keyboardController?.hide()
            }
        ),
        singleLine = true,
        textStyle = TextStyle(textAlign = TextAlign.Center),
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Done,
        ),
        label = {Text(text = labelText)}
//        leadingIcon = {
//            Icon(imageVector = Icons.AutoMirrored.Filled.Send, contentDescription = "", tint = Color.LightGray)
//        }

    )
} // USER INPUT FIELD


@Composable
fun HintNotes(
    modifier: Modifier = Modifier,
    category: String,
    hintCount: Int = 10, // remove 10 before applying to other composable
    headerFontFamily: FontFamily,
    bodyFontFamily: FontFamily,
    movieViewModel: MovieViewModel,
    movieHint: Map<Int, String> = movieViewModel.movieEasyHints, // change depending on the difficulty level
    movieYear: Map<Int, String> = movieViewModel.movieEasyYears // change depending on movies
) {
    val lazyListState = rememberLazyListState()
    val snapBehavior = rememberSnapFlingBehavior(lazyListState = lazyListState)
    LazyRow(
        state = lazyListState,
        modifier = Modifier
            .fillMaxSize(),
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        flingBehavior = snapBehavior
    ) {
        items(count = hintCount) {
            Card(
                modifier.size(width = 270.dp, height = 254.dp),
                elevation = CardDefaults.cardElevation(12.dp),
                colors = CardDefaults.cardColors(Yellow)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                ) {
                    Text(
                        text = category,
                        fontFamily = headerFontFamily,
                        fontSize = 22.sp
                    )
                    Spacer(modifier = Modifier.padding(horizontal = 4.dp))
                    Text(
                        text = "Hint",
                        fontFamily = headerFontFamily,
                        fontSize = 22.sp
                    )
                    if (it in movieHint.keys) {
                        Text(
                            text = "#".plus(it+1),
                            fontFamily = headerFontFamily,
                            fontSize = 22.sp
                        )
                    }
                    Spacer(modifier = Modifier.padding(horizontal = 15.dp))
                    if (it in movieYear.keys) {  // Check if 'it' is a valid key
                        Text(
                            text = "${movieYear[it]}",
                            fontFamily = bodyFontFamily,
                            fontSize = 18.sp
                        )
                    }

                } // row scope

                Column(
                    modifier
                        .fillMaxSize()
                        .padding(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    if (it in movieHint.keys) {
                        Text(
                            text = "${movieHint[it]}",
                            fontFamily = bodyFontFamily,
                            fontSize = 22.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ThankYouModule(modifier: Modifier = Modifier) {
    Box(
        modifier
            .size(320.dp)
            .padding(12.dp)
            .clip(shape = RoundedCornerShape(15.dp))
            .background(BackgroundScreenColor),
        Alignment.Center
    ) {
        Column(
            modifier = Modifier.padding(12.dp).fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                text = "Difficulty unlocked!",
                color = Color.Green,
                fontFamily = Spenbeb,
                fontSize = 22.sp
            )
            Text(
                text = "Click difficulty again to play",
                color = cream,
                fontFamily = Lalezar,
                fontSize = 18.sp
            )
        }
    }
}

@Preview
@Composable
fun Thankyoumodule(modifier: Modifier = Modifier) {
    ThankYouModule()
}

