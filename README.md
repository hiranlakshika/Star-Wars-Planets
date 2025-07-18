# Star Wars Planets

This is an Android application that displays a list of planets from the Star Wars universe and
allows users to view details about each planet.

## Project Structure

The project is structured into several modules:

- `:app`: The main application module.
- `:feature:planets`: Contains features related to displaying the list of planets.
- `:feature:planetdetails`: Contains features related to displaying the details of a single planet.
- `:shared`: Contains shared code, such as data models, utility functions, and common UI components.

## Architecture

This project follows a **Clean Architecture** approach, emphasizing separation of concerns and
testability. Key principles include:

- **Domain Layer**: Contains business logic and entities, independent of any framework.
- **Data Layer**: Implements repositories defined in the domain layer, handling data sources (
  network, local database).
- **Presentation Layer**: Manages UI logic and state, interacting with the domain layer.

Additionally, the project utilizes the **Model-View-Intent (MVI)** architectural pattern within the
presentation layer. MVI promotes a unidirectional data flow, making state management predictable and
debugging easier:

- **Model (State)**: Represents the current state of the UI.
- **View**: Observes the state and renders the UI, sending user intents to the ViewModel.
- **Intent**: User actions or events that trigger state changes.
- **ViewModel**: Processes intents, interacts with the domain layer, and updates the state.

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