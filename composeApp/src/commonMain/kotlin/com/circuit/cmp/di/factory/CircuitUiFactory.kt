package com.circuit.cmp.di.factory

import com.circuit.cmp.presentation.feature.login.Login
import com.circuit.cmp.presentation.feature.login.LoginScreen
import com.circuit.cmp.presentation.feature.main.Main
import com.circuit.cmp.presentation.feature.main.MainScreen
import com.slack.circuit.runtime.CircuitContext
import com.slack.circuit.runtime.CircuitUiState
import com.slack.circuit.runtime.screen.Screen
import com.slack.circuit.runtime.ui.Ui
import com.slack.circuit.runtime.ui.ui

class CircuitUiFactory : Ui.Factory {
    override fun create(screen: Screen, context: CircuitContext): Ui<*>? {
        return ui<CircuitUiState> { state, modifier ->
            when (state) {
                is MainScreen.State -> Main(state, modifier)
                is LoginScreen.State -> Login(state, modifier)
            }
        }
    }
}