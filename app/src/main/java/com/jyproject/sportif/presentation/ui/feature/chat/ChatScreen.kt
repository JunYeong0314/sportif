package com.jyproject.sportif.presentation.ui.feature.chat

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.jyproject.sportif.presentation.ui.feature.home.HomeContract
import kotlinx.coroutines.flow.Flow

@Composable
fun ChatScreen(
    state: ChatContract.State,
    effectFlow: Flow<ChatContract.Effect>?,
    onEventSend: (event: ChatContract.Event) -> Unit,
    onEffectSend: (effect: ChatContract.Effect) -> Unit
) {
    LaunchedEffect(effectFlow) {
        effectFlow?.collect { effect ->
            when (effect) {
                is ChatContract.Effect.Navigation.ToBack -> {
                    onEffectSend(effect)
                }
            }
        }
    }



}