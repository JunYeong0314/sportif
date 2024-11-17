package com.jyproject.sportif.presentation.ui.feature.searchFacility

import BaseViewModel
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.jyproject.sportif.data.features.searchFacility.SearchFacilityRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchFacilityViewModel @Inject constructor(
    private val searchFacilityRepository: SearchFacilityRepository
) :
    BaseViewModel<SearchFacilityContract.Event, SearchFacilityContract.State, SearchFacilityContract.Effect>() {
    override fun setInitialState() = SearchFacilityContract.State(
        searchFacilityState = SearchFacilityState()
    )

    override fun handleEvents(event: SearchFacilityContract.Event) {
        when (event) {
            is SearchFacilityContract.Event.NavigationToBack -> setEffect { SearchFacilityContract.Effect.Navigation.ToBack }
            is SearchFacilityContract.Event.SearchFacility -> searchFacility(
                city = event.city,
                region = event.region,
                facilityName = event.facilityName,
                pageNo = event.pageNo
            )
        }
    }

    private fun searchFacility(city: String, region: String?, facilityName: String?, pageNo: Int = 1) {
        setState { copy(searchFacilityState = searchFacilityState.copy(isLoading = true)) }

        viewModelScope.launch {
            searchFacilityRepository.searchFacility(
                pageNo = pageNo,
                numOfRows = 10,
                cityNm = city,
                localNm = region,
                facilityNm = facilityName
            ).onSuccess {
                setState {
                    copy(
                        searchFacilityState = searchFacilityState.copy(
                            isLoading = false,
                            searchResult = it,
                            isSuccess = true
                        )
                    )
                }
            }.onFailure {
                setState {
                    copy(
                        searchFacilityState = searchFacilityState.copy(
                            isLoading = false,
                            isSuccess = false,
                            errorMessage = it.message
                        )
                    )
                }
            }
        }
    }
}