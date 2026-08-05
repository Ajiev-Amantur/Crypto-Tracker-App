package com.example.crypto_tracker_app.presentation.menuTokens.detailToken
import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import co.yml.charts.common.extensions.isNotNull
import coil.compose.AsyncImage
import com.example.crypto_tracker_app.R
import com.example.crypto_tracker_app.presentation.viewmodel.TokenViewModel
import com.example.crypto_tracker_app.ui.theme.RedGradient
import kotlinx.coroutines.delay

@Composable
fun SellScreen(image: String,name: String,nav: NavHostController,tokenViewModel: TokenViewModel) {
    val token by tokenViewModel.selectedToken.observeAsState()
    var quantiryTextState by remember { mutableStateOf("") }
    var priceTextState by remember { mutableStateOf("") }
    var visibility by remember { mutableStateOf(false) }
    val balance = tokenViewModel.balance
    val context = LocalContext.current

    val tokenBalance = tokenViewModel.balanceToken.find { it.name == name }
    Box(modifier = Modifier.fillMaxSize()) {
    if (tokenBalance.isNotNull()) {
        Column(modifier = Modifier.fillMaxWidth().padding(top = 20.dp)) {
            Box(
                modifier = Modifier.fillMaxWidth().padding(20.dp, top = 50.dp)
                    .background(Color.White)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    AsyncImage(
                        model = image,
                        contentDescription = "image",
                        modifier = Modifier.size(30.dp)
                    )
                    Text(
                        "${tokenBalance?.amount}",
                        fontSize = 14.sp,
                        fontFamily = FontFamily.SansSerif,

                        )
                    TextButton(
                        onClick = {
                            quantiryTextState = tokenBalance?.amount.toString()
                        },
                        colors = ButtonDefaults.textButtonColors(
                            containerColor = Color.White,
                            contentColor = Color.Black
                        )
                    ) {
                        Text(
                            "Sell All",
                            fontFamily = FontFamily.SansSerif,
                            fontSize = 14.sp,
                        )
                    }
                }
            }
            Text(
                "Write here amount",
                fontSize = 14.sp,
                modifier = Modifier.padding(horizontal = 20.dp)

            )
            Box(modifier = Modifier.fillMaxWidth().padding(20.dp)) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    OutlinedTextField(
                        value = quantiryTextState,
                        onValueChange = { text -> quantiryTextState = text },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        label = { Text("0.001?") },
                        suffix = {Text("$name")},
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
            val price = token?.currentPrice ?: 0.0
            val textField = quantiryTextState.toDoubleOrNull() ?: 0.0

            val result = if (price > 0) {
                price * textField
            } else {
                0.0
            }
            val totalPrice = if (quantiryTextState.isEmpty()) "" else result.toString()
            Text(
                "quentity",
                fontSize = 14.sp,
                modifier = Modifier.padding(horizontal = 20.dp)
            )
            Box(modifier = Modifier.fillMaxWidth().padding(20.dp)) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    OutlinedTextField(
                        value = totalPrice,
                        onValueChange = {},
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        label = { Text("$result$") },
                        suffix = {Text("$")},
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
                .align(alignment = Alignment.BottomCenter),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            TextButton(
                modifier = Modifier.fillMaxWidth().padding(20.dp)
                    .background(brush = RedGradient),
                onClick = {
                    val sellAmount = quantiryTextState.toDoubleOrNull()?: 0.0
                    val userAmount = tokenBalance?.amount?: 0.0

                    if (sellAmount > userAmount){
                        Toast.makeText(context,"Недостаточно монет!", Toast.LENGTH_LONG)

                    }else{
                        tokenViewModel.sellUserToken(name,sellAmount
                            ,token?.currentPrice?: 0.0)
                    }
                },
                colors = ButtonDefaults.textButtonColors(
                    containerColor = Color.Red,
                    contentColor = Color.White
                )
            ) {
                Text(
                    "Sell",
                    fontSize = 14.sp
                )
            }

        }
    }
            LaunchedEffect(visibility) {
                if (visibility) {
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
                Box(
                    modifier = Modifier.size(200.dp).clip(RoundedCornerShape(12.dp))
                        .background(Color.White)
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            "Sucsess!",
                            fontSize = 20.sp,
                            fontFamily = FontFamily.SansSerif,
                            color = Color.Black
                        )

                        Image(
                            painter = painterResource(R.drawable.sucsess_icon_ic),
                            contentDescription = "image sucsess",
                            modifier = Modifier.size(90.dp)
                        )

                        Text(
                            "good buy",
                            fontSize = 16.sp,
                            fontFamily = FontFamily.SansSerif,
                            color = Color.Black
                        )

                        Text(
                            "$quantiryTextState$name",
                            fontSize = 20.sp,
                            fontFamily = FontFamily.SansSerif,
                            color = Color.Black
                        )

                    }
                }
            }

    }

}