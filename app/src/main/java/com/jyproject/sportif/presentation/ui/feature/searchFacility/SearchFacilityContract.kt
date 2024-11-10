package com.jyproject.sportif.presentation.ui.feature.searchFacility

import ViewEvent
import ViewSideEffect
import ViewState

class SearchFacilityContract {
    sealed class Event : ViewEvent {
        data object NavigateToBack: Event()
    }

    data class State(
        val searchFacilityState: SearchFacilityState
    ) : ViewState

    sealed class Effect : ViewSideEffect {
        sealed class Navigation: Effect() {
            data object ToBack: Navigation()
        }
    }
}
