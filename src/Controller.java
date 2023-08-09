import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class Controller {

    // Declare JavaFX components
    @FXML
    private TextField searchField;
    @FXML
    private ListView<String> movieListView;
    @FXML
    private ImageView posterImageView;

    // Constants for API access
    private static final String API_KEY = "8557b3a094mshe56819e2d96717ap1ff85fjsn0411cc1a5a2c";
    private static final String API_HOST = "movie-database-alternative.p.rapidapi.com";
    private static final String API_BASE_URL = "https://movie-database-alternative.p.rapidapi.com/";

    // Event handler for the search button
    @FXML
    private void searchButtonClicked(ActionEvent event) {
        String searchTerm = searchField.getText().trim();
        if (!searchTerm.isEmpty()) {
            fetchMovies(searchTerm);
        }
    }

    // Fetch movies based on search term
    private void fetchMovies(String searchTerm) {
        try {
            // Encode the search term
            String encodedSearchTerm = URLEncoder.encode(searchTerm, StandardCharsets.UTF_8.toString());

            // Build the HTTP request
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(API_BASE_URL + "?s=" + encodedSearchTerm + "&r=json&page=1"))
                    .header("X-RapidAPI-Key", API_KEY)
                    .header("X-RapidAPI-Host", API_HOST)
                    .method("GET", HttpRequest.BodyPublishers.noBody())
                    .build();

            // Send the request and get the response
            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

            // Parse the JSON response
            JsonElement jsonResponse = JsonParser.parseString(response.body());
            JsonObject jsonObject = jsonResponse.getAsJsonObject();
            JsonArray searchResults = jsonObject.getAsJsonArray("Search");

            // Clear the previous list and populate the ListView with movie titles
            movieListView.getItems().clear();
            for (JsonElement result : searchResults) {
                JsonObject movie = result.getAsJsonObject();
                movieListView.getItems().add(movie.get("Title").getAsString());
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Event handler for when a movie is selected from the ListView
    @FXML
    private void movieSelected() {
        String selectedMovie = movieListView.getSelectionModel().getSelectedItem();
        if (selectedMovie != null) {
            fetchMovieDetails(selectedMovie);
        }
    }

    // Fetch details for the selected movie
    private void fetchMovieDetails(String selectedMovie) {
        try {
            // Encode the movie title
            String encodedMovieTitle = URLEncoder.encode(selectedMovie, StandardCharsets.UTF_8.toString());

            // Build the HTTP request
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(API_BASE_URL + "?t=" + encodedMovieTitle + "&r=json"))
                    .header("X-RapidAPI-Key", API_KEY)
                    .header("X-RapidAPI-Host", API_HOST)
                    .method("GET", HttpRequest.BodyPublishers.noBody())
                    .build();

            // Send the request and get the response
            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

            // Parse the JSON response
            JsonElement jsonResponse = JsonParser.parseString(response.body());
            JsonObject movieDetails = jsonResponse.getAsJsonObject();

            // Check for error in response
            if (movieDetails.has("Error")) {
                showErrorAlert("Movie not found");
                return;
            }

            // Extract relevant information
            String title = movieDetails.get("Title").getAsString();
            String year = movieDetails.get("Year").getAsString();
            String imdbID = movieDetails.get("imdbID").getAsString();
            String type = movieDetails.get("Type").getAsString();
            String posterUrl = movieDetails.get("Poster").getAsString();

            // Update UI with movie details
            showMovieDetails(title, year, imdbID, type, posterUrl);

            // Fetch and show additional information using IMDb ID
            fetchAdditionalMovieInfo(imdbID);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Fetch additional movie information using IMDb ID
    private void fetchAdditionalMovieInfo(String imdbID) {
        try {
            // Build the HTTP request
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(API_BASE_URL + "?r=json&i=" + imdbID))
                    .header("X-RapidAPI-Key", API_KEY)
                    .header("X-RapidAPI-Host", API_HOST)
                    .method("GET", HttpRequest.BodyPublishers.noBody())
                    .build();

            // Send the request and get the response
            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

            // Parse the JSON response
            JsonElement jsonResponse = JsonParser.parseString(response.body());
            JsonObject additionalInfo = jsonResponse.getAsJsonObject();

            // Show additional information in a new alert
            showAdditionalMovieInfoAlert(additionalInfo);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Display additional movie information in an alert
    private void showAdditionalMovieInfoAlert(JsonObject additionalInfo) {
        // Extract information from the additionalInfo JsonObject and create an alert
        String plot = additionalInfo.get("Plot").getAsString();
        String director = additionalInfo.get("Director").getAsString();
        String actors = additionalInfo.get("Actors").getAsString();
        String awards = additionalInfo.get("Awards").getAsString();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Additional Movie Information");
        alert.setHeaderText(null);
        alert.setContentText("Plot: " + plot + "\n\nDirector: " + director + "\n\nActors: " + actors + "\n\nAwards: " + awards);
        alert.showAndWait();
    }

    // Display movie details and fetch additional info
    private void showMovieDetails(String title, String year, String imdbID, String type, String posterUrl) {
        // Update UI elements with movie details
        // Set posterImageView with the image from posterUrl
        posterImageView.setImage(new Image(posterUrl));

        // Create and show an Alert with basic movie information
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Movie Details");
        alert.setHeaderText(title + " (" + year + ")");
        alert.setContentText("IMDb ID: " + imdbID + "\nType: " + type);
        alert.showAndWait();
    }

    // Display an error alert
    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
