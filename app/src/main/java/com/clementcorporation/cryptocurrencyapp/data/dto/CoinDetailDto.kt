package com.clementcorporation.cryptocurrencyapp.data.dto

import com.clementcorporation.cryptocurrencyapp.domain.models.CoinDetailUiModel
import com.google.gson.annotations.SerializedName

fun CoinDetailDto.toCoinDetailUiModel(): CoinDetailUiModel {
    return CoinDetailUiModel(
        id = id,
        name = name,
        description = description,
        rank = rank,
        symbol = symbol,
        isActive = isActive,
        tags = tags.map { it.name },
        team = team
    )
}

data class CoinDetailDto(
    val id: String,
    val name: String,
    val description: String,
    val logo: String,
    val message: String,
    val links: Links,
    val rank: Int,
    val symbol: String,
    val tags: List<CryptoTag>,
    val team: List<TeamMember>,
    val type: String,
    val whitepaper: Whitepaper,
    @SerializedName("links_extended") val linksExtended: List<LinksExtended>,
    @SerializedName("development_status") val developmentStatus: String,
    @SerializedName("first_data_at") val firstDataAt: String,
    @SerializedName("hardware_wallet") val hardwareWallet: Boolean,
    @SerializedName("hash_algorithm") val hashAlgorithm: String,
    @SerializedName("is_active") val isActive: Boolean,
    @SerializedName("is_new") val isNew: Boolean,
    @SerializedName("last_data_at") val lastDataAt: String,
    @SerializedName("open_source") val openSource: Boolean,
    @SerializedName("org_structure") val orgStructure: String,
    @SerializedName("proof_type") val proofType: String,
    @SerializedName("started_at") val startedAt: String
)

data class Links(
    val explorer: List<String>,
    val facebook: List<String>,
    val reddit: List<String>,
    val website: List<String>,
    val youtube: List<String>,
    @SerializedName("source_code") val sourceCode: List<String>
)

data class LinksExtended(
    val stats: Stats,
    val type: String,
    val url: String
)

data class Stats(
    val contributors: Int,
    val followers: Int,
    val stars: Int,
    val subscribers: Int
)

data class CryptoTag(
    val id: String,
    val name: String,
    @SerializedName("coin_counter") val coinCounter: Int,
    @SerializedName("ico_counter") val icoCounter: Int
)

data class TeamMember(
    val id: String,
    val name: String,
    val position: String
)

data class Whitepaper(
    val link: String,
    val thumbnail: String
)