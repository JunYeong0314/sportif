package com.jyproject.sportif.presentation.ui.feature.searchFacility

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.jyproject.sportif.R
import com.jyproject.sportif.presentation.ui.feature.common.composable.SearchBox
import kotlinx.coroutines.flow.Flow

@Composable
fun SearchFacilityScreen(
    state: SearchFacilityContract.State,
    effectFlow: Flow<SearchFacilityContract.Effect>?,
    onEventSend: (event: SearchFacilityContract.Event) -> Unit,
    onEffectSend: (effect: SearchFacilityContract.Effect.Navigation) -> Unit
) {
    val focusManager = LocalFocusManager.current
    var searchText by remember {
        mutableStateOf("")
    }

    LaunchedEffect(effectFlow) {
        effectFlow?.collect { effect ->
            when(effect) {
                is SearchFacilityContract.Effect.Navigation.ToBack -> onEffectSend(SearchFacilityContract.Effect.Navigation.ToBack)
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .clickable(
                interactionSource = remember {
                    MutableInteractionSource()
                },
                indication = null
            ) {
                focusManager.clearFocus()
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier
                    .size(36.dp)
                    .clickable { onEventSend(SearchFacilityContract.Event.NavigateToBack) }
                ,
                imageVector = Icons.Filled.KeyboardArrowLeft,
                contentDescription = null,
                tint = colorResource(id = R.color.light_gray_hard1)
            )
            SearchBox(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 15.dp)
                    .padding(end = 10.dp)
                    .background(
                        color = colorResource(id = R.color.light_gray_weak1),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .height(50.dp),
                focusManager = focusManager,
                hint = "시설을 검색해보세요.",
                value = searchText,
                onValueChange = { searchText = it }
            )
        }
    }
}