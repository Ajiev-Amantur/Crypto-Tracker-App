package com.example.crypto_tracker_app.presentation
import androidx.compose.foundation.lazy.items
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.crypto_tracker_app.R
import com.example.crypto_tracker_app.data.TocenAPI.RetrofitIntance
import com.example.crypto_tracker_app.data.TocenByTimeApi.PriceTimeIntance
import com.example.crypto_tracker_app.data.repository.GetTocensRepositoryImpl
import com.example.crypto_tracker_app.data.repository.PriceTimeRepositoryImpl
import com.example.crypto_tracker_app.domain.usecase.SortTocenByPriceDown24h
import com.example.crypto_tracker_app.domain.usecase.SortTocenByRankBottomUseCase
import com.example.crypto_tracker_app.domain.usecase.SortTocenByRankTopUseCase
import com.example.crypto_tracker_app.domain.usecase.SortTocenHighPriceUseCase
import com.example.crypto_tracker_app.domain.usecase.SortTocenLowPriceUseCase
import com.example.crypto_tracker_app.domain.usecase.SortTocensByPriceUp24h
import com.example.crypto_tracker_app.presentation.viewmodel.CryptoViewModel
import com.example.crypto_tracker_app.presentation.viewmodel.TocenP_TimeViewModel
import com.example.crypto_tracker_app.ui.theme.CryptoTrackerAppTheme
import kotlin.collections.emptyList


