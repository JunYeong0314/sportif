package com.jyproject.sportif.presentation.ui.feature.searchFacility.selectCity

import BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SelectCityViewModel @Inject constructor() :
    BaseViewModel<SelectCityContract.Event, SelectCityContract.State, SelectCityContract.Effect>() {
    override fun setInitialState() = SelectCityContract.State(
        selectCityState = SelectCityState()
    )

    override fun handleEvents(event: SelectCityContract.Event) {
        when (event) {
            is SelectCityContract.Event.NavigateToBack -> setEffect { SelectCityContract.Effect.Navigation.ToBack }
            is SelectCityContract.Event.SelectCity -> setState {
                copy(
                    selectCityState = selectCityState.copy(
                        selectedCity = event.city
                    )
                )
            }
            is SelectCityContract.Event.NavigateToSearchFacility -> setEffect { SelectCityContract.Effect.Navigation.ToSearchFacility(city = event.city) }
        }
    }
}