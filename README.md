# The Daily Forecast

An Android app that brings weather, news and personal finance tracking together in one
place, with bottom-navigation tabs for each section.

> Built for the **ECM2425** mobile development module at the University of Exeter
> (individual coursework, 2025).

## Features

- **Weather:** current conditions and a 7-day forecast for your location from the
  [Open-Meteo](https://open-meteo.com/) API, with icons that change with the forecast
- **News:** top UK headlines from [GNews](https://gnews.io/). Tap an article to open it in
  your browser.
- **Finance:** record income and expenses with custom categories. They're stored in a
  Room database, and the app shows a running monthly balance.
- **Settings:** preferred currency, saved with Jetpack DataStore
- Light and dark themes that follow the system setting

## Tech stack

| Area | Library |
|---|---|
| Language | Kotlin, with some Java |
| UI | Fragments, Jetpack Navigation, RecyclerView, ConstraintLayout, View/Data Binding |
| Architecture | ViewModel, repository layer |
| Networking | Retrofit + Gson (weather), Volley (news), OkHttp logging |
| Persistence | Room (finance data), DataStore (preferences) |
| Other | Glide (images), Play Services Location |

## Running

1. Open the project in Android Studio (AGP 8.9, compile SDK 35, min SDK 26).
2. Get a free API key from [gnews.io](https://gnews.io/) and add it to `local.properties`:
   ```properties
   GNEWS_API_KEY=your_key_here
   ```
   Without a key, the weather and finance tabs still work, but the news tab can't load.
3. Run on an emulator or a device.

## Design notes

- **Fragments over Activities:** one activity hosts the Weather, News and Finance
  fragments, which keeps each section self-contained and navigation simple.
- **API integration:** the weather and news APIs return quite different JSON shapes,
  so each has its own parser and error handling. This was the main reason the
  app moved from Java to Kotlin partway through.
- **Future work:** better handling of API failures, offline caching of the latest
  weather and news, weather radar maps, and currency conversion.

## Project structure

```
app/src/main/java/com/edwardpratt/thedailyforecast/
├── model/        Data classes and Room database
├── network/      Weather (Retrofit) and news (Volley) clients
├── repository/   Data access layer
├── ui/           Activity, fragments and adapters per section
└── utils/        Location and DataStore helpers
```

## Licence

MIT
