package vue;

import java.io.File;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;

public class PremiereApplication extends Application {

    public void start(@SuppressWarnings("exports") Stage stage) {
        File css = new File("css" + File.separator + "premierStyles.css");

        VBoxRoot root = new VBoxRoot();

        Scene scene = new Scene(root, 400, 300);
        scene.getStylesheets().add(css.toURI().toString());

        stage.setScene(scene);
        stage.setTitle("Hello JavaFX");
        stage.show();
    }

    public static void main(String[] args) {
        Application.launch();
    }
}