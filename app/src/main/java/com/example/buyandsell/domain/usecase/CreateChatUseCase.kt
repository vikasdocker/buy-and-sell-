package com.example.buyandsell.domain.usecase

import com.example.buyandsell.data.model.Chat
import com.example.buyandsell.domain.repository.ChatRepository

class CreateChatUseCase(private val chatRepository: ChatRepository) {
    suspend operator fun invoke(chat: Chat) = chatRepository.createChat(chat)
}