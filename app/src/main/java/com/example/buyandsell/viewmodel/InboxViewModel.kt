package com.example.buyandsell.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.buyandsell.data.model.Chat
import com.example.buyandsell.domain.usecase.GetChatsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class InboxViewModel(private val getChatsUseCase: GetChatsUseCase) : ViewModel() {

    private val _chats = MutableStateFlow<List<Chat>>(emptyList())
    val chats: StateFlow<List<Chat>> = _chats

    init {
        loadChats()
    }

    private fun loadChats() {
        viewModelScope.launch {
            getChatsUseCase().collect {
                _chats.value = it
            }
        }
    }
}