package com.clementcorporation.cryptocurrencyapp.presentation.coin_details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.clementcorporation.cryptocurrencyapp.data.dto.CoinDetailDto
import com.clementcorporation.cryptocurrencyapp.domain.models.CoinDetailUiModel
import com.clementcorporation.cryptocurrencyapp.domain.models.CoinUiModel
import com.clementcorporation.cryptocurrencyapp.domain.repositories.CoinRepository
import com.clementcorporation.cryptocurrencyapp.domain.usecases.GetCoinByIdUseCase
import com.clementcorporation.cryptocurrencyapp.domain.usecases.GetCoinsUseCase
import com.clementcorporation.cryptocurrencyapp.util.Constants.PARAM_COIN_ID
import com.clementcorporation.cryptocurrencyapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CoinDetailsViewModel @Inject constructor(
    val savedStateHandle: SavedStateHandle,
    val getCoinByIdUseCase: GetCoinByIdUseCase
): ViewModel() {
    private val _coinsDetailsScreenEventsStateFlow = MutableStateFlow<CoinDetailsScreenEvents>(
        CoinDetailsScreenEvents.OnCoinsListLoading
    )
    val coinDetailsScreenEventsStateFlow = _coinsDetailsScreenEventsStateFlow.asStateFlow()
    var coinDetailUiModel: CoinDetailUiModel? = null

    init {
        savedStateHandle.get<String>(PARAM_COIN_ID)?.let { coinId ->
            getCoinById(coinId)
        }
    }

    private fun getCoinById(coinId: String) {
        getCoinByIdUseCase(coinId).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    val coinDetails = result.data as CoinDetailUiModel
                    _coinsDetailsScreenEventsStateFlow.value =
                        CoinDetailsScreenEvents.OnCoinDetailsRetrieved
                    coinDetailUiModel = coinDetails
                }
                is Resource.Loading -> {
                    _coinsDetailsScreenEventsStateFlow.value = CoinDetailsScreenEvents.OnCoinsListLoading
                }
                is Resource.Error -> _coinsDetailsScreenEventsStateFlow.value =
                    CoinDetailsScreenEvents.OnCoinDetailsFailedToRetrieve
            }
        }.launchIn(viewModelScope)
    }
}

sealed class CoinDetailsScreenEvents {
    object OnCoinsListLoading: CoinDetailsScreenEvents()
    object OnCoinDetailsRetrieved: CoinDetailsScreenEvents()
    object OnCoinDetailsFailedToRetrieve: CoinDetailsScreenEvents()
}