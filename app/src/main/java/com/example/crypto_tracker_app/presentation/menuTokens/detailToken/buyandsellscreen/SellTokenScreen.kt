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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import coil.compose.AsyncImage
import com.example.crypto_tracker_app.R
import com.example.crypto_tracker_app.presentation.viewmodel.TokenViewModel
import com.example.crypto_tracker_app.ui.theme.RedGradient
import kotlinx.coroutines.delay

@Composable
fun SellScreen(image: String, name: String, nav: NavHostController, tokenViewModel: TokenViewModel) {
    val token by tokenViewModel.selectedToken.observeAsState()
    var quantityTextState by remember { mutableStateOf("") }
    var visibility by remember { mutableStateOf(false) }
    val context = LocalContext.current

    val tokenBalance = tokenViewModel.balanceToken.find { it.name == name }
    
    Box(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
        if (tokenBalance != null) {
            Column(modifier = Modifier.fillMaxWidth().padding(top = 20.dp)) {
                Box(
                    modifier = Modifier.fillMaxWidth().padding(20.dp, top = 50.dp)
                        .background(MaterialTheme.colorScheme.background)
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
                            "${tokenBalance.amount}",
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onBackground,
                            fontFamily = FontFamily.SansSerif,
                        )
                        TextButton(
                            onClick = {
                                quantityTextState = tokenBalance.amount.toString()
                            },
                            colors = ButtonDefaults.textButtonColors(
                                containerColor = MaterialTheme.colorScheme.background,
                                contentColor = MaterialTheme.colorScheme.onBackground,
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
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.padding(horizontal = 20.dp)
                )
                Box(modifier = Modifier.fillMaxWidth().padding(20.dp)) {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        OutlinedTextField(
                            value = quantityTextState,
                            onValueChange = { text -> quantityTextState = text },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                            label = { Text("0.001?", color = MaterialTheme.colorScheme.onBackground) },
                            suffix = { Text(name, color = MaterialTheme.colorScheme.onBackground) },
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = MaterialTheme.colorScheme.onBackground,
                                unfocusedTextColor = MaterialTheme.colorScheme.onBackground
                            )
                        )
                    }
                }
                val price = token?.currentPrice ?: 0.0
                val textFieldValue = quantityTextState.toDoubleOrNull() ?: 0.0

                val result = price * textFieldValue
                val totalPrice = if (quantityTextState.isEmpty()) "" else "%.8f".format(result).trimEnd('0').trimEnd('.',',')
                
                Text(
                    "You will get (USD)",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.padding(horizontal = 20.dp)
                )
                Box(modifier = Modifier.fillMaxWidth().padding(20.dp)) {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        OutlinedTextField(
                            value = totalPrice,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Total", color = MaterialTheme.colorScheme.onBackground) },
                            suffix = { Text("$", color = MaterialTheme.colorScheme.onBackground) },
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = MaterialTheme.colorScheme.onBackground,
                                unfocusedTextColor = MaterialTheme.colorScheme.onBackground
                            )
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
                        val sellAmount = quantityTextState.toDoubleOrNull() ?: 0.0
                        val userAmount = tokenBalance.amount ?: 0.0

                        if (sellAmount > userAmount) {
                            Toast.makeText(context, "Недостаточно монет!", Toast.LENGTH_LONG).show()
                        } else if (sellAmount > 0) {
                            tokenViewModel.sellUserToken(name, sellAmount, token?.currentPrice ?: 0.0)
                            visibility = true 
                        }
                    },
                    colors = ButtonDefaults.textButtonColors(
                        containerColor = Color.Red,
                        contentColor = Color.White
                    )
                ) {
                    Text("Sell", fontSize = 14.sp)
                }
            }
        }

        // Анимация успеха должна быть ВНУТРИ Box, чтобы работал align
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
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text("Success!", fontSize = 20.sp, color = Color.Black)
                    Image(
                        painter = painterResource(R.drawable.sucsess_icon_ic),
                        contentDescription = "success",
                        modifier = Modifier.size(90.dp)
                    )
                    Text("sold", fontSize = 16.sp, color = Color.Black)
                    Text("$quantityTextState $name", fontSize = 20.sp, color = Color.Black)
                }
            }
        }
    }

    LaunchedEffect(visibility) {
        if (visibility) {
            delay(2000)
            visibility = false
        }
    }
}
