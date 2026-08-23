package com.example.crypto_tracker_app.presentation.menuTokens.priofile

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TimeCircle(timeLeft: Long,totalTime: Long){
    val color = MaterialTheme.colorScheme.onBackground
val sweepAngle = (timeLeft.toFloat() / totalTime) * 250
    Box(modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center){
        Canvas(modifier = Modifier.size(180.dp)) {
            drawArc(
                color = color,
                startAngle = -215f,
                sweepAngle = 250f,
                useCenter = false,
                style = Stroke(width = 18.dp.toPx(), cap = StrokeCap.Round)
            )
            drawArc(
                color = Color.Green,
                startAngle = -215f,
                sweepAngle = sweepAngle,
                useCenter = false,
                style = Stroke(width = 18.dp.toPx(), cap = StrokeCap.Round)
            )

        }
        Column(modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally) {
            val h = timeLeft / 3600
            val m = (timeLeft % 3600) / 60
            val s = timeLeft % 60

            Text(text = String.format("%02d:%02d:%02d" ,h, m, s),
                fontSize = 30.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground,)
            Text(text = "time remaining", fontSize = 14.sp, color = Color.Gray)        }
    }
 }
@Preview
@Composable
fun show(){
    TimeCircle(200,1000)
}