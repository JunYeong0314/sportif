package com.jyproject.sportif.data.remote.service.naverMapGeocode

import com.jyproject.sportif.data.remote.response.naverMapGeocode.GetNaverMapGeocodeResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NaverMapGeocodeService {
    @GET("map-reversegeocode/v2/gc")
    suspend fun getNaverMapGeocode(
        @Query("coords") coords: String,
        @Query("output") output: String
    ): Response<GetNaverMapGeocodeResponse>

}