package com.jyproject.sportif.presentation.ui.feature.chat

import BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor() :
    BaseViewModel<ChatContract.Event, ChatContract.State, ChatContract.Effect>() {
    private val chatState = viewState.value.chatState

    override fun setInitialState() = ChatContract.State(
        chatState = ChatState()
    )

    override fun handleEvents(event: ChatContract.Event) {
        when (event) {
            is ChatContract.Event.NavigationToBack -> setEffect { ChatContract.Effect.Navigation.ToBack }
            is ChatContract.Event.Question -> requestQuestion(event.question)
        }
    }

    private fun requestQuestion(question: Chat) {
        chatState.chatList.add(question)
    }
}