package com.example.buyandsell.data.model

import com.google.firebase.firestore.ServerTimestamp
import java.util.Date

data class Chat(
    val id: String = "",
    val participants: List<String> = emptyList(),
    val lastMessage: String = "",
    @ServerTimestamp
    val timestamp: Date? = null,
    val productId: String = ""
)