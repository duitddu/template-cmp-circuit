package com.circuit.cmp.presentation

import androidx.compose.runtime.Composable
import com.slack.circuit.backstack.rememberSaveableBackStack
import com.slack.circuit.foundation.Circuit
import com.slack.circuit.foundation.CircuitCompositionLocals
import com.slack.circuit.foundation.NavigableCircuitContent
import com.slack.circuit.foundation.rememberCircuitNavigator
import com.circuit.cmp.presentation.feature.main.MainScreen
import com.circuit.cmp.presentation.theme.AppTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App(circuit: Circuit) {
    AppTheme {
        val backStack = rememberSaveableBackStack(root = MainScreen)
        val navigator = rememberCircuitNavigator(backStack) {}

        CircuitCompositionLocals(circuit) {
            NavigableCircuitContent(navigator = navigator, backStack = backStack)
        }
    }
}