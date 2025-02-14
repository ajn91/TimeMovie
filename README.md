# TimeMovie

A simple Android application for displaying a list of movies with details, built using modern Android development tools and best practices. The project is designed with an offline-first approach using Room database and provides seamless navigation between screens using Jetpack Navigation.

## Features
- Display a list of movies.
- View detailed information about each movie.
- Offline-first approach with Room for local data caching.
- Clean and maintainable architecture using MVI/MVVM patterns.

## Tech Stack
- **Jetpack Compose**: Modern toolkit for building native Android UIs.
- **Retrofit**: For network API calls and data fetching.
- **Room**: For local database storage and offline-first functionality.
- **Coroutines + Flow**: For asynchronous programming and reactive streams.
- **Hilt**: Dependency injection framework to manage and provide app dependencies.
- **Jetpack Navigation Component**: For navigating between the movie list and details screens.

## Architecture
This project is built using a combination of **MVI (Model-View-Intent)** and **MVVM (Model-View-ViewModel)** architecture patterns to ensure clean, scalable, and testable code.

## Getting Started

### Prerequisites
- Android Studio (latest version recommended).
- Minimum SDK: 21
- Target SDK: 33

### Installation
1. Clone the repository:
   ```bash
   git clone https://github.com/ajn91/TimeMovie.git
2. Open the project in Android Studio.
3. Sync the project with Gradle.
4. Run the app on an emulator or a physical device.

## Folder Structure

app/src/main/java/jafari/movie/
├── Application.kt
├── data/
│   ├── di/
│   ├── local/
│   ├── mapper/
│   ├── network/
│   └── repository/
├── di/
│   ├── AppDispatchers.kt
│   ├── CoroutineScopesModule.kt
│   └── DispatchersModule.kt
├── domain/
│   ├── errors/
│   ├── models/
│   ├── repository/
│   └── usecase/
├── presentation/
│   ├── HomeActivity.kt
│   ├── components/
│   ├── feature/
│   ├── navigation/
│   └── ui/
└── utilities/
    └── Constants.kt

- `Application.kt`: The main application file.
- `data/`: Handles data sources (API and database).
  - `di/`: Dependency injection setup for data layer.
  - `local/`: Local data storage using Room.
  - `mapper/`: Data mappers for converting between different data models.
  - `network/`: Network API calls using Retrofit.
  - `repository/`: Repository pattern for data operations.
- `di/`: Hilt dependency injection setup.
  - `AppDispatchers.kt`: App dispatchers configuration.
  - `CoroutineScopesModule.kt`: Coroutine scopes module.
  - `DispatchersModule.kt`: Dispatchers module.
- `domain/`: Domain layer containing business logic and use cases.
  - `errors/`: Error handling for domain layer.
  - `models/`: Domain models.
  - `repository/`: Interfaces for repositories used in the domain layer.
  - `usecase/`: Use cases for the domain layer.
- `presentation/`: UI components built with Jetpack Compose.
  - `HomeActivity.kt`: Home activity file.
  - `components/`: UI components.
  - `feature/`: Feature-specific UI components and logic.
  - `navigation/`: Navigation setup using Jetpack Navigation Component.
  - `ui/`: General UI components and setup.
- `utilities/`: Utility classes and helper functions.
  - `Constants.kt`: Constants used across the application.

## Contributing
Contributions are welcome! Please read the [contributing guidelines](https://github.com/ajn91/TimeMovie/blob/main/CONTRIBUTING.md) first.

## License
This project is licensed under the MIT License - see the [LICENSE](https://github.com/ajn91/TimeMovie/blob/main/LICENSE) file for details.

## Project URL
[GitHub Repository](https://github.com/ajn91/TimeMovie)
