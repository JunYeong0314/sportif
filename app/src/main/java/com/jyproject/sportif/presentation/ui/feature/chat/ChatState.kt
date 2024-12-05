package com.jyproject.sportif.presentation.ui.feature.chat

data class ChatState(
    val chatList: MutableList<Chat> = arrayListOf(
        Chat(isMy = false, content = "무엇을 도와드릴까요?", isChatLoading = false)
    ),
    val aiAnswer: String = "",
    )

data class Chat(
    val isMy: Boolean,
    val content: String,
    val isChatLoading: Boolean
)