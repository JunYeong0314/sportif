package com.jyproject.sportif.presentation.navigation.destination.searchChair

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.jyproject.sportif.presentation.ui.feature.searchChair.SearchChairContract
import com.jyproject.sportif.presentation.ui.feature.searchChair.SearchChairScreen
import com.jyproject.sportif.presentation.ui.feature.searchChair.SearchChairViewModel
import com.jyproject.sportif.presentation.ui.feature.searchFacility.SearchFacilityViewModel
import com.jyproject.sportif.presentation.ui.feature.searchFacility.selectCity.SelectCityContract

@Composable
fun SearchChairScreenDestination(
    navController: NavController,
    viewModel: SearchChairViewModel = hiltViewModel()
) {
    SearchChairScreen(
        state = viewModel.viewState.value,
        effectFlow = viewModel.effect,
        onEventSend = viewModel::setEvent,
        onEffectSend = { effect ->
            when(effect) {
                is SearchChairContract.Effect.Navigation.ToBack -> navController.navigateUp()
            }
        }
    )

}