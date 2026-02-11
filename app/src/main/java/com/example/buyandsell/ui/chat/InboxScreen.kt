package com.example.buyandsell.ui.chat

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.buyandsell.data.model.Chat
import com.example.buyandsell.viewmodel.InboxViewModel
import org.koin.androidx.compose.getViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InboxScreen(navController: NavController, viewModel: InboxViewModel = getViewModel()) {
    val chats by viewModel.chats.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "Inbox") })
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            items(chats) { chat ->
                ChatItem(chat = chat, onChatClicked = {
                    navController.navigate("chat/${chat.id}")
                })
            }
        }
    }
}

@Composable
fun ChatItem(chat: Chat, onChatClicked: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onChatClicked() }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Chat with ${chat.participants.first { it != "" } }") // Placeholder for user name
            Text(text = chat.lastMessage)
        }
    }
}
