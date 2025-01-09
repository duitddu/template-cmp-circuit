package com.circuit.cmp

import androidx.compose.ui.window.ComposeUIViewController
import com.slack.circuit.foundation.Circuit
import com.circuit.cmp.di.module.initKoin
import com.circuit.cmp.presentation.App
import org.koin.compose.getKoin

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) {
    val koin = getKoin()
    val circuit: Circuit = koin.get()

    App(circuit)
}