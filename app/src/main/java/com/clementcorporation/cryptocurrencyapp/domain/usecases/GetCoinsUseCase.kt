package com.clementcorporation.cryptocurrencyapp.domain.usecases

import com.clementcorporation.cryptocurrencyapp.domain.models.CoinUiModel
import com.clementcorporation.cryptocurrencyapp.domain.repositories.CoinRepository
import com.clementcorporation.cryptocurrencyapp.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

class GetCoinsUseCase @Inject constructor(private val repo: CoinRepository) {
    operator fun invoke(): Flow<Resource<List<CoinUiModel>?>> = flow {
        try {
            emit(Resource.Loading())
            val coins = repo.getCoins()
            emit(Resource.Success(coins))
        } catch (e: HttpException) {
            emit(Resource.Error(e.message()))
        } catch (e: IOException) {
            emit(Resource.Error(e.message))
        }
    }
}