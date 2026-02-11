package com.example.buyandsell.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.buyandsell.data.model.Message
import com.example.buyandsell.domain.usecase.GetMessagesUseCase
import com.example.buyandsell.domain.usecase.SendMessageUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.Date

class ChatViewModel(
    private val getMessagesUseCase: GetMessagesUseCase,
    private val sendMessageUseCase: SendMessageUseCase
) : ViewModel() {

    private val _messages = MutableStateFlow<List<Message>>(emptyList())
    val messages: StateFlow<List<Message>> = _messages

    private val _newMessageText = MutableStateFlow("")
    val newMessageText: StateFlow<String> = _newMessageText

    fun loadMessages(chatId: String) {
        viewModelScope.launch {
            getMessagesUseCase(chatId).collect {
                _messages.value = it
            }
        }
    }

    fun onNewMessageTextChange(newText: String) {
        _newMessageText.value = newText
    }

    fun sendMessage(chatId: String) {
        val text = _newMessageText.value
        if (text.isNotBlank()) {
            viewModelScope.launch {
                val message = Message(text = text.trim(), timestamp = Date())
                sendMessageUseCase(chatId, message)
                _newMessageText.value = ""
            }
        }
    }
}