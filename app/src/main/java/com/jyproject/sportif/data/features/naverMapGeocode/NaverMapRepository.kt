package com.jyproject.sportif.data.features.naverMapGeocode

import com.jyproject.sportif.data.remote.response.naverMapGeocode.GetNaverMapGeocodeResponse
import com.jyproject.sportif.data.remote.service.naverMapGeocode.NaverMapGeocodeService
import javax.inject.Inject

class NaverMapRepository @Inject constructor(
    private val naverMapGeocodeService: NaverMapGeocodeService
) {
    suspend fun getGeocode(
        coords: String
    ): Result<GetNaverMapGeocodeResponse?> {
        return runCatching {
            naverMapGeocodeService.getNaverMapGeocode(
                coords = coords,
                output = "json"
            ).body()
        }
    }
}