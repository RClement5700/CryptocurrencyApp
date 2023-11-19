package com.clementcorporation.cryptocurrencyapp.main

import androidx.compose.runtime.Composable
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.clementcorporation.cryptocurrencyapp.domain.models.CoinUiModel
import com.clementcorporation.cryptocurrencyapp.presentation.coin_details.CoinDetailsScreen
import com.clementcorporation.cryptocurrencyapp.presentation.coin_list.CoinListScreen
import com.clementcorporation.cryptocurrencyapp.util.Constants.PARAM_COIN_ID
import com.clementcorporation.cryptocurrencyapp.util.CryptocurrencyScreens

private const val ROUTE_EXT = "/{$PARAM_COIN_ID}"
@Composable
fun CryptocurrencyAppNavigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = CryptocurrencyScreens.CoinsList.name) {
        composable(CryptocurrencyScreens.CoinsList.name) {
            CoinListScreen(navHost = navController)
        }

        composable(CryptocurrencyScreens.CoinDetails.name + ROUTE_EXT) {
            CoinDetailsScreen(navHost = navController)
        }
    }
}