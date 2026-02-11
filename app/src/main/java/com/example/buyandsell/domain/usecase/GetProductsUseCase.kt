package com.example.buyandsell.domain.usecase

import com.example.buyandsell.domain.repository.ProductRepository

class GetProductsUseCase(private val productRepository: ProductRepository) {
    suspend operator fun invoke() = productRepository.getProducts()
}