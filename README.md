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
