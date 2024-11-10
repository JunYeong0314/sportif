package com.jyproject.sportif.presentation.ui.feature.home

import BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(): BaseViewModel<HomeContract.Event, HomeContract.State, HomeContract.Effect>() {
    override fun setInitialState() = HomeContract.State(
        homeState = HomeState()
    )

    override fun handleEvents(event: HomeContract.Event) {

    }
}