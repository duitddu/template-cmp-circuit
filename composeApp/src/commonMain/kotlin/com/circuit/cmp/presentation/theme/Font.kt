package com.circuit.cmp.presentation.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import circuitcmp.composeapp.generated.resources.Res
import circuitcmp.composeapp.generated.resources.pretendard_black
import circuitcmp.composeapp.generated.resources.pretendard_bold
import circuitcmp.composeapp.generated.resources.pretendard_extrabold
import circuitcmp.composeapp.generated.resources.pretendard_extralight
import circuitcmp.composeapp.generated.resources.pretendard_light
import circuitcmp.composeapp.generated.resources.pretendard_medium
import circuitcmp.composeapp.generated.resources.pretendard_regular
import circuitcmp.composeapp.generated.resources.pretendard_semibold
import circuitcmp.composeapp.generated.resources.pretendard_thin
import org.jetbrains.compose.resources.Font

// Change to the font you want
@Composable
fun PretendardFontFamily() = FontFamily(
    Font(Res.font.pretendard_thin, FontWeight.W100, FontStyle.Normal),
    Font(Res.font.pretendard_extralight, FontWeight.W200, FontStyle.Normal),
    Font(Res.font.pretendard_light, FontWeight.W300, FontStyle.Normal),
    Font(Res.font.pretendard_regular, FontWeight.W400, FontStyle.Normal),
    Font(Res.font.pretendard_medium, FontWeight.W500, FontStyle.Normal),
    Font(Res.font.pretendard_semibold, FontWeight.W600, FontStyle.Normal),
    Font(Res.font.pretendard_bold, FontWeight.W700, FontStyle.Normal),
    Font(Res.font.pretendard_extrabold, FontWeight.W800, FontStyle.Normal),
    Font(Res.font.pretendard_black, FontWeight.W900, FontStyle.Normal),
)

@Composable
fun PretendardTypography() = Typography().run {
    val fontFamily = PretendardFontFamily()

    copy(
        displayLarge = displayLarge.copy(fontFamily = fontFamily),
        displayMedium = displayMedium.copy(fontFamily = fontFamily),
        displaySmall = displaySmall.copy(fontFamily = fontFamily),
        headlineLarge = headlineLarge.copy(fontFamily = fontFamily),
        headlineMedium = headlineMedium.copy(fontFamily = fontFamily),
        headlineSmall = headlineSmall.copy(fontFamily = fontFamily),
        titleLarge = titleLarge.copy(fontFamily = fontFamily),
        titleMedium = titleMedium.copy(fontFamily = fontFamily),
        titleSmall = titleSmall.copy(fontFamily = fontFamily),
        bodyLarge = bodyLarge.copy(fontFamily =  fontFamily),
        bodyMedium = bodyMedium.copy(fontFamily = fontFamily),
        bodySmall = bodySmall.copy(fontFamily = fontFamily),
        labelLarge = labelLarge.copy(fontFamily = fontFamily),
        labelMedium = labelMedium.copy(fontFamily = fontFamily),
        labelSmall = labelSmall.copy(fontFamily = fontFamily)
    )
}