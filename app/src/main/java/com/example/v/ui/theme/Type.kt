package com.example.v.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.v.R

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)

val Lalezar = FontFamily(
    Font(R.font.lalezar_regular, FontWeight.Normal),
)

val Spenbeb = FontFamily(
    Font(R.font.spenbeb, FontWeight.Normal),
)

val disnep = FontFamily(
    Font(R.font.disney_font, FontWeight.Normal),
)

val scifi = FontFamily(
    Font(R.font.scifi, FontWeight.Normal),
)

val marvel = FontFamily(
    Font(R.font.marvel, FontWeight.Normal),
)