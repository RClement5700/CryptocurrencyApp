package com.clementcorporation.cryptocurrencyapp.domain.usecases

import com.clementcorporation.cryptocurrencyapp.domain.models.CoinDetailUiModel
import com.clementcorporation.cryptocurrencyapp.domain.repositories.CoinRepository
import com.clementcorporation.cryptocurrencyapp.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

class GetCoinByIdUseCase @Inject constructor(private val repo: CoinRepository) {
    operator fun invoke(coinId: String): Flow<Resource<CoinDetailUiModel>> = flow {
        try {
            emit(Resource.Loading())
            val coin = repo.getCoinById(coinId)
            emit(Resource.Success(coin))
        } catch (e: HttpException) {
            emit(Resource.Error(e.message()))
        } catch (e: IOException) {
            emit(Resource.Error(e.message))
        }
    }
}