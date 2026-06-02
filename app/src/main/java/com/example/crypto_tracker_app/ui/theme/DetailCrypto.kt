package com.example.crypto_tracker_app.ui.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.ModifierLocalReadScope
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

@Composable
fun detailUITocen(name: String,price: Int,image: String,priceChange24h: Double,
                  priceAltProsent: Double,atlPrice: Double,athPrice: Double){

    Box(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Row() {
                AsyncImage(
                    image, contentDescription = "image",
                    modifier = Modifier.size(60.dp)
                )
                Text(
                    name,
                    Modifier.padding(10.dp),
                    fontSize = 30.sp
                )
                Text(
                    "$price$",
                    modifier = Modifier.padding(10.dp),
                    fontSize = 30.sp
                )

                Text(
                    "$priceChange24h%.",
                    fontSize = 30.sp,
                    modifier = Modifier.padding(10.dp),
                    color = Color.Magenta
                )
            }
                Box(
                    modifier = Modifier.padding(50.dp).fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {


                    Text(
                        "$priceAltProsent%",
                        fontSize = 70.sp,
                        color = Color.Green,
                        fontStyle = FontStyle.Italic
                    )
                }
            Column() {
                Text(
                    "Minimum price all time: $atlPrice",
                    fontSize = 20.sp
                )

                Text(
                    "Max price all time: $athPrice",
                    fontSize = 20.sp
                )
            }
        }
    }
        }


//@Preview(showBackground = true)
//@Composable
//fun Preview(){
//detailUITocen("BNB",700,"image",8.9,
//    100000.0,0.001,95000)
//}