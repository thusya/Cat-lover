package com.thusee.core_theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

// Set of Material typography styles to start with
val Typography: Typography
    @Composable
    get() = Typography(
        headlineLarge = TextStyle(
            color = MaterialTheme.colorScheme.onBackground,
            fontFamily = poppins,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            lineHeight = 24.sp,
            textAlign = TextAlign.Center
        ),

        titleMedium = TextStyle(
            color = MaterialTheme.colorScheme.onBackground,
            fontFamily = poppins,
            fontWeight = FontWeight.SemiBold,
            fontSize = 17.sp,
            lineHeight = 20.sp,
            textAlign = TextAlign.Left
        ),

        bodyLarge = TextStyle(
            fontFamily = poppins,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            lineHeight = 20.sp,
            letterSpacing = 0.5.sp
        ),

        bodyMedium = TextStyle(
            fontFamily = poppins,
            fontWeight = FontWeight.Normal,
            fontSize = 15.sp,
            lineHeight = 20.sp,
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

val poppins: FontFamily = FontFamily(
    Font(R.font.poppins_black, weight = FontWeight.W900, style = FontStyle.Normal),
    Font(R.font.poppins_black_italic, weight = FontWeight.W900, style = FontStyle.Italic),
    Font(R.font.poppins_bold, weight = FontWeight.W700, style = FontStyle.Normal),
    Font(R.font.poppins_bold_italic, weight = FontWeight.W700, style = FontStyle.Italic),
    Font(R.font.poppins_extrabold, weight = FontWeight.W800, style = FontStyle.Normal),
    Font(R.font.poppins_extrabold_italic, weight = FontWeight.W800, style = FontStyle.Italic),
    Font(R.font.poppins_extralight, weight = FontWeight.W200, style = FontStyle.Normal),
    Font(R.font.poppins_extralight_italic, weight = FontWeight.W200, style = FontStyle.Italic),
    Font(R.font.poppins_italic, weight = FontWeight.W400, style = FontStyle.Italic),
    Font(R.font.poppins_light, weight = FontWeight.W300, style = FontStyle.Normal),
    Font(R.font.poppins_light_italic, weight = FontWeight.W300, style = FontStyle.Italic),
    Font(R.font.poppins_medium, weight = FontWeight.W500, style = FontStyle.Normal),
    Font(R.font.poppins_medium_italic, weight = FontWeight.W500, style = FontStyle.Italic),
    Font(R.font.poppins_regular, weight = FontWeight.W400, style = FontStyle.Normal),
    Font(R.font.poppins_semibold, weight = FontWeight.W600, style = FontStyle.Normal),
    Font(R.font.poppins_semibold_italic, weight = FontWeight.W600, style = FontStyle.Italic),
    Font(R.font.poppins_thin, weight = FontWeight.W100, style = FontStyle.Normal),
    Font(R.font.poppins_thin_italic, weight = FontWeight.W100, style = FontStyle.Italic),
)