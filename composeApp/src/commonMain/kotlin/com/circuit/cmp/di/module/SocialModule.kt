package com.circuit.cmp.di.module

import com.circuit.cmp.shared.social.kakao.KakaoAuthProvider
import com.circuit.cmp.shared.social.naver.NaverAuthProvider
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Module

@Module
expect class SocialModule() {

    @Factory
    fun naverAuthProviderComponent(): NaverAuthProvider

    @Factory
    fun kakaoAuthProviderComponent(): KakaoAuthProvider
}
