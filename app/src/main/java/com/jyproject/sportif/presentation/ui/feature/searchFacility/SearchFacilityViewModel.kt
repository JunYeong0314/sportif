package com.jyproject.sportif.presentation.ui.feature.searchFacility

import BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchFacilityViewModel @Inject constructor() :
    BaseViewModel<SearchFacilityContract.Event, SearchFacilityContract.State, SearchFacilityContract.Effect>() {
    override fun setInitialState() = SearchFacilityContract.State(
        searchFacilityState = SearchFacilityState()
    )

    override fun handleEvents(event: SearchFacilityContract.Event) {
        when(event) {
            is SearchFacilityContract.Event.NavigateToBack -> setEffect { SearchFacilityContract.Effect.Navigation.ToBack }
        }
    }
}