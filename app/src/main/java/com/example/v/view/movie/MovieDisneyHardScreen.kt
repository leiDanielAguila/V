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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.v.R
import com.example.v.Screen
import com.example.v.model.MovieViewModel
import com.example.v.ui.theme.Lalezar
import com.example.v.ui.theme.darkRed
import com.example.v.ui.theme.disnep
import com.example.v.view.GameTiles
import com.example.v.view.HintNotes
import com.example.v.view.MovieTicketHeader
import com.example.v.view.ReusableNavigationButton
import com.example.v.view.ScoreCard
import com.example.v.view.TextFieldInput

@Composable
fun MovieDisneyHardMainScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
) {
    val movieViewModel = remember { MovieViewModel() }
    var isHintVisible by remember { mutableStateOf(false) }
    var isSettingVisible by remember { mutableStateOf(false) }
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
            MovieTicketHeader(header = "Disney")
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
            modifier.fillMaxSize().padding(),
            Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                GameTiles(
                    movieViewModel = movieViewModel,
                    outerBoxWidth = 340.dp,
                    outerBoxHeight = 420.dp,
                    textBoxWidth = 30.dp,
                    textBoxHeight = 30.dp,
                    tilesCount = movieViewModel.gameTilesDisneyHardCount,
                    movieTiles = movieViewModel.movieDisneyHardTiles,
                    movieNumberTiles = movieViewModel.movieDisneyHardNumberTiles,
                    gridCount = movieViewModel.movieDisneyHardGridCount
                )
                Spacer(modifier.height(22.dp))
                TextFieldInput(
                    movieViewModel = movieViewModel,
                    onDone = {
                        movieViewModel.checkUserInput(movieWords = movieViewModel.movieDisneyHardWords)
                    },
                )
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
                    hintCount = movieViewModel.hintNoteDisneyHard, // remove 10 before applying to other composable
                    headerFontFamily = disnep,
                    bodyFontFamily = Lalezar,
                    movieViewModel = movieViewModel,
                    movieHint = movieViewModel.movieDisneyHardHints, // change depending on the difficulty level
                    movieYear = movieViewModel.movieDisneyHardYears // change depending on movies
                )
            }
        }
    }
}



@Preview
@Composable
fun MovieDisneyHardPreview(modifier: Modifier = Modifier) {
    MovieDisneyHardMainScreen(navController = rememberNavController())
}





