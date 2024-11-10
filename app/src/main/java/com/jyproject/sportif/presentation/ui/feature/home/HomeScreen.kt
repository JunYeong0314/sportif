package com.jyproject.sportif.presentation.ui.feature.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jyproject.sportif.presentation.anim.LottieBook
import com.jyproject.sportif.presentation.anim.LottieSearch
import com.jyproject.sportif.presentation.ui.feature.home.composable.SearchButton
import com.jyproject.sportif.presentation.ui.theme.CustomTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@Composable
fun HomeScreen(
    state: HomeContract.State,
    effectFlow: Flow<HomeContract.Effect>?,
    onEventSend: (event: HomeContract.Event) -> Unit,
    onEffectSend: (effect: HomeContract.Effect.Navigation) -> Unit
) {
    LaunchedEffect(effectFlow) {
        effectFlow?.collect { effect->
            when(effect) {
                is HomeContract.Effect.Navigation.ToSearchFacility -> { onEffectSend(effect) }
                is HomeContract.Effect.Navigation.ToSearchChair -> { onEffectSend(effect) }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 18.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            SearchButton(
                onEventSend = {
                    onEventSend(HomeContract.Event.NavigationToSearchFacility)
                }
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    LottieSearch(
                        modifier = Modifier
                            .height(100.dp)
                    )
                    Text(
                        modifier = Modifier.padding(bottom = 8.dp),
                        text = "시설 찾아보기",
                        style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    )
                }
            }
            Spacer(modifier = Modifier.size(24.dp))
            SearchButton(
                onEventSend = {
                    onEventSend(HomeContract.Event.NavigationToSearchChair)
                }
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    LottieBook(modifier = Modifier.height(100.dp))
                    Text(
                        modifier = Modifier.padding(bottom = 8.dp),
                        text = "강좌 찾아보기",
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                    )
                }
            }
        }
    }

}

