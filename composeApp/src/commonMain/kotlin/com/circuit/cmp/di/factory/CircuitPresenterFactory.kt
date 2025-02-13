package com.circuit.cmp.di.factory

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
            else -> throw Exception("Invalid Screen Detected! :: $screen")
        }
    }
}
