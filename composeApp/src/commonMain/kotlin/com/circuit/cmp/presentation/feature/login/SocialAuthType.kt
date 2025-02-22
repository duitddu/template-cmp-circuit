package com.circuit.cmp.presentation.feature.login

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import circuitcmp.composeapp.generated.resources.Res
import circuitcmp.composeapp.generated.resources.ic_kakao
import circuitcmp.composeapp.generated.resources.ic_naver
import org.jetbrains.compose.resources.DrawableResource

enum class SocialAuthType(
    val label: String,
    val icon: DrawableResource,
    val iconSize: Dp,
    val textColor: Color,
    val bgColor: Color
) {
    NAVER(
        label = "Login with Naver",
        icon = Res.drawable.ic_naver,
        iconSize = 18.dp,
        textColor = Color.White,
        bgColor = Color(0xFF03C75A)
    ),
    KAKAO(
        label = "Login with Kakao",
        icon = Res.drawable.ic_kakao,
        iconSize = 24.dp,
        textColor = Color(0xFF191919),
        bgColor = Color(0xFFFEE500)
    )
}
