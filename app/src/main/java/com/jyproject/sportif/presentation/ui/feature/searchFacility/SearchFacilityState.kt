package com.jyproject.sportif.presentation.ui.feature.searchFacility

import com.jyproject.sportif.data.remote.response.searchFacility.SearchFacilityResponse

data class SearchFacilityState(
    val isLoading: Boolean = false,
    val searchResult: SearchFacilityResponse? = null,
    val isSuccess: Boolean? = null,
    val errorMessage: String? = null
)