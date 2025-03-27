package vue;

import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import modele.DateCalendrier;
import modele.CalendrierDuMois;
import java.time.LocalDate;

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
        CalendrierDuMois calendrier = new CalendrierDuMois(3, 2025); // Exemple : Mars 2025

        // Obtenir la date d'aujourd'hui
        LocalDate aujourdhui = LocalDate.now();
        int jourAujourdhui = aujourdhui.getDayOfMonth();
        int moisAujourdhui = aujourdhui.getMonthValue();
        int anneeAujourdhui = aujourdhui.getYear();

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

            // Vérifier si cette date est aujourd'hui
            if (date.getJour() == jourAujourdhui &&
                    date.getMois() == moisAujourdhui &&
                    date.getAnnee() == anneeAujourdhui) {
                labelDate.getStyleClass().add("today");
            }

            datesContainer.getChildren().add(labelDate);
        }

        // Création d'un ScrollPane pour permettre le défilement
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(datesContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefHeight(200); // Hauteur préférée
        scrollPane.getStyleClass().add("calendar-scroll");

        // Ajout du ScrollPane au lieu du conteneur de dates directement
        getChildren().add(scrollPane);
    }
}
