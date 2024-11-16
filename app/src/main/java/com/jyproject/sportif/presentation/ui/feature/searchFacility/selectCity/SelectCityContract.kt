package com.jyproject.sportif.presentation.ui.feature.searchFacility.selectCity

import ViewEvent
import ViewSideEffect
import ViewState

class SelectCityContract {
    sealed class Event : ViewEvent {
        data object NavigateToBack: Event()
        data class NavigateToSearchFacility(val city: String): Event()
        data class SelectCity(val city: String): Event()
    }

    data class State(
        val selectCityState: SelectCityState
    ) : ViewState

    sealed class Effect : ViewSideEffect {
        sealed class Navigation: Effect() {
            data object ToBack: Navigation()
            data class ToSearchFacility(val city: String): Navigation()
        }
    }
}
