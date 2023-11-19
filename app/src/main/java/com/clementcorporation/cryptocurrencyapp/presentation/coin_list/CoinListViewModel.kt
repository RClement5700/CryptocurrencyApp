package com.clementcorporation.cryptocurrencyapp.presentation.coin_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.clementcorporation.cryptocurrencyapp.domain.models.CoinUiModel
import com.clementcorporation.cryptocurrencyapp.domain.usecases.GetCoinsUseCase
import com.clementcorporation.cryptocurrencyapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class CoinListViewModel @Inject constructor(
    val getCoinsUseCase: GetCoinsUseCase
): ViewModel() {

    private val _coinsListScreenEventsStateFlow = MutableStateFlow<CoinsListScreenEvents>(
        CoinsListScreenEvents.OnCoinsListLoading
    )
    val coinsListScreenEventsStateFlow = _coinsListScreenEventsStateFlow.asStateFlow()
    var coinsUiModels = emptyList<CoinUiModel>()
    init {
        getCoins()
    }

    private fun getCoins() {
        getCoinsUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _coinsListScreenEventsStateFlow.value =
                        CoinsListScreenEvents.OnCoinsListRetrieved((result.data as List<CoinUiModel>))
                    coinsUiModels = result.data
                }
                is Resource.Loading -> {
                    _coinsListScreenEventsStateFlow.value = CoinsListScreenEvents.OnCoinsListLoading
                }
                else -> {
                    _coinsListScreenEventsStateFlow.value =
                        CoinsListScreenEvents.OnCoinsFailedToRetrieve
                }
            }
        }.launchIn(viewModelScope)
    }
}

sealed class CoinsListScreenEvents {
    object OnCoinsListLoading: CoinsListScreenEvents()
    class OnCoinsListRetrieved(val coins: List<CoinUiModel>): CoinsListScreenEvents()
    object OnCoinsFailedToRetrieve : CoinsListScreenEvents()
}