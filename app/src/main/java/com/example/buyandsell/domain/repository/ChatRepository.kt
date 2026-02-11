package com.example.buyandsell.domain.repository

import com.example.buyandsell.data.model.Chat
import com.example.buyandsell.data.model.Message
import kotlinx.coroutines.flow.Flow

interface ChatRepository {
    fun getChats(): Flow<List<Chat>>
    fun getMessages(chatId: String): Flow<List<Message>>
    suspend fun sendMessage(chatId: String, message: Message)
    suspend fun createChat(chat: Chat): String
}
