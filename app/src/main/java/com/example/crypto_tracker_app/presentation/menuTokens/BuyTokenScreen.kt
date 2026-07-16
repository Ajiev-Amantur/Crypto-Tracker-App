package com.example.crypto_tracker_app.presentation.menuTokens
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.crypto_tracker_app.R
import com.example.crypto_tracker_app.domain.model.BalanceTokenModel
import com.example.crypto_tracker_app.presentation.viewmodel.TokenViewModel
import com.example.crypto_tracker_app.ui.theme.GradientForCardBalance
import kotlinx.coroutines.delay

@Composable
fun BuyScreen(
    name: String, image: String,
    price: Double,
    navController: NavHostController, tokenViewModel: TokenViewModel
) {
    var balance = tokenViewModel.balance
    val token by tokenViewModel.selectedToken.observeAsState()
    var visibility by remember { mutableStateOf(false) }
    var quantityTextState by remember { mutableStateOf("") }
    var priceTextState by remember { mutableStateOf("") }


    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxWidth().padding(top = 20.dp)) {
            Box(modifier = Modifier.fillMaxWidth().padding(20.dp)) {
                Column {
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
                                onClick = {
                                },
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
                    Text(
                        "Price",
                        fontSize = 14.sp,
                        modifier = Modifier.padding(horizontal = 20.dp)

                    )
                    OutlinedTextField(
                        value = priceTextState,
                        onValueChange = { text -> priceTextState = text },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        label = { Text("10$") },
                        modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp)
                    )
                    val textFiledResult = quantityTextState.toFloatOrNull()?: 0.0f
                    val tokenPrice = token?.currentPrice?.toFloat()?: 0.0f

                    val result = if(tokenPrice > 0){
                        textFiledResult / tokenPrice
                    } else{
                        0.0
                    }

                    Text(
                        "Quantity",
                        fontSize = 14.sp,
                        modifier = Modifier.padding(horizontal = 20.dp)

                    )
                    OutlinedTextField(
                        value = quantityTextState,
                        onValueChange = { text -> quantityTextState = text },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        label = { Text("Ekvivalent: $result") },
                        modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp)
                    )
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
                .align(Alignment.BottomCenter),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            TextButton(
                modifier = Modifier.fillMaxWidth().padding(10.dp),
                onClick = {
                    visibility = true
                    val data = BalanceTokenModel(
                        name,
                        image,
                        price,
                        quantityTextState.toDouble(),
                        priceTextState.toDouble()

                    )
                    tokenViewModel.addTokenToBalance(data)
                },
                colors = ButtonDefaults.textButtonColors(
                    containerColor = Color.Green,
                    contentColor = Color.White
                )
            ) {
                Text(
                    "Buy",
                    fontSize = 14.sp
                )
            }
        }
        LaunchedEffect(visibility) {
            if (visibility){
                val sum = priceTextState.toIntOrNull()?: 0
                balance.value - sum
                delay(2000)
                visibility = false
            }
        }
        AnimatedVisibility(
            visible = visibility,
            enter = fadeIn(),
            exit = fadeOut(),
            modifier = Modifier.align(Alignment.Center)
        ) {
            Box(modifier = Modifier.size(200.dp).clip(RoundedCornerShape(12.dp))
                .background(Color.White)){
                Column(modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center) {
                    Text("Sucsess!",
                        fontSize = 20.sp,
                        fontFamily = FontFamily.SansSerif,
                        color = Color.Black)

                    Image(painter = painterResource(R.drawable.sucsess_icon_ic),
                        contentDescription = "image sucsess",
                        modifier = Modifier.size(90.dp))

                    Text("good buy",
                        fontSize = 16.sp,
                        fontFamily = FontFamily.SansSerif,
                        color = Color.Black)

                    Text("$quantityTextState$name",
                        fontSize = 20.sp,
                        fontFamily = FontFamily.SansSerif,
                        color = Color.Black)

                }
            }
        }
    }
}
