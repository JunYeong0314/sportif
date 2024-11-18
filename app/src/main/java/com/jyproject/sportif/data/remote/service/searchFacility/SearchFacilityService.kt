package com.jyproject.sportif.data.remote.service.searchFacility

import com.jyproject.sportif.data.Defines
import com.jyproject.sportif.data.remote.response.searchFacility.SearchFacilityResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import java.net.URLEncoder

interface SearchFacilityService {
    @GET("SRVC_OD_API_FACIL_MNG_DVOUCHER/TODZ_API_MNG_DVOUCHER_I")
    suspend fun getSearchFacility(
        @Query("serviceKey") serviceKey: String,
        @Query("resultType") resultType: String,
        @Query("pageNo") pageNo: Int,
        @Query("numOfRows") numOfRows: Int,
        @Query("city_nm") cityNm: String?,
        @Query("local_nm") localNm: String?,
        @Query("facil_nm") facilityNm: String?
    ): Response<SearchFacilityResponse>

}