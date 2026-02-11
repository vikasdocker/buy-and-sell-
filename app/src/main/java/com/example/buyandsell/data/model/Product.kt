package com.example.buyandsell.data.model

data class Product(
    val id: String,
    val title: String,
    val price: String,
    val location: String,
    val postedTime: String,
    val sellerType: String,
    val imageUrls: List<String>,
    val description: String,
    val category: String,
    val phoneNumber: String,
    val sellerId: String // Added sellerId
)