package vue;

import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.StackPane;
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

        // Obtenir la date d'aujourd'hui et l'année courante
        LocalDate aujourdhui = LocalDate.now();
        int jourAujourdhui = aujourdhui.getDayOfMonth();
        int moisAujourdhui = aujourdhui.getMonthValue();
        int anneeCourante = aujourdhui.getYear();

        // Création d'un StackPane pour empiler les 12 mois
        StackPane stackPaneMois = new StackPane();
        stackPaneMois.getStyleClass().add("stack-pane-mois");
        stackPaneMois.setPrefHeight(200); // Hauteur préférée

        // Créer 12 ScrollPane pour les 12 mois de l'année
        for (int mois = 1; mois <= 12; mois++) {
            // Création du calendrier pour ce mois
            CalendrierDuMois calendrier = new CalendrierDuMois(mois, anneeCourante);

            // Création d'une VBox pour contenir les dates du mois
            VBox datesContainer = new VBox(5);
            datesContainer.getStyleClass().add("dates-container");

            // Ajouter un en-tête pour le mois
            Label labelMoisAnnee = new Label(calendrier.getMois() + " " + calendrier.getAnnee());
            labelMoisAnnee.getStyleClass().add("label-mois");
            datesContainer.getChildren().add(labelMoisAnnee);

            // Ajout des dates pour ce mois
            for (DateCalendrier date : calendrier.getDates()) {
                Label labelDate = new Label(date.toString());
                labelDate.getStyleClass().add("label-date");

                // Vérifier si cette date est aujourd'hui
                if (date.getJour() == jourAujourdhui &&
                        date.getMois() == moisAujourdhui &&
                        date.getAnnee() == anneeCourante) {
                    labelDate.getStyleClass().add("today");
                }

                datesContainer.getChildren().add(labelDate);
            }

            // Créer un ScrollPane pour ce mois
            ScrollPane scrollPane = new ScrollPane();
            scrollPane.setContent(datesContainer);
            scrollPane.setFitToWidth(true);
            scrollPane.getStyleClass().add("calendar-scroll");

            // Ajouter le ScrollPane au StackPane (de janvier à décembre)
            stackPaneMois.getChildren().add(scrollPane);
        }

        // Ajouter une étiquette pour l'année
        Label labelAnnee = new Label("Calendrier " + anneeCourante);
        labelAnnee.getStyleClass().add("label-primary");
        getChildren().add(labelAnnee);

        // Ajouter le StackPane au VBox principal
        getChildren().add(stackPaneMois);
    }
}
