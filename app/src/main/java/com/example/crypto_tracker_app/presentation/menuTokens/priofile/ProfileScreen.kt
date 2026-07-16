package com.example.crypto_tracker_app.presentation.menuTokens.priofile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.crypto_tracker_app.presentation.viewmodel.TokenViewModel
import com.example.crypto_tracker_app.ui.theme.GradientForCardBalance
import kotlin.text.take
import kotlin.text.uppercase

@Composable
fun ProfileScreen(tokenViewModel: TokenViewModel) {
    LazyColumn {
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
                        "70,580$",
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
            items(items = tokenViewModel.balanceToken) { token ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 6.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .padding(12.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // 1. Иконка монеты
                        AsyncImage(
                            model = token.image,
                            contentDescription = null,
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                        )

                        Column(
                            modifier = Modifier
                                .padding(start = 12.dp)
                                .weight(1f) // Занимает всё свободное место
                        ) {
                            // 2. Название монеты
                            Text(
                                text = token.name,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            )
                            // 3. Цена за 1 токен
                            Text(
                                text = "$${token.price}",
                                color = Color.Gray,
                                fontSize = 12.sp
                            )
                        }

                        Column(horizontalAlignment = Alignment.End) {
                            // 4. Общая сумма в долларах (красиво жирным)
                            Text(
                                text = "$${"%.2f".format(token.totalValue)}",
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 16.sp,
                                color = Color(0xFF4CAF50) // Зеленый цвет денег
                            )
                            // 5. Количество монет
                            Text(
                                text = "${token.amount} ${token.name.take(3).uppercase()}",
                                color = Color.DarkGray,
                                fontSize = 12.sp
                            )
                        }
                    }
                }
            }
        }

    }

