package com.tugcearas.sweety.ui.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tugcearas.sweety.data.model.Product
import com.tugcearas.sweety.data.repo.ProductRepo
import com.tugcearas.sweety.util.resource.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchVM @Inject constructor(private val repo:ProductRepo): ViewModel() {

    val searchProduct: MutableLiveData<Resource<List<Product>>> = MutableLiveData()

    fun searchProduct(query: String) = viewModelScope.launch {
        if (query.length >= 3) {
            searchProduct.postValue(handleResponse(repo.searchProduct(query)))
        } else {
            searchProduct.postValue(Resource.Success(emptyList()))
        }
    }

    private fun handleResponse(response: Resource<List<Product>>) = when(response){
        is Resource.Loading -> Resource.Loading()
        is Resource.Success -> Resource.Success(response.data.orEmpty())
        is Resource.Error -> Resource.Error(response.message.orEmpty())
    }
}