class MainActivity : ComponentActivity() {
    private val tocenPriceViewModel: TocenP_TimeViewModel by viewModels {
        object: ViewModelProvider.Factory{
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val api = PriceTimeIntance.api
                val repository = PriceTimeRepositoryImpl(api)
                return TocenP_TimeViewModel(repository) as T
            }
        }
    }
    private  val cryptoViewModel: CryptoViewModel by viewModels {
        object : ViewModelProvider.Factory{
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val api = RetrofitIntance.api
                val repository = GetTocensRepositoryImpl(api)
                val highPriceUseCase = SortTocenHighPriceUseCase(repository)
                val lowPriceUseCase = SortTocenLowPriceUseCase(repository)
                val tocenByRankUseCase = SortTocenByRankTopUseCase(repository)
                val tocenByRankBottomUseCase = SortTocenByRankBottomUseCase(repository)
                val tocenByPriceUp24h = SortTocensByPriceUp24h(repository)
                val tocenByPriceDown = SortTocenByPriceDown24h(repository)

                return CryptoViewModel(repository,
                    highPriceUseCase,
                    lowPriceUseCase,
                    tocenByRankUseCase,
                    tocenByRankBottomUseCase,
                    tocenByPriceUp24h,
                    tocenByPriceDown) as T
            }
        }
    }
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CryptoTrackerAppTheme {
                val navController = rememberNavController()
                NavHost(navController,"UI1") {
                composable("UI1"){
                    val tocens by cryptoViewModel.tocen.observeAsState()
                    val loading by cryptoViewModel.progressBar.observeAsState(true)
                    val selectedPrice by cryptoViewModel.selectedPrice.observeAsState(true)
                    val selectedPrice24h by cryptoViewModel.selectedPrice24h.observeAsState(true)
                    val selectedRank by cryptoViewModel.selectedRank.observeAsState(true)
                    var searchText by remember {
                        mutableStateOf("")
                    }
                    if (loading) {
                        Box(
                            modifier = Modifier
                                .padding(20.dp)
                                .fillMaxWidth(1f),
                            contentAlignment = Alignment.Center
                        ) {

                        }
                    } else  {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .statusBarsPadding()
                                .background(Color.LightGray)
                        ) {
                            SearchBar(
                                modifier = Modifier.padding(12.dp),
                                colors = SearchBarDefaults.colors(containerColor = Color.White),
                                query = searchText,
                                onQueryChange = { text ->
                                    searchText = text
                                },
                                onSearch = {
                                },
                                active = false,
                                onActiveChange = {
                                },
                                placeholder = {
                                    Text("Search")
                                }
                            ) {
                            }
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(50.dp)
                                    .padding(vertical = 8.dp),
                                horizontalArrangement = Arrangement.SpaceAround,
                                verticalAlignment = Alignment.CenterVertically // Выравниваем всё по центру по вертикали
                            ) {
                                TextButton(
                                    colors = ButtonDefaults.textButtonColors(
                                        containerColor = Color.White,
                                        contentColor = Color.Black,
                                        disabledContentColor = Color.Gray
                                    ),
                                    onClick = {
                                    if (selectedRank) {
                                        cryptoViewModel.TocenByRankTop()
                                    }else{
                                        cryptoViewModel.TocenByPriceDown()
                                    }
                                }) {
                                    Text("Default",
                                        fontSize = 12.sp ,
                                        fontFamily = FontFamily.Serif
                                    )
                                }

                                TextButton(
                                    colors = ButtonDefaults.textButtonColors(
                                        contentColor = Color.Black,
                                        containerColor = Color.White,
                                        disabledContentColor = Color.Transparent
                                    ),
                                    onClick = {
                                        if (selectedPrice) {
                                            cryptoViewModel.TocenByHighPrice()
                                        } else {
                                            cryptoViewModel.TocenByLowPrice()
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
                                                    if (selectedPrice) Color.Black else Color.White.copy(alpha = 0.5f)
                                                ),
                                                contentDescription = null
                                            )
                                            Image(
                                                painter = painterResource(R.drawable.baseline_expand_less),
                                                modifier = Modifier.size(14.dp),
                                                colorFilter = ColorFilter.tint(
                                                    if (selectedPrice == false) Color.Black else Color.White.copy(alpha = 0.5f)
                                                ),
                                                contentDescription = null
                                            )
                                        }
                                    }
                                }
                                TextButton(
                                    colors = ButtonDefaults.textButtonColors(
                                        contentColor = Color.Black,
                                        containerColor = Color.White,
                                        disabledContentColor = Color.Transparent
                                    ),
                                    onClick = {
                                   if (selectedPrice24h){
                                       cryptoViewModel.TocenByPriceUp()
                                   } else{
                                       cryptoViewModel.TocenByPriceDown()
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


                            LazyColumn(
                                modifier = Modifier
                                    .fillMaxSize(
                                    )
                                    .statusBarsPadding()
                                    .background(Color.LightGray)
                            )
                            {
                                val filtredTOcens = tocens?.filter {
                                    it.name.contains(searchText, ignoreCase = true)
                                } ?: emptyList()
                                items(
                                    items = filtredTOcens,
                                    key = { it.id }
                                ){ token ->
                                    cryptoUI(
                                        token.name, token.currentPrice, image = token.image,
                                        navController,
                                        cryptoViewModel,
                                        tocenPriceViewModel,
                                        token,
                                        priceChange24hProsent = token.priceChange24hProsent,
                                    )
                                }
                            }
                        }
                    }
                    }
                    composable("UI2"){
                        val selectedTocen by cryptoViewModel.selectedTocen.observeAsState()
                        selectedTocen?.let { tocen->
                            detailUITocen(
                                id = tocen.id,
                                name = tocen.name,
                                price = tocen.currentPrice.toInt(),
                                image = tocen.image,
                                priceChange24h = tocen.priceChange24h,
                                priceAltProsent = tocen.atlChangePercentage,
                                atlPrice = tocen.atl,
                                athPrice = tocen.ath,
                                totalSupply = tocen.totalSupply,
                                maxSypply = tocen.maxSupply,
                                highPrice24h = tocen.high24h,
                                lowPrice24h = tocen.low24h,
                                viewModel= tocenPriceViewModel,
                                priceChange24hProsent = tocen.priceChange24hProsent,
                                priceChange7dProsent = tocen.priceChange7dProsent,
                                priceChange30dProsent = tocen.priceChange30dProsent,
                                priceChange1yProsent = tocen.priceChange1yProsent
                                )
                        }
                    }

                    }
            }
        }
    }
}

