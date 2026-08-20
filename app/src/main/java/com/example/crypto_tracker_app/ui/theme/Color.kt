package com.example.crypto_tracker_app.ui.theme

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)
// ТЕМНАЯ ТЕМА (Crypto Dark)
val CryptoDarkBackground = Color(0xFF0B0E11)
// Почти черный, как на Binance
val CryptoDarkSurface = Color(0xFF1E2329)
// Цвет карточек в темной теме
val CryptoPrimary = Color(0xFFFCD535)
// Фирменный желтый (как Binance) или выбери свой
val CryptoTextPrimary = Color(0xFFEAECEF)
// Светло-серый текст для темной темы
val CryptoTextSecondary = Color(0xFF848E9C)
// Серый текст для подписей
// СВЕТЛАЯ ТЕМА (Crypto Light)
 val CryptoLightBackground = Color(0xFFFFFFFF)
// Чистый белый
val CryptoLightSurface = Color(0xFFF5F5F5)
// Светло-серые карточки
 val CryptoTextDark = Color(0xFF1E2329)
// Темный текст

val GreenGradient = Brush.linearGradient(
    colors = listOf(
        Color(0xFF00FA9A),
        Color(0xFF7FFF00),
        Color(0xFF00FFFF),
        Color(0xFF0AFE47),
    ),
    start = Offset.Zero,
    end = Offset.Infinite
)

val RedGradient = Brush.linearGradient(
    colors = listOf(
        Color(0xFFFF5F6D),
        Color(0xFFDC143C),
        Color(0xFFFF0000)
    ),
    start = Offset.Zero,
    end = Offset.Infinite
)
val White = Color(0xFFF8F8FF)

val GradientForCardBalance = Brush.linearGradient(
    colors = listOf(
        Color(0xFF6A31FF), // Насыщенный фиолетовый
        Color(0xFFAD31FF), // Пурпурный
        Color(0xFF00D1FF)  // Циан
    ),
    start = Offset.Zero,
    end = Offset.Infinite
)
