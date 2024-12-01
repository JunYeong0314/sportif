package com.jyproject.sportif.data.remote.service.searchNearSportFacility

import com.jyproject.sportif.data.remote.response.searchNearFacility.GetSearchNearSportFacilityResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchNearSportFacilityService {
    @GET("SRVC_SFMS_FACIL_INFO/TODZ_SFMS_FACIL_INFO")
    suspend fun getSearchNearSportFacility(
        @Query("serviceKey") serviceKey: String,
        @Query("resultType") resultType: String,
        @Query("pageNo") pageNo: Int,
        @Query("numOfRows") numOfRows: Int,
        @Query("fmng_cp_nm") city: String,
        @Query("fmng_cpb_nm") district: String
    ): Response<GetSearchNearSportFacilityResponse>
}