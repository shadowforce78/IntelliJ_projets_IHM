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
 * Classe représentant le formulaire de réservation
 * Permet de saisir les informations pour une nouvelle réservation
 */
public class GridPaneFormulaireReservation extends GridPane {

    // Composants du formulaire
    private Label titreLabel;
    private Label dateLabel;
    private Label coursLabel;
    private Label horaireLabel;
    private Label typeReservationLabel;

    private DatePicker datePicker;
    private TextField coursField;

    // ComboBox pour sélectionner les horaires
    private ComboBox<Integer> heureDebutCombo;
    private ComboBox<Integer> minuteDebutCombo;
    private ComboBox<Integer> heureFinCombo;
    private ComboBox<Integer> minuteFinCombo;

    // Horaires en tant qu'objets du modèle
    private Horaire horaireDebut;
    private Horaire horaireFin;

    private ToggleGroup typeReservationGroup;
    private RadioButton presentielRadio;
    private RadioButton distancielRadio;

    private Button annulerButton;
    private Button enregistrerButton;

    /**
     * Constructeur de la classe GridPaneFormulaireReservation
     */
    public GridPaneFormulaireReservation() {
        // Configuration de base du GridPane
        setPadding(new Insets(20));
        setHgap(10);
        setVgap(15);
        setAlignment(Pos.TOP_CENTER);
        getStyleClass().add("formulaire-container");

        // Initialisation des objets Horaire par défaut (8h00 à 9h00)
        horaireDebut = new Horaire(8, 0);
        horaireFin = new Horaire(9, 0);

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
        titreLabel = new Label("Formulaire de réservation");
        titreLabel.setFont(Font.font("System", FontWeight.BOLD, 16));
        titreLabel.getStyleClass().add("titre-formulaire");

        dateLabel = new Label("_Date :");
        dateLabel.setMnemonicParsing(true);

        coursLabel = new Label("_Nom du cours :");
        coursLabel.setMnemonicParsing(true);

        horaireLabel = new Label("_Horaire :");
        horaireLabel.setMnemonicParsing(true);

        typeReservationLabel = new Label("T_ype de réservation :");
        typeReservationLabel.setMnemonicParsing(true);

        // Initialisation des champs de saisie
        datePicker = new DatePicker(LocalDate.now());
        datePicker.setPromptText("Sélectionnez une date");
        datePicker.setPrefWidth(200);

        coursField = new TextField();
        coursField.setPromptText("Entrez le nom du cours");
        coursField.setPrefWidth(200);

        // Initialisation des combos pour les horaires
        heureDebutCombo = new ComboBox<>();
        for (int i = 8; i <= 20; i++) {
            heureDebutCombo.getItems().add(i);
        }
        heureDebutCombo.setValue(horaireDebut.getHeure());
        heureDebutCombo.setPrefWidth(70);

        minuteDebutCombo = new ComboBox<>();
        minuteDebutCombo.getItems().addAll(0, 15, 30, 45);
        minuteDebutCombo.setValue(horaireDebut.getMinutes());
        minuteDebutCombo.setPrefWidth(70);

        heureFinCombo = new ComboBox<>();
        for (int i = 8; i <= 20; i++) {
            heureFinCombo.getItems().add(i);
        }
        heureFinCombo.setValue(horaireFin.getHeure());
        heureFinCombo.setPrefWidth(70);

        minuteFinCombo = new ComboBox<>();
        minuteFinCombo.getItems().addAll(0, 15, 30, 45);
        minuteFinCombo.setValue(horaireFin.getMinutes());
        minuteFinCombo.setPrefWidth(70);

        // Initialisation des boutons radio
        typeReservationGroup = new ToggleGroup();

        presentielRadio = new RadioButton("Présentiel");
        presentielRadio.setToggleGroup(typeReservationGroup);
        presentielRadio.setSelected(true);

        distancielRadio = new RadioButton("Distanciel");
        distancielRadio.setToggleGroup(typeReservationGroup);

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
        add(titreLabel, 0, 0, 2, 1);

        // Champ Date
        add(dateLabel, 0, 1);
        add(datePicker, 1, 1);

        // Champ Nom du cours
        add(coursLabel, 0, 2);
        add(coursField, 1, 2);

        // Champ Horaire
        add(horaireLabel, 0, 3);

        HBox horaireDebutBox = new HBox(5);
        horaireDebutBox.getChildren().addAll(heureDebutCombo, new Label("h"), minuteDebutCombo);

        HBox horaireFinBox = new HBox(5);
        horaireFinBox.getChildren().addAll(heureFinCombo, new Label("h"), minuteFinCombo);

        HBox horaireCompletBox = new HBox(10);
        horaireCompletBox.getChildren().addAll(horaireDebutBox, new Label("à"), horaireFinBox);

        add(horaireCompletBox, 1, 3);

        // Champ Type de réservation
        add(typeReservationLabel, 0, 4);

        HBox typeReservationBox = new HBox(20);
        typeReservationBox.getChildren().addAll(presentielRadio, distancielRadio);

        add(typeReservationBox, 1, 4);

        // Boutons d'action
        HBox buttonsBox = new HBox(15);
        buttonsBox.setAlignment(Pos.CENTER_RIGHT);
        buttonsBox.getChildren().addAll(annulerButton, enregistrerButton);

        add(buttonsBox, 0, 5, 2, 1);

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

        // Mise à jour des objets Horaire lors de la sélection des valeurs
        heureDebutCombo.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                try {
                    horaireDebut.setHeure(newVal);
                    validerHoraires();
                } catch (IllegalArgumentException e) {
                    // Restaurer l'ancienne valeur en cas d'erreur
                    heureDebutCombo.setValue(oldVal);
                }
            }
        });

        minuteDebutCombo.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                try {
                    // Convertir minutes en quarts d'heure (0, 1, 2, 3)
                    int quartHeure = newVal / 15;
                    horaireDebut.setQuartHeure(quartHeure);
                    validerHoraires();
                } catch (IllegalArgumentException e) {
                    // Restaurer l'ancienne valeur en cas d'erreur
                    minuteDebutCombo.setValue(oldVal);
                }
            }
        });

        heureFinCombo.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                try {
                    horaireFin.setHeure(newVal);
                    validerHoraires();
                } catch (IllegalArgumentException e) {
                    // Restaurer l'ancienne valeur en cas d'erreur
                    heureFinCombo.setValue(oldVal);
                }
            }
        });

        minuteFinCombo.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                try {
                    // Convertir minutes en quarts d'heure (0, 1, 2, 3)
                    int quartHeure = newVal / 15;
                    horaireFin.setQuartHeure(quartHeure);
                    validerHoraires();
                } catch (IllegalArgumentException e) {
                    // Restaurer l'ancienne valeur en cas d'erreur
                    minuteFinCombo.setValue(oldVal);
                }
            }
        });
    }

    /**
     * Configure l'accessibilité des composants
     */
    private void configurerAccessibilite() {
        // Association des labels avec leurs contrôles correspondants
        dateLabel.setLabelFor(datePicker);
        coursLabel.setLabelFor(coursField);
        horaireLabel.setLabelFor(heureDebutCombo);
        typeReservationLabel.setLabelFor(presentielRadio);

        // Focus initial sur le champ de date
        datePicker.requestFocus();

        // Définition de l'ordre de tabulation
        datePicker.setFocusTraversable(true);
        coursField.setFocusTraversable(true);
        heureDebutCombo.setFocusTraversable(true);
        minuteDebutCombo.setFocusTraversable(true);
        heureFinCombo.setFocusTraversable(true);
        minuteFinCombo.setFocusTraversable(true);
        presentielRadio.setFocusTraversable(true);
        distancielRadio.setFocusTraversable(true);
        annulerButton.setFocusTraversable(true);
        enregistrerButton.setFocusTraversable(true);
    }

    /**
     * Valide que l'heure de fin est supérieure à l'heure de début
     */
    private void validerHoraires() {
        // Utilisation de la méthode toMinutes() de la classe Horaire
        int totalMinutesDebut = horaireDebut.toMinutes();
        int totalMinutesFin = horaireFin.toMinutes();

        if (totalMinutesFin <= totalMinutesDebut) {
            // Si l'heure de fin est antérieure ou égale à l'heure de début
            // On met l'heure de fin à l'heure de début + 1 heure
            int nouvHeure = horaireDebut.getHeure() + 1;
            if (nouvHeure > 20)
                nouvHeure = 20;

            // Mise à jour de l'horaire de fin
            horaireFin = new Horaire(nouvHeure, horaireDebut.getMinutes());

            // Mise à jour des ComboBox pour refléter les changements
            // Utiliser Platform.runLater pour éviter des problèmes de mise à jour pendant
            // le traitement des événements
            javafx.application.Platform.runLater(() -> {
                heureFinCombo.setValue(horaireFin.getHeure());
                minuteFinCombo.setValue(horaireFin.getMinutes());
            });
        }
    }

    /**
     * Valide le formulaire avant soumission
     * 
     * @return true si le formulaire est valide, false sinon
     */
    private boolean validerFormulaire() {
        // Vérifier que la date est renseignée
        if (datePicker.getValue() == null) {
            afficherErreur("Veuillez sélectionner une date.");
            datePicker.requestFocus();
            return false;
        }

        // Vérifier que le nom du cours est renseigné
        if (coursField.getText().trim().isEmpty()) {
            afficherErreur("Veuillez saisir le nom du cours.");
            coursField.requestFocus();
            return false;
        }

        // Vérification des horaires déjà effectuée dans validerHoraires()

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
        datePicker.setValue(LocalDate.now());
        coursField.clear();

        // Réinitialisation des horaires
        horaireDebut = new Horaire(8, 0);
        horaireFin = new Horaire(9, 0);

        // Mise à jour des ComboBox
        heureDebutCombo.setValue(horaireDebut.getHeure());
        minuteDebutCombo.setValue(horaireDebut.getMinutes());
        heureFinCombo.setValue(horaireFin.getHeure());
        minuteFinCombo.setValue(horaireFin.getMinutes());

        presentielRadio.setSelected(true);

        datePicker.requestFocus();
    }

    /**
     * Enregistre la réservation à partir des données du formulaire
     */
    private void enregistrerReservation() {
        // Récupération des valeurs
        LocalDate date = datePicker.getValue();
        String nomCours = coursField.getText().trim();
        String typeReservation = presentielRadio.isSelected() ? "Présentiel" : "Distanciel";

        // Formatage de la date pour affichage
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dateFormatee = date.format(formatter);

        // Construction du message de confirmation en utilisant les objets Horaire
        String message = "Réservation enregistrée :\n" +
                "Date : " + dateFormatee + "\n" +
                "Cours : " + nomCours + "\n" +
                "Horaire : " + horaireDebut + " à " + horaireFin + "\n" +
                "Type : " + typeReservation;

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
     * @return L'objet Horaire représentant l'heure de début
     */
    public Horaire getHoraireDebut() {
        return horaireDebut;
    }

    /**
     * Récupère l'horaire de fin
     * 
     * @return L'objet Horaire représentant l'heure de fin
     */
    public Horaire getHoraireFin() {
        return horaireFin;
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
     * Récupère le nom du cours
     * 
     * @return Le nom du cours
     */
    public String getNomCours() {
        return coursField.getText();
    }

    /**
     * Indique si la réservation est en présentiel
     * 
     * @return true si la réservation est en présentiel, false si elle est à
     *         distance
     */
    public boolean estPresentiel() {
        return presentielRadio.isSelected();
    }
}