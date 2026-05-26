package com.example.crypto_tracker_app
import androidx.compose.foundation.lazy.items
import android.media.Image
import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.crypto_tracker_app.domain.CryptoViewModel
import com.example.crypto_tracker_app.ui.theme.CryptoTrackerAppTheme

class MainActivity : ComponentActivity() {
    private  val cryptoViewModel: CryptoViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CryptoTrackerAppTheme {
                val tocens by cryptoViewModel.tocen.observeAsState()
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(items = tocens?: emptyList()){token->
                        cryptoUI(token.name,token.current_price)
                    }
                }
            }
                     }

                 }
                }


@Composable
fun cryptoUI(name: String,price: Int){
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(name)
        Text(price.toString())

    }
}

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