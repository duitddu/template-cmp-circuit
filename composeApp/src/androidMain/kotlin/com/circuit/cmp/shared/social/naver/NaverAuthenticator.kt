package com.circuit.cmp.shared.social.naver

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.circuit.cmp.shared.social.SocialAuthResult
import com.circuit.cmp.shared.social.SocialAuthenticator
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.NidOAuthErrorCode
import com.navercorp.nid.oauth.OAuthLoginCallback
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

internal class NaverAuthProviderImpl(
    private val oauthClientId: String,
    private val oauthClientSecret: String,
    private val oauthClientName: String
) : NaverAuthProvider {

    @Composable
    override fun get(): SocialAuthenticator<NaverUser> {
        val context = LocalContext.current
        return NaverAuthenticator(context, oauthClientId, oauthClientSecret, oauthClientName)
    }
}

private class NaverAuthenticator(
    private val context: Context,
    oauthClientId: String,
    oauthClientSecret: String,
    oauthClientName: String
): SocialAuthenticator<NaverUser> {

    init {
        NaverIdLoginSDK.initialize(context, oauthClientId, oauthClientSecret, oauthClientName)
    }

    override suspend fun authenticate(): SocialAuthResult<NaverUser> = suspendCancellableCoroutine { cont ->
        val callback = object : OAuthLoginCallback {
            override fun onSuccess() {
                NaverIdLoginSDK.oauthLoginCallback = null

                val accessToken = NaverIdLoginSDK.getAccessToken()
                if (accessToken.isNullOrEmpty()) {
                    cont.resume(SocialAuthResult.Error)
                } else {
                    cont.resume(SocialAuthResult.Success(NaverUser(accessToken)))
                }
            }

            override fun onError(errorCode: Int, message: String) {
                NaverIdLoginSDK.oauthLoginCallback = null
                handleError()
            }

            override fun onFailure(httpStatus: Int, message: String) {
                NaverIdLoginSDK.oauthLoginCallback = null
                handleError()
            }

            private fun handleError() {
                val lastErrorCode = NaverIdLoginSDK.getLastErrorCode()
                if (lastErrorCode == NidOAuthErrorCode.CLIENT_USER_CANCEL) {
                    cont.resume(SocialAuthResult.UserCancelled)
                } else {
                    cont.resume(SocialAuthResult.Error)
                }
            }
        }

        NaverIdLoginSDK.authenticate(context, callback)
    }
}
