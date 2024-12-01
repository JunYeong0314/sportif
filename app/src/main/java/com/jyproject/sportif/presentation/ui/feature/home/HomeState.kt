package com.jyproject.sportif.presentation.ui.feature.home

import com.jyproject.sportif.data.remote.response.searchNearFacility.GetSearchNearSportFacilityResponse
import com.naver.maps.geometry.LatLng

data class HomeState(
    val isLoading: Boolean = false,
    val geoCodeResponse: Pair<String, String>? = null,
    val nearSportFacilityData: List<LatLng>? = null,
    val errorMessage: String? = null
)