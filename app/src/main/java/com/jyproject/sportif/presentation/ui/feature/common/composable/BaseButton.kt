package com.jyproject.sportif.presentation.ui.feature.common.composable

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun BaseButton(
    modifier: Modifier = Modifier,
    text: String,
    buttonColor: ButtonColors = ButtonDefaults.buttonColors(Color.Black),
    textColor: Color = Color.White,
    roundedCornerShapeValueOfDp: Dp = 15.dp,
    borderStroke: BorderStroke? = null,
    enabled: Boolean = true,
    onClickButton: () -> Unit,
) {
    Button(
        modifier = modifier,
        onClick = onClickButton,
        enabled = enabled,
        shape = RoundedCornerShape(roundedCornerShapeValueOfDp),
        border = borderStroke,
        colors = buttonColor
    ) {
        Text(
            text = text,
            fontWeight = FontWeight.Bold,
            color = textColor
        )
    }
}
