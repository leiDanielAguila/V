package com.example.v

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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.v.ui.theme.Yellow
import com.example.v.ui.theme.cream
import com.example.v.ui.theme.darkRed
import com.example.v.ui.theme.lightBlue
import com.example.v.ui.theme.navyBlue
import com.example.v.ui.theme.onyx
import com.example.v.ui.theme.pastelGreen

@Composable
fun MovieScreen(modifier: Modifier = Modifier, navController: NavController) {

    var backgroundColor by remember {
        mutableIntStateOf(1)
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
                navController = navController
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
                .fillMaxSize(),
        ) {
            Image(
                painterResource(R.drawable.score_movie),
                contentDescription = "",
                modifier = Modifier
                    .padding(vertical = 125.dp, horizontal = 12.dp),
                )
        }

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            GameTiles()
        }

    }
}

@Composable
fun ButtonBar(
    modifier: Modifier = Modifier,
    backgroundColor: Int,
    onBackgroundChange: (Int) -> Unit,
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
                IconButton(onClick = { navController.navigate(CategoryScreen) }) {
                    Icon(
                        painterResource(R.drawable.baseline_exit_to_app_24),
                        contentDescription = "",
                        tint = onyx,
                        modifier = Modifier.size(32.dp)
                    )
                } // EXIT
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        painterResource(R.drawable.baseline_menu_book_24),
                        contentDescription = "",
                        tint = onyx,
                        modifier = Modifier.size(32.dp)

                    )
                } // HINT BOOK
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        painterResource(R.drawable.baseline_lightbulb_24),
                        contentDescription = "",
                        tint = onyx,
                        modifier = Modifier.size(32.dp)
                    )
                } // LIGHT BULB
                IconButton(onClick = {
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
fun MovieInputBox(modifier: Modifier = Modifier) {
    
}

@Composable
fun GameTiles() {
    Surface(
        modifier = Modifier.size(width = 300.dp, height = 300.dp),
        color = Color.Transparent,
        shape = RoundedCornerShape(15.dp)
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(8),
            verticalArrangement = Arrangement.spacedBy(6.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(4.dp)
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
                            if (it == 1) {
                                Text(text = "A")
                            }
                            else if (it == 13) {
                                Text(text = "B")
                            }
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