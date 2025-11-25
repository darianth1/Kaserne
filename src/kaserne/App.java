package kaserne;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

public class App extends Application {

    @Override
    public void start(Stage stage) {
        Label label = new Label("Hallo JavaFX!");
        StackPane root = new StackPane(label);

        Scene scene = new Scene(root, 400, 200);
        stage.setScene(scene);
        stage.setTitle("Mein JavaFX Fenster");
        stage.show();
    }
}