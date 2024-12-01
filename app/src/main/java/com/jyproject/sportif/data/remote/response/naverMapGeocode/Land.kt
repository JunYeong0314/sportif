package com.jyproject.sportif.data.remote.response.naverMapGeocode


import com.squareup.moshi.Json

data class Land(
    @Json(name = "addition0")
    val addition0: Addition0?,
    @Json(name = "addition1")
    val addition1: Addition0?,
    @Json(name = "addition2")
    val addition2: Addition0?,
    @Json(name = "addition3")
    val addition3: Addition0?,
    @Json(name = "addition4")
    val addition4: Addition0?,
    @Json(name = "coords")
    val coords: Coords?,
    @Json(name = "name")
    val name: String?,
    @Json(name = "number1")
    val number1: String?,
    @Json(name = "number2")
    val number2: String?,
    @Json(name = "type")
    val type: String?
)