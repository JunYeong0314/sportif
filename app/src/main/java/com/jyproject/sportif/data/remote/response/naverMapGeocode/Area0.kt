package com.jyproject.sportif.data.remote.response.naverMapGeocode


import com.squareup.moshi.Json

data class Area0(
    @Json(name = "coords")
    val coords: Coords?,
    @Json(name = "name")
    val name: String?
)