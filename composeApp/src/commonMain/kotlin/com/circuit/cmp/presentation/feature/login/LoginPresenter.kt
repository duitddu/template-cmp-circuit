package com.circuit.cmp.presentation.feature.login

import androidx.compose.runtime.Composable
import com.circuit.cmp.presentation.feature.main.MainScreen
import com.circuit.cmp.shared.social.SocialAuthResult
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter

class LoginPresenter(
    private val screen: LoginScreen,
    private val navigator: Navigator,
) : Presenter<LoginScreen.State> {

    @Composable
    override fun present(): LoginScreen.State {
        return LoginScreen.State(
            eventSink = { event ->
                when (event) {
                    is LoginScreen.Event.OnKakaoAuthenticated -> {
                        when (event.ret) {
                            is SocialAuthResult.Error -> {
                                // Handle error
                            }

                            is SocialAuthResult.UserCancelled -> {
                                // Handle user cancelled
                            }

                            is SocialAuthResult.Success -> {
                                // Handle success
                                navigator.goTo(MainScreen)
                            }
                        }
                    }

                    is LoginScreen.Event.OnNaverAuthenticated -> {
                        when (event.ret) {
                            is SocialAuthResult.Error -> {
                                // Handle error
                            }

                            is SocialAuthResult.UserCancelled -> {
                                // Handle user cancelled
                            }

                            is SocialAuthResult.Success -> {
                                // Handle success
                                navigator.goTo(MainScreen)
                            }
                        }
                    }
                }
            }
        )
    }
}
