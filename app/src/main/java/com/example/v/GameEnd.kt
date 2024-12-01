package com.example.v

import android.app.Activity
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.v.ui.theme.BackgroundScreenColor
import com.example.v.ui.theme.Lalezar
import com.example.v.ui.theme.Spenbeb
import com.example.v.ui.theme.anotherWhite
import com.example.v.ui.theme.cream
import com.example.v.ui.theme.darkRed
import com.example.v.ui.theme.navyBlue

@Composable
fun GameEnd(modifier: Modifier = Modifier, navController: NavController) {
    val activity = LocalContext.current as? Activity
    val gameEndConfetti by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.difficultybought))
    Box(
        modifier
            .fillMaxSize()
            .background(BackgroundScreenColor),
        Alignment.Center
    ) {
        Image(
            painter = painterResource(R.drawable.letters),
            contentDescription = "background letters",
            contentScale = ContentScale.Crop,
            modifier = Modifier.matchParentSize()
        )
        Box(
            modifier = Modifier
                .size(300.dp),
            Alignment.Center
        ) {
            Card(
                modifier = Modifier
                    .fillMaxSize(),
                border = BorderStroke(5.dp, anotherWhite),
                colors = CardDefaults.cardColors(navyBlue)
            ) {
                Box(modifier = Modifier.fillMaxSize(), Alignment.Center) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceAround
                    ) {
                        Text(
                            text = "Game Over",
                            fontFamily = Spenbeb,
                            fontSize = 34.sp,
                            color = cream
                        )
                        Text(
                            text = "Congratulations!",
                            fontFamily = Lalezar,
                            fontSize = 22.sp,
                            color = cream
                        )
                        Button(
                            onClick = { activity?.finishAffinity() },
                            modifier = Modifier
                                .padding(16.dp),
                            colors = ButtonDefaults.buttonColors(darkRed)
                        ) {
                            Text(
                                text = "Close App",
                                fontSize = 22.sp,
                                fontFamily = Lalezar,
                                color = cream
                            )
                        }

                    }
                    LottieAnimation(
                        composition = gameEndConfetti,
                        iterations = 100,
                        modifier = Modifier.matchParentSize()
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun GameEndPreview(modifier: Modifier = Modifier) {
    GameEnd(navController = rememberNavController())
}