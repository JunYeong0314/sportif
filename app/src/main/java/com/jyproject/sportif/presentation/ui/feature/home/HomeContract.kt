package com.jyproject.sportif.presentation.ui.feature.home

import ViewEvent
import ViewSideEffect
import ViewState

class HomeContract {
    sealed class Event: ViewEvent {
        data object NavigationToSearchFacility: Event()
        data object NavigationToSearchChair: Event()
    }

    data class State(val homeState: HomeState): ViewState

    sealed class Effect: ViewSideEffect {
        sealed class Navigation: Effect() {
            data object ToSearchFacility: Navigation()
            data object ToSearchChair: Navigation()
        }
    }
}