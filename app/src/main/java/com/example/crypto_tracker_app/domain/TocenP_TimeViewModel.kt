package com.example.crypto_tracker_app.domain

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.crypto_tracker_app.data.TocenByTime.PriceTimeIntance
import com.example.crypto_tracker_app.data.TocenByTime.PriceTimeModel
import kotlinx.coroutines.launch

class TocenP_TimeViewModel: ViewModel(){

    private var _tocenP = MutableLiveData<PriceTimeModel>()
    val tocen: MutableLiveData<PriceTimeModel> = _tocenP


    fun loadTocensByTime(){
        viewModelScope.launch {

            val tocen = PriceTimeIntance.api.getTocenByTime(
                "ethereum",
                "USDC",
                "usd",
                "30"
            )
        }
    }
}