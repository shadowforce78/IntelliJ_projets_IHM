package vue;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import modele.DateCalendrier;
import modele.CalendrierDuMois;
import java.time.LocalDate;
import controleur.Controleur;

public class VBoxCalendrier extends VBox {
    private int currentMonthIndex;
    private Label monthLabel;
    private StackPane stackPaneMois;
    private Controleur controleur;

    public VBoxCalendrier() {
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
                // Revenir à l'utilisation de Label au lieu de ToggleButton
                Label labelDate = new Label(String.valueOf(date.getJour()));
                labelDate.getStyleClass().add("label-date");

                // Stocker la date dans les propriétés du label
                labelDate.setUserData(date);

                // Rendre le label cliquable avec un style visuel
                labelDate.getStyleClass().add("clickable-label");

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

    /**
     * Définit le contrôleur qui sera à l'écoute des actions sur les labels des
     * dates
     * 
     * @param controleur Le contrôleur à associer aux labels des dates
     */
    public void setControleur(Controleur controleur) {
        this.controleur = controleur;

        // Parcourir tous les mois du calendrier
        for (javafx.scene.Node monthContainer : stackPaneMois.getChildren()) {
            if (monthContainer instanceof VBox) {
                // Parcourir les enfants de la VBox du mois
                for (javafx.scene.Node node : ((VBox) monthContainer).getChildren()) {
                    if (node instanceof GridPane) {
                        GridPane datesGrid = (GridPane) node;
                        // Parcourir toutes les dates dans la grille
                        for (javafx.scene.Node dateNode : datesGrid.getChildren()) {
                            if (dateNode instanceof Label && !((Label) dateNode).getText().equals("")) {
                                Label dateLabel = (Label) dateNode;
                                // Rendre le label cliquable et associer le contrôleur
                                dateLabel.setOnMouseClicked(this::handleDateLabelClick);
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Gère le clic sur un label de date
     * 
     * @param event L'événement de clic de souris
     */
    private void handleDateLabelClick(MouseEvent event) {
        Label dateLabel = (Label) event.getSource();
        DateCalendrier dateCalendrier = (DateCalendrier) dateLabel.getUserData();

        System.out.println("DEBUG VBoxCalendrier - handleDateLabelClick: Clic sur la date " +
                dateCalendrier.getJour() + "/" + dateCalendrier.getMois() + "/" +
                dateCalendrier.getAnnee());
        System.out.println("DEBUG VBoxCalendrier - handleDateLabelClick: userData=" + dateLabel.getUserData());

        // Transmettre l'événement au contrôleur
        if (controleur != null) {
            System.out.println("DEBUG VBoxCalendrier - handleDateLabelClick: Envoi au contrôleur");
            controleur.handleDateSelection(dateCalendrier);
        } else {
            System.out.println("DEBUG VBoxCalendrier - handleDateLabelClick: ERREUR - contrôleur null");
        }
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

}
