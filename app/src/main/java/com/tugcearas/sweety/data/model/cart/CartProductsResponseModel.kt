package com.tugcearas.sweety.data.model.cart

import com.tugcearas.sweety.data.model.Product

data class CartProductsResponseModel(
    val message: String?,
    val products: List<Product>?,
    val status: Int?
)