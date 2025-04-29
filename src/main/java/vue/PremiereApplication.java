package vue;

import java.io.File;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;

public class PremiereApplication extends Application {

    public void start   (@SuppressWarnings("exports") Stage stage) {
        File css = new File("css" + File.separator + "premierStyles.css");

        HBoxRoot root = new HBoxRoot();
        // VBoxCalendrier root = new VBoxCalendrier();

        Scene scene = new Scene(root, 800, 600);
        scene.getStylesheets().add(css.toURI().toString());

        stage.setScene(scene);
        stage.setTitle("Hello JavaFX");
        stage.show();
    }

    public static void main(String[] args) {
        Application.launch();
    }
}