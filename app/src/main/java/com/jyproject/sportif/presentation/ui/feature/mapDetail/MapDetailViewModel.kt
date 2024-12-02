package com.jyproject.sportif.presentation.ui.feature.mapDetail

import BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MapDetailViewModel @Inject constructor(): BaseViewModel<MapDetailContract.Event, MapDetailContract.State, MapDetailContract.Effect>() {
    override fun setInitialState() = MapDetailContract.State(
        mapDetailState = MapDetailState()
    )

    override fun handleEvents(event: MapDetailContract.Event) {
        when(event) {
            is MapDetailContract.Event.NavigationToBack -> setEffect { MapDetailContract.Effect.Navigation.ToBack }
        }
    }
}