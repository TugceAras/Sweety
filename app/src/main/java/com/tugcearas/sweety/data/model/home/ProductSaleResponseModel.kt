package com.tugcearas.sweety.data.model.home

import com.tugcearas.sweety.data.model.Product

data class ProductSaleResponseModel(
    val message: String?,
    val products: List<Product>?,
    val status: Int?
)