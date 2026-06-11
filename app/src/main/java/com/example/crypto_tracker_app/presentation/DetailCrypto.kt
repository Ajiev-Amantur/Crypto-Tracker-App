package com.example.crypto_tracker_app.presentation

import android.R
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.yml.charts.axis.AxisData
import co.yml.charts.common.extensions.formatToSinglePrecision
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

@Composable
fun detailUITocen(name: String,price: Int,image: String,priceChange24h: Double,
                  priceAltProsent: Double,atlPrice: Double,athPrice: Double,
                  viewModel: TocenP_TimeViewModel){
    val priceByTime by viewModel.graphPoints.collectAsState()
    val loadingProgress by viewModel.progressBar.observeAsState(false)

    Box(modifier = Modifier.fillMaxWidth().padding(10.dp)
        .statusBarsPadding()) {
        Column(modifier = Modifier.fillMaxWidth().background(Color.Unspecified)) {
            Row() {
                LaunchedEffect(name) {
                    viewModel.loadTocensByTime(
                        id = name.lowercase(), // Проверь, что это ID (например, "bitcoin"), а не имя
                        currency = "usd",
                        days = "1"
                    )
                }
                val tocen by viewModel.tocen.observeAsState()
//                val price = tocen?.prices
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
                    "${price}$",
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


            if (priceByTime.size < 2){
                Box(modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center){
                    CircularProgressIndicator()
                }

            }else {
                val configuration = LocalConfiguration.current
                val screenWithDp = configuration.screenWidthDp

                val steps = 5
                val minPrice = priceByTime.minBy { it.y }.y
                val maxPrice = priceByTime.maxBy { it.y }.y
                val screen = screenWithDp / priceByTime.size

                val xAxisData = AxisData.Builder()
                    .axisStepSize(screen.dp)
                    .backgroundColor(Color.White)
                    .steps(priceByTime.size / -1 )
                    .labelData { i -> "" }
                    .labelAndAxisLinePadding(15.dp)
                    .build()

                val yAxisData = AxisData.Builder()
                    .steps(steps)
                    .backgroundColor(Color.White)
                    .labelAndAxisLinePadding(20.dp)
                    .labelData { i ->
                        val yScale = (maxPrice - minPrice) / steps
                        ((i * yScale) + minPrice).formatToSinglePrecision()

//                    val yScale = 100 / steps
//                    (i * yScale).formatToSinglePrecision()
                    }.build()

                val lineChartData = LineChartData(
                    linePlotData = LinePlotData(
                        lines = listOf(
                            Line(
                                dataPoints = priceByTime,
                                LineStyle(color = Color.Green, width = 4.0f),
                                IntersectionPoint(radius = 0.dp),
                                SelectionHighlightPoint(color = Color.Black,1.dp),
                                ShadowUnderLine(color = Color.White),
                                SelectionHighlightPopUp(backgroundColor = Color.White  )
                            )
                        ),
                    ),
                    xAxisData = xAxisData,
                    yAxisData = yAxisData,
                    gridLines = GridLines(enableHorizontalLines = false,
                        enableVerticalLines = false),
                    backgroundColor = Color.White
                )
                LineChart(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp),
                    lineChartData = lineChartData
                )
            }
//                Box(
//                    modifier = Modifier.padding(20.dp).fillMaxWidth(1f),
//                    contentAlignment = Alignment.Center
//                ) {
//                    Text(
//                        "${priceAltProsent.toInt()}%",
//                        fontSize = 50.sp,
//                        color = Color.Green,
//                        fontStyle = FontStyle.Italic
//                    )
//                }
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
                        containerColor = if(days =="30")Color.LightGray else Color.White,
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
                        containerColor = if(days =="365")Color.LightGray else Color.White,
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
//data class Point(
//    val x: Float,
//    val y: Float,
//)
//
//@Composable
//fun PointData(): List<Point>{
//    return
//}

//@Preview(showBackground = true)
//@Composable
//fun Preview(){
//detailUITocen("BNB",700,"image",8.9,
//   100000.0,0.001,95000.00)
//}