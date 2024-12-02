package com.jyproject.sportif.presentation.navigation.destination

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.jyproject.sportif.presentation.navigation.Navigation
import com.jyproject.sportif.presentation.ui.feature.home.HomeContract
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
        onEffectSend = { effect ->
            when(effect) {
                is HomeContract.Effect.Navigation.ToSearchFacility -> {
                    navController.navigate(Navigation.Routes.SELECT_CITY)
                }
                is HomeContract.Effect.Navigation.ToSearchChair -> {
                    navController.navigate(Navigation.Routes.SEARCH_CHAIR)
                }
                is HomeContract.Effect.Navigation.ToMapDetail -> {
                    navController.navigate(Navigation.Routes.MAP_DETAIL)
                }
            }
        }
    )

}