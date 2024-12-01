package com.jyproject.sportif.data.remote.response.naverMapGeocode


import com.squareup.moshi.Json

data class GetNaverMapGeocodeResponse(
    @Json(name = "results")
    val results: List<Result>?,
    @Json(name = "status")
    val status: Status?
)