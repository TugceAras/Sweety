package com.tugcearas.sweety.data.source.remote

import com.tugcearas.sweety.data.model.search.SearchResponseModel
import com.tugcearas.sweety.data.model.cart.AddToCartRequestModel
import com.tugcearas.sweety.data.model.cart.AddToCartResponseModel
import com.tugcearas.sweety.data.model.cart.CartProductsResponseModel
import com.tugcearas.sweety.data.model.cart.ClearCartRequestModel
import com.tugcearas.sweety.data.model.cart.ClearCartResponseModel
import com.tugcearas.sweety.data.model.cart.DeleteFromCartRequestModel
import com.tugcearas.sweety.data.model.cart.DeleteFromCartResponseModel
import com.tugcearas.sweety.data.model.home.ProductAllResponseModel
import com.tugcearas.sweety.data.model.detail.ProductDetailResponseModel
import com.tugcearas.sweety.data.model.home.ProductSaleResponseModel
import com.tugcearas.sweety.data.model.UserId
import com.tugcearas.sweety.util.constants.Constants
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface ProductApi {

    @Headers("store:sweety")
    @GET(Constants.GET_SALE_PRODUCTS)
    suspend fun getSaleProducts(): Response<ProductSaleResponseModel>

    @Headers("store:sweety")
    @GET(Constants.GET_PRODUCTS)
    suspend fun getAllProducts(
        @Query("userId") userId: UserId
    ) : Response<ProductAllResponseModel>

    @Headers("store:sweety")
    @GET(Constants.GET_PRODUCT_DETAIL)
    suspend fun getProductDetail(
        @Query("id") id:Int
    ) : Response<ProductDetailResponseModel>

    @Headers("store:sweety")
    @POST(Constants.ADD_TO_CART)
    suspend fun addToCart(
        @Body addToCartRequestModel: AddToCartRequestModel
    ): AddToCartResponseModel

    @Headers("store:sweety")
    @GET(Constants.GET_CART_PRODUCTS)
    suspend fun getCartProducts(
        @Query("userId") userId:String
    ):Response<CartProductsResponseModel>

    @Headers("store:sweety")
    @POST(Constants.DELETE_FROM_CART)
    suspend fun deleteFromCart(
        @Body deleteFromCart: DeleteFromCartRequestModel
    ): DeleteFromCartResponseModel

    @Headers("store:sweety")
    @POST(Constants.CLEAR_CART)
    suspend fun clearCart(
        @Body clearCartRequestModel: ClearCartRequestModel
    ): ClearCartResponseModel

    @Headers("store:sweety")
    @GET(Constants.SEARCH_PRODUCT)
    suspend fun searchProduct(
        @Query("query") query:String
    ):Response<SearchResponseModel>
}