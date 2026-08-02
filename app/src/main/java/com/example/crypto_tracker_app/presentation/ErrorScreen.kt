package com.example.crypto_tracker_app.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.room.util.TableInfo
import com.example.crypto_tracker_app.R
import com.example.crypto_tracker_app.domain.model.CryptoTokenModel
import com.example.crypto_tracker_app.presentation.viewmodel.TokenViewModel

@Composable
fun ErrorScreen(tokenViewModel: TokenViewModel){

    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Image(painter = painterResource(R.drawable.vecteezy_error_code_vector_icon_19026877),
            contentDescription = "image",
            modifier = Modifier.size(200.dp).padding(10.dp))
        Text("No Internet Connection",
            fontSize = 30.sp,
            fontFamily = FontFamily.Serif,
            color = Color.Black)

        Text("Please check your internet connection and try again",
            fontSize = 16.sp,
            fontFamily = FontFamily.Serif,
            color = Color.Black,
            modifier = Modifier.padding(20.dp))
        Button(modifier =
            Modifier.fillMaxWidth().
            padding(horizontal = 30.dp),
            colors = ButtonDefaults.textButtonColors(containerColor = Color.LightGray),
            onClick = {
                tokenViewModel.loadTokens()
            }) {
            Text("Retry",
                fontSize = 30.sp,
                color = Color.Black)        }
    }
}