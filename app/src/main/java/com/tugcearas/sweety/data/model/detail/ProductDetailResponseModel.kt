package com.tugcearas.sweety.data.model.detail

import com.tugcearas.sweety.data.model.Product

data class ProductDetailResponseModel(
    val message: String?,
    val product: Product?,
    val status: Int?
)