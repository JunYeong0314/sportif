package com.jyproject.sportif.data.remote.response.naverMapGeocode


import com.squareup.moshi.Json

data class Addition0(
    @Json(name = "type")
    val type: String?,
    @Json(name = "value")
    val value: String?
)