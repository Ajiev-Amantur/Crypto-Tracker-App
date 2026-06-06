package com.example.crypto_tracker_app.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.crypto_tracker_app.domain.model.PriceTimeModel
import com.example.crypto_tracker_app.domain.repository.PriceTimeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TocenP_TimeViewModel(private val tocenByTime: PriceTimeRepository): ViewModel(){

    private var _tocenP = MutableLiveData<PriceTimeModel>()
    val tocen: MutableLiveData<PriceTimeModel> = _tocenP

    private var _selectedButton = MutableStateFlow("1")
    val selectedButton = _selectedButton.asStateFlow()

    fun updateSelectB(selectedB: String){
        _selectedButton.value = selectedB
    }

    fun loadTocensByTime(id: String,currency: String,days: String){
        try {
        viewModelScope.launch {
            val result = tocenByTime.getTocenPriceByTime(
                id = id,
                currency = currency,
                days = days
            )
            _tocenP.value = result
        }
        }catch (e: Exception){
            e.printStackTrace()
        }
    }
}