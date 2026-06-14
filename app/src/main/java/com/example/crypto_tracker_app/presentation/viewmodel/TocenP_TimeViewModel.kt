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



class TocenP_TimeViewModel(private val tocenByTime: PriceTimeRepository): ViewModel() {
    // tocens price by time
    private var _tocenP = MutableLiveData<PriceTimeModel>()
    val tocen: LiveData<PriceTimeModel> = _tocenP

    // selectedButton
    private var _selectedButton = MutableStateFlow("1")
    val selectedButton = _selectedButton.asStateFlow()
    // points for grafic
    private val _grapchPoints = MutableStateFlow<List<Point>>(emptyList())
    var graphPoints: StateFlow<List<Point>> = _grapchPoints
    //progress Bar
    private var _progressBar = MutableLiveData(false)
    val progressBar: LiveData<Boolean> = _progressBar
    // dates
    private var _dataGraph = MutableLiveData<List<Long>>(emptyList())
    val dateGraph : MutableLiveData<List<Long>> = _dataGraph

    fun updateSelectB(selectedB: String) {
        _selectedButton.value = selectedB
    }

        fun loadTocensByTime(id: String, currency: String, days: String) {
            _grapchPoints.value = emptyList()

            viewModelScope.launch {
                _progressBar.value = true
                try {
                    val result = tocenByTime.getTocenPriceByTime(
                        id = id,
                        currency = currency,
                        days = days
                    )
                    _tocenP.value = result
                    _progressBar.value = false


                    _progressBar.value = true
                    val filtredPrices = result.prices.filterIndexed { index, _-> index % 10 == 0  }
                    _dataGraph.value = filtredPrices.map { it[0].toLong() }
                    _grapchPoints.value = filtredPrices.mapIndexed { index,priceByTime ->
                        Point(
                            x = index.toFloat(),
                            y = priceByTime[1].toFloat()
                        )
                    }
                    _progressBar.value = false
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }