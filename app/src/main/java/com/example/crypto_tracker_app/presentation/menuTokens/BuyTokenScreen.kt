package com.example.crypto_tracker_app.presentation.menuTokens
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.crypto_tracker_app.ui.theme.GradientForCardBalance

@Composable
fun BuyScreen(image: String, navController: NavHostController) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Box(modifier = Modifier.fillMaxWidth().padding(20.dp)) {
                Column {
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
                    Text(
                        "Price",
                        fontSize = 14.sp,
                        modifier = Modifier.padding(horizontal = 20.dp)

                    )
                    var quantityTextState by remember { mutableStateOf("") }
                    OutlinedTextField(
                        value = quantityTextState,
                        onValueChange = { text -> quantityTextState = text },
                        label = { Text("0.000000") },
                        modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp)
                    )

                    Text(
                        "Quantity",
                        fontSize = 14.sp,
                        modifier = Modifier.padding(horizontal = 20.dp)

                    )
                    var priceTextState by remember { mutableStateOf("") }
                    OutlinedTextField(
                        value = priceTextState,
                        onValueChange = { text -> priceTextState = text },
                        label = { Text("0.000000") },
                        modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp)
                    )
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
                .align(Alignment.BottomCenter),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            TextButton(
                modifier = Modifier.weight(1f).padding(10.dp),
                onClick = {
                    navController.popBackStack()
                },
                colors = ButtonDefaults.textButtonColors(
                    containerColor = Color.Magenta,
                    contentColor = Color.White
                )
            ) {
                Text(
                    "Buy",
                    fontSize = 14.sp
                )
            }
        }
    }
}
