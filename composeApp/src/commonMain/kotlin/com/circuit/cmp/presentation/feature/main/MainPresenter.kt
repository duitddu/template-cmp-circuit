package com.circuit.cmp.presentation.feature.main

import androidx.compose.runtime.Composable
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter

class MainPresenter(
    private val screen: MainScreen,
    private val navigator: Navigator,
) : Presenter<MainScreen.State> {

    @Composable
    override fun present(): MainScreen.State {
        return MainScreen.State(
            eventSink = { event ->

            }
        )
    }
}