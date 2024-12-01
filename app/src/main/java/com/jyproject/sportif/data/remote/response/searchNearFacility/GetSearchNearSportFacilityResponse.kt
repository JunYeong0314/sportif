package com.jyproject.sportif.data.remote.response.searchNearFacility


import com.squareup.moshi.Json

data class GetSearchNearSportFacilityResponse(
    @Json(name = "response")
    val response: Response?
)