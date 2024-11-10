package com.jyproject.sportif.presentation.ui.feature.home.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun SearchButton(
    onEventSend: () -> Unit,
    content: @Composable () -> Unit,
) {
    Box(
        modifier = Modifier
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { onEventSend() }
            .background(
                shape = RoundedCornerShape(20.dp),
                color = Color.White
            )
            .shadow(
                shape = RoundedCornerShape(20.dp),
                elevation = 3.dp
            )
        ,
    ) {
        Column(
            modifier = Modifier
                .background(Color.White)
        ) {
            content()
        }
    }
}