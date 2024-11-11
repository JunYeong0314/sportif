package com.jyproject.sportif.data.features.searchFacility

import com.jyproject.sportif.data.remote.service.searchFacility.SearchFacilityService
import javax.inject.Inject

class SearchFacilityRepository @Inject constructor(
    private val getSearchFacilityService: SearchFacilityService
) {
}