package com.example.crypto_tracker_app.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.yml.charts.common.model.Point
import com.example.crypto_tracker_app.domain.model.CryptoTocensModel
import com.example.crypto_tracker_app.domain.repository.GetTocensRepository
import com.example.crypto_tracker_app.domain.usecase.SortTocenHighPriceUseCase
import com.example.crypto_tracker_app.domain.usecase.SortTocenLowPriceUseCase
import kotlinx.coroutines.launch

class CryptoViewModel(
    private val getTocenRepo: GetTocensRepository,
    private val sortTocenHighPriceUseCase: SortTocenHighPriceUseCase,
    private val sortTocenLowPriceUseCase: SortTocenLowPriceUseCase
): ViewModel() {

    private var _tocen = MutableLiveData<List<CryptoTocensModel>>()
    val tocen : LiveData<List<CryptoTocensModel>> = _tocen

    private var _selectedToken = MutableLiveData<CryptoTocensModel>()
    val selectedTocen: LiveData<CryptoTocensModel?> = _selectedToken

    private var _progressBar = MutableLiveData(true)
    val progressBar: LiveData<Boolean> = _progressBar

    // Флаг для отслеживания текущей сортировки (true - High Price, false - Low Price)
    private var _selected = MutableLiveData<Boolean>(true)
    val selected: LiveData<Boolean> = _selected

    fun prepareSparkline(prices: List<Double>): List<Point>{
        return prices.mapIndexed { index, price ->
            Point(
                x = index.toFloat(),
                price.toFloat()
            )
        }
    }

    fun selectTocen(token: CryptoTocensModel){
        _selectedToken.value = token
    }

    init {
        loadTocens()
    }

    // Добавление сортировки списка по высокой цене
    fun TocenByHighPrice(){
        _progressBar.value = true
        _selected.value = true
        viewModelScope.launch {
            try {
                val tocen = sortTocenHighPriceUseCase.execute()
                _tocen.value = tocen
            } catch (e: Exception){
                println(e)
            } finally {
                _progressBar.value = false
            }
        }
    }

    // Добавление сортировки списка по низкой цене
    fun TocenByLowPrice(){
        _progressBar.value = true
        _selected.value = false
        viewModelScope.launch {
            try {
                val tocen = sortTocenLowPriceUseCase.execute()
                _tocen.value = tocen
            } catch (e: Exception){
                println(e)
            } finally {
                _progressBar.value = false
            }
        }
    }

    fun loadTocens(){
        _progressBar.value = true
        viewModelScope.launch {
            try {
                val tocens = getTocenRepo.getAllTocens()
                _tocen.value = tocens
            } catch (e: Exception){
                println("ERROR:  $e")
            } finally {
                _progressBar.value = false
            }
        }
    }
}
