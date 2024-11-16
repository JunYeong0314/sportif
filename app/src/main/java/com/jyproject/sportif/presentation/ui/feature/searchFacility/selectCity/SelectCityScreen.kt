package com.jyproject.sportif.presentation.ui.feature.searchFacility.selectCity

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.jyproject.sportif.R
import com.jyproject.sportif.presentation.ui.feature.common.composable.BaseButton
import kotlinx.coroutines.flow.Flow
import androidx.compose.foundation.layout.Column
import androidx.compose.ui.graphics.Color
import com.jyproject.sportif.presentation.ui.feature.common.composable.BaseTopBar

@Composable
fun SelectCityScreen(
    state: SelectCityContract.State,
    effectFlow: Flow<SelectCityContract.Effect>?,
    onEventSend: (event: SelectCityContract.Event) -> Unit,
    onEffectSend: (effect: SelectCityContract.Effect.Navigation) -> Unit
) {
    val regionItems = arrayListOf(
        "서울", "부산", "대구", "인천",
        "광주", "대전", "울산", "세종",
        "경기", "강원", "강원", "충청",
        "경상", "제주",
    )
    var selectedIndex by remember {
        mutableIntStateOf(-1)
    }

    LaunchedEffect(effectFlow) {
        effectFlow?.collect { effect ->
            when (effect) {
                is SelectCityContract.Effect.Navigation.ToBack -> onEffectSend(
                    SelectCityContract.Effect.Navigation.ToBack
                )

                is SelectCityContract.Effect.Navigation.ToSearchFacility -> onEffectSend(
                    SelectCityContract.Effect.Navigation.ToSearchFacility(city = effect.city)
                )
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BaseTopBar(
            title = "지역 선택",
            onClickBackButton = {
                onEventSend(SelectCityContract.Event.NavigateToBack)
            }
        )
        Spacer(modifier = Modifier.size(12.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            regionItems.chunked(2).forEachIndexed { rowIndex, rowItems ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    rowItems.forEachIndexed { columnIndex, item ->
                        val buttonIndex = rowIndex * 2 + columnIndex
                        BaseButton(
                            modifier = Modifier.weight(1f),
                            onClickButton = { selectedIndex = buttonIndex },
                            buttonColor = if (selectedIndex == buttonIndex) {
                                ButtonDefaults.buttonColors(
                                    containerColor = colorResource(id = R.color.black)
                                )
                            } else {
                                ButtonDefaults.buttonColors(
                                    containerColor = colorResource(id = R.color.white)
                                )
                            },
                            text = item,
                            textColor = if (selectedIndex == buttonIndex) {
                                colorResource(id = R.color.white)
                            } else {
                                colorResource(id = R.color.black)
                            },
                            roundedCornerShapeValueOfDp = 12.dp,
                            borderStroke = BorderStroke(0.5.dp, Color.Black),
                        )
                    }

                }
            }

        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 40.dp),
            contentAlignment = Alignment.BottomCenter,
        ) {
            BaseButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                text = "다음",
                enabled = selectedIndex != -1,
                roundedCornerShapeValueOfDp = 10.dp,
                onClickButton = {
                    onEventSend(SelectCityContract.Event.NavigateToSearchFacility(city = regionItems[selectedIndex]))
                }
            )
        }
    }
}