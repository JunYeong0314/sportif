package com.jyproject.sportif.presentation.ui.feature.searchChair

import BaseViewModel
import androidx.lifecycle.viewModelScope
import com.jyproject.sportif.data.features.searchChair.SearchChairRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchChairViewModel @Inject constructor(
    private val searchChairRepository: SearchChairRepository
): BaseViewModel<SearchChairContract.Event, SearchChairContract.State, SearchChairContract.Effect>() {
    override fun setInitialState() = SearchChairContract.State(
        searchChairState = SearchChairState()
    )

    override fun handleEvents(event: SearchChairContract.Event) {
        when(event) {
            is SearchChairContract.Event.NavigationToBack -> setEffect { SearchChairContract.Effect.Navigation.ToBack }
            is SearchChairContract.Event.SearchChair ->
                searchChair(contentName = event.contentName, pageNo = event.pageNo)
        }
    }

    private fun searchChair(contentName: String, pageNo: Int = 1) {
        setState { copy(searchChairState = searchChairState.copy(isLoading = true)) }

        viewModelScope.launch {
            searchChairRepository.searchChair(
                pageNo = pageNo,
                contentName = contentName,
                numOfRows = 10
            ).onSuccess {
                setState {
                    copy(searchChairState = searchChairState.copy(isLoading = false, isSuccess = true, searchResult = it))
                }
            }.onFailure {
                setState {
                    copy(searchChairState = searchChairState.copy(isLoading = false, isSuccess = false, errorMessage = it.message))
                }
            }
        }
    }
}