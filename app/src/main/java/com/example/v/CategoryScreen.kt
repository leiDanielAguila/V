package com.example.v

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.AbsoluteAlignment
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.v.ui.theme.BackgroundScreenColor
import com.example.v.ui.theme.Lalezar
import com.example.v.ui.theme.Yellow
import com.example.v.ui.theme.differentBlack
import com.example.v.ui.theme.lightRed

@Composable
fun CategoryScreen(modifier: Modifier = Modifier, navController: NavController) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(BackgroundScreenColor),
        contentAlignment = Alignment.TopCenter
    ) {
        Image(
            painter = painterResource(R.drawable.letters),
            contentDescription = "background letters",
            contentScale = ContentScale.Crop,
            modifier = Modifier.matchParentSize()
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 36.dp),
        ) {
            BackButton(navController = navController)
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 100.dp)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
           // verticalArrangement = Arrangement.SpaceEvenly,
        ) {
            MovieCard(navController = navController)
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            // verticalArrangement = Arrangement.SpaceEvenly,
        ) {
            TechnologyCard()
        }
    }
}

@Composable
fun MovieCard(modifier: Modifier = Modifier, navController: NavController) {
    Box(
        modifier = Modifier
            .clickable { navController.navigate(MovieEasy) }
            .size(width = 268.dp, height = 181.dp)
    ) {
        Image(
            painterResource(id = R.drawable.movie_category),
            contentDescription = "Movie Card",
            contentScale = ContentScale.Crop,
            modifier = Modifier.matchParentSize()
        )
        Row(
            modifier = Modifier
                .fillMaxSize(),
            verticalAlignment = Alignment.Bottom,
        ) {
            Icon(
                painterResource(R.drawable.baseline_star_24),
                contentDescription = "Star",
                modifier = Modifier.size(54.dp),
                tint = Yellow
            )
            Icon(
                painterResource(R.drawable.baseline_star_24),
                contentDescription = "Star",
                modifier = Modifier.size(54.dp),
                tint = Yellow
            )
            Icon(
                painterResource(R.drawable.baseline_star_24),
                contentDescription = "Star",
                modifier = Modifier.size(54.dp),
                tint = Yellow
            )
        }
    }
}

@Composable
fun TechnologyCard(modifier: Modifier = Modifier) {
    Box(
        modifier = Modifier
            .clickable { /* TODO */ }
            .size(width = 294.dp, height = 140.dp)
    ) {
        Image(
            painterResource(id = R.drawable.tech_card),
            contentDescription = "Movie Card",
            modifier = Modifier.matchParentSize()
        )
        Row(
            modifier = Modifier
                .fillMaxSize(),
            verticalAlignment = Alignment.Bottom,
        ) {
            Icon(
                painterResource(R.drawable.baseline_star_24),
                contentDescription = "Star",
                modifier = Modifier.size(54.dp),
                tint = Yellow
            )
            Icon(
                painterResource(R.drawable.baseline_star_24),
                contentDescription = "Star",
                modifier = Modifier.size(54.dp),
                tint = Yellow
            )
            Icon(
                painterResource(R.drawable.baseline_star_24),
                contentDescription = "Star",
                modifier = Modifier.size(54.dp),
                tint = Yellow
            )
        }
    }
}

@Composable
fun BackButton(modifier: Modifier = Modifier, navController: NavController) {
    Box(
        modifier = Modifier
            .clickable {
                SoundManager.playSound()
                navController.navigate(MainMenu)
            }
            .clip(shape = RoundedCornerShape(20.dp))
            .background(differentBlack)
            .size(width = 118.dp, height = 62.dp),
    ) {
        Box(
            modifier = Modifier
                .clip(shape = RoundedCornerShape(15.dp))
                .background(lightRed)
                .size(width = 108.dp, height = 57.dp)
                .align(AbsoluteAlignment.TopRight),
        ) {
            Column(
                modifier = Modifier.matchParentSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    "Back",
                    fontSize = 32.sp,
                    fontFamily = Lalezar
                )
            } // Text Column Scope
        } // Inner Box Scope
    } // Outer Box Scope
}

@Preview(showBackground = true)
@Composable
fun CategoryScreenPreview(modifier: Modifier = Modifier) {
    CategoryScreen(navController = rememberNavController())
}