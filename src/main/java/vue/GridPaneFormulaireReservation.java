package vue;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import modele.Horaire;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Classe représentant le formulaire de réservation simplifié
 * Permet de saisir le titre, la date et les horaires pour une nouvelle
 * réservation
 */
public class GridPaneFormulaireReservation extends GridPane {

    // Composants du formulaire
    private Label titreFormulaireLabel;
    private Label coursLabel; // Renommé pour clarté, représente le titre de la réservation
    private Label dateLabel;
    private Label horaireLabel;

    private TextField coursField; // Champ pour le titre de la réservation
    private DatePicker datePicker;

    // ComboBox pour sélectionner les horaires
    private ComboBox<String> heureDebutCombo;
    private ComboBox<String> minuteDebutCombo;
    private ComboBox<String> heureFinCombo;
    private ComboBox<String> minuteFinCombo;

    private Button annulerButton;
    private Button enregistrerButton;

    /**
     * Constructeur de la classe GridPaneFormulaireReservation
     */
    public GridPaneFormulaireReservation() {

        this.setGridLinesVisible(true);
        // Configuration de base du GridPane
        setPadding(new Insets(20));
        setHgap(10);
        setVgap(15);
        setAlignment(Pos.TOP_CENTER);
        getStyleClass().add("formulaire-container");

        // Initialisation des composants
        initialiserComposants();

        // Disposition des composants dans la grille
        disposerComposants();

        // Configuration des actions et validations
        configurerActions();

        // Configuration de l'accessibilité
        configurerAccessibilite();
    }

    /**
     * Initialise tous les composants du formulaire
     */
    private void initialiserComposants() {
        // Initialisation des labels
        titreFormulaireLabel = new Label("Nouvelle Réservation");
        titreFormulaireLabel.setFont(Font.font("System", FontWeight.BOLD, 16));
        titreFormulaireLabel.getStyleClass().add("titre-formulaire");

        coursLabel = new Label("_Titre :");
        coursLabel.setMnemonicParsing(true);

        dateLabel = new Label("_Date :");
        dateLabel.setMnemonicParsing(true);

        horaireLabel = new Label("_Horaire :");
        horaireLabel.setMnemonicParsing(true);

        // Initialisation des champs de saisie
        coursField = new TextField();
        coursField.setPromptText("Entrez le titre de la réservation");
        coursField.setPrefWidth(200);

        datePicker = new DatePicker(LocalDate.now());
        datePicker.setPromptText("Sélectionnez une date");
        datePicker.setPrefWidth(200);

        // Initialisation des combos pour les horaires avec les valeurs par défaut
        // (08h00 et 09h00)
        heureDebutCombo = new ComboBox<>();
        for (int i = 8; i <= 20; i++) {
            heureDebutCombo.getItems().add(String.format("%02d", i));
        }
        heureDebutCombo.setValue("08"); // Valeur par défaut
        heureDebutCombo.setPrefWidth(70);

        minuteDebutCombo = new ComboBox<>();
        minuteDebutCombo.getItems().addAll("00", "15", "30", "45");
        minuteDebutCombo.setValue("00"); // Valeur par défaut
        minuteDebutCombo.setPrefWidth(70);

        heureFinCombo = new ComboBox<>();
        for (int i = 8; i <= 20; i++) {
            heureFinCombo.getItems().add(String.format("%02d", i));
        }
        heureFinCombo.setValue("09"); // Valeur par défaut
        heureFinCombo.setPrefWidth(70);

        minuteFinCombo = new ComboBox<>();
        minuteFinCombo.getItems().addAll("00", "15", "30", "45");
        minuteFinCombo.setValue("00"); // Valeur par défaut
        minuteFinCombo.setPrefWidth(70);

        // Initialisation des boutons d'action
        annulerButton = new Button("_Annuler");
        annulerButton.setMnemonicParsing(true);
        annulerButton.getStyleClass().add("bouton-annuler");

        enregistrerButton = new Button("_Enregistrer");
        enregistrerButton.setMnemonicParsing(true);
        enregistrerButton.getStyleClass().add("bouton-enregistrer");
        enregistrerButton.setDefaultButton(true);
    }

