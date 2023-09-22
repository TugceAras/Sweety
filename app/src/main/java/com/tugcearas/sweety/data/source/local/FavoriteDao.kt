package com.tugcearas.sweety.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tugcearas.sweety.data.model.favorite.FavoriteModel

@Dao
interface FavoriteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addProductToFavorite(product: FavoriteModel)

    @Query("SELECT * FROM favoriteTable")
    fun getProductToFavorite():List<FavoriteModel>

    @Query("DELETE FROM favoriteTable WHERE id=:favId")
    suspend fun deleteProductFromFavorites(favId: Int)
}