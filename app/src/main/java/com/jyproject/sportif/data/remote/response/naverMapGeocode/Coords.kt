package com.jyproject.sportif.data.remote.response.naverMapGeocode


import com.squareup.moshi.Json

data class Coords(
    @Json(name = "center")
    val center: Center?
)