    /**
     * Dispose les composants dans la grille
     */
    private void disposerComposants() {
        // Titre du formulaire
        add(titreFormulaireLabel, 0, 0, 2, 1);

        // Champ Titre (cours)
        add(coursLabel, 0, 1);
        add(coursField, 1, 1);

        // Champ Date
        add(dateLabel, 0, 2);
        add(datePicker, 1, 2);

        // Champ Horaire
        add(horaireLabel, 0, 3);

        HBox horaireDebutBox = new HBox(5);
        horaireDebutBox.getChildren().addAll(heureDebutCombo, new Label("h"), minuteDebutCombo);

        HBox horaireFinBox = new HBox(5);
        horaireFinBox.getChildren().addAll(heureFinCombo, new Label("h"), minuteFinCombo);

        HBox horaireCompletBox = new HBox(10);
        horaireCompletBox.getChildren().addAll(horaireDebutBox, new Label("à"), horaireFinBox);

        add(horaireCompletBox, 1, 3);

        // Boutons d'action
        HBox buttonsBox = new HBox(15);
        buttonsBox.setAlignment(Pos.CENTER_RIGHT);
        buttonsBox.getChildren().addAll(annulerButton, enregistrerButton);

        add(buttonsBox, 0, 4, 2, 1); // Remonté à la ligne 4

        // Espacement supplémentaire en bas du formulaire
        setPadding(new Insets(20, 20, 30, 20));
    }

    /**
     * Configure les actions associées aux composants
     */
    private void configurerActions() {
        // Action pour le bouton Annuler
        annulerButton.setOnAction(e -> reinitialiserFormulaire());

        // Action pour le bouton Enregistrer
        enregistrerButton.setOnAction(e -> {
            if (validerFormulaire()) {
                enregistrerReservation();
            }
        });

        // Actions pour les ComboBox d'horaires - uniquement pour la validation visuelle
        heureDebutCombo.setOnAction(e -> validerHoraires());
        minuteDebutCombo.setOnAction(e -> validerHoraires());
        heureFinCombo.setOnAction(e -> validerHoraires());
        minuteFinCombo.setOnAction(e -> validerHoraires());
    }

    /**
     * Crée des instances d'Horaire à partir des valeurs des ComboBox
     * Cette méthode n'est appelée qu'après la validation du formulaire
     * 
     * @return un tableau contenant les deux horaires (début et fin), ou null si une
     *         valeur est manquante ou invalide
     */
    private Horaire[] creerHoraires() {
        try {
            // Vérifier si toutes les valeurs sont sélectionnées
            if (heureDebutCombo.getValue() == null || minuteDebutCombo.getValue() == null ||
                    heureFinCombo.getValue() == null || minuteFinCombo.getValue() == null) {
                afficherErreur("Veuillez sélectionner une heure de début et de fin complète.");
                return null; // Retourner null si une valeur est manquante
            }

            int heureDebut = Integer.parseInt(heureDebutCombo.getValue());
            int minuteDebut = Integer.parseInt(minuteDebutCombo.getValue());
            int heureFin = Integer.parseInt(heureFinCombo.getValue());
            int minuteFin = Integer.parseInt(minuteFinCombo.getValue());

            // Création des objets Horaire uniquement à ce moment
            Horaire horaireDebut = new Horaire(heureDebut, minuteDebut);
            Horaire horaireFin = new Horaire(heureFin, minuteFin);

            // Vérification supplémentaire que l'heure de fin est après l'heure de début
            if (horaireFin.toMinutes() <= horaireDebut.toMinutes()) {
                afficherErreur("L'heure de fin doit être postérieure à l'heure de début.");
                return null;
            }

            return new Horaire[] { horaireDebut, horaireFin };
        } catch (NumberFormatException e) {
            afficherErreur("Erreur lors de la lecture des horaires.");
            return null; // Retourner null en cas d'erreur de parsing
        } catch (IllegalArgumentException e) {
            afficherErreur("Erreur dans les horaires saisis: " + e.getMessage());
            return null; // Retourner null si Horaire lève une exception
        }
    }

