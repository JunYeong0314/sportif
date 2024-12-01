package com.jyproject.sportif.data.remote.response.naverMapGeocode


import com.squareup.moshi.Json

data class Area1(
    @Json(name = "alias")
    val alias: String?,
    @Json(name = "coords")
    val coords: Coords?,
    @Json(name = "name")
    val name: String?
)