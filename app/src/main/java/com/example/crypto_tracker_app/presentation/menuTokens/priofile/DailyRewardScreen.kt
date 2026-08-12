package com.example.crypto_tracker_app.presentation.menuTokens.priofile

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.crypto_tracker_app.presentation.viewmodel.TokenViewModel

@Composable
fun DailyRewardScreen(tokenViewModel: TokenViewModel) {
    val context = LocalContext.current
    val goldGradient = Brush.verticalGradient(
        colors = listOf(Color(0xFFFFD700), Color(0xFFFFA500))
    )
    val cardGradient = Brush.linearGradient(
        colors = listOf(Color(0xFF1E3C72), Color(0xFF2A5298))
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F9FA)) // Светлый приятный фон
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally // Центрируем всё!
    ) {
        // Заголовок
        Text(
            text = "Daily Reward",
            fontSize = 28.sp,
            fontWeight = FontWeight.ExtraBold,
            color = Color(0xFF2D3436)
        )
        Text(
            text = "Your free daily tokens are ready!",
            fontSize = 14.sp,
            color = Color.Gray,
            modifier = Modifier.padding(top = 8.dp)
        )

        Spacer(modifier = Modifier.height(40.dp))

        // Главная карточка
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            shape = RoundedCornerShape(32.dp),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            // Внутри карточки используем Box, чтобы градиент лег правильно
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(cardGradient),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        "Bonus amount",
                        fontSize = 16.sp,
                        color = Color.White.copy(alpha = 0.8f)
                    )
                    Text(
                        "$ 50.00",
                        fontSize = 48.sp,
                        fontWeight = FontWeight.Black,
                        color = Color.White
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(40.dp))

        // Иконка подарка
        Surface(
            modifier = Modifier.size(100.dp),
            shape = CircleShape,
            color = Color(0xFFFFEAA7),
            tonalElevation = 4.dp,
            shadowElevation = 6.dp
        ) {
            Box(contentAlignment = Alignment.Center) {
                Text("🎁", fontSize = 50.sp)
            }
        }

        Spacer(modifier = Modifier.weight(1f)) // Выталкивает кнопку вниз

        // Кнопка
        Button(
            onClick = { /* Логика зачисления */
                tokenViewModel.checkDailyBonus { message ->
                    Toast.makeText(context,message,Toast.LENGTH_SHORT)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(goldGradient), // Применяем градиент
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent // Обязательно!
            ),
            shape = RoundedCornerShape(20.dp)
        ) {
            Text(
                "CLAIM NOW",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }

        Text(
            modifier = Modifier.padding(top = 12.dp, bottom = 10.dp),
            text = "Come back in 24h for more!",
            fontSize = 12.sp,
            color = Color.Gray
        )
    }
}