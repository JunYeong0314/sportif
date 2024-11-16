package com.jyproject.sportif.presentation.ui.feature.searchFacility

import ViewEvent
import ViewSideEffect
import ViewState

class SearchFacilityContract {
    sealed class Event: ViewEvent {
        data object NavigationToBack: Event()
        data class SearchFacility(val city: String, val region: String? = null, val facilityName: String? = null): Event()
    }

    data class State(
        val searchFacilityState: SearchFacilityState,
    ): ViewState

    sealed class Effect: ViewSideEffect {
        sealed class Navigation: Effect() {
            data object ToBack: Navigation()
        }
    }
}