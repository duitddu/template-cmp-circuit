package com.circuit.cmp.di.factory

import com.slack.circuit.runtime.CircuitContext
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.screen.Screen
import org.koin.core.module.Module
import org.koin.core.scope.Scope

class CircuitPresenterFactory<P : Presenter<*>>(
    private val factory: (Navigator, Screen) -> P
) : Presenter.Factory {
    override fun create(
        screen: Screen,
        navigator: Navigator,
        context: CircuitContext
    ): Presenter<*> {
        return factory(navigator, screen)
    }
}

fun Module.circuitPresenterFactory(presenter: Scope.(Navigator, Screen) -> Presenter<*>) {
    factory<Presenter.Factory> {
        CircuitPresenterFactory { navigator, screen ->
            presenter(navigator, screen)
        }
    }
}