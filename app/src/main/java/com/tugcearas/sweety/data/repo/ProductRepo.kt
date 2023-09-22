package com.tugcearas.sweety.data.repo

import com.tugcearas.sweety.data.model.cart.AddToCartRequestModel
import com.tugcearas.sweety.data.model.cart.ClearCartRequestModel
import com.tugcearas.sweety.data.model.cart.DeleteFromCartRequestModel
import com.tugcearas.sweety.data.model.UserId
import com.tugcearas.sweety.data.model.favorite.FavoriteModel
import com.tugcearas.sweety.data.source.local.FavoriteDao
import com.tugcearas.sweety.data.source.remote.ProductApi
import com.tugcearas.sweety.util.resource.Resource
import javax.inject.Inject

class ProductRepo @Inject constructor(private val api:ProductApi,private val db:FavoriteDao) {

    suspend fun getSaleProducts() = try {
        Resource.Success(api.getSaleProducts().body()?.products.orEmpty())
    } catch (e:Exception){
        Resource.Error(e.message.orEmpty())
    }

    suspend fun getAllProducts(userId: UserId) = try {
        Resource.Success(api.getAllProducts(userId).body()?.products.orEmpty())
    }catch (e:Exception){
        Resource.Error(e.message.orEmpty())
    }

    suspend fun getProductDetail(id:Int) = try{
        Resource.Success(api.getProductDetail(id).body()?.product)
    }catch (e:Exception){
        Resource.Error(e.message.orEmpty())
    }

    suspend fun addToCart(addToCartRequestModel: AddToCartRequestModel) = try {
        Resource.Success(api.addToCart(addToCartRequestModel))
    } catch (e: Exception) {
        Resource.Error(e.message.orEmpty())
    }

    suspend fun getCartProducts(userId:String) = try{
        Resource.Success(api.getCartProducts(userId).body()?.products.orEmpty())
    }catch(e:Exception){
        Resource.Error(e.message.orEmpty())
    }

    suspend fun deleteFromCart(deleteFromCartRequestModel: DeleteFromCartRequestModel) = try{
        Resource.Success(api.deleteFromCart(deleteFromCartRequestModel))
    }catch (e:Exception){
        Resource.Error(e.message.toString())
    }

    suspend fun clearCart(clearCartRequestModel: ClearCartRequestModel) = try{
        Resource.Success(api.clearCart(clearCartRequestModel))
    }catch (e:Exception){
       Resource.Error(e.message.toString())
    }

    suspend fun addProductToFavorite(product: FavoriteModel) = try{
        Resource.Success(db.addProductToFavorite(product))
    }catch(e:Exception){
        Resource.Error(e.message.orEmpty())
    }

    fun getProductToFavorite():List<FavoriteModel> {
        return db.getProductToFavorite()
    }

    suspend fun deleteProductFromFavorites(favId:Int){
        db.deleteProductFromFavorites(favId)
    }

    suspend fun searchProduct(query:String) = try{
        Resource.Success(api.searchProduct(query).body()?.products.orEmpty())
    }catch(e:Exception){
        Resource.Error(e.message.orEmpty())
    }
}