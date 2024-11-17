package com.example.v.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.v.Screen
import com.example.v.ui.theme.BackgroundScreenColor
import com.example.v.ui.theme.Spenbeb
import com.example.v.ui.theme.cream

@Composable
fun GenreScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    font: FontFamily = Spenbeb,
) {
    Box(
        modifier.fillMaxSize().background(BackgroundScreenColor)
    ) {
        Box(
            modifier = Modifier.matchParentSize().padding(12.dp),
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
            modifier = Modifier.matchParentSize().padding(12.dp),
        ) {
            Text(
                text = "Select Genre",
                fontSize = 26.sp,
                fontFamily = font,
                modifier = Modifier.padding(12.dp),
                color = cream
            )
        }
    }
}

@Preview
@Composable
fun GenreScreenPreview() {
    GenreScreen(navController = rememberNavController())
}