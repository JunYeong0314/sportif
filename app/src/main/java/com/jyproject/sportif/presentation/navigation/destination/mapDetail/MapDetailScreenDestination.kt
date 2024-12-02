package com.jyproject.sportif.presentation.navigation.destination.mapDetail

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.jyproject.sportif.presentation.ui.feature.mapDetail.MapDetailContract
import com.jyproject.sportif.presentation.ui.feature.mapDetail.MapDetailScreen
import com.jyproject.sportif.presentation.ui.feature.mapDetail.MapDetailViewModel

@Composable
fun MapDetailScreenDestination(
    navController: NavController,
    viewModel: MapDetailViewModel = hiltViewModel()
) {
    MapDetailScreen(
        state = viewModel.viewState.value,
        effectFlow = viewModel.effect,
        onEventSend = viewModel::setEvent,
        onEffectSend = { effect ->
            when(effect) {
                is MapDetailContract.Effect.Navigation.ToBack -> {
                    navController.navigateUp()
                }
            }
        }
    )
}