package com.example.crypto_tracker_app.presentation.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.intl.Locale
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.yml.charts.common.model.Point
import com.example.crypto_tracker_app.domain.model.room.BalanceUser.BalanceDataModel
import com.example.crypto_tracker_app.domain.model.room.TokenUserBalance.UserTokenModel
import com.example.crypto_tracker_app.domain.model.CryptoTokenModel
import com.example.crypto_tracker_app.domain.model.room.BalanceUser.BalanceDao
import com.example.crypto_tracker_app.domain.model.room.TokenUserBalance.TokenUserDao
import com.example.crypto_tracker_app.domain.repository.GetTokensRepository
import com.example.crypto_tracker_app.domain.usecase.SortTokensByPriceUp24h
import com.example.crypto_tracker_app.domain.usecase.SortTokenByPriceDown24h
import com.example.crypto_tracker_app.domain.usecase.SortTokenByRankBottomUseCase
import com.example.crypto_tracker_app.domain.usecase.SortTokenByRankTopUseCase
import com.example.crypto_tracker_app.domain.usecase.SortTokenHighPriceUseCase
import com.example.crypto_tracker_app.domain.usecase.SortTokenLowPriceUseCase
import com.example.crypto_tracker_app.presentation.TokenUiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date

class TokenViewModel(
    private val getTokenRepo: GetTokensRepository,
    private val sortTokenHighPriceUseCase: SortTokenHighPriceUseCase,
    private val sortTokenLowPriceUseCase: SortTokenLowPriceUseCase,
    private val sortTokenByRankTopUseCase: SortTokenByRankTopUseCase,
    private val sortTokenByRankBottomUseCase: SortTokenByRankBottomUseCase,
    private val sortTokenByPriceUp24h: SortTokensByPriceUp24h,
    private val sortTokenByPriceDown24h: SortTokenByPriceDown24h,
    private val dao: BalanceDao,
    private val TokenDao: TokenUserDao
): ViewModel() {
    val balance = mutableStateOf(500.0)
    var isTimeVisisble = mutableStateOf(false)
    var isSecondLeft = mutableStateOf(86400L)

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

    // user tokens
    val balanceToken = mutableStateListOf<UserTokenModel>()

    private val _uiState = MutableStateFlow<TokenUiState>(TokenUiState.loading)
    val uiState : StateFlow<TokenUiState> = _uiState


    // know actual price token
    val totalPriceToken: Double
        get(){
            val tokenPrice = balanceToken.sumOf { it.amount * it.price }
            return balance.value + tokenPrice
        }

    val tokenPriceUpOrDown: Double
        get(){
            val priceBoughtToken = balanceToken.sumOf { it.amount * it.price }
            val money = balanceToken.sumOf { it.totalValue }
            return priceBoughtToken - money

        }

    init {
        viewModelScope.launch {
            val tokens = TokenDao.getTokenBalance()
            balanceToken.addAll(tokens)
        }
    }
    init {
        loadDailyBonus()
    }
    fun loadDailyBonus(){
        viewModelScope.launch {
            val currentData = dao.getBalance()
            if (currentData != null && currentData.lastBonusTime != 0L) {
                val nowTime = System.currentTimeMillis()
                val passed = nowTime - currentData.lastBonusTime
                val dayMs = 24 * 60 * 60 * 1000L
                if (passed < dayMs){
                    isTimeVisisble.value = true
                    isSecondLeft.value = (dayMs - passed) / 1000
                    startTime() // Запускаем тикалку!
                }else{
                    isTimeVisisble.value = false
                }
            }
        }
    }
    fun checkDailyBonus(onResult: ((String) -> Unit)? = null){
        viewModelScope.launch {
            val time = System.currentTimeMillis()
            val today = SimpleDateFormat("yyyy-MM-dd",
                java.util.Locale.getDefault()).format(Date())
            val currentData = dao.getBalance()
            if (currentData != null){
                if (today != currentData.lastBonusData){
                    val newBalance = balance.value + 50.0
                    dao.insertBalance(
                        BalanceDataModel(
                            id = 0,
                            balance = newBalance.toInt(),
                            today,
                            time
                        )
                    )
                    isTimeVisisble.value = true
                    isSecondLeft.value = 86400L // Исправили опечатку
                    startTime()
                    balance.value = newBalance
                    onResult?.invoke("Success! 50$ added to your balance.")
                }else{
                    onResult?.invoke("You already claimed your reward today!")

            }
        }else{
            val newBalance = balance.value + 50.0
                balance.value = newBalance
                dao.insertBalance(BalanceDataModel(
                    id = 0,
                    newBalance.toInt(),
                    today,
                    time
                ))
                isTimeVisisble.value = true
                isSecondLeft.value = 86400L
                startTime()
                onResult?.invoke("Welcome! 50$ bonus awarded.")
            }
    }
    }
   private fun startTime(){
        viewModelScope.launch {
            while (isSecondLeft.value > 0 && isTimeVisisble.value){
                delay(1000)
                isSecondLeft.value -= 1
            }
            if (isSecondLeft.value <= 0) {
                isTimeVisisble.value = false
            }
        }
    }
    fun sellUserToken(nameToken: String, sellAmount: Double, currentPrice: Double){
        val index = balanceToken.indexOfFirst { it.name == nameToken }
        if (index == -1) return

        val token = balanceToken[index]
        val actualAmountSell = if (sellAmount > token.amount)token.amount else sellAmount
        val money = actualAmountSell * currentPrice
        if (actualAmountSell > token.amount){

            balanceToken.removeAt(index)
            updateBalane(money)
            balance.value += money
            viewModelScope.launch {
                TokenDao.addToken(token.copy(amount = 0.0, totalValue = 0.0))
            }
        }else{
            val newAmount = token.amount - actualAmountSell
            val value = (newAmount / token.amount * token.totalValue)
            val updatedToken = token.copy(amount = newAmount, totalValue = value)

            balanceToken[index] = updatedToken
            updateBalane(money)

            viewModelScope.launch {
                TokenDao.addToken(updatedToken)
            }
        }
    }
    fun addUserToken(newToken: UserTokenModel){
        val index = balanceToken.indexOfFirst { newToken.name == it.name }
        if (index != -1){
            val token = balanceToken[index]

            val updatedToken = token.copy(
                amount = token.amount + newToken.amount,
                totalValue = token.totalValue + newToken.totalValue
            )

            balanceToken[index] = updatedToken
            viewModelScope.launch {
                TokenDao.addToken(token)
            }
        }else {
            viewModelScope.launch {
                TokenDao.addToken(newToken)
                balanceToken.add(newToken)
          }
        }
    }
init {
    viewModelScope.launch {
        val totalValue = dao.getBalance()?.balance
        if (totalValue != null){
            balance.value = totalValue.toDouble()
        }
    }
}


    fun updateBalane(newSum: Double) {
        viewModelScope.launch {
            val currentData = dao.getBalance()
            balance.value = newSum
            dao.insertBalance(BalanceDataModel(
                id = 0,
                balance = newSum.toInt(),
                lastBonusData = currentData?.lastBonusData ?: "",
                lastBonusTime = currentData?.lastBonusTime ?: 0L
            ))
        }
    }


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
                _uiState.value = TokenUiState.loading
                val tokens = getTokenRepo.getAllTokens()

                balanceToken.forEachIndexed { index, tokenUser ->
                    val searchedToken = tokens.find { it.name == tokenUser.name }
                    if (searchedToken != null){
                        balanceToken[index] = tokenUser.copy(price = searchedToken.currentPrice)
                    }
                }
                _tokenList.value = tokens
                _uiState.value = TokenUiState.Sucsess(tokens)
            } catch (e: Exception){
                println("ERROR:  $e")
                _uiState.value = TokenUiState.Error(e.message?: "Error")
            } finally {
                _progressBar.value = false
            }
        }
    }
}
