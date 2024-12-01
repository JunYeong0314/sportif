package com.jyproject.sportif.data.remote.response.naverMapGeocode


import com.squareup.moshi.Json

data class Center(
    @Json(name = "crs")
    val crs: String?,
    @Json(name = "x")
    val x: Double?,
    @Json(name = "y")
    val y: Double?
)