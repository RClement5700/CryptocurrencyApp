package com.clementcorporation.cryptocurrencyapp.data.api

import com.clementcorporation.cryptocurrencyapp.data.dto.CoinDetailDto
import com.clementcorporation.cryptocurrencyapp.data.dto.CoinDto
import com.clementcorporation.cryptocurrencyapp.util.Constants.GET_COIN_BY_ID_ENDPOINT
import com.clementcorporation.cryptocurrencyapp.util.Constants.GET_COIN_ENDPOINT
import com.clementcorporation.cryptocurrencyapp.util.Constants.PARAM_COIN_ID
import com.clementcorporation.cryptocurrencyapp.util.Resource
import retrofit2.http.GET
import retrofit2.http.Path
import javax.inject.Singleton

interface CoinPaprikaApi {

    @GET(GET_COIN_ENDPOINT)
    suspend fun getCoins(): List<CoinDto>?

    @GET(GET_COIN_BY_ID_ENDPOINT)
    suspend fun getCoinById(@Path(PARAM_COIN_ID) coinId: String): CoinDetailDto
}