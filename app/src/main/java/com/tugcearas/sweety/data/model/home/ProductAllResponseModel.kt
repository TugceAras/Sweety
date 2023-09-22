package com.tugcearas.sweety.data.model.home

import com.tugcearas.sweety.data.model.Product

data class ProductAllResponseModel(
    val message: String?,
    val products: List<Product>?,
    val status: Int?
)
