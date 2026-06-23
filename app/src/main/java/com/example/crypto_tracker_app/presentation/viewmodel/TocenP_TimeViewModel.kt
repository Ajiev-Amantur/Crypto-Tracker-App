package com.example.crypto_tracker_app.presentation.viewmodel

import android.icu.util.LocaleData
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.yml.charts.common.model.Point
import com.example.crypto_tracker_app.domain.model.PriceTimeModel
import com.example.crypto_tracker_app.domain.repository.PriceTimeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.collections.emptyList


class TocenP_TimeViewModel(private val tocenByTime: PriceTimeRepository): ViewModel() {
    // tocens price by time
    private var _tocenP = MutableLiveData<PriceTimeModel>()
    val tocen: LiveData<PriceTimeModel> = _tocenP

    // selectedButton
    private var _selectedButton = MutableStateFlow("1")
    val selectedButton = _selectedButton.asStateFlow()

        // points for grafic detailUI
   private val _grapchPoints = MutableStateFlow<List<Point>>(emptyList())
    var graphPoints: StateFlow<List<Point>> = _grapchPoints
    //progress Bar
    private var _progressBar = MutableLiveData(false)
    val progressBar: LiveData<Boolean> = _progressBar

    // dates
    private var _dataGraph = MutableLiveData<List<Long>>(emptyList())
    val dateGraph: MutableLiveData<List<Long>> = _dataGraph

//    //selectedDate 1,7,30,1y
//    private var _selectedDate = MutableStateFlow<Double>(0.0)
//    val selectedDate : StateFlow<Double> = _selectedDate

    fun updateSelectB(selectedB: String) {
        _selectedButton.value = selectedB
    }

    fun loadTocensByTime(id: String, currency: String, days: String) {
        viewModelScope.launch {
            _progressBar.value = true
            try {
                val result = tocenByTime.getTocenPriceByTime(
                    id = id,
                    currency = currency,
                    days = days
                )
                _tocenP.value = result

                val filtredPrices = result.prices.filterIndexed { index, _ ->  index % 10 == 0}

                dateGraph.value = filtredPrices.map { it[0].toLong() }

                _grapchPoints.value = filtredPrices.mapIndexed { index, priceByTime ->
                    Point(
                        x = index.toFloat(),
                        y = priceByTime[1].toFloat()
                    )
                }

            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _progressBar.value = false

            }
        }
    }

    suspend fun loadPoints(id: String, currency: String, day: String): List<Point> {
        return try {
            val data = tocenByTime.getTocenPriceByTime(id, currency, day)
            data.prices.filterIndexed { index, _ -> index % 10 == 0 }
                .mapIndexed { index, priceByTime ->
                    Point(
                        x = index.toFloat(),
                        y = priceByTime[1].toFloat()
                    )
                }
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }

    }
}