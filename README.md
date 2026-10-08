# NSE-ANALYSIS-

NSE Option Analyzer V10 Android app.

- Package: com.sachin.nseanalyzer
- Version: 10.0
- Offline-safe NSE/SENSEX option-flow analyzer
- EMA 8/13 + WaveTrend + OI engine
- CE/PE setup planner, spike detector and historical snapshot backtest
- Self-contained analyzer bundled as an Android WebView asset
- GitHub Actions builds a debug APK and uploads it as an artifact

## Build

    gradle test
    gradle :app:assembleDebug

APK output: app/build/outputs/apk/debug/app-debug.apk
