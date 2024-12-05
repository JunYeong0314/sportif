package com.jyproject.sportif.presentation.ui.feature.chat

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.jyproject.sportif.R
import com.jyproject.sportif.presentation.ui.feature.common.composable.BaseTopBar
import com.jyproject.sportif.presentation.ui.feature.common.composable.SearchTextField
import com.jyproject.sportif.presentation.ui.feature.home.HomeContract
import com.jyproject.sportif.presentation.ui.feature.searchChair.SearchChairContract
import kotlinx.coroutines.flow.Flow
import org.w3c.dom.Text

@Composable
fun ChatScreen(
    state: ChatContract.State,
    effectFlow: Flow<ChatContract.Effect>?,
    onEventSend: (event: ChatContract.Event) -> Unit,
    onEffectSend: (effect: ChatContract.Effect) -> Unit
) {
    val focusManager = LocalFocusManager.current

    var chatText by remember {
        mutableStateOf("")
    }

    LaunchedEffect(effectFlow) {
        effectFlow?.collect { effect ->
            when (effect) {
                is ChatContract.Effect.Navigation.ToBack -> {
                    onEffectSend(effect)
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                focusManager.clearFocus()
            }
            .background(Color.White),
    ) {
        BaseTopBar(
            onClickBackButton = {
                onEventSend(ChatContract.Event.NavigationToBack)
            }
        )
        Spacer(modifier = Modifier.height(4.dp))
        LazyColumn(
            modifier = Modifier.weight(0.85f)
        ) {
            itemsIndexed(items = state.chatState.chatList) { _, message ->
                ChatBubble(isLoading = message.isChatLoading, isMy = message.isMy, body = message.content)
            }
        }
        ChatInputBox(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.1f)
                .padding(horizontal = 8.dp, vertical = 10.dp)
                .background(
                    color = colorResource(id = R.color.light_gray_weak1),
                    shape = RoundedCornerShape(12.dp)
                ),
            focusManager = focusManager,
            value = chatText,
            hint = "질문해보세요.",
            onValueChange = { chatText = it },
            onSendClick = {
                onEventSend(ChatContract.Event.Question(Chat(isMy = true, isChatLoading = false, content = chatText)))
                chatText = ""
                focusManager.clearFocus()
            }
        )

    }

}

@Composable
fun ChatBubble(
    isLoading: Boolean,
    isMy: Boolean,
    body: String
) {
    val chatBackgroundColor: Color
    val chatTextColor: Color

    if (isMy) {
        chatBackgroundColor = Color.White
        chatTextColor = Color.Black
    } else {
        chatBackgroundColor = Color.Black
        chatTextColor = Color.White
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = if (isMy) Arrangement.End else Arrangement.Start,
    ) {
        if (!isMy) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .border(
                        width = 0.5.dp,
                        color = Color.Black,
                        shape = CircleShape
                    )
                    .background(
                        color = Color.White,
                        shape = CircleShape
                    )
            ) {
                Image(
                    modifier = Modifier.padding(4.dp),
                    painter = painterResource(id = R.drawable.ic_app_icon),
                    contentDescription = "chat",
                    contentScale = ContentScale.FillHeight
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
        }

        Box(
            modifier = Modifier
                .background(
                    color = chatBackgroundColor,
                    shape = CircleShape
                )
                .border(
                    width = 0.5.dp,
                    color = Color.Black,
                    shape = CircleShape
                )
                .padding(horizontal = 12.dp, vertical = 8.dp) // 내부 텍스트 패딩
        ) {
            Text(
                text = body,
                style = TextStyle(color = chatTextColor)
            )
        }
    }
}

@Composable
private fun ChatInputBox(
    modifier: Modifier,
    focusManager: FocusManager,
    value: String,
    hint: String,
    onValueChange: (String) -> Unit,
    onSendClick: () -> Unit
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
            modifier = Modifier
                .weight(0.1f)
                .clickable { onSendClick() },
            imageVector = Icons.Default.Send,
            contentDescription = null,
            tint = Color.Black
        )
    }
}
