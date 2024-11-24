package com.example.v.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.v.R
import com.example.v.Screen
import com.example.v.ui.theme.Spenbeb
import com.example.v.ui.theme.anotherWhite
import com.example.v.ui.theme.cream
import com.example.v.ui.theme.darkRed
import com.example.v.ui.theme.disnep
import com.example.v.ui.theme.lightBlue
import com.example.v.ui.theme.marvel
import com.example.v.ui.theme.navyBlue
import com.example.v.ui.theme.onyx
import com.example.v.ui.theme.scifi

@Composable
fun GenreScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    font: FontFamily = Spenbeb,
) {

    var onClick by remember { mutableStateOf(false) }
    var easyScreen by remember { mutableStateOf<Screen?>(null) }
    var mediumScreen by remember { mutableStateOf<Screen?>(null) }
    var hardScreen by remember { mutableStateOf<Screen?>(null) }

    var animationClick by remember { mutableStateOf(false) }
    var scifiClick by remember { mutableStateOf(false) }
    var superheroClick by remember { mutableStateOf(false) }

    if (animationClick) {
        easyScreen = Screen.MovieEasy
        mediumScreen = Screen.MovieDisneyMedium
        hardScreen = Screen.MovieDisneyHard
    } else if (scifiClick) {
        easyScreen = null
        mediumScreen = null
        hardScreen = null
    } else if (superheroClick) {
        easyScreen = Screen.MovieSuperHeroEasy
        mediumScreen = null
        hardScreen = null
    }


    Box(
        modifier
            .fillMaxSize()
            .background(anotherWhite)
    ) {
        Box(
            modifier = Modifier
                .matchParentSize()
                .padding(12.dp),
            Alignment.TopEnd
        ) {
            ReusableNavigationButton(
                destination = Screen.CategoryScreen,
                textInButton = "Back",
                fontSize = 20.sp,
                navController = navController,
                font = Spenbeb
            )
        }

        Box(
            modifier = Modifier
                .matchParentSize()
                .padding(12.dp),
        ) {
            Text(
                text = "Select Genre",
                fontSize = 26.sp,
                fontFamily = font,
                modifier = Modifier.padding(12.dp),
                color = onyx
            )
        }

        Box(
            modifier = Modifier.matchParentSize(),
            Alignment.Center
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                AnimationGenreButton(
                    font = disnep,
                    onClick = onClick,
                    onClickChange = ({onClick = it}),
                    animationClick = animationClick,
                    animationClickChange = ({animationClick = it})
                )
                SciFiGenreButton(
                    font = scifi,
                    navController
                )
                SuperheroGenreButton(
                    font = marvel,
                    onClick = onClick,
                    onClickChange = ({onClick = it}),
                    superheroClick = superheroClick,
                    superheroClickChange = ({superheroClick = it})
                )
            }
        }

        Box(modifier = Modifier.fillMaxSize()) {
            DifficultySelector(
                onClick = onClick,
                onClickChange = ({ onClick = it }),
                navController = navController,
                easyScreen = easyScreen,
                mediumScreen = mediumScreen,
                hardScreen = hardScreen
            )
        }
    }
}

@Composable
fun AnimationGenreButton(
    font: FontFamily,
    onClick: Boolean,
    onClickChange: (Boolean) -> Unit,
    animationClick: Boolean,
    animationClickChange: (Boolean) -> Unit,
) {
    Card(
        modifier = Modifier
            .size(width = 300.dp, height = 93.dp)
            .clickable {
                animationClickChange(!animationClick)
                onClickChange(!onClick)
                       },
        colors = CardDefaults.cardColors(lightBlue),
        elevation = CardDefaults.cardElevation(defaultElevation = 12.dp)
    ) {
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(12.dp)) {
            Row(
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    text = "Disney Animations",
                    fontFamily = font,
                    fontSize = 24.sp,
                    color = cream
                )
                Spacer(modifier = Modifier.width(12.dp))
                Box(
                    modifier = Modifier.size(width = 64.dp, height = 66.dp),
                ) {
                    Image(
                        painterResource(R.drawable.pngmickeymouse),
                        contentDescription = "",
                        modifier = Modifier.matchParentSize()
                    )
                }
            }
        }

    }
}

@Composable
fun SciFiGenreButton(
    font: FontFamily,
    navController: NavController
) {
    Card(
        modifier = Modifier
            .size(width = 300.dp, height = 93.dp)
            .clickable { navController.navigate(Screen.CategoryScreen) },
        colors = CardDefaults.cardColors(Color.LightGray),
        elevation = CardDefaults.cardElevation(defaultElevation = 12.dp)
    ) {
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(12.dp)) {
            Row(
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    text = "Science Fiction",
                    fontFamily = font,
                    fontSize = 22.sp,
                    color = navyBlue
                )
                Spacer(modifier = Modifier.width(8.dp))
                Box(
                    modifier = Modifier.size(width = 41.dp, height = 93.dp),
                ) {
                    Image(
                        painterResource(R.drawable.scifi),
                        contentDescription = "",
                        modifier = Modifier.matchParentSize()
                    )
                }
            }
        }
    }
}

@Composable
fun SuperheroGenreButton(
    font: FontFamily,
    onClick: Boolean,
    onClickChange: (Boolean) -> Unit,
    superheroClick: Boolean,
    superheroClickChange:(Boolean) -> Unit,
) {
    Card(
        modifier = Modifier
            .size(width = 300.dp, height = 93.dp)
            .clickable {
                superheroClickChange(!superheroClick)
                onClickChange(!onClick)
                       },
        colors = CardDefaults.cardColors(darkRed),
        elevation = CardDefaults.cardElevation(defaultElevation = 12.dp)
    ) {
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(12.dp)) {
            Row(
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    text = "Marvel",
                    fontFamily = font,
                    fontSize = 32.sp,
                    color = cream
                )
                Spacer(modifier = Modifier.width(100.dp))
                Box(
                    modifier = Modifier.size(width = 96.dp, height = 94.dp),
                    ) {
                    Image(
                        painterResource(R.drawable.spiderman),
                        contentDescription = "",
                        modifier = Modifier.matchParentSize()
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun GenreScreenPreview() {
    GenreScreen(navController = rememberNavController())
}