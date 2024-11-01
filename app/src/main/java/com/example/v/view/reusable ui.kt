package com.example.v.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.v.R
import com.example.v.Screen
import com.example.v.service.SoundManager
import com.example.v.ui.theme.Lalezar
import com.example.v.ui.theme.Spenbeb
import com.example.v.ui.theme.anotherWhite
import com.example.v.ui.theme.darkRed
import com.example.v.ui.theme.darkYellow
import com.example.v.ui.theme.lightGreen
import com.example.v.ui.theme.lightRed
import com.example.v.ui.theme.onyx

@Composable
fun ReusableNavigationButton(
    destination: Screen,
    textInButton: String,
    fontSize: TextUnit,
    navController: NavController
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
            fontFamily = Spenbeb
        )
    }
}

@Composable
fun DifficultySelector(
    modifier: Modifier = Modifier,
    onClick: Boolean,
    onClickChange: (Boolean) -> Unit,
    navController: NavController
) {
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
                        navController.navigate(Screen.MovieEasy.route)
                    },
                    elevation = ButtonDefaults.elevatedButtonElevation(20.dp),
                    colors = ButtonDefaults.buttonColors(lightGreen)
                ) {
                    // easy button
                    Text("Easy", fontSize = 32.sp, fontFamily = Spenbeb)
                }

                Button(
                    onClick = { /*TODO*/ },
                    elevation = ButtonDefaults.elevatedButtonElevation(20.dp),
                    colors = ButtonDefaults.buttonColors(darkYellow)
                ) {
                    // medium button
                    Text("Medium", fontSize = 32.sp, fontFamily = Spenbeb)
                }

                Button(
                    onClick = {},
                    elevation = ButtonDefaults.elevatedButtonElevation(20.dp),
                    colors = ButtonDefaults.buttonColors(darkRed)
                ) {
                    // hard button
                    Text("Hard", fontSize = 32.sp, fontFamily = Spenbeb)
                }
            }
        }
    }
}

@Composable
fun ScoreCard(modifier: Modifier = Modifier) {
    Box(
        modifier.size(width = 109.dp, height = 93.01.dp).background(Color.Transparent)
    ) {
        Image ( // SCORE CARD IMAGE
            painterResource(R.drawable.score_movie),
            contentDescription = "score card across the game",
            contentScale = ContentScale.Fit,
        )

        Column(
            modifier.matchParentSize().padding(top = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "10",
                fontSize = 28.sp,
                fontFamily = Spenbeb,
                color = Color.White
            )
        }
    }
}