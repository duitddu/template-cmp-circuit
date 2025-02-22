package com.circuit.cmp.di.factory

import com.circuit.cmp.presentation.feature.login.LoginPresenter
import com.circuit.cmp.presentation.feature.login.LoginScreen
import com.circuit.cmp.presentation.feature.main.MainPresenter
import com.circuit.cmp.presentation.feature.main.MainScreen
import com.slack.circuit.runtime.CircuitContext
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.screen.Screen

class CircuitPresenterFactory : Presenter.Factory {
    override fun create(
        screen: Screen,
        navigator: Navigator,
        context: CircuitContext
    ): Presenter<*> {
        return when (screen) {
            is MainScreen -> MainPresenter(screen, navigator)
            is LoginScreen -> LoginPresenter(screen, navigator)
            else -> throw Exception("Invalid Screen Detected! :: $screen")
        }
    }
}
