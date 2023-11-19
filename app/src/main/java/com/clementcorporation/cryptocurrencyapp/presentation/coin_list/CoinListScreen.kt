package com.clementcorporation.cryptocurrencyapp.presentation.coin_list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.clementcorporation.cryptocurrencyapp.R
import com.clementcorporation.cryptocurrencyapp.util.CoinListItem
import com.clementcorporation.cryptocurrencyapp.util.Constants
import com.clementcorporation.cryptocurrencyapp.util.CryptocurrencyScreens

@Composable
fun CoinListScreen(navHost: NavHostController) {
    val viewModel = hiltViewModel<CoinListViewModel>()
    val coinsListState = viewModel.coinsListScreenEventsStateFlow.collectAsStateWithLifecycle()
    var isLoading by remember { mutableStateOf(true) }
    var isErrorState by remember { mutableStateOf(false) }
    when (coinsListState.value) {
        is CoinsListScreenEvents.OnCoinsListLoading -> {
            isLoading = true
            isErrorState = false
        }
        is CoinsListScreenEvents.OnCoinsListRetrieved -> {
            isLoading = false
            isErrorState = false
        }
        is CoinsListScreenEvents.OnCoinsFailedToRetrieve -> {
            isLoading = false
            isErrorState = true
        }
    }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (isLoading)
            CircularProgressIndicator(
                strokeWidth = 4.dp,
                color = Color.Green,
                modifier = Modifier.size(48.dp)
            )
        else if (isErrorState) {
            Column(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(id = R.string.failed_to_load_coin_list_label),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.body1,
                    color = Color.Red,
                )
                Text(
                    text = stringResource(id = R.string.click_here_to_reload_label),
                    textDecoration = TextDecoration.Underline,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.body1,
                    color = Color.Red,
                    modifier = Modifier.clickable { viewModel.getCoinsUseCase() }
                )
            }
        }
    }
    LazyColumn{
        items(viewModel.coinsUiModels) { coin ->
            CoinListItem(coin = coin, onClick = {
                val route = CryptocurrencyScreens.CoinDetails.name + "/${coin.id}"
                navHost.navigate(route)
            })
        }
    }
}