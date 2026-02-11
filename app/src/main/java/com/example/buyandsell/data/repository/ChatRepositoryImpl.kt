package com.example.buyandsell.data.repository

import com.example.buyandsell.data.model.Chat
import com.example.buyandsell.data.model.Message
import com.example.buyandsell.domain.repository.ChatRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class ChatRepositoryImpl(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth
) : ChatRepository {

    private val currentUserId: String
        get() = auth.currentUser?.uid ?: ""

    override fun getChats(): Flow<List<Chat>> = callbackFlow {
        val chatsCollection = firestore.collection("chats")
            .whereArrayContains("participants", currentUserId)
            .orderBy("timestamp", Query.Direction.DESCENDING)

        val listener = chatsCollection.addSnapshotListener { snapshot, error ->
            if (error != null) {
                close(error)
                return@addSnapshotListener
            }
            snapshot?.let {
                trySend(it.toObjects(Chat::class.java))
            }
        }
        awaitClose { listener.remove() }
    }

    override fun getMessages(chatId: String): Flow<List<Message>> = callbackFlow {
        val messagesCollection = firestore.collection("chats").document(chatId)
            .collection("messages")
            .orderBy("timestamp", Query.Direction.ASCENDING)

        val listener = messagesCollection.addSnapshotListener { snapshot, error ->
            if (error != null) {
                close(error)
                return@addSnapshotListener
            }
            snapshot?.let {
                trySend(it.toObjects(Message::class.java))
            }
        }
        awaitClose { listener.remove() }
    }

    override suspend fun sendMessage(chatId: String, message: Message) {
        val chatRef = firestore.collection("chats").document(chatId)
        val messageRef = chatRef.collection("messages").document()

        firestore.runBatch {
            it.set(messageRef, message.copy(id = messageRef.id, senderId = currentUserId))
            it.update(chatRef, "lastMessage", message.text, "timestamp", message.timestamp)
        }.await()
    }

    override suspend fun createChat(chat: Chat): String {
        val chatRef = firestore.collection("chats").document()
        chatRef.set(chat.copy(id = chatRef.id, participants = chat.participants + currentUserId)).await()
        return chatRef.id
    }
}
