package com.tugcearas.sweety.di

import android.content.Context
import androidx.room.Room
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.tugcearas.sweety.data.source.local.FavoriteDao
import com.tugcearas.sweety.data.source.local.FavoriteDatabase
import com.tugcearas.sweety.data.source.remote.ProductApi
import com.tugcearas.sweety.util.constants.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private const val TIMEOUT = 60L

    @Provides
    @Singleton
    fun provideChuckerInterceptor(@ApplicationContext context: Context) = ChuckerInterceptor.Builder(context).build()

    @Provides
    @Singleton
    fun provideOkHttpClient(chuckerInterceptor: ChuckerInterceptor) = OkHttpClient.Builder().apply {
        addInterceptor(chuckerInterceptor)
        readTimeout(TIMEOUT, TimeUnit.SECONDS)
        connectTimeout(TIMEOUT, TimeUnit.SECONDS)
        writeTimeout(TIMEOUT, TimeUnit.SECONDS)
    }.build()

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient) = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Singleton
    @Provides
    fun provideApi(retrofit:Retrofit):ProductApi{
        return retrofit.create(ProductApi::class.java)
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): FavoriteDatabase =
        Room.databaseBuilder(context,FavoriteDatabase::class.java,"productDatabase")
            .fallbackToDestructiveMigration().build()

    @Provides
    @Singleton
    fun provideDao(database:FavoriteDatabase):FavoriteDao{
        return database.getProductFromDao()
    }
}