package com.example.buyandsell.domain.usecase

import com.example.buyandsell.domain.repository.ChatRepository

class GetChatsUseCase(private val chatRepository: ChatRepository) {
    operator fun invoke() = chatRepository.getChats()
}