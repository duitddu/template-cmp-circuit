package com.circuit.cmp.di.module

import com.circuit.cmp.di.factory.circuitPresenterFactory
import com.circuit.cmp.di.factory.circuitUiFactory
import com.slack.circuit.foundation.Circuit
import com.circuit.cmp.presentation.feature.main.Main
import com.circuit.cmp.presentation.feature.main.MainScreen
import com.circuit.cmp.presentation.feature.main.MainPresenter
import org.koin.dsl.module

val circuitModule = module {
    circuitPresenterFactory { navigator, screen ->
        when (screen) {
            is MainScreen -> MainPresenter(screen, navigator)
            else -> throw Exception("Invalid Screen Detected! :: $screen")
        }
    }

    circuitUiFactory { state, modifier ->
        when (state) {
            is MainScreen.State -> Main(state, modifier)
        }
    }

    single {
        Circuit.Builder()
            .addUiFactories(getAll())
            .addPresenterFactories(getAll())
            .build()
    }
}