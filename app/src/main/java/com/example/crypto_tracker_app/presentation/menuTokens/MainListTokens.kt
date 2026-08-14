@file:OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalFoundationApi::class
)

package com.example.crypto_tracker_app.presentation.menuTokens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import com.example.crypto_tracker_app.R
import androidx.compose.material3.TextButton
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.crypto_tracker_app.domain.model.CryptoTokenModel
import com.example.crypto_tracker_app.presentation.ErrorScreen
import com.example.crypto_tracker_app.presentation.TokenUiState
import com.example.crypto_tracker_app.presentation.menuTokens.detailToken.BuyScreen
import com.example.crypto_tracker_app.presentation.menuTokens.detailToken.SellScreen
import com.example.crypto_tracker_app.presentation.menuTokens.detailToken.detailUIToken
import com.example.crypto_tracker_app.presentation.menuTokens.priofile.DailyRewardScreen
import com.example.crypto_tracker_app.presentation.menuTokens.priofile.ProfileScreen
import com.example.crypto_tracker_app.presentation.viewmodel.TokenViewModel
import com.example.crypto_tracker_app.presentation.viewmodel.TokenP_TimeViewModel
import com.example.crypto_tracker_app.ui.theme.GradientForCardBalance
import org.koin.core.qualifier.named


