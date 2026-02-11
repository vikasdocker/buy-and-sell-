package com.example.buyandsell.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.buyandsell.data.model.Chat
import com.example.buyandsell.data.model.Product
import com.example.buyandsell.domain.usecase.CreateChatUseCase
import com.example.buyandsell.domain.usecase.GetProductsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getProductsUseCase: GetProductsUseCase,
    private val createChatUseCase: CreateChatUseCase
) : ViewModel() {

    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products

    fun loadProducts() {
        viewModelScope.launch {
            _products.value = getProductsUseCase()
        }
    }

    fun createChat(productId: String, sellerId: String, callback: (String) -> Unit) {
        viewModelScope.launch {
            val chat = Chat(productId = productId, participants = listOf(sellerId))
            val chatId = createChatUseCase(chat)
            callback(chatId)
        }
    }
}