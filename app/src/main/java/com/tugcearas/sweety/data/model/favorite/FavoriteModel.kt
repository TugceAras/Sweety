package com.tugcearas.sweety.data.model.favorite

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favoriteTable")
data class FavoriteModel(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id:Int?,

    @ColumnInfo("imageOne")
    val imageOne:String?,

    @ColumnInfo("title")
    val title:String?,

    @ColumnInfo("rate")
    val rate: Double?
)
