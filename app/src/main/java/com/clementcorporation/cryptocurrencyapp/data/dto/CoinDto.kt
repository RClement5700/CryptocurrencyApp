package com.clementcorporation.cryptocurrencyapp.data.dto

import com.clementcorporation.cryptocurrencyapp.domain.models.CoinUiModel
import com.google.gson.annotations.SerializedName

data class CoinDto(
    val id: String,
    val name: String,
    val symbol: String,
    val rank: Int,
    val type: String,
    @SerializedName("is_new") val isNew: Boolean,
    @SerializedName("is_active") val isActive: Boolean
)

fun CoinDto.toCoinUiModel(): CoinUiModel {
    return CoinUiModel(
        id = id,
        name = name,
        symbol = symbol,
        rank = rank,
        isActive = isActive
    )
}
