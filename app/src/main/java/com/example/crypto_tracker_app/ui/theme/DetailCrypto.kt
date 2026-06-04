package com.example.crypto_tracker_app.ui.theme

import android.text.Layout
import android.widget.GridLayout
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.crypto_tracker_app.domain.TocenP_TimeViewModel

@Composable
fun detailUITocen(name: String,price: Int,image: String,priceChange24h: Double,
                  priceAltProsent: Double,atlPrice: Double,athPrice: Double,
                  viewModel: TocenP_TimeViewModel){
    Box(modifier = Modifier.fillMaxWidth().padding(10.dp)
        .statusBarsPadding()) {
        Column(modifier = Modifier.fillMaxWidth().background(Color.Unspecified)) {
            Row() {
                val tocen by viewModel.tocen.observeAsState()
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
                    "${tocen}$",
                    modifier = Modifier.padding(10.dp),
                    fontSize = 30.sp
                )

                Text(
                    "${priceChange24h.toInt()}%",
                    fontSize = 30.sp,
                    modifier = Modifier.padding(10.dp),
                    color = Color.Magenta
                )
            }
                Box(
                    modifier = Modifier.padding(20.dp).fillMaxWidth(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "${priceAltProsent.toInt()}%",
                        fontSize = 50.sp,
                        color = Color.Green,
                        fontStyle = FontStyle.Italic
                    )
                }
            Row(modifier = Modifier.fillMaxWidth(1f),
                horizontalArrangement = Arrangement.SpaceAround){
                val days by viewModel.selectedButton.collectAsState()
                Button(onClick = {
                    viewModel.loadTocensByTime(
                        id = name.lowercase(),
                        currency = "usd",
                        "1"
                    )

                    viewModel.updateSelectB("1")
                },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (days == "1") Color.LightGray else Color.White,
                        contentColor = Color.Black
                    )
                ) {
                    Text("1day")
                }


                Button(onClick = {
                    viewModel.loadTocensByTime(
                        id = name.lowercase(),
                        currency = "usd",
                        "7"
                    )
                    viewModel.updateSelectB("7")

                },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if(days =="7")Color.LightGray else Color.White,
                        contentColor = Color.Black
                    )
                    ) {
                    Text("7day")
                }

                Button(onClick = {
                    viewModel.loadTocensByTime(
                        id = name.lowercase(),
                        currency = "usd",
                        "30"
                    )
                    viewModel.updateSelectB("30")
                },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if(days =="7")Color.LightGray else Color.White,
                        contentColor = Color.Black
                    )
                    ) {
                    Text("30")
                }

                Button(onClick = {
                    viewModel.loadTocensByTime(
                        id = name.lowercase(),
                        currency = "usd",
                        "365"
                    )
                    viewModel.updateSelectB("365")
                },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if(days =="7")Color.LightGray else Color.White,
                        contentColor = Color.Black
                    )
                    ) {
                    Text("1year")
                }

            }
                Text(
                    "Minimum price all time: $atlPrice",
                    fontSize = 20.sp,
                    modifier = Modifier.padding(10.dp, bottom = 20.dp)
                )

                Text(
                    "Max price all time: $athPrice",
                    fontSize = 20.sp,
                    modifier = Modifier.padding(10.dp, bottom = 20.dp)
                )
        }
    }
        }


//@Preview(showBackground = true)
//@Composable
//fun Preview(){
//detailUITocen("BNB",700,"image",8.9,
//   100000.0,0.001,95000.00)
//}