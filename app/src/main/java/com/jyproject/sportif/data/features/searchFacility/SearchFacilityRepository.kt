package com.jyproject.sportif.data.features.searchFacility
import com.jyproject.sportif.data.Defines
import com.jyproject.sportif.data.remote.response.searchFacility.SearchFacilityResponse
import com.jyproject.sportif.data.remote.service.searchFacility.SearchFacilityService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.URLEncoder
import javax.inject.Inject

class SearchFacilityRepository @Inject constructor(
    private val getSearchFacilityService: SearchFacilityService
) {
    suspend fun searchFacility(
        pageNo: Int,
        numOfRows: Int,
        cityNm: String?,
        localNm: String?,
        facilityNm: String?
    ): Result<SearchFacilityResponse?> {

        return runCatching {
            getSearchFacilityService.getSearchFacility(
                serviceKey = Defines.SERVICE_KEY,
                resultType = "json",
                pageNo = pageNo,
                numOfRows = numOfRows,
                cityNm = cityNm,
                localNm = localNm,
                facilityNm = facilityNm
            ).body()
        }
    }
}