package com.example.crypto_tracker_app.presentation.viewmodel

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


class TokenP_TimeViewModel(private val tokenByTime: PriceTimeRepository): ViewModel() {
    // tokens price by time
    private var _tokenP = MutableLiveData<PriceTimeModel>()
    val token: LiveData<PriceTimeModel> = _tokenP

    // selectedButton
    private var _selectedButton = MutableStateFlow("1")
    val selectedButton = _selectedButton.asStateFlow()

        // points for grafic detailUI
   private val _graphPoints = MutableStateFlow<List<Point>>(emptyList())
    var graphPoints: StateFlow<List<Point>> = _graphPoints
    // Progress Bar
    private var _progressBar = MutableLiveData(false)
    val progressBar: LiveData<Boolean> = _progressBar

    // dates
    private var _dateGraph = MutableLiveData<List<Long>>(emptyList())
    val dateGraph: LiveData<List<Long>> = _dateGraph

    private var fetchJob: kotlinx.coroutines.Job? = null // Для отмены старых запросов

    fun updateSelectB(selectedB: String) {
        _selectedButton.value = selectedB
    }
    var savedTimeinMil = 0L

    fun loadTokensByTime(id: String, currency: String, days: String) {
        val timeinMil = System.currentTimeMillis()
            if (timeinMil - savedTimeinMil < 2000) {
                return
            }
            savedTimeinMil = timeinMil
            fetchJob?.cancel() // Отменяем предыдущий запрос, если он еще идет
            fetchJob = viewModelScope.launch {
            _progressBar.value = true

                try {
                    val result = tokenByTime.getTokenPriceByTime(
                        id = id,
                        currency = currency,
                        days = days
                    )
                    _tokenP.value = result

                    val filteredPrices = result.prices.filterIndexed { index, _ -> index % 10 == 0 }

                    _dateGraph.value = filteredPrices.map { it[0].toLong() }

                    _graphPoints.value = filteredPrices.mapIndexed { index, priceByTime ->
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
            val data = tokenByTime.getTokenPriceByTime(id, currency, day)
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