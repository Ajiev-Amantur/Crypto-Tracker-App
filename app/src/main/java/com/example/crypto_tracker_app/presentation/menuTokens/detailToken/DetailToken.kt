package com.example.crypto_tracker_app.presentation.menuTokens.detailToken

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import co.yml.charts.axis.AxisData
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
fun detailUIToken(id: String,name: String,price: Double,image: String,priceChange24h: Double,
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

    // Универсальная функция для "умного" форматирования цен и количеств
    fun formatSmart(value: Double): String {
        return when {
            value >= 1.0 -> "%.2f".format(value)
            value >= 0.0001 -> "%.4f".format(value)
            else -> "%.10f".format(value).trimEnd('0').trimEnd('.', ',')
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding().background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 80.dp, top = 10.dp, start = 10.dp, end = 10.dp)
                .background(MaterialTheme.colorScheme.background)
                .verticalScroll(rememberScrollState())
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp),
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
                Image(painter = painterResource(R.drawable.ic_back)
                    , contentDescription = "",
                    modifier = Modifier
                        .size(30.dp)
                        .clickable{
                        nav.popBackStack()
                    })
                Spacer(modifier = Modifier.width(16.dp)) // Небольшой отступ от стрелки
                AsyncImage(
                    image, contentDescription = "image",
                    modifier = Modifier.size(36.dp)
                )
                Text(
                    name,
                    Modifier.padding(10.dp),
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(
                    "${formatSmart(price)}$",
                    modifier = Modifier.padding(10.dp),
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )

                val formatterPrice = "%.1f".format(priceChanged)
                Card(
                    modifier = Modifier.background(
                        brush = if (priceChange24hProsent > 0) GreenGradient else RedGradient,
                        shape = CardDefaults.shape
                    ),
                    colors = CardDefaults.cardColors
                        (containerColor = Color.Transparent)
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
                            color = MaterialTheme.colorScheme.onBackground ,
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
                    .backgroundColor(MaterialTheme.colorScheme.background)
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
                    .backgroundColor(MaterialTheme.colorScheme.background)
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
                    backgroundColor = MaterialTheme.colorScheme.background,
                    paddingRight = 0.dp,
                    containerPaddingEnd = 0.dp
                )
                LineChart(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.background)
                        .clip(RoundedCornerShape(20.dp))
                        .height(250.dp),
                    lineChartData = lineChartData
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(1f),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Button(
                    modifier = Modifier.background(MaterialTheme.colorScheme.background),
                    onClick = {
                        viewModel.loadTokensByTime(
                            id = id,
                            currency = "usd",
                            "1"
                        )
                        viewModel.updateSelectB("1")
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.background,
                        contentColor = if (selectedDay == "1") Color(0xFF1E90FF) else Color(
                            0xFF4B0082))
                ) {
                    Text("24h")
                }


                Button(
                    modifier = Modifier.background(MaterialTheme.colorScheme.background),

                    onClick = {
                        viewModel.loadTokensByTime(
                            id = id.toString(),
                            currency = "usd",
                            "7"
                        )
                        viewModel.updateSelectB("7")

                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.background,
                        contentColor = if (selectedDay == "7") Color(0xFF1E90FF) else Color(
                            0xFF4B0082
                        ),
                    )
                ) {
                    Text("7day")
                }

                Button(
                    modifier = Modifier.background(MaterialTheme.colorScheme.background),

                    onClick = {
                        viewModel.loadTokensByTime(
                            id = id.toString(),
                            currency = "usd",
                            "30"
                        )
                        viewModel.updateSelectB("30")
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.background,
                        contentColor = if (selectedDay == "30") Color(0xFF1E90FF) else Color(
                            0xFF4B0082
                        ),
                    )
                ) {
                    Text("30")
                }

                Button(
                    modifier = Modifier.background(MaterialTheme.colorScheme.background),

                    onClick = {
                        viewModel.loadTokensByTime(
                            id = id.toString(),
                            currency = "usd",
                            "365"
                        )
                        viewModel.updateSelectB("365")
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.background,
                        contentColor = if (selectedDay == "365") Color(0xFF1E90FF) else Color(
                            0xFF4B0082
                        ),
                    )
                ) {
                    Text("1year")
                }

            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(10.dp)
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "Market Statistics",
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.onBackground,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )
                    
                    StatRow("Min price", "${formatSmart(atlPrice)}$")
                    HorizontalDivider(thickness = 0.5.dp, color = MaterialTheme.colorScheme.onBackground)

                    StatRow("Max price", "${formatSmart(athPrice)}$")
                    HorizontalDivider(thickness = 0.5.dp, color = MaterialTheme.colorScheme.onBackground)

                    StatRow("High price 24h", "${formatSmart(highPrice24h)}$")
                    HorizontalDivider(thickness = 0.5.dp, color = MaterialTheme.colorScheme.onBackground)

                    StatRow("Low price 24h", "${formatSmart(lowPrice24h)}$")
                    HorizontalDivider(thickness = 0.5.dp, color = MaterialTheme.colorScheme.onBackground)

                    StatRow("Total Supply", "${totalSupply.toLong()}$")
                    HorizontalDivider(thickness = 0.5.dp, color = MaterialTheme.colorScheme.onBackground)

                    StatRow("Max Supply", "${maxSypply.toLong()}$")
                }
            }




            if (token != null) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.background)
                        .padding(10.dp)
                        .clip(RoundedCornerShape(20.dp))
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            AsyncImage(
                                image, contentDescription = "",
                                modifier = Modifier.size(30.dp),
                            )
                            
                            Text(
                                "${formatSmart(token.amount)} $name",
                                fontSize = 14.sp,
                                fontFamily = FontFamily.Monospace,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        }
                        HorizontalDivider(
                            thickness = 0.2.dp, color = MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier.padding(10.dp)
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.Bottom // Выравнивание по низу
                        ) {
                            // ЛЕВАЯ ЧАСТЬ: Сколько потратили
                            Column {
                                Text("Invested", fontSize = 10.sp,
                                    color = MaterialTheme.colorScheme.onBackground)
                                Text(
                                    "${"%.2f".format(token.totalValue)}$",
                                    fontSize = 14.sp,
                                    fontFamily = FontFamily.Monospace,
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                            }

                            // ПРАВАЯ ЧАСТЬ: Профит (сгруппирован)
                            Column(horizontalAlignment = Alignment.End) {
                                val tokenProfit = (token.amount * token.price) - token.totalValue
                                val tokenProsent =
                                    if (token.totalValue > 0) (tokenProfit / token.totalValue * 100) else 0.0

                                val isPositive = tokenProfit >= 0
                                val pColor = if (isPositive) Color(0xFF4CAF50) else Color.Red
                                val sign = if (isPositive) "+" else ""

                                Text("Profit", fontSize = 10.sp,
                                    color = MaterialTheme.colorScheme.onBackground)
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    // Доллары профита
                                    Text(
                                        text = "$sign${"%.2f".format(tokenProfit)}$",
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold,
                                        fontFamily = FontFamily.Monospace,
                                        color = pColor
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    // Проценты в маленькой плашке или просто текстом
                                    Text(
                                        text = "($sign${tokenProsent.toInt()}%)",
                                        fontSize = 12.sp,
                                        fontFamily = FontFamily.Monospace,
                                        color = pColor.copy(alpha = 0.8f)
                                    )
                                }
                            }
                        }
                    }
                }
            }

        }
                Box(modifier = Modifier.fillMaxWidth().align(Alignment.BottomCenter)) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        TextButton(
                            modifier = Modifier
                                .weight(1f)
                                .padding(5.dp)
                                .background(brush = RedGradient, shape = RoundedCornerShape(20.dp)),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Transparent,
                                contentColor = Color.White,
                            ),
                            onClick = {
                                if (token.isNotNull()) {
                                    nav.navigate("sellScreen")

                                } else {
                                    Toast.makeText(
                                        context,
                                        "You dont have token",
                                        Toast.LENGTH_LONG
                                    ).show()

                                }
                            }
                        ) {
                            Text(
                                "Sell"
                            )
                        }
                        TextButton(
                            modifier = Modifier
                                .weight(1f)
                                .padding(5.dp)
                                .background(
                                    brush = GreenGradient,
                                    shape = RoundedCornerShape(20.dp)
                                ),
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
}
