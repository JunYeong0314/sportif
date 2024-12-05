package com.jyproject.sportif.data.remote.service.chat

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ChatService {
    @POST("/stream_chat")
    suspend fun getChat(
        @Body content: String
    ): Response<String>
}