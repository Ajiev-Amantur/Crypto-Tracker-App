package com.example.crypto_tracker_app.presentation.menuTokens.priofile

import android.R
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.NotificationCompat
import com.example.crypto_tracker_app.presentation.menuTokens.MainListTokens
import com.example.crypto_tracker_app.presentation.viewmodel.TokenViewModel

@Composable
fun DailyRewardScreen(tokenViewModel: TokenViewModel) {
    var isTimeVisible by tokenViewModel.isTimeVisisble
    var isSecondLeft by tokenViewModel.isSecondLeft

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
            .background(MaterialTheme.colorScheme.background) // Светлый приятный фон
            .padding(24.dp, end = 24.dp, bottom = 24.dp, top = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally // Центрируем всё!
    ) {
        // Заголовок
        Text(
            text = "Daily Reward",
            fontSize = 28.sp,
            fontWeight = FontWeight.ExtraBold,
            color = MaterialTheme.colorScheme.onBackground
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
        LaunchedEffect(isTimeVisible){
            if (!isTimeVisible){
                val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                val channelId = "Bonus_channel"
                val channel = NotificationChannel(channelId,"Daily_Bonus", NotificationManager.IMPORTANCE_DEFAULT)
                notificationManager.createNotificationChannel(channel)

                val notification = NotificationCompat.Builder(context,channelId)
                    .setSmallIcon(com.example.crypto_tracker_app.R.mipmap.ic_launcher)
                    .setContentTitle("Bonus Ready!")
                    .setContentText("Your $50 daily reward is waiting for you!")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setAutoCancel(true)
                    .build()
                notificationManager.notify(20,notification)
            }
        }

        Spacer(modifier = Modifier.height(40.dp))
        if (!isTimeVisible) {
            // Иконка подарка
            Surface(
                modifier = Modifier.size(100.dp),
                shape = CircleShape,
                color = Color(0xFFFFEAA7),
                tonalElevation = 4.dp,
                shadowElevation = 6.dp
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text("🎁", fontSize = 50.sp, color = MaterialTheme.colorScheme.onBackground)
                }
            }
        }else {

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TimeCircle(isSecondLeft, 86400L)
            }
        }
        Spacer(modifier = Modifier.weight(1f)) // Выталкивает кнопку вниз

        // Кнопка
        if (!isTimeVisible) {
            Button(
                onClick = { /* Логика зачисления */
                    tokenViewModel.checkDailyBonus { message ->
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
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
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }else{
            Text(
                text = "Reward claimed! Come back tomorrow.",
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
        }

        Text(
            modifier = Modifier.padding(top = 12.dp, bottom = 10.dp),
            text = "Come back in 24h for more!",
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}