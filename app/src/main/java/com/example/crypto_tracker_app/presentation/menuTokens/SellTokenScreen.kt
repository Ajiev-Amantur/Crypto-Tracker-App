package com.example.crypto_tracker_app.presentation.menuTokens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage

@Composable
fun SellScreen(image: String,nav: NavHostController) {
            Box(modifier = Modifier.fillMaxSize()) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Box(modifier = Modifier.fillMaxWidth().padding(20.dp, top = 50.dp)
                        .background(Color.White)) {
                        Row(modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceAround) {
                            AsyncImage(
                                model = image,
                                contentDescription = "image",
                                modifier = Modifier.size(30.dp)
                            )
                        Text(
                            "0.0000001",
                            fontSize = 14.sp,
                            fontFamily = FontFamily.SansSerif,

                            )
                        TextButton(
                            onClick = {},
                            colors = ButtonDefaults.textButtonColors(
                                containerColor = Color.Magenta,
                                contentColor = Color.White
                            )
                        ) {
                            Text(
                                "Sell All",
                                fontSize = 14.sp
                            )
                        }
                    }}
                    Text(
                        "Price",
                        fontSize = 14.sp,
                        modifier = Modifier.padding(horizontal = 20.dp)

                    )
                    var quantiryTextState by remember { mutableStateOf("") }
                    Box(modifier = Modifier.fillMaxWidth().padding(20.dp)) {
                        Row(modifier = Modifier.fillMaxWidth()) {
                            OutlinedTextField(
                                value = quantiryTextState,
                                onValueChange = { text -> quantiryTextState = text },
                                label = { Text("0.000000") },
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                    Text(
                        "quentity",
                        fontSize = 14.sp,
                        modifier = Modifier.padding(horizontal = 20.dp)
                    )
                    var priceTextState by remember { mutableStateOf("") }
                    Box(modifier = Modifier.fillMaxWidth().padding(20.dp)) {
                        Row(modifier = Modifier.fillMaxWidth()) {
                            OutlinedTextField(
                                value = priceTextState,
                                onValueChange = { text -> priceTextState = text },
                                label = { Text("0.000000") },
                                modifier = Modifier.fillMaxWidth()
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
                        modifier = Modifier.weight(1f).padding(20.dp),
                        onClick = {},
                        colors = ButtonDefaults.textButtonColors(
                            containerColor = Color.Magenta,
                            contentColor = Color.White
                        )
                    ) {
                        Text(
                            "Sell",
                            fontSize = 14.sp
                        )
                    }

                }
            }
        }