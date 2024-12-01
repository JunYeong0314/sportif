package com.jyproject.sportif.data.remote.response.naverMapGeocode


import com.squareup.moshi.Json

data class Result(
    @Json(name = "code")
    val code: Code?,
    @Json(name = "land")
    val land: Land?,
    @Json(name = "name")
    val name: String?,
    @Json(name = "region")
    val region: Region?
)