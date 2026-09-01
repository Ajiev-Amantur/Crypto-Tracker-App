package com.example.crypto_tracker_app.di

import com.example.crypto_tracker_app.data.TokenAPI.RetrofitInstance
import com.example.crypto_tracker_app.data.TokenByTimeApi.PriceTimeInstance
import com.example.crypto_tracker_app.data.repository.GetTokensRepositoryImpl
import com.example.crypto_tracker_app.data.repository.PriceTimeRepositoryImpl
import com.example.crypto_tracker_app.domain.model.room.BalanceUser.BalanceDao
import com.example.crypto_tracker_app.domain.model.room.BalanceUser.BalanceDataBase
import com.example.crypto_tracker_app.domain.model.room.TokenUserBalance.TokenUserBalanceDataBase
import com.example.crypto_tracker_app.domain.model.room.TokenUserBalance.TokenUserDao
import com.example.crypto_tracker_app.domain.repository.GetTokensRepository
import com.example.crypto_tracker_app.domain.repository.PriceTimeRepository
import com.example.crypto_tracker_app.domain.usecase.*
import com.example.crypto_tracker_app.presentation.viewmodel.TokenViewModel
import com.example.crypto_tracker_app.presentation.viewmodel.TokenP_TimeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val tokenModule = module {
    single { RetrofitInstance.api }
    single<GetTokensRepository> { GetTokensRepositoryImpl(get()) }

    factory { SortTokenByPriceDown24h() }
    factory { SortTokenByRankBottomUseCase() }
    factory { SortTokenByRankTopUseCase() }
    factory { SortTokenHighPriceUseCase() }
    factory { SortTokenLowPriceUseCase() }
    factory { SortTokensByPriceUp24h() }

    single { BalanceDataBase.createBalanceDataBase(get()) }
    single<BalanceDao> { get<BalanceDataBase>().dao}

    single { TokenUserBalanceDataBase.createTokenUserBDataBase(get()) }
    single<TokenUserDao> {get<TokenUserBalanceDataBase>().dao}
    viewModel {
        TokenViewModel(
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            get()
        )
    }
}

val tokenPriceModule = module {
    single { PriceTimeInstance.api }
    single<PriceTimeRepository> { PriceTimeRepositoryImpl(get()) }
    viewModel { TokenP_TimeViewModel(get()) }
}
