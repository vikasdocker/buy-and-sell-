package com.example.buyandsell.data.repository

import com.example.buyandsell.data.model.Product
import com.example.buyandsell.domain.repository.ProductRepository

class ProductRepositoryImpl : ProductRepository {
    override suspend fun getProducts(): List<Product> {
        // Dummy data for now
        return listOf(
            Product(
                id = "1",
                title = "iPhone 13 Pro",
                price = "$999",
                location = "New York, NY",
                postedTime = "2 hours ago",
                sellerType = "Individual",
                imageUrls = listOf("https://www.apple.com/v/iphone-13-pro/f/images/overview/design/design_pro_blue_1_large.jpg"),
                description = "Slightly used iPhone 13 Pro, in great condition.",
                category = "Mobiles",
                phoneNumber = "1234567890",
                sellerId = "seller123"
            ),
            Product(
                id = "2",
                title = "MacBook Pro 16-inch",
                price = "$2399",
                location = "San Francisco, CA",
                postedTime = "5 hours ago",
                sellerType = "Dealer",
                imageUrls = listOf("https://www.apple.com/v/macbook-pro-14-and-16/b/images/overview/hero/hero_intro_endframe__e62172im43me_large.jpg"),
                description = "Brand new MacBook Pro with M1 Max chip.",
                category = "Electronics",
                phoneNumber = "0987654321",
                sellerId = "seller456"
            )
        )
    }
}