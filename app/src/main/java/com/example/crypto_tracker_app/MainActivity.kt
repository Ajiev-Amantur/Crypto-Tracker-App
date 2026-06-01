package com.example.crypto_tracker_app
import androidx.compose.foundation.lazy.items
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.crypto_tracker_app.domain.CryptoViewModel
import com.example.crypto_tracker_app.ui.theme.CryptoTrackerAppTheme
import com.example.crypto_tracker_app.ui.theme.detailUITocen

class MainActivity : ComponentActivity() {
    private  val cryptoViewModel: CryptoViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CryptoTrackerAppTheme {

                val tocens by cryptoViewModel.tocen.observeAsState()
                LazyColumn(modifier = Modifier.fillMaxSize(
                ).statusBarsPadding())
                 {
                    items(items = tocens?: emptyList()){token->
                        cryptoUI(token.name,token.current_price.toDouble()
                            ,image = token.image,
                            priceChange24h = token.price_change_percentage_24h,
                            priceAltProsent = token.atl_change_percentage,
                            athPrice = token.ath,
                            atlPrice = token.atl)

                    }
                }
            }
                     }

                 }

                }
@Composable
fun cryptoUI(name: String,price: Double,image: String,
             priceChange24h: Double,
             priceAltProsent: Double,atlPrice: Double,athPrice: Int){
    val navController = rememberNavController()
    NavHost(navController,"menu"){
        composable("menu"){
    val context = LocalContext.current
    Card(modifier = Modifier.fillMaxWidth()
        .padding(horizontal = 16.dp, vertical = 8.dp)
        .clickable(true, onClick = {
            Toast.makeText(context,"clicked!!! $name", Toast.LENGTH_LONG).show()
            navController.navigate("player")
        })
        ,
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
                price.toString(), fontStyle = FontStyle.Normal,
                fontSize = 14.sp,
                modifier = Modifier.padding(start = 12.dp, 4.dp)
            )

        }
    }
        }
        composable("player"){
            detailUITocen(name,price.toInt(),image,priceChange24h,priceAltProsent,
                priceAltProsent,athPrice)
        }
    }
}
//@Preview(showBackground = true)
//@Composable
//fun Preview(){
//    cryptoUI("crypto",1222.0,"alla")
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