package com.circuit.cmp.di.module

import com.circuit.cmp.shared.social.kakao.KakaoAuthProvider
import com.circuit.cmp.shared.social.kakao.KakaoAuthProviderImpl
import com.circuit.cmp.shared.social.naver.NaverAuthProvider
import com.circuit.cmp.shared.social.naver.NaverAuthProviderImpl
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Module

@Module
actual class SocialModule {

    @Factory(binds = [NaverAuthProvider::class])
    actual fun naverAuthProviderComponent(): NaverAuthProvider = NaverAuthProviderImpl(
        oauthClientId = "YOUR_NAVER_CLIENT_ID",
        oauthClientSecret = "YOUR_NAVER_CLIENT_SECRET",
        oauthClientName = "YOUR_NAVER_CLIENT_NAME"
    )

    @Factory(binds = [KakaoAuthProvider::class])
    actual fun kakaoAuthProviderComponent(): KakaoAuthProvider = KakaoAuthProviderImpl(
        appKey = "YOUT_KAKAO_APP_KEY"
    )
}
