import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Load the FXML file and create the main UI layout
        Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));

        // Set the title of the application window
        primaryStage.setTitle("Movie Database App");

        // Load the icon image
        Image icon = new Image(getClass().getResourceAsStream("icon.png"));

        // Set the icon for the stage
        primaryStage.getIcons().add(icon);

        // Create a scene with the main UI layout
        Scene scene = new Scene(root);

        // Set the scene for the primary stage
        primaryStage.setScene(scene);

        // Display the application window
        primaryStage.show();
    }

    public static void main(String[] args) {
        // Launch the JavaFX application
        launch(args);
    }
}
