package com.jyproject.sportif.presentation.ui.feature.searchChair

import ViewEvent
import ViewSideEffect
import ViewState

class SearchChairContract {
    sealed class Event: ViewEvent {
        data object NavigationToBack: Event()
        data class SearchChair(val contentName: String, val pageNo: Int = 1): Event()
    }

    data class State(
        val searchChairState: SearchChairState
    ): ViewState

    sealed class Effect: ViewSideEffect {
        sealed class Navigation: Effect() {
            data object ToBack: Navigation()
        }
    }
}