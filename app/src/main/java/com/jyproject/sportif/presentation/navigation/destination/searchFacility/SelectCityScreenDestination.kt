package com.jyproject.sportif.presentation.navigation.destination.searchFacility

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.jyproject.sportif.presentation.navigation.navigateToSearchFacility
import com.jyproject.sportif.presentation.ui.feature.searchFacility.selectCity.SelectCityContract
import com.jyproject.sportif.presentation.ui.feature.searchFacility.selectCity.SelectCityScreen
import com.jyproject.sportif.presentation.ui.feature.searchFacility.selectCity.SelectCityViewModel

@Composable
fun SelectCityScreenDestination(
    navController: NavController,
    viewModel: SelectCityViewModel = hiltViewModel()
) {
    SelectCityScreen(
        state = viewModel.viewState.value,
        effectFlow = viewModel.effect,
        onEventSend = viewModel::setEvent,
        onEffectSend = { effect ->
            when(effect) {
                is SelectCityContract.Effect.Navigation.ToBack -> navController.navigateUp()
                is SelectCityContract.Effect.Navigation.ToSearchFacility -> navController.navigateToSearchFacility(city = effect.city)
            }
        }
    )

}