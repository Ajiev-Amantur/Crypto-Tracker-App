# 📈 Crypto Bull Tracker — Clean Architecture & Jetpack Compose

![App Logo](screenshots/logo.png)

**Crypto Bull Tracker** is a high-performance Android application for monitoring the cryptocurrency market and managing a virtual investment portfolio. The project demonstrates modern Android development standards, including reactive UI, local data persistence, and strict architectural decomposition.

## 💡 Key Features

*   **Real-time Market Data:** Integration with REST APIs to fetch live prices, market cap, and 24h statistics for the TOP-100 cryptocurrencies.
*   **Virtual Portfolio System:** Comprehensive buy/sell logic. The app automatically calculates:
    *   **Break-even Price:** Average entry cost for each asset.
    *   **Live PnL:** Real-time Profit and Loss tracking in both percentage and USD.
    *   **Balance Management:** Wallet synchronization with local storage.
*   **Performance-Optimized Charts:** Dynamic YCharts implementation with support for multiple timeframes (1D, 7D, 30D, 1Y).
*   **Daily Bonus Engine:** A gamified reward system with a persistent countdown timer implemented using Coroutines and Room.
*   **Smart Currency Formatting:** A custom algorithm that correctly displays ultra-low-priced tokens (e.g., $0.00000028) without falling back to scientific notation (`1.2E-7`).
*   **Full Theming:** Native support for Light and Dark modes using Material 3 color schemes.

## 🏗 Architecture

The app is built on **Clean Architecture** principles, ensuring scalability and testability:

*   **Presentation Layer:** Jetpack Compose + MVVM. Utilizes `StateFlow` and `LiveData` for reactive UI updates triggered by database or network changes.
*   **Domain Layer:** Pure business logic. Contains Repository interfaces and **Use Cases** for invariant data processing (sorting, filtering, calculations).
*   **Data Layer:** Repository implementations. Uses **Room** as a Single Source of Truth (local cache) and **Retrofit** for network operations.

## 🛠 Tech Stack

*   **UI:** Jetpack Compose (Material 3), Coil (Image Loading).
*   **DI:** Koin (Dependency Injection / Service Locator).
*   **Persistence:** Room Database (with transaction support).
*   **Network:** Retrofit 2, OkHttp3 (Logging Interceptor), GSON.
*   **Concurrency:** Kotlin Coroutines (Scopes, Dispatchers, Jobs), Flow.
*   **Analytics/Charts:** YCharts (Customized implementation).

## ⚡️ Advanced Optimizations

The project addresses real-world engineering challenges:
1.  **API Rate Limiting:** Implemented Cooldown logic (2 seconds) to protect against IP bans (Error 429).
2.  **Race Condition Prevention:** Uses `Job.cancel()` during chart timeframe switching to ensure only the latest data request is rendered.
3.  **UI Performance:** Optimized `LazyColumn` using unique `keys`, achieving 60 FPS even when rendering multiple complex charts.
4.  **Memory Management:** Data Decimation for sparkline charts, reducing the number of rendered points by 10x without losing visual fidelity.

## 📱 Screenshots
<p align="center">
  <img src="screenshots/main_screen.png" width="250">
  <img src="screenshots/detail_screen.png" width="250">
  <img src="screenshots/portfolio_screen.png" width="250">
</p>

---

### How to Run
1. Clone the repository: `git clone https://github.com/yourusername/CryptoTrackerApp.git`
2. Open the project in **Android Studio Ladybug** or newer.
3. Sync Gradle and run the app on an emulator or physical device (Android 8.0+).