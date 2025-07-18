# Star Wars Planets

This is an Android application that displays a list of planets from the Star Wars universe and
allows users to view details about each planet.

## Project Structure

The project is structured into several modules:

- `:app`: The main application module.
- `:feature:planets`: Contains features related to displaying the list of planets.
- `:feature:planetdetails`: Contains features related to displaying the details of a single planet.
- `:shared`: Contains shared code, such as data models, utility functions, and common UI components.

## Technologies Used

- **Kotlin**: The primary programming language.
- **Android Jetpack Compose**: For building the user interface.
- **Hilt**: For dependency injection.
- **Retrofit**: For making network requests to fetch planet data.
- **Kotlinx Serialization**: For serializing and deserializing JSON data.
- **Room**: For local data persistence.
- **Coroutines**: For asynchronous programming.
- **Turbine**: A testing library for Kotlin Coroutines Flow.
- **Mockito**: For mocking dependencies in unit tests.