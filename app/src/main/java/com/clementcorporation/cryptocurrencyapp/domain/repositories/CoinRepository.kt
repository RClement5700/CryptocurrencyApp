package com.clementcorporation.cryptocurrencyapp.domain.repositories

import com.clementcorporation.cryptocurrencyapp.domain.models.CoinDetailUiModel
import com.clementcorporation.cryptocurrencyapp.domain.models.CoinUiModel
import com.clementcorporation.cryptocurrencyapp.util.Resource
import kotlinx.coroutines.flow.Flow

interface CoinRepository {
    suspend fun getCoins(): List<CoinUiModel>?
    suspend fun getCoinById(coinId: String): CoinDetailUiModel?
}