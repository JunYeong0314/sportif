package com.jyproject.sportif.presentation.ui.feature

import com.jyproject.sportif.data.remote.response.searchNearFacility.Item
import com.naver.maps.geometry.LatLng

object StaticStore {
    var facilityData: List<Item?> = emptyList()
    var currentLocation: LatLng? = null
}