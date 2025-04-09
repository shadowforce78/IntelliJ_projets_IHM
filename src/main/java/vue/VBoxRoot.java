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

        // Création du conteneur pour le mois et les boutons
        HBox bottomContainer = new HBox(10);
        bottomContainer.setAlignment(Pos.BOTTOM_RIGHT);

        // Label pour le mois
        monthLabel = new Label("");
        monthLabel.getStyleClass().add("month-label");

        // Bouton pour janvier
        Button firstMonthBtn = new Button("⟪");
        firstMonthBtn.getStyleClass().add("nav-button");
        firstMonthBtn.setOnAction(e -> showFirstMonth());

        // Bouton pour le mois précédent
        Button prevMonthBtn = new Button("←");
        prevMonthBtn.getStyleClass().add("nav-button");
        prevMonthBtn.setOnAction(e -> showPrevMonth());

        // Bouton pour le mois suivant
        Button nextMonthBtn = new Button("→");
        nextMonthBtn.getStyleClass().add("nav-button");
        nextMonthBtn.setOnAction(e -> showNextMonth());

        // Bouton pour décembre
        Button lastMonthBtn = new Button("⟫");
        lastMonthBtn.getStyleClass().add("nav-button");
        lastMonthBtn.setOnAction(e -> showLastMonth());

        bottomContainer.getChildren().addAll(firstMonthBtn, prevMonthBtn, monthLabel, nextMonthBtn, lastMonthBtn);

        // Placement des éléments
        mainContainer.setCenter(stackPaneMois);
        mainContainer.setBottom(bottomContainer);

        // Création des mois
        for (int mois = 1; mois <= 12; mois++) {
            CalendrierDuMois calendrier = new CalendrierDuMois(mois, anneeCourante);

            VBox monthContainer = new VBox(5);
            monthContainer.getStyleClass().add("month-container");

            // Create HBox for weekday headers
            HBox weekDaysHeader = new HBox(5);
            weekDaysHeader.getStyleClass().add("week-days-header");

            // Ajouter les en-têtes des jours de la semaine
            String[] joursAbrev = { "Lu", "Ma", "Me", "Je", "Ve", "Sa", "Di" };
            for (String jour : joursAbrev) {
                Label labelJour = new Label(jour);
                labelJour.getStyleClass().add("label-jour-semaine");
                weekDaysHeader.getChildren().add(labelJour);
            }

            monthContainer.getChildren().add(weekDaysHeader);

            // Create GridPane for dates instead of TilePane
            GridPane datesGrid = new GridPane();
            datesGrid.getStyleClass().add("dates-container");
            datesGrid.setHgap(5);
            datesGrid.setVgap(5);

            // Obtenir le jour de la semaine du premier jour du mois
            DateCalendrier premierJour = null;
            for (DateCalendrier date : calendrier.getDates()) {
                if (date.getJour() == 0) {
                    premierJour = date;
                    break;
                }
            }

            int jourSemainePremierJour = (premierJour != null) ? premierJour.getJourSemaine() : 1;
            int column = jourSemainePremierJour - 1;
            if (column < 0)
                column = 6;
            int row = 0;

            for (DateCalendrier date : calendrier.getDates()) {
                Label labelDate = new Label(String.valueOf(date.getJour()));
                labelDate.getStyleClass().add("label-date");
                final int dayNumber = date.getJour();

                if (date.getJour() == jourAujourdhui &&
                        date.getMois() == moisAujourdhui &&
                        date.getAnnee() == anneeCourante) {
                    labelDate.getStyleClass().add("today");
                }

                datesGrid.add(labelDate, column, row);
                column++;
                if (column > 6) {
                    column = 0;
                    row++;
                }
            }

            monthContainer.getChildren().add(datesGrid);
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

    private void showFirstMonth() {
        currentMonthIndex = 1;
        updateMonthDisplay();
        showCurrentMonth();
    }

    private void showLastMonth() {
        currentMonthIndex = 12;
        updateMonthDisplay();
        showCurrentMonth();
    }

    public static void main(String[] args) {
        PremiereApplication.main(args);
    }
}
