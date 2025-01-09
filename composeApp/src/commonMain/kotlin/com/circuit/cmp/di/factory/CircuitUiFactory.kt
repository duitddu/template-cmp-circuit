package com.circuit.cmp.di.factory

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.slack.circuit.runtime.CircuitContext
import com.slack.circuit.runtime.CircuitUiState
import com.slack.circuit.runtime.screen.Screen
import com.slack.circuit.runtime.ui.Ui
import com.slack.circuit.runtime.ui.ui
import org.koin.core.module.Module

class CircuitUiFactory(
    private val factory: @Composable (CircuitUiState, Modifier) -> Unit
) : Ui.Factory {
    override fun create(screen: Screen, context: CircuitContext): Ui<*>? {
        return ui<CircuitUiState> { state, modifier ->
            factory(state, modifier)
        }
    }
}

fun Module.circuitUiFactory(ui: @Composable (CircuitUiState, Modifier) -> Unit) {
    factory<Ui.Factory> {
        CircuitUiFactory { state, modifier ->
            ui(state, modifier)
        }
    }
}