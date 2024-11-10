package com.jyproject.sportif.presentation.ui.feature.common.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp


@Composable
fun SearchBox(
    modifier: Modifier,
    focusManager: FocusManager,
    value: String,
    hint: String,
    onValueChange: (String) -> Unit
){
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        SearchTextField(
            modifier = Modifier
                .weight(0.9f)
                .padding(horizontal = 15.dp)
            ,
            value = value,
            hint = hint,
            onValueChange = onValueChange,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                }
            )
        )
        Icon(
            modifier = Modifier.weight(0.1f),
            imageVector = Icons.Default.Search,
            contentDescription = null,
            tint = Color.DarkGray
        )
    }
}