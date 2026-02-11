package com.example.buyandsell.domain.usecase

import com.example.buyandsell.data.model.Message
import com.example.buyandsell.domain.repository.ChatRepository

class SendMessageUseCase(private val chatRepository: ChatRepository) {
    suspend operator fun invoke(chatId: String, message: Message) = chatRepository.sendMessage(chatId, message)
}