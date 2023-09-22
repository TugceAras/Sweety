package com.tugcearas.sweety.ui.favorite

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tugcearas.sweety.data.model.favorite.FavoriteModel
import com.tugcearas.sweety.data.repo.ProductRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteVM @Inject constructor(private val repo : ProductRepo) : ViewModel() {

    val getFavoriteProduct: MutableLiveData<List<FavoriteModel>> = MutableLiveData()

    init {
        getFavorite()
    }

    private fun getFavorite() = viewModelScope.launch(Dispatchers.IO){
        getFavoriteProduct.postValue(repo.getProductToFavorite())
    }

    fun deleteFromFavorites(favId:Int) = viewModelScope.launch(Dispatchers.IO){
        repo.deleteProductFromFavorites(favId)
        getFavorite()
    }
}