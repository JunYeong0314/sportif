package com.jyproject.sportif.presentation.ui.feature.mapDetail

import ViewEvent
import ViewSideEffect
import ViewState

class MapDetailContract {
    sealed class Event: ViewEvent {
        data object NavigationToBack: Event()
    }

    data class State(
        val mapDetailState: MapDetailState
    ): ViewState

    sealed class Effect: ViewSideEffect {
        sealed class Navigation: Effect() {
            data object ToBack: Navigation()
        }
    }
}