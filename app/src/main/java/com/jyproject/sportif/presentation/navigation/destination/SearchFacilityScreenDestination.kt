package com.jyproject.sportif.presentation.navigation.destination

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.jyproject.sportif.presentation.ui.feature.searchFacility.SearchFacilityContract
import com.jyproject.sportif.presentation.ui.feature.searchFacility.SearchFacilityScreen
import com.jyproject.sportif.presentation.ui.feature.searchFacility.SearchFacilityViewModel

@Composable
fun SearchFacilityScreenDestination(
    navController: NavController,
    viewModel: SearchFacilityViewModel = hiltViewModel()
) {
    SearchFacilityScreen(
        state = viewModel.viewState.value,
        effectFlow = viewModel.effect,
        onEventSend = viewModel::setEvent,
        onEffectSend = { effect ->
            when(effect) {
                is SearchFacilityContract.Effect.Navigation.ToBack -> navController.navigateUp()
            }
        }
    )

}