    /**
     * Configure l'accessibilité des composants
     */
    private void configurerAccessibilite() {
        // Association des labels avec leurs contrôles correspondants
        coursLabel.setLabelFor(coursField);
        dateLabel.setLabelFor(datePicker);
        horaireLabel.setLabelFor(heureDebutCombo);

        // Focus initial sur le champ titre
        coursField.requestFocus();

        // Définition de l'ordre de tabulation
        coursField.setFocusTraversable(true);
        datePicker.setFocusTraversable(true);
        heureDebutCombo.setFocusTraversable(true);
        minuteDebutCombo.setFocusTraversable(true);
        heureFinCombo.setFocusTraversable(true);
        minuteFinCombo.setFocusTraversable(true);
        annulerButton.setFocusTraversable(true);
        enregistrerButton.setFocusTraversable(true);
    }

    /**
     * Valide que l'heure de fin est supérieure à l'heure de début
     * Travaille uniquement avec les valeurs des ComboBox, sans créer d'objets
     * Horaire
     */
    private void validerHoraires() {
        try {
            // Ne rien faire si une des valeurs n'est pas sélectionnée
            if (heureDebutCombo.getValue() == null || minuteDebutCombo.getValue() == null ||
                    heureFinCombo.getValue() == null || minuteFinCombo.getValue() == null) {
                return;
            }

            // On récupère les valeurs sélectionnées
            int heureDebut = Integer.parseInt(heureDebutCombo.getValue());
            int minuteDebut = Integer.parseInt(minuteDebutCombo.getValue());
            int heureFin = Integer.parseInt(heureFinCombo.getValue());
            int minuteFin = Integer.parseInt(minuteFinCombo.getValue());

            int totalMinutesDebut = heureDebut * 60 + minuteDebut;
            int totalMinutesFin = heureFin * 60 + minuteFin;

            // Si l'heure de fin est avant l'heure de début, on ajuste
            if (totalMinutesFin <= totalMinutesDebut) {
                heureFin = heureDebut + 1;
                if (heureFin > 20)
                    heureFin = 20;

                // Mise à jour seulement de la ComboBox, pas d'objet Horaire créé
                // Utiliser Platform.runLater pour éviter les problèmes de mise à jour pendant
                // l'événement
                final int finalHeureFin = heureFin;
                javafx.application.Platform.runLater(() -> {
                    heureFinCombo.setValue(String.format("%02d", finalHeureFin));
                    minuteFinCombo.setValue(minuteDebutCombo.getValue());
                });
            }
        } catch (Exception e) {
            // Ignorer les erreurs de conversion
        }
    }

    /**
     * Valide le formulaire avant soumission
     * 
     * @return true si le formulaire est valide, false sinon
     */
    private boolean validerFormulaire() {
        // Vérifier que le titre est renseigné
        if (coursField.getText().trim().isEmpty()) {
            afficherErreur("Veuillez saisir le titre de la réservation.");
            coursField.requestFocus();
            return false;
        }

        // Vérifier que la date est renseignée
        if (datePicker.getValue() == null) {
            afficherErreur("Veuillez sélectionner une date.");
            datePicker.requestFocus();
            return false;
        }

        // Vérifier que tous les horaires sont sélectionnés
        if (heureDebutCombo.getValue() == null || minuteDebutCombo.getValue() == null ||
                heureFinCombo.getValue() == null || minuteFinCombo.getValue() == null) {
            afficherErreur("Veuillez sélectionner une heure de début et de fin complète.");
            // Mettre le focus sur le premier ComboBox vide
            if (heureDebutCombo.getValue() == null)
                heureDebutCombo.requestFocus();
            else if (minuteDebutCombo.getValue() == null)
                minuteDebutCombo.requestFocus();
            else if (heureFinCombo.getValue() == null)
                heureFinCombo.requestFocus();
            else
                minuteFinCombo.requestFocus();
            return false;
        }

        // Vérification finale des horaires (fin > début)
        try {
            int heureDebut = Integer.parseInt(heureDebutCombo.getValue());
            int minuteDebut = Integer.parseInt(minuteDebutCombo.getValue());
            int heureFin = Integer.parseInt(heureFinCombo.getValue());
            int minuteFin = Integer.parseInt(minuteFinCombo.getValue());
            if ((heureFin * 60 + minuteFin) <= (heureDebut * 60 + minuteDebut)) {
                afficherErreur("L'heure de fin doit être postérieure à l'heure de début.");
                heureFinCombo.requestFocus();
                return false;
            }
        } catch (NumberFormatException e) {
            afficherErreur("Erreur dans la sélection des horaires.");
            return false;
        }

        return true;
    }

