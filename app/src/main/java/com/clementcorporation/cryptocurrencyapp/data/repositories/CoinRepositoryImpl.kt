package com.clementcorporation.cryptocurrencyapp.data.repositories

import com.clementcorporation.cryptocurrencyapp.data.api.CoinPaprikaApi
import com.clementcorporation.cryptocurrencyapp.data.dto.toCoinDetailUiModel
import com.clementcorporation.cryptocurrencyapp.data.dto.toCoinUiModel
import com.clementcorporation.cryptocurrencyapp.domain.models.CoinDetailUiModel
import com.clementcorporation.cryptocurrencyapp.domain.models.CoinUiModel
import com.clementcorporation.cryptocurrencyapp.domain.repositories.CoinRepository
import javax.inject.Inject

class CoinRepositoryImpl @Inject constructor(private val api: CoinPaprikaApi): CoinRepository {
    override suspend fun getCoins(): List<CoinUiModel>? {
        val result = api.getCoins()
        return result?.map { coin -> coin.toCoinUiModel() }
    }

    override suspend fun getCoinById(coinId: String): CoinDetailUiModel {
        val result = api.getCoinById(coinId)
        return result.toCoinDetailUiModel()
    }
}