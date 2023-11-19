package com.clementcorporation.cryptocurrencyapp.domain.models

import com.clementcorporation.cryptocurrencyapp.data.dto.TeamMember

data class CoinDetailUiModel(
    val id: String,
    val name: String,
    val description: String,
    val symbol: String,
    val rank: Int,
    val isActive: Boolean,
    val tags: List<String>,
    val team: List<TeamMember>
)

