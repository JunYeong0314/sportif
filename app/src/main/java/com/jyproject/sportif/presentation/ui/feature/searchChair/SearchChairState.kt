package com.jyproject.sportif.presentation.ui.feature.searchChair

import com.jyproject.sportif.data.remote.response.searchChair.SearchChairResponse

data class SearchChairState(
    val isLoading: Boolean = false,
    val searchResult: SearchChairResponse? = null,
    val isSuccess: Boolean? = null,
    val errorMessage: String? = null
)