    /**
     * Affiche un message d'erreur dans une boîte de dialogue
     */
    private void afficherErreur(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur de saisie");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Réinitialise le formulaire à ses valeurs par défaut
     */
    private void reinitialiserFormulaire() {
        coursField.clear();
        datePicker.setValue(LocalDate.now());

        // Réinitialisation des ComboBox avec les valeurs par défaut
        heureDebutCombo.setValue("08");
        minuteDebutCombo.setValue("00");
        heureFinCombo.setValue("09");
        minuteFinCombo.setValue("00");

        coursField.requestFocus(); // Focus sur le champ titre après réinitialisation
    }

    /**
     * Enregistre la réservation à partir des données du formulaire
     */
    private void enregistrerReservation() {
        // Création des objets Horaire uniquement au moment de l'enregistrement
        Horaire[] horaires = creerHoraires();
        // Si creerHoraires retourne null, une erreur a déjà été affichée
        if (horaires == null) {
            return;
        }
        Horaire horaireDebut = horaires[0];
        Horaire horaireFin = horaires[1];

        // Récupération des autres valeurs
        String titreReservation = coursField.getText().trim();
        LocalDate date = datePicker.getValue();

        // Formatage de la date pour affichage
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dateFormatee = date.format(formatter);

        // Construction du message de confirmation
        String message = "Réservation enregistrée :\n" +
                "Titre : " + titreReservation + "\n" +
                "Date : " + dateFormatee + "\n" +
                "Horaire : " + horaireDebut + " à " + horaireFin;

        // Affichage de la confirmation
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Réservation enregistrée");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();

        // Réinitialisation du formulaire
        reinitialiserFormulaire();
    }

    /**
     * Récupère l'horaire de début
     * 
     * @return Un nouvel objet Horaire représentant l'heure de début, ou null si non
     *         sélectionné
     */
    public Horaire getHoraireDebut() {
        try {
            if (heureDebutCombo.getValue() == null || minuteDebutCombo.getValue() == null)
                return null;
            int heure = Integer.parseInt(heureDebutCombo.getValue());
            int minute = Integer.parseInt(minuteDebutCombo.getValue());
            return new Horaire(heure, minute);
        } catch (Exception e) {
            return null; // Retourner null en cas d'erreur
        }
    }

    /**
     * Récupère l'horaire de fin
     * 
     * @return Un nouvel objet Horaire représentant l'heure de fin, ou null si non
     *         sélectionné
     */
    public Horaire getHoraireFin() {
        try {
            if (heureFinCombo.getValue() == null || minuteFinCombo.getValue() == null)
                return null;
            int heure = Integer.parseInt(heureFinCombo.getValue());
            int minute = Integer.parseInt(minuteFinCombo.getValue());
            return new Horaire(heure, minute);
        } catch (Exception e) {
            return null; // Retourner null en cas d'erreur
        }
    }

    /**
     * Récupère la date sélectionnée
     * 
     * @return La date sélectionnée
     */
    public LocalDate getDateSelectionnee() {
        return datePicker.getValue();
    }

    /**
     * Récupère le titre de la réservation
     * 
     * @return Le titre saisi
     */
    public String getTitreReservation() {
        return coursField.getText();
    }
}