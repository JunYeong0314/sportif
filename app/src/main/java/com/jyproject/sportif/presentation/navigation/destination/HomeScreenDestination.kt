package com.jyproject.sportif.presentation.navigation.destination

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.jyproject.sportif.presentation.ui.feature.home.HomeScreen
import com.jyproject.sportif.presentation.ui.feature.home.HomeViewModel

@Composable
fun HomeScreenDestination(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {

    HomeScreen(
        state = viewModel.viewState.value,
        effectFlow = viewModel.effect,
        onEventSend = viewModel::setEvent,
        onEffectSend = {}
    )

}