package com.jyproject.sportif.presentation.ui.feature.home

import ViewEvent
import ViewSideEffect
import ViewState

class HomeContract {
    sealed class Event: ViewEvent {

    }

    data class State(val homeState: HomeState): ViewState

    sealed class Effect: ViewSideEffect {
        sealed class Navigation: Effect() {

        }
    }
}