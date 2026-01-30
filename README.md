# MovieBrowser

Android application for browsing popular movies using The Movie Database (TMDB) API.

The app allows users to explore popular movies, view detailed information, and manage a list of favorite movies stored locally.

---

## Features

- Browse popular movies from TMDB
- Grid-based movie list (2 columns)
- Movie details screen with full description
- Pagination (loading more movies on scroll)
- Add/remove movies to favorites
- Favorites stored locally using Room
- Separate Favorites screen
- Modern UI built with Jetpack Compose

---

## Tech Stack

- **Kotlin**
- **Jetpack Compose**
- **MVVM architecture**
- **StateFlow**
- **Retrofit**
- **Room (with Flow)**
- **Navigation Compose**
- **Coroutines**
- **KSP**

---

## Architecture

The project follows the **MVVM** pattern:

- **UI (Compose)** - stateless composables observing ViewModel state  
- **ViewModel** - handles UI state and business logic using `StateFlow`  
- **Repository** - single source of truth for data  
- **Data layer** - Retrofit for network, Room for local persistence  

---

## API

- **The Movie Database (TMDB)**  
- Movie list loaded via `/movie/popular` endpoint with pagination  
- Movie details loaded via `/movie/{id}` endpoint  

### API Key handling
The TMDB API key is **not hardcoded** in the source code.  
It is provided via `BuildConfig` and excluded from version control.

---

## How to run

1. Clone the repository  
2. Create a file `apikey.properties` in the project root  
3. Add your TMDB API key to the file:
  API_KEY=your_api_key_here
4. Build and run the project

---

## Notes

- Pagination is implemented manually without Paging 3 to keep the architecture simple
- Favorites state is synchronized using Room + Flow
- UI focuses on clarity and usability rather than heavy animations
