package com.jyproject.sportif.data.features.searchChair

import com.jyproject.sportif.data.Defines
import com.jyproject.sportif.data.remote.response.searchChair.SearchChairResponse
import com.jyproject.sportif.data.remote.service.searchChair.SearchChairService
import javax.inject.Inject

class SearchChairRepository @Inject constructor(
    private val getSearchChairService: SearchChairService
) {
    suspend fun searchChair(
        pageNo: Int,
        numOfRows: Int,
        contentName: String
    ): Result<SearchChairResponse?> {
        return runCatching {
            getSearchChairService.getSearchChair(
                serviceKey = Defines.SERVICE_KEY,
                resultType = "json",
                pageNo = pageNo,
                numOfRows = numOfRows,
                contentName = contentName
            ).body()
        }
    }
}