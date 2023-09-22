package com.tugcearas.sweety.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tugcearas.sweety.data.model.favorite.FavoriteModel

@Database(entities = [FavoriteModel::class], version = 4)
abstract class FavoriteDatabase: RoomDatabase() {

    abstract fun getProductFromDao():FavoriteDao
}