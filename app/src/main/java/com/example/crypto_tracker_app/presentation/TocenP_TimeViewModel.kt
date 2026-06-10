package com.example.crypto_tracker_app.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.crypto_tracker_app.domain.model.PriceTimeModel
import com.example.crypto_tracker_app.domain.repository.PriceTimeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import co.yml.charts.common.model.Point

class TocenP_TimeViewModel(private val tocenByTime: PriceTimeRepository): ViewModel() {

    private var _tocenP = MutableLiveData<PriceTimeModel>()
    val tocen: LiveData<PriceTimeModel> = _tocenP

    private var _selectedButton = MutableStateFlow("1")
    val selectedButton = _selectedButton.asStateFlow()

    private val _grapchPoints = MutableStateFlow<List<Point>>(emptyList())
    var graphPoints: StateFlow<List<Point>> = _grapchPoints

    fun updateSelectB(selectedB: String) {
        _selectedButton.value = selectedB
    }

        fun loadTocensByTime(id: String, currency: String, days: String) {
            viewModelScope.launch {
                try {
                    val result = tocenByTime.getTocenPriceByTime(
                        id = id,
                        currency = currency,
                        days = days
                    )
                    _tocenP.value = result
                    _grapchPoints.value = result.prices.mapIndexed { index,priceByTime ->
                        Point(
                            x = index.toFloat(),
                            y = priceByTime[1].toFloat()
                        )
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }