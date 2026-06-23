package com.example.crypto_tracker_app.presentation

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import co.yml.charts.axis.AxisData
import co.yml.charts.common.extensions.formatToSinglePrecision
import co.yml.charts.common.model.Point
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
import com.example.crypto_tracker_app.domain.model.CryptoTocensModel
import com.example.crypto_tracker_app.presentation.viewmodel.CryptoViewModel
import com.example.crypto_tracker_app.presentation.viewmodel.TocenP_TimeViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.invoke
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.collections.get

@Composable
fun cryptoUI(name: String, price: Double, image: String, nav:
NavHostController, tocenViewModel: CryptoViewModel,tocenPTviewModel: TocenP_TimeViewModel,
             tocen: CryptoTocensModel,
             priceChange24hProsent: Double) {

    val progressBar by tocenViewModel.progressBar.observeAsState()
    var myPoint by remember {
        mutableStateOf<List<Point>>(emptyList())
    }
    val context = LocalContext.current
    Dispatchers.IO.apply {
        LaunchedEffect(tocen.id) {
            val result = tocenPTviewModel.loadPoints(
                tocen.id.lowercase(),
                currency = "usd",
                day = "1"
            )
            myPoint = result
        }
    }

        Card(
            modifier = Modifier.fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable(true, onClick = {
                tocenViewModel.selectTocen(tocen)
                nav.navigate("UI2")
                Toast.makeText(context, "clicked!!! $name", Toast.LENGTH_LONG).show()
            }
            ),
            colors = CardDefaults.cardColors(Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                AsyncImage(
                    model = image,
                    contentDescription = "image",
                    modifier = Modifier.size(60.dp).padding(12.dp)
                )
                Row(
                    modifier = Modifier.fillMaxWidth().padding(end = 10.dp),
                    Arrangement.SpaceBetween
                ) {
                    Text(
                        name, fontStyle = FontStyle.Italic,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(color = android.graphics.Color.BLACK),
                        modifier = Modifier.padding(start = 12.dp)
                    )
                    if (myPoint.size < 2) {
                        Box(modifier = Modifier.fillMaxSize()) {
                            CircularProgressIndicator()
                        }
                    } else {
                        val sizeScreen = 80f
                        val steps = 5
                        val points = myPoint.takeLast(20)
                        val stepSize = (sizeScreen / (points.size - 1)).dp
                        val xAxisData = AxisData.Builder()
                            .axisLineColor(Color.Transparent)
                            .axisStepSize(stepSize)
                            .backgroundColor(Color.Transparent)
                            .labelAndAxisLinePadding(0.dp)
                            .steps(5)
                            .labelData { "" }
                            .labelAndAxisLinePadding(2.dp)
                            .build()

                        val yAxisData = AxisData.Builder()
                            .axisLineColor(Color.Transparent)
                            .steps(steps)
                            .backgroundColor(Color.White)
                            .labelAndAxisLinePadding(0.dp)
                            .labelData { "" }.build()

                        val lineChartData = LineChartData(
                            linePlotData = LinePlotData(
                                lines = listOf(
                                    Line(
                                        dataPoints = points,
                                        LineStyle(
                                            color = if (priceChange24hProsent > 0) Color.Green else Color.Red,
                                            width = 3.0f),
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
                            backgroundColor = Color.White
                        )
                        LineChart(
                            modifier = Modifier
                                .width(100.dp)
                                .height(40.dp),
                            lineChartData = lineChartData
                        )
                    }
                    val formatterPrice = "%.1f".format(priceChange24hProsent)
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor =
                                if (priceChange24hProsent > 0) Color.Green else Color.Red
                        )
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                modifier = Modifier.size(20.dp),
                                painter = painterResource(
                                    id =
                                        if (priceChange24hProsent > 0)
                                            R.drawable.arrow_up_right else R.drawable.arrow_up_right__1_
                                ),
                                contentDescription = "image"
                            )
                            Text(
                                color = if (priceChange24hProsent > 0) Color.Black else Color.White,
                                modifier = Modifier.padding(5.dp),
                                text = "$formatterPrice%",
                                fontSize = 18.sp,
                                style = TextStyle(fontStyle = FontStyle.Italic)
                            )
                        }
                    }
                }
                Text(
                    "$price$", fontStyle = FontStyle.Normal,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(start = 12.dp, 4.dp)
                )

            }
        }
    }
