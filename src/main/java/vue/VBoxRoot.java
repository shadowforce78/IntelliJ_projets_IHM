package vue;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import modele.DateCalendrier;
import modele.CalendrierDuMois;
import java.time.LocalDate;

public class VBoxRoot extends VBox {
    private int currentMonthIndex;
    private Label monthLabel;
    private StackPane stackPaneMois;

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
        stackPaneMois = new StackPane();
        stackPaneMois.getStyleClass().add("stack-pane-mois");
        stackPaneMois.setPrefHeight(200); // Hauteur préférée

        // Création du conteneur principal
        BorderPane mainContainer = new BorderPane();

        // Création du conteneur pour le mois et le bouton
        HBox bottomContainer = new HBox(10);
        bottomContainer.setAlignment(Pos.BOTTOM_RIGHT);

        // Label pour le mois (en bas à droite)
        monthLabel = new Label("");
        monthLabel.getStyleClass().add("month-label");

        // Bouton pour le mois précédent
        Button prevMonthBtn = new Button("←");
        prevMonthBtn.getStyleClass().add("nav-button");
        prevMonthBtn.setOnAction(e -> showPrevMonth());

        // Bouton de navigation pour le mois suivant
        Button nextMonthBtn = new Button("→");
        nextMonthBtn.getStyleClass().add("nav-button");
        nextMonthBtn.setOnAction(e -> showNextMonth());

        bottomContainer.getChildren().addAll(prevMonthBtn, monthLabel, nextMonthBtn);

        // Placement des éléments
        mainContainer.setCenter(stackPaneMois);
        mainContainer.setBottom(bottomContainer);

        // Création des mois
        for (int mois = 1; mois <= 12; mois++) {
            CalendrierDuMois calendrier = new CalendrierDuMois(mois, anneeCourante);

            VBox monthContainer = new VBox(10);
            monthContainer.getStyleClass().add("month-container");

            TilePane tilePane = new TilePane();
            tilePane.setPrefColumns(7);
            tilePane.setHgap(5);
            tilePane.setVgap(5);
            tilePane.getStyleClass().add("dates-container");

            // Ajouter les en-têtes des jours de la semaine
            String[] joursAbrev = { "Lu", "Ma", "Me", "Je", "Ve", "Sa", "Di" };
            for (String jour : joursAbrev) {
                Label labelJour = new Label(jour);
                labelJour.getStyleClass().add("label-jour-semaine");
                tilePane.getChildren().add(labelJour);
            }

            // Obtenir le jour de la semaine du premier jour du mois (1=Lundi, 7=Dimanche)
            // Utiliser l'itérateur pour accéder au premier élément de la collection
            DateCalendrier premierJour = null;
            for (DateCalendrier date : calendrier.getDates()) {
                if (date.getJour() == 1) {
                    premierJour = date;
                    break;
                }
            }

            int jourSemainePremierJour = 1; // Par défaut lundi
            if (premierJour != null) {
                jourSemainePremierJour = premierJour.getJourSemaine();
            }

            // Ajuster pour que notre index commence à 0 (0=Lundi, 6=Dimanche)
            int debutIndex = jourSemainePremierJour - 1;
            if (debutIndex < 0)
                debutIndex = 6; // Si c'est dimanche (7), l'index devient 6

            // Ajouter des espaces vides pour aligner le premier jour
            for (int i = 0; i < debutIndex; i++) {
                Label vide = new Label("");
                vide.getStyleClass().add("label-date");
                vide.getStyleClass().add("label-vide");
                tilePane.getChildren().add(vide);
            }

            // Ajout des dates dans le TilePane
            for (DateCalendrier date : calendrier.getDates()) {
                // Afficher uniquement le numéro du jour
                Label labelDate = new Label(String.valueOf(date.getJour()));
                labelDate.getStyleClass().add("label-date");

                if (date.getJour() == jourAujourdhui &&
                        date.getMois() == moisAujourdhui &&
                        date.getAnnee() == anneeCourante) {
                    labelDate.getStyleClass().add("today");
                }

                tilePane.getChildren().add(labelDate);
            }

            monthContainer.getChildren().add(tilePane);
            stackPaneMois.getChildren().add(monthContainer);
            monthContainer.setAccessibleText(String.valueOf(mois));
        }

        currentMonthIndex = moisAujourdhui;
        updateMonthDisplay();
        showCurrentMonth();

        getChildren().add(mainContainer);
    }

    private void showNextMonth() {
        currentMonthIndex = (currentMonthIndex % 12) + 1;
        updateMonthDisplay();
        showCurrentMonth();
    }

    private void showPrevMonth() {
        currentMonthIndex = (currentMonthIndex > 1) ? (currentMonthIndex - 1) : 12;
        updateMonthDisplay();
        showCurrentMonth();
    }

    private void updateMonthDisplay() {
        CalendrierDuMois calendrier = new CalendrierDuMois(currentMonthIndex, LocalDate.now().getYear());

        // Convertir le numéro du mois en nom de mois
        String[] nomsMois = { "", "Janvier", "Février", "Mars", "Avril", "Mai", "Juin",
                "Juillet", "Août", "Septembre", "Octobre", "Novembre", "Décembre" };

        String nomMois = nomsMois[currentMonthIndex];
        monthLabel.setText(nomMois);
    }

    private void showCurrentMonth() {
        String monthToShow = String.valueOf(currentMonthIndex);
        for (javafx.scene.Node node : stackPaneMois.getChildren()) {
            if (node instanceof VBox) {
                VBox monthContainer = (VBox) node;
                if (monthContainer.getAccessibleText().equals(monthToShow)) {
                    monthContainer.toFront();
                    break;
                }
            }
        }
    }

    public static void main(String[] args) {
        PremiereApplication.main(args);
    }
}
