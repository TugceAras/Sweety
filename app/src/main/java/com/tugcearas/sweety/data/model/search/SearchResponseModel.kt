package com.tugcearas.sweety.data.model.search

import com.tugcearas.sweety.data.model.Product

data class SearchResponseModel(
    val message: String?,
    val products: List<Product>?,
    val status: Int?
)