package com.example.buyandsell.domain.usecase

import com.example.buyandsell.domain.repository.ChatRepository

class GetMessagesUseCase(private val chatRepository: ChatRepository) {
    operator fun invoke(chatId: String) = chatRepository.getMessages(chatId)
}