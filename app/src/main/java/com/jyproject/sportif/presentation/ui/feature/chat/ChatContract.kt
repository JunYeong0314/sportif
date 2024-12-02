package com.jyproject.sportif.presentation.ui.feature.chat

import ViewEvent
import ViewSideEffect
import ViewState

class ChatContract {
    sealed class Event: ViewEvent {
        data object NavigationToBack: Event()

    }

    data class State(val chatState: ChatState): ViewState

    sealed class Effect: ViewSideEffect {
        sealed class Navigation: Effect() {
            data object ToBack: Navigation()
        }
    }
}