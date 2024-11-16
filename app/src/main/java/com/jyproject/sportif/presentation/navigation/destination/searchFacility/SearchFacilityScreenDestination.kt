package com.jyproject.sportif.presentation.navigation.destination.searchFacility

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.jyproject.sportif.presentation.ui.feature.searchFacility.SearchFacilityScreen
import com.jyproject.sportif.presentation.ui.feature.searchFacility.SearchFacilityViewModel

@Composable
fun SearchFacilityScreenDestination(
    city: String,
    navController: NavController,
    viewModel: SearchFacilityViewModel = hiltViewModel()
) {

    SearchFacilityScreen(
        city = city,
        state = viewModel.viewState.value,
        effectFlow = viewModel.effect,
        onEventSend = viewModel::setEvent,
        onEffectSend = {

        }
    )

}