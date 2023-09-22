package com.tugcearas.sweety.ui.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tugcearas.sweety.data.model.Product
import com.tugcearas.sweety.data.model.cart.AddToCartRequestModel
import com.tugcearas.sweety.data.model.cart.AddToCartResponseModel
import com.tugcearas.sweety.data.model.favorite.FavoriteModel
import com.tugcearas.sweety.data.repo.ProductRepo
import com.tugcearas.sweety.util.resource.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailVM @Inject constructor(private val repo : ProductRepo) : ViewModel() {

    private val _getProduct : MutableLiveData<Resource<Product?>> = MutableLiveData()
    val getProduct get() = _getProduct

    val addToCartResult: MutableLiveData<Resource<AddToCartResponseModel>> = MutableLiveData()

    fun addToCart(addToCartRequestModel: AddToCartRequestModel) = viewModelScope.launch {
        addToCartResult.postValue(repo.addToCart(addToCartRequestModel))
    }

    fun getDetailProduct(id:Int) = viewModelScope.launch {
        _getProduct.postValue(handleResponse(repo.getProductDetail(id)))
    }

    fun addFavorite() = viewModelScope.launch {
        val productDetailResponse = _getProduct.value

        if (productDetailResponse != null && productDetailResponse is Resource.Success) {
            val product = productDetailResponse.data

            if (product != null) {
                repo.addProductToFavorite(
                    FavoriteModel(
                        product.id,
                        product.imageOne,
                        product.title,
                        product.rate
                    )
                )
            }
        } else {
            println("Something is wrong!")
        }
    }

    private fun handleResponse(response: Resource<Product?>)= when(response){
        is Resource.Loading -> Resource.Loading()
        is Resource.Success -> Resource.Success(response.data)
        is Resource.Error -> Resource.Error(response.message.orEmpty())
    }
}