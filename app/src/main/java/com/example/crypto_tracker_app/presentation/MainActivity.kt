package com.example.crypto_tracker_app.presentation
import androidx.compose.foundation.lazy.items
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarColors
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.crypto_tracker_app.data.TocenAPI.RetrofitIntance
import com.example.crypto_tracker_app.data.TocenByTimeApi.PriceTimeIntance
import com.example.crypto_tracker_app.data.repository.GetTocensRepositoryImpl
import com.example.crypto_tracker_app.data.repository.PriceTimeRepositoryImpl
import com.example.crypto_tracker_app.domain.model.CryptoTocensModel
import com.example.crypto_tracker_app.ui.theme.CryptoTrackerAppTheme

class MainActivity : ComponentActivity() {
    private val tocenPriceViewModel: TocenP_TimeViewModel by viewModels {
        object: ViewModelProvider.Factory{
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val api = PriceTimeIntance.api
                val repository = PriceTimeRepositoryImpl(api)
                return TocenP_TimeViewModel(repository)as T
            }
        }
    }
    private  val cryptoViewModel: CryptoViewModel by viewModels {
        object : ViewModelProvider.Factory{
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val api = RetrofitIntance.api
                val repository = GetTocensRepositoryImpl(api)
                return CryptoViewModel(repository) as T
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
                    val loading by cryptoViewModel.progressBar.observeAsState(false)
                    var searchText by remember {
                        mutableStateOf("")
                    }
                    if (loading) {
                        Box(
                            modifier = Modifier.padding(20.dp).fillMaxWidth(1f),
                            contentAlignment = Alignment.Center
                        ) {

                        }
                    } else  {
                        Column(
                            modifier = Modifier.fillMaxSize().statusBarsPadding()
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
                            LazyColumn(
                                modifier = Modifier.fillMaxSize(
                                ).statusBarsPadding().background(Color.LightGray)
                            )
                            {
                                val filtredTOcens = tocens?.filter {
                                    it.name.contains(searchText, ignoreCase = true)
                                }
                                items(items = filtredTOcens ?: emptyList()) { token ->
                                    cryptoUI(
                                        token.name, token.current_price, image = token.image,
                                        navController,
                                        cryptoViewModel,
                                        token
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
                                name = tocen.name,
                                price = tocen.current_price.toInt(),
                                image = tocen.image,
                                priceChange24h = tocen.price_change_percentage_24h,
                                priceAltProsent = tocen.atl_change_percentage,
                                atlPrice = tocen.atl,
                                athPrice = tocen.ath,
                                viewModel= tocenPriceViewModel
                                )
                        }
                    }

                    }
            }
        }
    }
}
@Composable
fun cryptoUI(name: String,price: Double,image: String,nav:
NavHostController,viewModel: CryptoViewModel,tocen: CryptoTocensModel){
    val context = LocalContext.current
    Card(modifier = Modifier.fillMaxWidth()
        .padding(horizontal = 16.dp, vertical = 8.dp)
        .clickable(true, onClick = {
            viewModel.selectTocen(tocen)
            nav.navigate("UI2")
            Toast.makeText(context,"clicked!!! $name", Toast.LENGTH_LONG).show()
            }
        ),
        colors = CardDefaults.cardColors(Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
        ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            AsyncImage(
                model = image,
                contentDescription = "image",
                modifier = Modifier.size(80.dp).padding(12.dp)
            )
            Text(
                name, fontStyle = FontStyle.Italic,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(color = android.graphics.Color.BLACK),
                modifier = Modifier.padding(start = 12.dp)
            )
            Text(
                "$price$", fontStyle = FontStyle.Normal,
                fontSize = 14.sp,
                modifier = Modifier.padding(start = 12.dp, 4.dp)
            )

        }
    }
        }

//@Preview(showBackground = true)
//@Composable
//fun Preview(){
//   cryptoUI("crypto",1222.0,"alla",
//       NavHostController, CryptoViewModel, CryptoTocensModel)
//}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CryptoTrackerAppTheme {
        Greeting("Android")
    }
}