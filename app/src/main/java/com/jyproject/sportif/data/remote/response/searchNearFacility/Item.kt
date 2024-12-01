package com.jyproject.sportif.data.remote.response.searchNearFacility


import com.squareup.moshi.Json

data class Item(
    @Json(name = "addr_cpb_nm")
    val addrCpbNm: String?,
    @Json(name = "addr_ctpv_nm")
    val addrCtpvNm: String?,
    @Json(name = "addr_emd_nm")
    val addrEmdNm: String?,
    @Json(name = "del_yn")
    val delYn: String?,
    @Json(name = "faci_gb_nm")
    val faciGbNm: String?,
    @Json(name = "faci_lat")
    val faciLat: String?,
    @Json(name = "faci_lot")
    val faciLot: String?,
    @Json(name = "faci_nm")
    val faciNm: String?,
    @Json(name = "faci_road_addr")
    val faciRoadAddr: String?,
    @Json(name = "faci_stat_cd")
    val faciStatCd: String?,
    @Json(name = "fcob_nm")
    val fcobNm: String?,
    @Json(name = "fmng_cp_nm")
    val fmngCpNm: String?,
    @Json(name = "fmng_cpb_nm")
    val fmngCpbNm: String?,
    @Json(name = "fmng_type_gb_nm")
    val fmngTypeGbNm: String?,
    @Json(name = "ftype_nm")
    val ftypeNm: String?,
    @Json(name = "nation_yn")
    val nationYn: String?,
    @Json(name = "row_num")
    val rowNum: Int?
)