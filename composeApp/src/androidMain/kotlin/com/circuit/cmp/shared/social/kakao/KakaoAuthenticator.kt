package com.circuit.cmp.shared.social.kakao

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.circuit.cmp.shared.social.SocialAuthResult
import com.circuit.cmp.shared.social.SocialAuthenticator
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

internal class KakaoAuthProviderImpl(
    private val appKey: String
) : KakaoAuthProvider {

    @Composable
    override fun get(): SocialAuthenticator<KakaoUser> {
        val context = LocalContext.current
        return KakaoAuthenticator(context, appKey)
    }
}

private class KakaoAuthenticator(
    private val context: Context,
    appKey: String
) : SocialAuthenticator<KakaoUser> {

    init {
        KakaoSdk.init(context, appKey)
    }

    override suspend fun authenticate(): SocialAuthResult<KakaoUser> = suspendCancellableCoroutine { cont ->
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
            UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
                cont.handle(token, error)
            }
        } else {
            UserApiClient.instance.loginWithKakaoAccount(context) { token, error ->
                cont.handle(token, error)
            }
        }
    }

    private fun CancellableContinuation<SocialAuthResult<KakaoUser>>.handle(token: OAuthToken?, error: Throwable?) {
        if (error != null) {
            if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                resume(SocialAuthResult.UserCancelled)
            } else {
                resume(SocialAuthResult.Error)
            }
        } else {
            val accessToken = token?.accessToken
            if (accessToken.isNullOrEmpty()) {
                resume(SocialAuthResult.Error)
            } else {
                resume(SocialAuthResult.Success(KakaoUser(accessToken)))
            }
        }
    }
}
