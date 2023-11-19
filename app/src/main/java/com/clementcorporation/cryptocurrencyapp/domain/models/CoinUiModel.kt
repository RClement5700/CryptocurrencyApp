package com.clementcorporation.cryptocurrencyapp.domain.models

data class CoinUiModel(
    val id: String,
    val name: String,
    val symbol: String,
    val rank: Int,
    val isActive: Boolean,
)
