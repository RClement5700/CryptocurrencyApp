package com.clementcorporation.cryptocurrencyapp.presentation.coin_details

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.clementcorporation.cryptocurrencyapp.R
import com.clementcorporation.cryptocurrencyapp.domain.models.CoinDetailUiModel
import com.clementcorporation.cryptocurrencyapp.util.CoinTag
import com.clementcorporation.cryptocurrencyapp.util.Constants.PARAM_COIN_ID
import com.clementcorporation.cryptocurrencyapp.util.TeamListItem

@Composable
fun CoinDetailsScreen(navHost: NavHostController) {
    BackHandler {
        navHost.popBackStack()
    }
    val viewModel = hiltViewModel<CoinDetailsViewModel>()
    val coinDetailState = viewModel.coinDetailsScreenEventsStateFlow.collectAsStateWithLifecycle()
    var isLoading by remember { mutableStateOf(true) }
    var isErrorState by remember { mutableStateOf(false) }
    when (coinDetailState.value) {
        is CoinDetailsScreenEvents.OnCoinsListLoading -> {
            isLoading = true
            isErrorState = false
        }
        is CoinDetailsScreenEvents.OnCoinDetailsRetrieved -> {
            isLoading = false
            isErrorState = false
        }
        is CoinDetailsScreenEvents.OnCoinDetailsFailedToRetrieve -> {
            isLoading = false
            isErrorState = true
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
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
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(id = R.string.failed_to_load_coin_label),
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
                    modifier = Modifier.clickable {
                        viewModel.savedStateHandle.get<String>(PARAM_COIN_ID)?.let {
                            viewModel.getCoinByIdUseCase(it)
                        }
                    }
                )
            }
        } else {
            viewModel.coinDetailUiModel?.let { coin ->
                CoinDetailsContent(coin)
            }
        }
    }
}

@Composable
fun CoinDetailsContent(coin: CoinDetailUiModel) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "${coin.rank}. ${coin.name} (${coin.symbol})",
                style = MaterialTheme.typography.h2,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = if (coin.isActive)
                    stringResource(id = R.string.coin_active_label) else
                        stringResource(id = R.string.coin_inactive_label),
                color = if (coin.isActive) Color.Green else Color.Red,
                fontStyle = FontStyle.Italic,
                textAlign = TextAlign.End,
                style = MaterialTheme.typography.body2,
            )
        }
        if (coin.description.isNotBlank()) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = coin.description,
                style = MaterialTheme.typography.body1,
                overflow = TextOverflow.Ellipsis
            )
        }
        if (coin.tags.isNotEmpty()) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(id = R.string.tags_header_label),
                style = MaterialTheme.typography.h3,
            )
            Spacer(modifier = Modifier.height(4.dp))
            LazyVerticalGrid(columns = GridCells.Adaptive(minSize = 72.dp)) {
                items(coin.tags) { tag ->
                    CoinTag(tag = tag)
                }
            }
        }
        if (coin.team.isNotEmpty()) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(id = R.string.team_header_label),
                style = MaterialTheme.typography.h3,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Divider(modifier = Modifier.fillMaxWidth(), color = Color.White)
            Spacer(modifier = Modifier.height(8.dp))
            LazyColumn{
                items(coin.team) { member ->
                    TeamListItem(teamMember = member, modifier = Modifier)
                }
            }
        }
    }
}