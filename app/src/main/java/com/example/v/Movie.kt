package com.example.v

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.v.ui.theme.Lalezar
import com.example.v.ui.theme.darkRed
import com.example.v.ui.theme.lightBlue
import com.example.v.ui.theme.navyBlue
import com.example.v.ui.theme.onyx
import com.example.v.ui.theme.pastelGreen

@Composable
fun MovieScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    movieViewModel: MovieViewModel = viewModel()
) {

    val movieUiState by movieViewModel.movieUiState.collectAsState()


    var backgroundColor by remember {
        mutableIntStateOf(1)
    }

    var isVisible by remember {
        mutableStateOf(false)
    }

    val colorChanger = when(backgroundColor){
        1 -> darkRed
        2 -> navyBlue
        else -> lightBlue
    }

    Box(
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

        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.End,
        ) {
            ButtonBar(
                backgroundColor = backgroundColor,
                onBackgroundChange = {
                    onClickChange -> backgroundColor = onClickChange
                },
                navController = navController,
                movieViewModel = movieViewModel,
                isVisible = isVisible,
                isVisibleChange = {
                    isVisibleChange -> isVisible = isVisibleChange
                },
            )
        }// Button Bar Column

        Column(
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
        }

        Column(
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
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 150.dp),
        ) {
            Box(
                modifier = Modifier
                    .size(width = 109.dp, height = 93.01.dp)
                    .fillMaxSize(),
            ){
                Image (
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
        }

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            GameTiles()
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 450.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            TextField(
                value = movieViewModel.userInput,
                onValueChange = {
                    movieViewModel.updateUserInput(it)
                                },
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        movieViewModel.ifUserInputCorrect()
                        // movieViewModel.resetUserInput()
                    }
                ),
            )
        }

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            HintBox(isVisible = isVisible)
        }


    }
}

@Composable
fun ButtonBar(
    modifier: Modifier = Modifier,
    movieViewModel: MovieViewModel,
    backgroundColor: Int,
    onBackgroundChange: (Int) -> Unit,
    isVisible: Boolean,
    isVisibleChange: (Boolean) -> Unit,
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
                IconButton(onClick = {
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
                IconButton(onClick = {
                    isVisibleChange(!isVisible)
                    SoundManager.clickSound()
                }) {
                    Icon(
                        painterResource(R.drawable.baseline_menu_book_24),
                        contentDescription = "",
                        tint = onyx,
                        modifier = Modifier.size(32.dp)

                    )
                } // HINT BOOK
                IconButton(onClick = {
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
                IconButton(onClick = {
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
fun HintBox(isVisible: Boolean) {
    AnimatedVisibility(
        visible = isVisible,
        modifier = Modifier.size(width = 337.dp, height = 601.dp)
    ) {
        Box {
            Image(
                painterResource(id = R.drawable.hintbook),
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier.matchParentSize()
            )

            Divider(
                thickness = 3.dp,
                color = Color.DarkGray,
                modifier = Modifier.padding(vertical = 145.dp, horizontal = 55.dp)
            )

            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.Top
            ) {
                Surface(
                    color = Color.Transparent,
                    modifier = Modifier.padding(horizontal = 18.dp, vertical = 50.dp)
                ) {
                    Icon(
                        painterResource(R.drawable.baseline_close_24),
                        contentDescription = "",
                        tint = darkRed,
                        modifier = Modifier.size(54.dp)
                    )
                }
            }

        }
    }
}

@Composable
fun GameTiles() {
    Surface(
        modifier = Modifier
            .size(width = 300.dp, height = 320.dp),
        color = onyx,
        shape = RoundedCornerShape(15.dp)
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(8),
            verticalArrangement = Arrangement.spacedBy(6.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
        ) {
            items(count = boxCountMovie1) {
                if (it in movieTilesNumber) {
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
                            // logic for texts
                        }
                    }
                }
            }
        }
    } // Game Tiles
}

@Preview
@Composable
fun MoviePreview() {
    MovieScreen(navController = rememberNavController())
}

@Preview
@Composable
fun hintBoxPreview() {
    HintBox(isVisible = true)
}