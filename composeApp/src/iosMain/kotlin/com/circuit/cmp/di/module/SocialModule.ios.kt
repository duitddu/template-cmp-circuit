package com.circuit.cmp.di.module

import com.circuit.cmp.shared.social.kakao.KakaoAuthProvider
import com.circuit.cmp.shared.social.kakao.KakaoAuthProviderImpl
import com.circuit.cmp.shared.social.naver.NaverAuthProvider
import com.circuit.cmp.shared.social.naver.NaverAuthProviderImpl
import kotlinx.cinterop.ExperimentalForeignApi
import nativeIosShared.KakaoLoginBridge
import nativeIosShared.NaverLoginBridge
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Module

@Module
@OptIn(ExperimentalForeignApi::class)
actual class SocialModule {

    @Factory(binds = [NaverAuthProvider::class])
    actual fun naverAuthProviderComponent(): NaverAuthProvider = NaverAuthProviderImpl(
        NaverLoginBridge()
    )

    @Factory(binds = [KakaoAuthProvider::class])
    actual fun kakaoAuthProviderComponent(): KakaoAuthProvider = KakaoAuthProviderImpl(
        KakaoLoginBridge()
    )
}
