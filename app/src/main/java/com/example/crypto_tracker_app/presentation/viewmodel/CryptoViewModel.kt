package com.example.crypto_tracker_app.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.crypto_tracker_app.domain.model.CryptoTocensModel
import com.example.crypto_tracker_app.domain.repository.GetTocensRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CryptoViewModel(private val getTocenRepo: GetTocensRepository): ViewModel() {

private var _tocen = MutableLiveData<List<CryptoTocensModel>>()
val tocen : LiveData<List<CryptoTocensModel>> = _tocen

    private var _selectedToken = MutableLiveData<CryptoTocensModel>()
    val selectedTocen: LiveData<CryptoTocensModel?> = _selectedToken

    private var _progressBar = MutableLiveData(false)
    val progressBar: LiveData<Boolean> = _progressBar



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