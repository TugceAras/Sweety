package com.tugcearas.sweety.ui.cart

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tugcearas.sweety.data.model.Product
import com.tugcearas.sweety.data.model.cart.ClearCartRequestModel
import com.tugcearas.sweety.data.model.cart.ClearCartResponseModel
import com.tugcearas.sweety.data.model.cart.DeleteFromCartRequestModel
import com.tugcearas.sweety.data.model.cart.DeleteFromCartResponseModel
import com.tugcearas.sweety.data.repo.ProductRepo
import com.tugcearas.sweety.util.resource.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartVM @Inject constructor(private val repo: ProductRepo): ViewModel() {

    val cartProducts: MutableLiveData<Resource<List<Product>>> = MutableLiveData()
    val deleteFromCart : MutableLiveData<Resource<DeleteFromCartResponseModel>> = MutableLiveData()
    val clearCart : MutableLiveData<Resource<ClearCartResponseModel>> = MutableLiveData()

    fun deleteFromCart(deleteFromCartRequestModel: DeleteFromCartRequestModel) = viewModelScope.launch {
        deleteFromCart.postValue(repo.deleteFromCart(deleteFromCartRequestModel))
    }

    fun getCartProducts(userId: String) = viewModelScope.launch {
        cartProducts.postValue(handleResponse(repo.getCartProducts(userId)))
    }

    fun clearCart(clearCartRequestModel: ClearCartRequestModel) = viewModelScope.launch {
        clearCart.postValue(repo.clearCart(clearCartRequestModel))
    }

    private fun handleResponse(response: Resource<List<Product>>) = when(response){
        is Resource.Loading -> Resource.Loading()
        is Resource.Success -> Resource.Success(response.data.orEmpty())
        is Resource.Error -> Resource.Error(response.message.orEmpty())
    }
}