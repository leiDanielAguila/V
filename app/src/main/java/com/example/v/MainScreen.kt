package com.example.v

import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.AbsoluteAlignment
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.v.ui.theme.BackgroundScreenColor
import com.example.v.ui.theme.Lalezar
import com.example.v.ui.theme.VTheme
import com.example.v.ui.theme.differentBlack
import com.example.v.ui.theme.lightRed
import com.example.v.ui.theme.lighterBrown

@Composable
fun MainScreen(modifier: Modifier = Modifier, navController : NavController) {

    val context = LocalContext.current

    Box(
        modifier
            .fillMaxSize()
            .background(BackgroundScreenColor),
    ) {

        Image(
            painter = painterResource(R.drawable.letters),
            contentDescription = "background letters",
            contentScale = ContentScale.Crop,
            modifier = Modifier.matchParentSize()
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp),
            horizontalArrangement = Arrangement.End
        ) {
            SettingsAndSoundButton()
        }

        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painterResource(R.drawable.logo),
                contentDescription = "Game Logo",
                modifier = Modifier.padding(top = 70.dp),
            )
        } // COLUMN LOGO SCOPE

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 96.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            PlayGameButton(navController = navController, context = context)
            Spacer(modifier = Modifier.padding(8.dp))
            HowToPLayButton()
            Spacer(modifier = Modifier.padding(8.dp))
//            GiveFeedbackButton()
        } // Column Scope
    }
}

@Composable
fun PlayGameButton(
    modifier: Modifier = Modifier,
    navController : NavController,
    context: Context,
) {

    Box(
        modifier = Modifier
            .clickable {
                SoundManager.clickSound()
                navController.navigate(CategoryScreen)
            }
            .clip(shape = RoundedCornerShape(20.dp))
            .background(differentBlack)
            .size(width = 300.dp, height = 120.dp),
    ) {
        Box(
            modifier = Modifier
                .clip(shape = RoundedCornerShape(15.dp))
                .background(lightRed)
                .size(width = 285.dp, height = 100.dp)
                .align(AbsoluteAlignment.TopRight),
        ) {
            Column(
                modifier = Modifier.matchParentSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    "Play Game",
                    fontSize = 56.sp,
                    fontFamily = Lalezar
                )
            } // Text Column Scope
        } // Inner Box Scope
    } // Outer Box Scope
}

@Composable
fun HowToPLayButton(modifier: Modifier = Modifier) {
    Box(
        modifier = Modifier
            .clickable { /*TODO*/ SoundManager.clickSound()}
            .clip(shape = RoundedCornerShape(20.dp))
            .background(differentBlack)
            .size(width = 250.dp, height = 90.dp),
    ) {
        Box(
            modifier = Modifier
                .clip(shape = RoundedCornerShape(15.dp))
                .background(lightRed)
                .size(width = 235.dp, height = 70.dp)
                .align(AbsoluteAlignment.TopRight),
        ) {
            Column(
                modifier = Modifier.matchParentSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    "How To Play",
                    fontSize = 42.sp,
                    fontFamily = Lalezar
                )
            } // Text Column Scope
        } // Inner Box Scope
    } // Outer Box Scope
}

//@Composable
//fun GiveFeedbackButton(modifier: Modifier = Modifier) {
//    Box(
//        modifier = Modifier
//            .clickable { /*TODO*/ SoundManager.clickSound() }
//            .clip(shape = RoundedCornerShape(20.dp))
//            .background(differentBlack)
//            .size(width = 250.dp, height = 90.dp),
//    ) {
//        Box(
//            modifier = Modifier
//                .clip(shape = RoundedCornerShape(15.dp))
//                .background(lightRed)
//                .size(width = 235.dp, height = 70.dp)
//                .align(AbsoluteAlignment.TopRight),
//        ) {
//            Column(
//                modifier = Modifier.matchParentSize(),
//                horizontalAlignment = Alignment.CenterHorizontally,
//                verticalArrangement = Arrangement.Center
//            ) {
//                Text(
//                    "Give Feedback",
//                    fontSize = 38.sp,
//                    fontFamily = Lalezar
//                )
//            } // Text Column Scope
//        } // Inner Box Scope
//    } // Outer Box Scope
//}

@Composable
fun SettingsAndSoundButton(
    modifier: Modifier = Modifier,
) {

    var onSoundClick by remember { mutableStateOf(true) }

    val soundIcon = when (onSoundClick) {
        true ->  painterResource(id = R.drawable.baseline_volume_up_24)
        else -> painterResource(id = R.drawable.baseline_volume_off_24)
    }

    Column(
        modifier.padding(12.dp)
    ) {
        Surface(
            color = lighterBrown,
            shape = RoundedCornerShape(50.dp),
            border = BorderStroke(2.dp, color = Color.Black)
        ) {
            IconButton(
                onClick = { SoundManager.clickSound() },
                modifier = Modifier.size(50.dp)
            ) {
                Icon(
                    Icons.Rounded.Settings,
                    contentDescription = null,
                    modifier = Modifier.size(40.dp),
                )
            }
        }
        Spacer(modifier = Modifier.padding(4.dp))
        Surface(
            color = lighterBrown,
            shape = RoundedCornerShape(50.dp),
            border = BorderStroke(2.dp, color = Color.Black)
        ) {
            IconButton(
                onClick = {
                    SoundManager.clickSound()
                    onSoundClick = !onSoundClick
                          },
                modifier = Modifier.size(50.dp)
            ) {
                Icon(
                    painter = soundIcon,
                    contentDescription = null,
                    modifier = Modifier.size(40.dp),
                )
            }
        }
    }
}

@Preview
@Composable
fun MainScreenPreview() {
    VTheme {
        MainScreen(navController = rememberNavController())
    }
}