@Composable
fun MainListTokens(
    tokenViewModel: TokenViewModel,
    tokenPriceViewModel: TokenP_TimeViewModel,
    ) {
    val navController = rememberNavController()
    NavHost(navController, "UI1") {
        composable("UI1") {
            val tokens by tokenViewModel.tokenList.observeAsState()
            val selectedPrice by tokenViewModel.selectedPrice.observeAsState(true)
            val selectedPrice24h by tokenViewModel.selectedPrice24h.observeAsState(true)
            val selectedRank by tokenViewModel.selectedRank.observeAsState(true)
            var balance = tokenViewModel.balance
            val uiState by tokenViewModel.uiState.collectAsState()

            var searchText by remember {
                mutableStateOf("")
            }

            val filtredTokens = remember(tokens, searchText) {
                tokens?.filter { it.name.contains(searchText, ignoreCase = true) }
            } ?: emptyList()

            val isRefreshing = uiState is TokenUiState.loading && tokens?.isNotEmpty() == true


            Box(modifier = Modifier.fillMaxSize().background(Color.White)) {
                PullToRefreshBox(
                    isRefreshing,
                    onRefresh = {
                        tokenViewModel.loadTokens()
                    },
                    modifier = Modifier.fillMaxSize()

                ) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .statusBarsPadding()
                            .background(Color.Transparent)
                    ) {
                        // Карточка баланса
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp, vertical = 6.dp)
                                    .clip(RoundedCornerShape(16.dp))
                                    .background(GradientForCardBalance),
                            ) {
                                Column(modifier = Modifier.fillMaxWidth()) {
                                    Text(
                                        "${balance.value}$",
                                        fontSize = 30.sp,
                                        color = Color.White,
                                        fontFamily = FontFamily.Cursive,
                                        modifier = Modifier.padding(10.dp),
                                    )
                                    Text(
                                        "your balance is equivalent",
                                        fontSize = 14.sp,
                                        color = Color.Gray,
                                        fontFamily = FontFamily.Monospace,
                                        modifier = Modifier.padding(8.dp, bottom = 30.dp)
                                    )

                                    TextButton(
                                        colors = ButtonDefaults.textButtonColors(containerColor = Color.Transparent),
                                        onClick = { },
                                    ) {
                                        Text(
                                            "Deposit",
                                            color = Color.White,
                                            fontSize = 18.sp,
                                            fontFamily = FontFamily.Monospace
                                        )
                                    }
                                }
                            }
                        }
                        stickyHeader {
                            Column(
                                modifier = Modifier.fillMaxWidth()
                                    .background(colorResource(R.color.white))
                            ) {
                                SearchBar(
                                    modifier = Modifier.padding(
                                        horizontal = 12.dp,
                                        vertical = 8.dp
                                    ),
                                    colors = SearchBarDefaults.colors(
                                        containerColor = Color(
                                            0xFFF0F8FF
                                        )
                                    ),
                                    query = searchText,
                                    onQueryChange = { text -> searchText = text },
                                    onSearch = { },
                                    active = false,
                                    onActiveChange = { },
                                    placeholder = { Text("Search") }
                                ) { }

                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(50.dp)
                                        .padding(vertical = 4.dp),
                                    horizontalArrangement = Arrangement.SpaceAround,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    TextButton(
                                        colors = ButtonDefaults.textButtonColors(
                                            containerColor = Color(0xFFF0FFFF),
                                            contentColor = Color.Black,
                                            disabledContentColor = Color.Gray
                                        ),
                                        onClick = {
                                            if (selectedRank) {
                                                tokenViewModel.TokenByRankTop()
                                            } else {
                                                tokenViewModel.TokenByPriceDown()
                                            }
                                        }) {
                                        Text(
                                            "Default",
                                            fontSize = 12.sp,
                                            fontFamily = FontFamily.Serif
                                        )
                                    }

                                    TextButton(
                                        colors = ButtonDefaults.textButtonColors(
                                            contentColor = Color.Black,
                                            containerColor = Color(0xFFF0FFFF)
                                        ),
                                        onClick = {
                                            if (selectedPrice) {
                                                tokenViewModel.TokenByHighPrice()
                                            } else {
                                                tokenViewModel.TokenByLowPrice()
                                            }
                                        }
                                    ) {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Text(
                                                text = "Price",
                                                color = Color.Black,
                                                fontSize = 12.sp,
                                                fontFamily = FontFamily.Serif
                                            )
                                            Spacer(modifier = Modifier.width(4.dp))
                                            Column(
                                                horizontalAlignment = Alignment.CenterHorizontally,
                                                verticalArrangement = Arrangement.Center
                                            ) {
                                                Image(
                                                    painter = painterResource(R.drawable.baseline_expand_more),
                                                    modifier = Modifier.size(14.dp),
                                                    colorFilter = ColorFilter.tint(
                                                        if (selectedPrice) Color.Black else Color.White.copy(
                                                            alpha = 0.5f
                                                        )
                                                    ),
                                                    contentDescription = null
                                                )
                                                Image(
                                                    painter = painterResource(R.drawable.baseline_expand_less),
                                                    modifier = Modifier.size(14.dp),
                                                    colorFilter = ColorFilter.tint(
                                                        if (selectedPrice == false) Color.Black else Color.White.copy(
                                                            alpha = 0.5f
                                                        )
                                                    ),
                                                    contentDescription = null
                                                )
                                            }
                                        }
                                    }

                                    TextButton(
                                        colors = ButtonDefaults.textButtonColors(
                                            contentColor = Color.Black,
                                            containerColor = Color(0xFFF0FFFF)
                                        ),
                                        onClick = {
                                            if (selectedPrice24h) {
                                                tokenViewModel.TokenByPriceUp()
                                            } else {
                                                tokenViewModel.TokenByPriceDown()
                                            }
                                        }) {
                                        Text(
                                            "24h change",
                                            color = Color.Black,
                                            fontSize = 12.sp,
                                            fontFamily = FontFamily.Serif
                                        )
                                    }
                                }
                            }
                        }

                        when (val state = uiState) {
                            is TokenUiState.loading -> {
                                if (tokens.isNullOrEmpty()) {
                                    item {
                                        Box(
                                            modifier = Modifier.fillMaxSize(),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            CircularProgressIndicator()
                                        }
                                    }
                                } else {
                                    items(items = filtredTokens, key = { it.id }) { token ->
                                        tokenUI(
                                            token.name,
                                            token.currentPrice,
                                            token.image,
                                            navController,
                                            tokenViewModel,
                                            tokenPriceViewModel,
                                            token,
                                            token.priceChange24hProsent
                                        )
                                    }
                                }

                            }

                            is TokenUiState.Sucsess -> {
                                items(
                                    items = filtredTokens,
                                    key = { it.id }
                                ) { token ->
                                    tokenUI(
                                        token.name, token.currentPrice, image = token.image,
                                        navController,
                                        tokenViewModel,
                                        tokenPriceViewModel,
                                        token,
                                        priceChange24hProsent = token.priceChange24hProsent,
                                    )
                                }
                                item {
                                    Spacer(modifier = Modifier.height(100.dp))
                                }
                            }

                            is TokenUiState.Error -> {
                                if (tokens.isNullOrEmpty()) {
                                    item {
                                        ErrorScreen(tokenViewModel)
                                    }
                                }else{
                                    items(items = filtredTokens,key = { it.id}) { token ->
                                        tokenUI(
                                            name = token.name,
                                            price = token.currentPrice,
                                            image = token.image,
                                            navController,
                                            tokenViewModel,
                                            tokenPriceViewModel,
                                            token,
                                            token.priceChange24hProsent
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                if (uiState is TokenUiState.Sucsess) {
                    Box(
                        modifier = Modifier.fillMaxWidth()
                            .height(80.dp).align(Alignment.BottomCenter)
                            .background(Color(0xFFF5FFFA))
                            .padding(horizontal = 16.dp, vertical = 10.dp)
                            .clip(RoundedCornerShape(20.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(
                                modifier = Modifier.weight(1f),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Image(
                                    modifier = Modifier.size(26.dp)
                                        .padding(bottom = 6.dp),
                                    painter = painterResource(R.drawable.menuhome_ic),
                                    contentDescription = "image",
                                )
                                Text(
                                    "Menu",
                                    fontFamily = FontFamily.SansSerif,
                                    fontSize = 16.sp,
                                    color = Color.Gray
                                )
                            }

                            Column(
                                modifier = Modifier.weight(1f).clickable {
                                    navController.navigate("profileScreen")
                                },
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Image(
                                    modifier = Modifier.size(26.dp),
                                    painter = painterResource(R.drawable.profile_ic),
                                    contentDescription = "image",
                                )
                                Text(
                                    "Profile",
                                    fontFamily = FontFamily.SansSerif,
                                    fontSize = 16.sp,
                                    color = Color.Gray
                                )
                            }
                        }
                    }
                }
            }
        }


            composable("UI2") {
                val selectedToken by tokenViewModel.selectedToken.observeAsState()
                selectedToken?.let { token ->
                    detailUIToken(
                        id = token.id,
                        name = token.name,
                        price = token.currentPrice.toInt(),
                        image = token.image,
                        priceChange24h = token.priceChange24h,
                        priceAltProsent = token.atlChangePercentage,
                        atlPrice = token.atl,
                        athPrice = token.ath,
                        totalSupply = token.totalSupply,
                        maxSypply = token.maxSupply,
                        highPrice24h = token.high24h,
                        lowPrice24h = token.low24h,
                        tokenViewModel = tokenViewModel,
                        viewModel = tokenPriceViewModel,
                        priceChange24hProsent = token.priceChange24hProsent,
                        priceChange7dProsent = token.priceChange7dProsent,
                        priceChange30dProsent = token.priceChange30dProsent,
                        priceChange1yProsent = token.priceChange1yProsent,
                        navController
                    )
                }
            }
        composable("sellScreen"){
            val token by tokenViewModel.selectedToken.observeAsState()
            SellScreen(image = token?.image ?: "", token?.name ?: "", navController, tokenViewModel)
        }
        composable("buyScreen"){
            val token by tokenViewModel.selectedToken.observeAsState()

            BuyScreen(
                token?.name ?: "", token?.image ?: "",
                token?.currentPrice ?: 0.0, navController, tokenViewModel
            )
        }
        composable("profileScreen"){
            ProfileScreen(tokenViewModel,navController)
        }
        composable("dailyRewardScreen"){
            DailyRewardScreen(tokenViewModel)
        }
        }
}
