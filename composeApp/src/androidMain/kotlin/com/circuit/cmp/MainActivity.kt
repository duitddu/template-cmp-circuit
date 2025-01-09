package com.circuit.cmp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.circuit.cmp.presentation.App
import com.slack.circuit.foundation.Circuit
import org.koin.compose.getKoin

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()

        setContent {
            val koin = getKoin()
            val circuit: Circuit = koin.get()

            App(circuit)
        }
    }
}