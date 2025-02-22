package com.circuit.cmp.shared.social.naver

import androidx.compose.runtime.Composable
import com.circuit.cmp.shared.social.SocialAuthResult
import com.circuit.cmp.shared.social.SocialAuthenticator
import kotlinx.cinterop.ExperimentalForeignApi
import nativeIosShared.NaverLoginBridge
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@OptIn(ExperimentalForeignApi::class)
internal class NaverAuthProviderImpl(
    private val bridge: NaverLoginBridge
) : NaverAuthProvider {

    @Composable
    override fun get(): SocialAuthenticator<NaverUser> {
        return NaverAuthenticatorImpl(bridge)
    }
}

@OptIn(ExperimentalForeignApi::class)
private class NaverAuthenticatorImpl(
    private val bridge: NaverLoginBridge
) : SocialAuthenticator<NaverUser> {

    override suspend fun authenticate(): SocialAuthResult<NaverUser> = suspendCoroutine { cont ->
        bridge.requestWithSuccess(
            success = {
                if (it.isNullOrEmpty()) {
                    cont.resume(SocialAuthResult.Error)
                } else {
                    cont.resume(SocialAuthResult.Success(NaverUser(it)))
                }
            },
            failure = {
                cont.resume(SocialAuthResult.Error)
            },
            cancel = {
                cont.resume(SocialAuthResult.UserCancelled)
            },
        )
    }
}