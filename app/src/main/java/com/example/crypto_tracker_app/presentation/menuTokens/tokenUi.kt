package com.example.crypto_tracker_app.presentation.menuTokens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import co.yml.charts.axis.AxisData
import co.yml.charts.common.model.Point
import co.yml.charts.ui.linechart.LineChart
import co.yml.charts.ui.linechart.model.GridLines
import co.yml.charts.ui.linechart.model.Line
import co.yml.charts.ui.linechart.model.LineChartData
import co.yml.charts.ui.linechart.model.LinePlotData
import co.yml.charts.ui.linechart.model.LineStyle
import coil.compose.AsyncImage
import com.example.crypto_tracker_app.R
import com.example.crypto_tracker_app.domain.model.CryptoTokenModel
import com.example.crypto_tracker_app.presentation.viewmodel.TokenViewModel
import com.example.crypto_tracker_app.presentation.viewmodel.TokenP_TimeViewModel
import com.example.crypto_tracker_app.ui.theme.GreenGradient
import com.example.crypto_tracker_app.ui.theme.RedGradient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun tokenUI(name: String, price: Double, image: String, nav:
NavHostController, tokenViewModel: TokenViewModel, tokenPTviewModel: TokenP_TimeViewModel,
             token: CryptoTokenModel,
             priceChange24hProsent: Double) {

    val progressBar by tokenViewModel.progressBar.observeAsState()
    var myPoint by remember(token.id){ mutableStateOf<List<Point>>(emptyList())}
    LaunchedEffect(token.id) {
        withContext(Dispatchers.Default){
            val result = tokenViewModel.prepareSparkline(token.sparkline)
            myPoint = result
        }
    }

    val context = LocalContext.current
    val sizeScreen = 80f
    val steps = 5
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable(true, onClick = {
                tokenViewModel.selectToken(token)
                nav.navigate("UI2")
            }
            ),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.background),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            AsyncImage(
                model = image,
                contentDescription = "image",
                modifier = Modifier
                    .size(50.dp)
                    .padding(12.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 10.dp),
                Arrangement.SpaceBetween
            ) {
                Text(
                    name, fontStyle = FontStyle.Italic,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.surface,
                    modifier = Modifier
                        .padding(start = 12.dp)
                        .weight(1f)
                )
                if (myPoint.size < 2) {
                    Box(
                        modifier = Modifier.size(100.dp, 40.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(20.dp),
                            strokeWidth = 2.dp
                        )
                    }
                } else {
                    val points = remember(myPoint) {
                        myPoint.takeLast(15)
                    }
                    val stepSize = remember(points) {
                        (sizeScreen / (points.size - 1)).dp
                    }

                    val xAxisData = remember(stepSize) {
                        AxisData.Builder()
                            .axisLineColor(Color.Transparent)
                            .axisStepSize(stepSize)
                            .backgroundColor(Color.Transparent)
                            .labelAndAxisLinePadding(0.dp)
                            .axisLabelFontSize(0.sp)
                            .steps(5)
                            .labelData { "" }
                            .labelAndAxisLinePadding(2.dp)
                            .build()
                    }

                    val yAxisData = remember(steps) {
                        AxisData.Builder()
                            .axisLineColor(Color.Transparent)
                            .steps(steps)
                            .backgroundColor(Color.White)
                            .labelAndAxisLinePadding(0.dp)
                            .labelData { "" }.build()
                    }

                    val lineChartData = remember(points, xAxisData, yAxisData) {
                        LineChartData(
                            linePlotData = LinePlotData(
                                lines = listOf(
                                    Line(
                                        dataPoints = points,
                                        LineStyle(
                                            color = if (priceChange24hProsent > 0) Color(0xFF0AFE47) else Color.Red,
                                            width = 6.0f),
                                        intersectionPoint = null,
                                        selectionHighlightPoint = null,
                                        shadowUnderLine = null,
                                        selectionHighlightPopUp = null,
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
                    }
                    LineChart(
                        modifier = Modifier
                            .width(100.dp)
                            .height(40.dp),
                        lineChartData = lineChartData
                    )
                }

                val formatterPrice = "%.1f".format(priceChange24hProsent)
                Card(
                    modifier = Modifier
                        .padding(start = 10.dp)
                        .background(
                            brush = if (priceChange24hProsent > 0) GreenGradient else RedGradient,
                            shape = CardDefaults.shape
                        ),
                    colors = CardDefaults.cardColors(containerColor = Color.Transparent)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                    ) {
                        Image(
                            modifier = Modifier.size(14.dp),
                            painter = painterResource(
                                id =
                                if (priceChange24hProsent > 0)
                                    R.drawable.arrow_up_right else R.drawable.arrow_up_right__1_
                            ),
                            contentDescription = "image"
                        )
                        Text(
                            color = if (priceChange24hProsent > 0) MaterialTheme.colorScheme.onSurface
                            else MaterialTheme.colorScheme.surface,
                            modifier = Modifier.padding(5.dp),
                            text = "$formatterPrice%",
                            fontSize = 16.sp,
                            style = TextStyle(fontStyle = FontStyle.Italic)
                        )
                    }
                }
            }
            Text(
                "$price$", fontStyle = FontStyle.Normal,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.surface,
                modifier = Modifier.padding(start = 12.dp, 4.dp)
            )

        }
    }
}
