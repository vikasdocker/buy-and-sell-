package com.example.buyandsell.di

import com.example.buyandsell.data.repository.ChatRepositoryImpl
import com.example.buyandsell.data.repository.ProductRepositoryImpl
import com.example.buyandsell.domain.repository.ChatRepository
import com.example.buyandsell.domain.repository.ProductRepository
import com.example.buyandsell.domain.usecase.CreateChatUseCase
import com.example.buyandsell.domain.usecase.GetChatsUseCase
import com.example.buyandsell.domain.usecase.GetMessagesUseCase
import com.example.buyandsell.domain.usecase.GetProductsUseCase
import com.example.buyandsell.domain.usecase.SendMessageUseCase
import com.example.buyandsell.viewmodel.AuthViewModel
import com.example.buyandsell.viewmodel.ChatViewModel
import com.example.buyandsell.viewmodel.HomeViewModel
import com.example.buyandsell.viewmodel.InboxViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single<ProductRepository> { ProductRepositoryImpl() }
    single { GetProductsUseCase(get()) }
    viewModel { HomeViewModel(get(), get()) }

    // Firebase
    single { FirebaseAuth.getInstance() }
    single { FirebaseFirestore.getInstance() }

    // Auth
    viewModel { AuthViewModel(get()) }

    // Chat
    single<ChatRepository> { ChatRepositoryImpl(get(), get()) }
    single { GetChatsUseCase(get()) }
    single { GetMessagesUseCase(get()) }
    single { SendMessageUseCase(get()) }
    single { CreateChatUseCase(get()) }
    viewModel { InboxViewModel(get()) }
    viewModel { ChatViewModel(get(), get()) }
}