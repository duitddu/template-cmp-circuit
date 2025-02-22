package com.circuit.cmp.presentation.feature.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.circuit.cmp.Parcelize
import com.circuit.cmp.shared.social.SocialAuthResult
import com.circuit.cmp.shared.social.kakao.KakaoAuthProvider
import com.circuit.cmp.shared.social.naver.NaverAuthProvider
import com.circuit.cmp.shared.social.kakao.KakaoUser
import com.circuit.cmp.shared.social.naver.NaverUser
import com.slack.circuit.runtime.CircuitUiEvent
import com.slack.circuit.runtime.CircuitUiState
import com.slack.circuit.runtime.screen.Screen
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject

@Parcelize
data object LoginScreen : Screen {
    data class State(
        val eventSink: (Event) -> Unit
    ) : CircuitUiState

    sealed interface Event : CircuitUiEvent {
        data class OnKakaoAuthenticated(val ret: SocialAuthResult<KakaoUser>) : Event
        data class OnNaverAuthenticated(val ret: SocialAuthResult<NaverUser>) : Event
    }
}

@Composable
fun Login(
    state: LoginScreen.State,
    modifier: Modifier = Modifier,
) {
    val scope = rememberCoroutineScope()
    val kakaoAuthenticator = koinInject<KakaoAuthProvider>().get()
    val naverAuthenticator = koinInject<NaverAuthProvider>().get()

    val socialAuthLaunch: (SocialAuthType) -> Unit = remember {
        { type ->
            scope.launch {
                when (type) {
                    SocialAuthType.NAVER -> {
                        val ret = naverAuthenticator.authenticate()
                        state.eventSink.invoke(LoginScreen.Event.OnNaverAuthenticated(ret))
                    }

                    SocialAuthType.KAKAO -> {
                        val ret = kakaoAuthenticator.authenticate()
                        state.eventSink.invoke(LoginScreen.Event.OnKakaoAuthenticated(ret))
                    }
                }
            }
        }
    }

    Box(
        modifier = modifier,
        contentAlignment = Alignment.TopStart
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
                .statusBarsPadding()
                .navigationBarsPadding(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SocialAuthButtons { type ->
                socialAuthLaunch.invoke(type)
            }
        }
    }
}

@Composable
private fun SocialAuthButtons(
    onPressed: (SocialAuthType) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SocialAuthType.entries.forEach {
            SocialAuthButton(it, onPressed)
        }
    }
}

@Composable
private fun SocialAuthButton(
    type: SocialAuthType,
    onPressed: (SocialAuthType) -> Unit
) {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .clickable { onPressed.invoke(type) }
            .background(type.bgColor)
            .fillMaxWidth()
            .height(56.dp)
            .padding(horizontal = 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(type.icon),
            modifier = Modifier.size(type.iconSize),
            contentDescription = type.name
        )
        Text(
            text = type.label,
            color = type.textColor,
            fontWeight = FontWeight.W500,
            fontSize = 16.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .weight(1f)
                .wrapContentHeight()
        )
    }
}
