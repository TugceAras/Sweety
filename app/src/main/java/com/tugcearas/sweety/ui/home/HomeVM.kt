package com.tugcearas.sweety.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tugcearas.sweety.data.model.Product
import com.tugcearas.sweety.data.model.UserId
import com.tugcearas.sweety.data.repo.ProductRepo
import com.tugcearas.sweety.util.resource.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeVM @Inject constructor(private val repo:ProductRepo) : ViewModel(){

    val getSaleProducts : MutableLiveData<Resource<List<Product>>> = MutableLiveData()
    val getAllProducts : MutableLiveData<Resource<List<Product>>> = MutableLiveData()

    init{
        getSaleProducts()
    }

    private fun getSaleProducts() = viewModelScope.launch {
        getSaleProducts.postValue(handleResponse(repo.getSaleProducts()))
    }

    fun getAllProducts(userId: UserId) = viewModelScope.launch {
        getAllProducts.postValue(handleResponse(repo.getAllProducts(userId)))
    }

    private fun handleResponse(response:Resource<List<Product>>) = when(response){
        is Resource.Loading -> Resource.Loading()
        is Resource.Success -> Resource.Success(response.data.orEmpty())
        is Resource.Error -> Resource.Error(response.message.orEmpty())
    }
}