package com.example.crypto_tracker_app.presentation.menuTokens.priofile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.crypto_tracker_app.ui.theme.GradientForCardBalance
@Preview
@Composable
fun ProfileScreen() {
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