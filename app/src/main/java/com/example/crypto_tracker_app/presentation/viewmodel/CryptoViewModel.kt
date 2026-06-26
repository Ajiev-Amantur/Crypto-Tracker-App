package com.example.crypto_tracker_app.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.yml.charts.common.model.Point
import com.example.crypto_tracker_app.domain.model.CryptoTocensModel
import com.example.crypto_tracker_app.domain.repository.GetTocensRepository
import kotlinx.coroutines.launch

class CryptoViewModel(private val getTocenRepo: GetTocensRepository): ViewModel() {

private var _tocen = MutableLiveData<List<CryptoTocensModel>>()
val tocen : LiveData<List<CryptoTocensModel>> = _tocen

    private var _selectedToken = MutableLiveData<CryptoTocensModel>()
    val selectedTocen: LiveData<CryptoTocensModel?> = _selectedToken

    private var _progressBar = MutableLiveData(true)
    val progressBar: LiveData<Boolean> = _progressBar


//   suspend fun pointsForListTocens(): List<Point>{
//       return try {
//           val result = getTocenRepo.getAllTocens()
//           result.mapIndexed { index, price ->
//               Point(
//                   x = index.toFloat(),
//                   y = price.sparkline.toFloat()
//               )
//           }
//       }catch (e: Exception){
//           e.printStackTrace()
//           emptyList()
//       }
//    }
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
    fun loadTocens(){
        _progressBar.value = true
    viewModelScope.launch {
        try {
            val tocens = getTocenRepo.getAllTocens()
            _tocen.value = tocens
} catch (e: Exception){
            println("ERROR:  $e")
        }finally {
            _progressBar.value = false
        }
}

    }
}