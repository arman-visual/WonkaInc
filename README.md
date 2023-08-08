# Android Project - Wonka App  :busts_in_silhouette:

Wonka App is an Android application that retrieves data from a server, stores it locally, and displays the data in a paginated list. Users can access the details of each item by navigating to its individual view. The app also includes unit tests to ensure the stability and accuracy of its functionality. Additionally, it supports local data-based search functionality.

## Dependencies

The following are the key dependencies used in the project:

### Core Libraries :calling:

- **androidx.core:core-ktx:1.7.0**: Provides Kotlin extension functions for the Android framework components, making development with Kotlin more concise and expressive.

- **androidx.appcompat:appcompat:1.6.1**: Offers support for the latest Android features on older versions of the platform, ensuring a consistent user experience across different devices.

- **com.google.android.material:material:1.9.0**: Implements the Material Design components, allowing for beautiful and modern UI design.

- **androidx.constraintlayout:constraintlayout:2.1.4**: Offers a flexible layout manager for creating complex UI designs in Android.

- **io.arrow-kt:arrow-core:1.0.1**: Introduces functional programming constructs and data types in Kotlin, helping to write more concise and robust code. (Catch result with Either)

### Dependency Injection :syringe:

- **com.google.dagger:hilt-android:2.44.2**: Provides a fast and efficient way to implement dependency injection in Android using Hilt.

### Network and Data Handling :satellite:

- **com.squareup.retrofit2:retrofit:2.9.0**: A type-safe HTTP client used for making network requests and handling API responses.

- **com.squareup.okhttp3:logging-interceptor:4.9.2**: An OkHttp interceptor that logs network requests and responses, aiding in debugging and monitoring.

- **com.google.code.gson:gson:2.9.0**: A library for converting Java/Kotlin objects to JSON and vice versa, making data serialization and deserialization effortless.

- **com.squareup.retrofit2:converter-moshi:2.9.0**: An adapter for Retrofit that enables easy conversion between JSON and Kotlin objects using Moshi.

- **com.squareup.moshi:moshi-kotlin:1.14.0**: A JSON serialization/deserialization library that works seamlessly with Kotlin.

### Database and Local Storage :floppy_disk:

- **androidx.room:room-runtime:2.4.3**: Provides an abstraction layer over SQLite, simplifying database operations and management.

- **androidx.room:room-paging:2.4.3**: Enables the integration of Room with Android Paging Library, optimizing data loading for large datasets.

- **androidx.room:room-ktx:2.4.3**: Kotlin extensions for Room, enhancing database interactions with more concise and idiomatic Kotlin code.

### Pagination :page_facing_up:

- **androidx.paging:paging-runtime:3.1.1**: Offers a set of components for implementing data pagination in an Android app, improving performance and memory usage.

### Navigation :arrows_counterclockwise:

- **androidx.navigation:navigation-fragment-ktx:2.6.0**: Provides the navigation components for fragments, simplifying the implementation of in-app navigation.

- **androidx.navigation:navigation-ui-ktx:2.6.0**: Adds navigation-related UI components for the Toolbar and other UI elements, enhancing the user experience.

### Image Loading :sunrise_over_mountains:

- **com.github.bumptech.glide:glide:4.12.0**: A fast and efficient image loading library for Android that handles image loading, caching, and display.

- **de.hdodenhof:circleimageview:3.1.0**: A custom ImageView that displays images in circular shape, perfect for profile pictures and thumbnails.

### Testing :white_check_mark:

- **junit:junit:4.13.2**: A widely used testing framework for writing and running unit tests in Java/Kotlin.

- **io.mockk:mockk:1.13.4**: A mocking library for Kotlin, making it easier to create mock objects for testing.

- **org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.3**: Provides testing utilities for Kotlin Coroutines, allowing for easier coroutine testing.

### Android Testing :white_check_mark:

- **androidx.test.ext:junit:1.1.5**: An AndroidJUnitRunner extension that allows for writing Android instrumentation tests using JUnit 4.

- **androidx.test.espresso:espresso-core:3.5.1**: A testing framework for Android that enables writing user interface tests.

## Getting Started

To run the MyApp application, follow these steps:

1. Clone the repository to your local machine.
2. Open the project in Android Studio.
3. Build and run the app on an emulator or physical device.

## License

This project is licensed under the Apache License. See the _LICENSE_ file for more details.
