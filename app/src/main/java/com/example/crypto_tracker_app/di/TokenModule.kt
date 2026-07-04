package com.example.crypto_tracker_app.di

import com.example.crypto_tracker_app.data.TokenAPI.RetrofitInstance
import com.example.crypto_tracker_app.data.TokenByTimeApi.PriceTimeInstance
import com.example.crypto_tracker_app.data.repository.GetTokensRepositoryImpl
import com.example.crypto_tracker_app.data.repository.PriceTimeRepositoryImpl
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

    factory { SortTokenByPriceDown24h(get()) }
    factory { SortTokenByRankBottomUseCase(get()) }
    factory { SortTokenByRankTopUseCase(get()) }
    factory { SortTokenHighPriceUseCase(get()) }
    factory { SortTokenLowPriceUseCase(get()) }
    factory { SortTokensByPriceUp24h(get()) }

    viewModel {
        TokenViewModel(
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
