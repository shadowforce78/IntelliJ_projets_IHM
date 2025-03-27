package vue;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import modele.DateCalendrier;
import modele.CalendrierDuMois;

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

        // Création du calendrier du mois
        CalendrierDuMois calendrier = new CalendrierDuMois(10, 2023); // Exemple : Octobre 2023

        // Étiquette pour le mois et l'année
        Label labelMoisAnnee = new Label("Calendrier: " + calendrier.getMois() + " " + calendrier.getAnnee());
        labelMoisAnnee.getStyleClass().add("label-primary");
        getChildren().add(labelMoisAnnee);

        // Création d'une VBox pour contenir les dates
        VBox datesContainer = new VBox(5); // Espacement de 5 entre les dates
        datesContainer.getStyleClass().add("dates-container");

        // Ajout d'une étiquette pour chaque date du calendrier
        for (DateCalendrier date : calendrier.getDates()) {
            Label labelDate = new Label(date.toString());
            labelDate.getStyleClass().add("label-date");
            datesContainer.getChildren().add(labelDate);
        }

        getChildren().add(datesContainer);
    }
}
