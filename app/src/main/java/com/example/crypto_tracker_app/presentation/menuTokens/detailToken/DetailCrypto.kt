package com.example.crypto_tracker_app.presentation.menuTokens.detailToken

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import co.yml.charts.axis.AxisData
import co.yml.charts.common.extensions.formatToSinglePrecision
import co.yml.charts.common.extensions.isNotNull
import co.yml.charts.ui.linechart.LineChart
import co.yml.charts.ui.linechart.model.GridLines
import co.yml.charts.ui.linechart.model.IntersectionPoint
import co.yml.charts.ui.linechart.model.Line
import co.yml.charts.ui.linechart.model.LineChartData
import co.yml.charts.ui.linechart.model.LinePlotData
import co.yml.charts.ui.linechart.model.LineStyle
import co.yml.charts.ui.linechart.model.SelectionHighlightPoint
import co.yml.charts.ui.linechart.model.SelectionHighlightPopUp
import co.yml.charts.ui.linechart.model.ShadowUnderLine
import coil.compose.AsyncImage
import com.example.crypto_tracker_app.R
import com.example.crypto_tracker_app.presentation.viewmodel.TokenP_TimeViewModel
import com.example.crypto_tracker_app.presentation.viewmodel.TokenViewModel
import com.example.crypto_tracker_app.ui.theme.GreenGradient
import com.example.crypto_tracker_app.ui.theme.RedGradient
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun detailUIToken(id: String,name: String,price: Int,image: String,priceChange24h: Double,
                  priceAltProsent: Double,atlPrice: Double,athPrice: Double,
                  totalSupply : Double,maxSypply: Double,
                  highPrice24h: Double,lowPrice24h: Double,
                  tokenViewModel: TokenViewModel,
                  viewModel: TokenP_TimeViewModel,
                  priceChange24hProsent: Double,
                  priceChange7dProsent: Double,
                  priceChange30dProsent: Double,
                  priceChange1yProsent: Double,
                  nav : NavHostController
) {
    val points by viewModel.graphPoints.collectAsState()
    val selectedDay by viewModel.selectedButton.collectAsState()
    val loadingProgress by viewModel.progressBar.observeAsState(false)
    val datePriceToken by viewModel.dateGraph.observeAsState(emptyList())
    val token = tokenViewModel.balanceToken.find { it.name == name }
    val context = LocalContext.current
    var priceChanged = when (selectedDay) {
        "1" -> priceChange24hProsent
        "7" -> priceChange7dProsent
        "30" -> priceChange30dProsent
        "365" -> priceChange1yProsent
        else -> priceChange24hProsent
    }
    val scrollState = rememberScrollState()
        Box(
            modifier = Modifier.fillMaxSize().padding(10.dp)
                .statusBarsPadding()
        ) {
            Column(modifier = Modifier.fillMaxWidth().background(Color.Unspecified).verticalScroll(scrollState)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    LaunchedEffect(name) {
                        viewModel.loadTokensByTime(
                            id = id,
                            currency = "usd",
                            days = "1"
                        )
                    }
                    AsyncImage(
                        image, contentDescription = "image",
                        modifier = Modifier.size(36.dp)
                    )
                    Text(
                        name,
                        Modifier.padding(10.dp),
                        fontSize = 20.sp
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Text(
                        "${price}$",
                        modifier = Modifier.padding(10.dp),
                        fontSize = 20.sp
                    )

                    val formatterPrice = "%.1f".format(priceChanged)
                    Card(
                        modifier = Modifier.background(
                            brush = if (priceChange24hProsent > 0) GreenGradient else RedGradient,
                            shape = CardDefaults.shape
                        ),
                        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                modifier = Modifier.size(20.dp),
                                painter = painterResource(
                                    id =
                                        if (priceChanged > 0)
                                            R.drawable.arrow_up_right else R.drawable.arrow_up_right__1_
                                ),
                                contentDescription = "image"
                            )
                            Text(
                                color = if (priceChanged > 0) Color.Black else Color.White,
                                modifier = Modifier.padding(5.dp),
                                text = "$formatterPrice%",
                                fontSize = 18.sp,
                                style = TextStyle(fontStyle = FontStyle.Italic)
                            )
                        }
                    }
                }


                if (points.size < 2) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }

                } else {
                    val configuration = LocalConfiguration.current
                    val screenWithDp = configuration.screenWidthDp
                    val steps = 5
                    val minPrice = points.minBy { it.y }.y
                    val maxPrice = points.maxBy { it.y }.y
                    val screen = screenWithDp / points.size

                    val xAxisData = AxisData.Builder()
                        .axisLineColor(Color.White)
                        .axisStepSize(screen.dp)
                        .axisLabelFontSize(0.sp)
                        .backgroundColor(Color.White)
                        .steps(5)
                        .labelData { i ->
                            if (datePriceToken.isEmpty() || i >= datePriceToken.size) {
                                return@labelData ""
                            }

                            val timeInMs = datePriceToken[i]
                            val date = Date(timeInMs)
                            val formater = SimpleDateFormat("d MMM", Locale.getDefault())
                            formater.format(date)


                        }
                        .labelAndAxisLinePadding(15.dp)
                        .build()

                    val yAxisData = AxisData.Builder()
                        .axisLineColor(Color.Transparent)
                        .steps(steps)
                        .backgroundColor(Color.White)
                        .labelAndAxisLinePadding(20.dp)
                        .labelData { " " }
                        .labelAndAxisLinePadding(0.dp)
                        .build()


                    val lineChartData = LineChartData(
                        linePlotData = LinePlotData(
                            lines = listOf(
                                Line(
                                    dataPoints = points,
                                    LineStyle(
                                        color = if (priceChanged > 0) Color(0xFF1E90FF) else Color.Red,
                                        width = 5.0f
                                    ),
                                    IntersectionPoint(radius = 0.dp),
                                    SelectionHighlightPoint(color = Color.Black, 1.dp),
                                    ShadowUnderLine(color = Color.White),
                                    SelectionHighlightPopUp(backgroundColor = Color.White)
                                )
                            ),
                        ),
                        xAxisData = xAxisData,
                        yAxisData = yAxisData,
                        gridLines = GridLines(
                            enableHorizontalLines = false,
                            enableVerticalLines = false
                        ),
                        backgroundColor = Color.White,
                        paddingRight = 0.dp,
                        containerPaddingEnd = 0.dp
                    )
                    LineChart(
                        modifier = Modifier
                            .fillMaxWidth().clip(RoundedCornerShape(20.dp))
                            .height(250.dp),
                        lineChartData = lineChartData
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(1f),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Button(
                        onClick = {
                            viewModel.loadTokensByTime(
                                id = id,
                                currency = "usd",
                                "1"
                            )
                            viewModel.updateSelectB("1")
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (selectedDay == "1") Color(0xFFE0FFFF) else Color(
                                0xFFF0FFFF
                            ),
                            contentColor = if (selectedDay == "1") Color(0xFF1E90FF) else Color(
                                0xFF4B0082
                            ),
                        )
                    ) {
                        Text("1day")
                    }


                    Button(
                        modifier = Modifier.background(Color(0xFFF0FFFF)),

                        onClick = {
                            viewModel.loadTokensByTime(
                                id = id.toString(),
                                currency = "usd",
                                "7"
                            )
                            viewModel.updateSelectB("7")

                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (selectedDay == "7") Color(0xFFE0FFFF) else Color(
                                0xFFF0FFFF
                            ),
                            contentColor = if (selectedDay == "7") Color(0xFF1E90FF) else Color(
                                0xFF4B0082
                            ),
                        )
                    ) {
                        Text("7day")
                    }

                    Button(
                        modifier = Modifier.background(Color(0xFFF0FFFF)),

                        onClick = {
                            viewModel.loadTokensByTime(
                                id = id.toString(),
                                currency = "usd",
                                "30"
                            )
                            viewModel.updateSelectB("30")
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (selectedDay == "30") Color(0xFFE0FFFF) else Color(
                                0xFFF0FFFF
                            ),
                            contentColor = if (selectedDay == "30") Color(0xFF1E90FF) else Color(
                                0xFF4B0082
                            ),
                        )
                    ) {
                        Text("30")
                    }

                    Button(
                        modifier = Modifier.background(Color(0xFFF0FFFF)),

                        onClick = {
                            viewModel.loadTokensByTime(
                                id = id.toString(),
                                currency = "usd",
                                "365"
                            )
                            viewModel.updateSelectB("365")
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (selectedDay == "365") Color(0xFFE0FFFF) else Color(
                                0xFFF0FFFF
                            ),
                            contentColor = if (selectedDay == "365") Color(0xFF1E90FF) else Color(
                                0xFF4B0082
                            ),
                        )
                    ) {
                        Text("1year")
                    }

                }
                Box(
                    modifier = Modifier.fillMaxWidth().padding(10.dp).background(Color.Transparent)
                ) {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Text(
                                "Min price",
                                style = TextStyle(fontStyle = FontStyle.Italic),
                                fontSize = 14.sp,
                                modifier = Modifier.padding(6.dp, bottom = 20.dp)
                            )
                            Text(
                                "$atlPrice$",
                                style = TextStyle(fontStyle = FontStyle.Italic),
                                fontSize = 14.sp,
                                color = Color(0xFFB0C4DE),
                                modifier = Modifier.padding(6.dp, bottom = 20.dp)
                            )

                        }
                        HorizontalDivider(
                            modifier = Modifier.fillMaxWidth()
                                .padding(horizontal = 10.dp, vertical = 4.dp),
                            thickness = 1.dp,
                            color = Color.Black
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {
                            Text(
                                "Max price",
                                style = TextStyle(fontStyle = FontStyle.Italic),
                                fontSize = 14.sp,
                                modifier = Modifier.padding(6.dp, bottom = 20.dp)
                            )
                            Text(
                                "$athPrice$",
                                style = TextStyle(fontStyle = FontStyle.Italic),
                                fontSize = 14.sp,
                                color = Color(0xFFB0C4DE),

                                modifier = Modifier.padding(6.dp, bottom = 20.dp)
                            )

                        }
                        HorizontalDivider(
                            modifier = Modifier.fillMaxWidth()
                                .padding(horizontal = 10.dp, vertical = 4.dp),
                            thickness = 1.dp,
                            color = Color.Black
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {
                            Text(
                                "High price 24h",
                                fontSize = 14.sp,
                                style = TextStyle(fontStyle = FontStyle.Italic),
                                modifier = Modifier.padding(6.dp, bottom = 20.dp)
                            )

                            Text(
                                "$highPrice24h$",
                                fontSize = 14.sp,
                                style = TextStyle(fontStyle = FontStyle.Italic),
                                color = Color(0xFFB0C4DE),
                                modifier = Modifier.padding(6.dp, bottom = 20.dp)
                            )

                        }
                        HorizontalDivider(
                            modifier = Modifier.fillMaxWidth()
                                .padding(horizontal = 10.dp, vertical = 4.dp),
                            thickness = 1.dp,
                            color = Color.Black
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {
                            Text(
                                "Low price 24h",
                                fontSize = 14.sp,
                                modifier = Modifier.padding(6.dp, bottom = 20.dp),
                                style = TextStyle(fontStyle = FontStyle.Italic)

                            )
                            Text(
                                "${lowPrice24h.toInt()}$",
                                fontSize = 14.sp,
                                color = Color(0xFFB0C4DE),
                                modifier = Modifier.padding(6.dp, bottom = 20.dp),
                                style = TextStyle(fontStyle = FontStyle.Italic)

                            )

                        }
                        HorizontalDivider(
                            modifier = Modifier.fillMaxWidth()
                                .padding(horizontal = 10.dp, vertical = 4.dp),
                            thickness = 1.dp,
                            color = Color.Black
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {
                            Text(
                                "Total Supply",
                                fontSize = 14.sp,
                                style = TextStyle(fontStyle = FontStyle.Italic),
                                modifier = Modifier.padding(6.dp, bottom = 20.dp)
                            )
                            Text(
                                "${totalSupply.toInt()}$",
                                fontSize = 14.sp,
                                color = Color(0xFFB0C4DE),
                                style = TextStyle(fontStyle = FontStyle.Italic),
                                modifier = Modifier.padding(6.dp, bottom = 20.dp)
                            )

                        }
                        HorizontalDivider(
                            modifier = Modifier.fillMaxWidth()
                                .padding(horizontal = 10.dp, vertical = 4.dp),
                            thickness = 1.dp,
                            color = Color.Black
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {
                            Text(
                                "Max Supply",
                                fontSize = 14.sp,
                                modifier = Modifier.padding(6.dp, bottom = 20.dp),
                                style = TextStyle(fontStyle = FontStyle.Italic)

                            )
                            Text(
                                "${maxSypply.toInt()}$",
                                fontSize = 14.sp,
                                color = Color(0xFFB0C4DE),
                                modifier = Modifier.padding(6.dp, bottom = 20.dp),
                                style = TextStyle(fontStyle = FontStyle.Italic)

                            )
                        }
                    }
                }


                if (token != null) {
                    Box(
                        modifier = Modifier.fillMaxWidth().background(Color.White)
                            .padding(10.dp).clip(RoundedCornerShape(20.dp))
                    ) {
                        Column(modifier = Modifier.fillMaxWidth().padding(10.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                AsyncImage(
                                    image, contentDescription = "",
                                    modifier = Modifier.size(30.dp),
                                )

                                Text(
                                    "${token.amount} $name",
                                    fontSize = 14.sp,
                                    fontFamily = FontFamily.Monospace,
                                    color = Color.Black
                                )
                            }
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceAround
                            ) {
                                Text(
                                    "${token.totalValue}$",
                                    fontSize = 14.sp,
                                    fontFamily = FontFamily.Monospace,
                                    color = Color.Black
                                )
                                Text(
                                    "",
                                    fontSize = 14.sp,
                                    fontFamily = FontFamily.Monospace,
                                    color = Color.Black
                                )
                            }
                        }
                    }
                }
            }
            Row (
                modifier = Modifier.fillMaxWidth().padding(20.dp).align(Alignment.BottomCenter),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                TextButton(
                    modifier = Modifier.weight(1f)
                        .padding(5.dp)
                        .background(brush = RedGradient, shape = RoundedCornerShape(20.dp)),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent,
                        contentColor = Color.White,
                    ),
                    onClick = {
                        if (token.isNotNull()) {
                            nav.navigate("sellScreen")

                        }else{
                            Toast.makeText(context,"You dont have token", Toast.LENGTH_LONG).show()

                        }
                    }
                ) {
                   Text(
                       "Sell"
                   )
                }
                TextButton(
                    modifier = Modifier.weight(1f).padding(5.dp)
                        .background(brush = GreenGradient, shape = RoundedCornerShape(20.dp)),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent,
                        contentColor = Color.White,
                    ),
                    onClick = {
                        nav.navigate("buyScreen")
                    }
                ) {
                    Text(
                        "Buy"
                    )
                }
            }

            }
}
