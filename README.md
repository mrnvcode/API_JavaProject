# Movie Database App

![Java Version](https://img.shields.io/badge/Java-11-green.svg)
![JavaFX Version](https://img.shields.io/badge/JavaFX-16-blue.svg)

## Overview

The Movie Database App is a JavaFX application that allows users to search for movies and retrieve detailed information about them. It leverages the [RapidAPI Movie Database API](https://rapidapi.com/api-sports/api/movie-database-imdb-alternative) to fetch movie data, including titles, posters, and additional information.

This application provides a user-friendly interface for searching and viewing movie details, making it a handy tool for movie enthusiasts who want to quickly access information about their favorite films.

## Features

- **Search Movies:** Users can enter keywords in the search bar to find movies matching their criteria. The application will display a list of movies that match the search query.

- **View Movie Details:** Clicking on a movie from the search results will display detailed information about the selected movie. This includes the movie's title, release date, genre, runtime, plot summary, and a poster image.

- **Additional Information:** Users can also access additional information such as cast, crew, ratings, and reviews for the selected movie.

## Prerequisites

- Java 11 or higher installed on your system.
- JavaFX 16 or higher installed on your system.
- RapidAPI API Key (You can obtain it by signing up at [RapidAPI](https://rapidapi.com/))

## Getting Started

Follow these steps to get the Movie Database App up and running on your local machine:

1. Clone the repository to your local machine:

   ```bash
   git clone https://github.com/mrnvcode/MovieDatabase.git
   ```

2. Open the project in your favorite Java IDE.

3. Replace `YOUR_RAPIDAPI_KEY` in the `MovieApiClient.java` file with your RapidAPI API Key:

   ```java
   private static final String API_KEY = "YOUR_RAPIDAPI_KEY";
   ```

4. Build and run the application.

## Usage

1. Launch the Movie Database App.

2. Enter a movie title or keyword in the search bar and press the "Search" button.

3. Browse the list of movies that match your search query.

4. Click on a movie to view its detailed information, including the title, release date, genre, runtime, plot summary, and a poster image.

5. Explore additional information about the selected movie by clicking on the relevant tabs, such as "Cast," "Crew," "Ratings," and "Reviews."

## Credits

- This project uses the [RapidAPI Movie Database API](https://rapidapi.com/api-sports/api/movie-database-imdb-alternative) to fetch movie data.

## Contributing

Contributions to this project are welcome. If you have any improvements or bug fixes to suggest, please open an issue or create a pull request.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---

Feel free to reach out if you have any questions or need assistance with the Movie Database App. Enjoy exploring movies with this handy application!
