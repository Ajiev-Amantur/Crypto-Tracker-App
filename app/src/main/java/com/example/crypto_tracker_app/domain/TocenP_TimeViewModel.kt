package com.example.crypto_tracker_app.domain

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.crypto_tracker_app.data.TocenByTime.PriceTimeIntance
import com.example.crypto_tracker_app.data.TocenByTime.PriceTimeModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TocenP_TimeViewModel: ViewModel(){

    private var _tocenP = MutableLiveData<PriceTimeModel>()
    val tocen: MutableLiveData<PriceTimeModel> = _tocenP

    private var _selectedButton = MutableStateFlow("1")
    val selectedButton = _selectedButton.asStateFlow()

    fun updateSelectB(selectedB: String){
        _selectedButton.value = selectedB
    }

    fun loadTocensByTime(id: String,currency: String,days: String){
        viewModelScope.launch {

            val tocen = PriceTimeIntance.api.getTocenByTime(
                id,
   //             address,
                currency,
                days
            )
            _tocenP.value = tocen
        }
    }
}