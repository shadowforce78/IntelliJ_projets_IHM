package vue;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import modele.DateCalendrier;

public class VBoxRoot extends VBox {

    public VBoxRoot() {
        super(10); // Espacement de 10 entre les éléments
        getStyleClass().add("root-container");

        Label labelHello = new Label("Hello");
        labelHello.getStyleClass().add("label-primary");
        getChildren().add(labelHello);

        Label labelHelloBis = new Label("Hello JavaFX");
        labelHelloBis.getStyleClass().add("label-secondary");
        getChildren().add(labelHelloBis);

        // Ajout des étiquettes pour les dates
        DateCalendrier today = new DateCalendrier(); // Date d'aujourd'hui
        DateCalendrier tomorrow = today.dateDuLendemain(); // Date de demain

        Label labelToday = new Label("Date d'aujourd'hui: " + today.toString());
        labelToday.getStyleClass().add("label-date");
        getChildren().add(labelToday);

        Label labelTomorrow = new Label("Date de demain: " + tomorrow.toString());
        labelTomorrow.getStyleClass().add("label-date");
        getChildren().add(labelTomorrow);
    }
}
