package com.jyproject.sportif.presentation.navigation.destination.chat

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.jyproject.sportif.presentation.ui.feature.chat.ChatContract
import com.jyproject.sportif.presentation.ui.feature.chat.ChatScreen
import com.jyproject.sportif.presentation.ui.feature.chat.ChatViewModel

@Composable
fun ChatScreenDestination(
    navController: NavController,
    viewModel: ChatViewModel = hiltViewModel()
) {
    ChatScreen(
        state = viewModel.viewState.value,
        effectFlow = viewModel.effect,
        onEventSend = viewModel::setEvent,
        onEffectSend = {effect ->
            when(effect) {
                is ChatContract.Effect.Navigation.ToBack -> {
                    navController.navigateUp()
                }
            }
        }
    )

}