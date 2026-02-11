package com.example.buyandsell.domain.repository

import com.example.buyandsell.data.model.Product

interface ProductRepository {
    suspend fun getProducts(): List<Product>
}