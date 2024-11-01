package com.example.v.view.movie

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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.v.R
import com.example.v.Screen
import com.example.v.service.SoundManager
import com.example.v.ui.theme.darkRed
import com.example.v.ui.theme.disnep
import com.example.v.ui.theme.lightGreen
import com.example.v.ui.theme.lightRed
import com.example.v.ui.theme.onyx
import com.example.v.view.ReusableNavigationButton
import com.example.v.view.ScoreCard

@Composable
fun MovieEasyMainScreen(
    modifier: Modifier = Modifier,
    navController: NavController
) {

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
    }
}

@Composable
fun MovieTicketHeader(
    modifier: Modifier = Modifier,
    header: String
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
            fontFamily = disnep,
            fontSize = 32.sp,
            color = Color.White
        )
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
fun MovieEasyMainScreenPreview() {
    MovieEasyMainScreen(navController = rememberNavController())
}



