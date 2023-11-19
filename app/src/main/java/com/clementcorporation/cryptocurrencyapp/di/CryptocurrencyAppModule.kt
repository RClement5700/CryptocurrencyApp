package com.clementcorporation.cryptocurrencyapp.di

import com.clementcorporation.cryptocurrencyapp.data.api.CoinPaprikaApi
import com.clementcorporation.cryptocurrencyapp.data.repositories.CoinRepositoryImpl
import com.clementcorporation.cryptocurrencyapp.domain.repositories.CoinRepository
import com.clementcorporation.cryptocurrencyapp.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CryptocurrencyAppModule {

    @Provides
    @Singleton
    fun providesCoinsRepository(api: CoinPaprikaApi): CoinRepository = CoinRepositoryImpl(api)

    @Provides
    @Singleton
    fun providesPaprikaApi(): CoinPaprikaApi = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(OkHttpClient())
        .build()
        .create(CoinPaprikaApi::class.java)
}