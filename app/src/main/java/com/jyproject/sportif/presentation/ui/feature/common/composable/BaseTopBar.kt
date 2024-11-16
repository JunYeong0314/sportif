package com.jyproject.sportif.presentation.ui.feature.common.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.jyproject.sportif.R

@Composable
fun BaseTopBar(
    title: String? = null,
    onClickBackButton: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            modifier = Modifier
                .size(50.dp)
                .padding(start = 16.dp)
                .align(Alignment.CenterStart)
                .clickable { onClickBackButton() },
            imageVector = Icons.Filled.KeyboardArrowLeft,
            contentDescription = "뒤로가기 버튼",
            tint = colorResource(id = R.color.light_gray_hard1)
        )

        if (title != null) {
            Text(
                text = title,
                color = colorResource(id = R.color.black),
                modifier = Modifier.align(Alignment.Center),
                fontWeight = FontWeight.Bold
            )
        }
    }
}