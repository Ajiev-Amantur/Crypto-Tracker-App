package com.example.crypto_tracker_app.domain

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.crypto_tracker_app.data.CryptoTocensModel
import com.example.crypto_tracker_app.data.RetrofitIntance
import kotlinx.coroutines.launch

class CryptoViewModel: ViewModel() {

private var _tocen = MutableLiveData<List<CryptoTocensModel>>()
val tocen : LiveData<List<CryptoTocensModel>> = _tocen
    init {
        loadTocens()
    }
    fun loadTocens(){
try {
    viewModelScope.launch {
        val tocens = RetrofitIntance.api.getTocens("usd")
        _tocen.value = tocens
    }
} catch (e: Exception){
    println("ERROR:  $e")
    Log.d("AMAN: ","$e")
}

    }
}