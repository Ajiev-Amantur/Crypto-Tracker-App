package com.example.crypto_tracker_app.presentation.menuTokens.priofile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.crypto_tracker_app.presentation.viewmodel.TokenViewModel
import com.example.crypto_tracker_app.ui.theme.CryptoTrackerAppTheme
import com.example.crypto_tracker_app.ui.theme.GradientForCardBalance

@Composable
fun ProfileScreen(tokenViewModel: TokenViewModel) {
    // 1. Берем общие данные из ViewModel
    val totalBalance = tokenViewModel.totalPriceToken // Общий капитал
    val totalProfit = tokenViewModel.tokenPriceUpOrDown // Общий плюс/минус

    CryptoTrackerAppTheme {
        LazyColumn(modifier = Modifier.fillMaxSize()) {

            // --- ШАПКА: ОБЩИЙ БАЛАНС ---
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .background(GradientForCardBalance)
                        .padding(20.dp)
                ) {
                    Column {
                        Text(
                            text = "${"%.2f".format(totalBalance)}$",
                            fontSize = 32.sp,
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Monospace
                        )

                        // Общий профит всего кошелька
                        val color = if (totalProfit >= 0) Color(0xFF00FF00) else Color.Red
                        val sign = if (totalProfit >= 0) "+" else ""
                        Text(
                            text = "$sign${"%.2f".format(totalProfit)}$ profit today",
                            color = color,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            text = "your total balance equivalent",
                            color = Color.LightGray,
                            fontSize = 12.sp,
                            modifier = Modifier.padding(top = 8.dp, bottom = 16.dp)
                        )

                        Button(
                            onClick = { /* Deposit */ },
                            colors = ButtonDefaults.buttonColors(containerColor = Color.White.copy(alpha = 0.2f)),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Text("Deposit", color = Color.White)
                        }
                    }
                }
            }

            // --- СПИСОК МОНЕТ В КОШЕЛЬКЕ ---
            items(tokenViewModel.balanceToken) { token ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 6.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(2.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            AsyncImage(
                                model = token.image,
                                contentDescription = null,
                                modifier = Modifier.size(40.dp).clip(CircleShape)
                            )

                            Column(modifier = Modifier.padding(start = 12.dp).weight(1f)) {
                                Text(token.name, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                                Text("Price: $${token.price}", color = Color.Gray, fontSize = 12.sp)
                            }

                            Column(horizontalAlignment = Alignment.End) {
                                // Текущая стоимость этого актива (сколько стоит сейчас)
                                val currentVal = token.amount * token.price
                                Text(
                                    text = "$${"%.2f".format(currentVal)}",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp
                                )
                                Text("${token.amount} ${token.name.take(3).uppercase()}", fontSize = 12.sp)
                            }
                        }

                        // Линия разграничения
                        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp), thickness = 0.5.dp)

                        // ПРОФИТ КОНКРЕТНОЙ МОНЕТЫ
                        val tokenProfit = (token.amount * token.price) - token.totalValue
                        val tColor = if (tokenProfit >= 0) Color(0xFF4CAF50) else Color.Red

                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Profit / Loss", fontSize = 12.sp, color = Color.Gray)
                            Text(
                                text = "${if(tokenProfit >= 0) "+" else ""}${"%.2f".format(tokenProfit)}$",
                                color = tColor,
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp
                            )
                        }
                    }
                }
            }
        }
    }
}
