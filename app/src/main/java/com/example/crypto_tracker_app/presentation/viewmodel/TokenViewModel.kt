package com.example.crypto_tracker_app.presentation.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.yml.charts.common.model.Point
import com.example.crypto_tracker_app.domain.model.BalanceDataModel
import com.example.crypto_tracker_app.domain.model.BalanceTokenModel
import com.example.crypto_tracker_app.domain.model.CryptoTokenModel
import com.example.crypto_tracker_app.domain.repository.GetTokensRepository
import com.example.crypto_tracker_app.domain.usecase.SortTokensByPriceUp24h
import com.example.crypto_tracker_app.domain.usecase.SortTokenByPriceDown24h
import com.example.crypto_tracker_app.domain.usecase.SortTokenByRankBottomUseCase
import com.example.crypto_tracker_app.domain.usecase.SortTokenByRankTopUseCase
import com.example.crypto_tracker_app.domain.usecase.SortTokenHighPriceUseCase
import com.example.crypto_tracker_app.domain.usecase.SortTokenLowPriceUseCase
import kotlinx.coroutines.launch

class TokenViewModel(
    private val getTokenRepo: GetTokensRepository,
    private val sortTokenHighPriceUseCase: SortTokenHighPriceUseCase,
    private val sortTokenLowPriceUseCase: SortTokenLowPriceUseCase,
    private val sortTokenByRankTopUseCase: SortTokenByRankTopUseCase,
    private val sortTokenByRankBottomUseCase: SortTokenByRankBottomUseCase,
    private val sortTokenByPriceUp24h: SortTokensByPriceUp24h,
    private val sortTokenByPriceDown24h: SortTokenByPriceDown24h,
): ViewModel() {
    val balance = mutableStateOf(70000)
    private var _tokenList = MutableLiveData<List<CryptoTokenModel>>()
    val tokenList : LiveData<List<CryptoTokenModel>> = _tokenList

    private var _selectedToken = MutableLiveData<CryptoTokenModel>()
    val selectedToken: LiveData<CryptoTokenModel?> = _selectedToken

    private var _progressBar = MutableLiveData(true)
    val progressBar: LiveData<Boolean> = _progressBar

    // Флаг для отслеживания текущей сортировки (true - High Price, false - Low Price)
    private var _selectedPrice = MutableLiveData<Boolean>()
    val selectedPrice: LiveData<Boolean> = _selectedPrice
    private var _selectedPrice24h = MutableLiveData<Boolean>()
    val selectedPrice24h: LiveData<Boolean> = _selectedPrice24h

    private var _selectedRank = MutableLiveData<Boolean>()
    val selectedRank: LiveData<Boolean> = _selectedRank

    fun prepareSparkline(prices: List<Double>): List<Point>{
        return prices.mapIndexed { index, price ->
            Point(
                x = index.toFloat(),
                price.toFloat()
            )
        }
    }

    fun selectToken(token: CryptoTokenModel){
        _selectedToken.value = token
    }

    init {
        loadTokens()
    }
    val balanceToken = mutableStateListOf<BalanceTokenModel>()

    fun addTokenToBalance(token: BalanceTokenModel){
        balanceToken.add(token)
    }

    // Добавление сортировки списка по высокой цене
    fun TokenByHighPrice(){
        _progressBar.value = true
        _selectedPrice.value = false
        viewModelScope.launch {
            try {
                val token = sortTokenHighPriceUseCase.execute()
                _tokenList.value = token
            } catch (e: Exception){
                println(e)
            } finally {
                _progressBar.value = false
            }
        }
    }

    // Добавление сортировки списка по низкой цене
    fun TokenByLowPrice(){
        _progressBar.value = true
        _selectedPrice.value = true
        viewModelScope.launch {
            try {
                val token = sortTokenLowPriceUseCase.execute()
                _tokenList.value = token
            } catch (e: Exception){
                println(e)
            } finally {
                _progressBar.value = false
            }
        }
    }
    fun TokenByRankTop(){
        viewModelScope.launch {
            _progressBar.value = true
            _selectedRank.value = false
            try {
                val tokens = sortTokenByRankTopUseCase.execute()
                _tokenList.value = tokens
            }catch (e: Exception){
                println(e)
            }finally {
                _progressBar.value = false
            }
        }
    }
    fun TokenByRankBottom(){
        viewModelScope.launch {
            _progressBar.value = true
            _selectedRank.value = true
            try {
                val tokens = sortTokenByRankBottomUseCase.execute()
                _tokenList.value = tokens
            }catch (e: Exception){
                println(e)
            }finally {
                _progressBar.value = false
            }
        }
    }
    fun TokenByPriceUp(){
        _progressBar.value = true
        _selectedPrice24h.value = false
        viewModelScope.launch {
            try {
                val tokens = sortTokenByPriceUp24h.execute()
                _tokenList.value = tokens
            }catch (e: Exception){
                println(e)
            }finally {
                _progressBar.value = false
            }
        }
    }
    fun TokenByPriceDown(){
        _progressBar.value = true
        _selectedPrice24h.value = true
        viewModelScope.launch {
            try {
                val tokens = sortTokenByPriceDown24h.execute()
                _tokenList.value = tokens
            }catch (e: Exception){
                println(e)
            }finally {
                _progressBar.value = false
            }
        }
    }
    fun loadTokens(){
        _progressBar.value = true
        viewModelScope.launch {
            try {
                val tokens = getTokenRepo.getAllTokens()
                _tokenList.value = tokens
            } catch (e: Exception){
                println("ERROR:  $e")
            } finally {
                _progressBar.value = false
            }
        }
    }
}
