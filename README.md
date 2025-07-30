# 📱 Profile Match App

A **modern Android app** built with **Jetpack Compose**, **MVVM architecture**, and **Room Database** for browsing and interacting with user profiles.  

The app fetches profiles via API, stores them locally for offline use, and allows users to **Accept** or **Decline** profiles with persistent interaction states.

---

## 🚀 Features
✅ **Browse Profiles** – Fetch profiles from `randomuser.me` API and display them in a smooth list.  
✅ **Accept / Decline Actions** – Users can mark profiles as **Accepted** or **Declined**.  
✅ **Persistent Data** – All profiles and user interactions are stored in **Room DB**, surviving app restarts.  
✅ **Pagination** – Loads profiles **10 at a time** for an infinite-scroll experience.  
✅ **Offline Support** – Displays cached profiles when there’s no network.  
✅ **Animations & UX Enhancements** – Interactive buttons and smooth UI animations for better user experience.  

---

## 🏗 Tech Stack
This app follows **MVVM** architecture with a clean separation of concerns.

### 🖌 **UI Layer**
- **Jetpack Compose** – Declarative UI for building screens.
- **LazyColumn** – Efficient list rendering for profiles.
- **Compose Animations** – Used for interactive elements and button transitions.

### 🌐 **Network Layer**
- **Retrofit** – API requests.
- **GSON Converter** – JSON parsing.
- **OkHttp Logging Interceptor** – API request/response logging.

### 💾 **Local Storage**
- **Room Database** – Stores profiles & user actions for offline support.

### 🔧 **Dependency Injection**
- **Hilt (Dagger Hilt)** – Simplified dependency management.

### 🏗 **Concurrency**
- **Kotlin Coroutines** – For async API calls & database operations.

---

## 📦 Dependencies

/** Jetpack Compose Image Loading */
- implementation("io.coil-kt:coil-compose:2.6.0")

/** Dagger Hilt (Dependency Injection) */
- implementation(libs.hilt.android)
- implementation(libs.androidx.hilt.navigation.compose)
- ksp(libs.hilt.android.compiler)
- ksp(libs.androidx.hilt.compiler)

/** Retrofit & Networking */
- implementation("com.squareup.retrofit2:retrofit:2.9.0")
- implementation("com.squareup.retrofit2:converter-gson:2.9.0")
- implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.1")
- implementation("com.squareup.okhttp3:logging-interceptor:4.11.0")

/** Room Database */
- implementation("androidx.room:room-ktx:$room_version")
- ksp("androidx.room:room-compiler:$room_version")


##⚙️ How It Works
- 1️⃣ On app launch, the app first checks Room DB for cached profiles.
- 2️⃣ If internet is available, profiles are fetched from https://randomuser.me/api/.
- 3️⃣ Each API response (10 users) is stored in Room DB.
- 4️⃣ Users can Accept ✅ or Decline ❌ profiles:

Interaction status is updated and persisted in the database.
- 5️⃣ Offline Mode: If the network fails, cached profiles are displayed from Room DB.

##▶ How to Run
1️⃣ Clone the repository
- git clone https://github.com/your-username/ProfileMatchApp.git
- cd ProfileMatchApp
2️⃣ Open in Android Studio

3️⃣ Sync Gradle – All dependencies will download automatically.

4️⃣ Run the app on an emulator or a physical device.

