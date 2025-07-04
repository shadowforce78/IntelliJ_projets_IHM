package vue;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import modele.Horaire;
import controleur.Controleur;
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
    private Label niveauLabel; // Label pour le niveau, non utilisé dans cette version

    private TextField coursField; // Champ pour le titre de la réservation
    private DatePicker datePicker;

    private RadioButton niveauDebutant;
    private RadioButton niveauMoyen;
    private RadioButton niveauAvance;
    private RadioButton niveauExpert;
    private ToggleGroup niveauGroup; // Groupe pour les radio buttons

    // ComboBox pour sélectionner les horaires
    private ComboBox<String> heureDebutCombo;
    private ComboBox<String> minuteDebutCombo;
    private ComboBox<String> heureFinCombo;
    private ComboBox<String> minuteFinCombo;

    private Button annulerButton;
    private Button enregistrerButton;
    private Button afficherPlanningButton; // Nouveau bouton pour afficher le planning

    private Controleur controleur;

    /**
     * Constructeur de la classe GridPaneFormulaireReservation
     */
    public GridPaneFormulaireReservation() {

        this.setGridLinesVisible(false);
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

        // Initialisation des boutons radio pour le niveau
        niveauLabel = new Label("_Niveau :");
        niveauLabel.setMnemonicParsing(true);

        // Création du groupe pour les radio buttons
        niveauGroup = new ToggleGroup();

        niveauDebutant = new RadioButton("Débutant");
        niveauDebutant.setToggleGroup(niveauGroup);

        niveauMoyen = new RadioButton("Moyen");
        niveauMoyen.setToggleGroup(niveauGroup);

        niveauAvance = new RadioButton("Avancé");
        niveauAvance.setToggleGroup(niveauGroup);

        niveauExpert = new RadioButton("Expert");
        niveauExpert.setToggleGroup(niveauGroup);

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

        // Initialisation du bouton pour afficher le planning
        afficherPlanningButton = new Button("_Planning");
        afficherPlanningButton.setMnemonicParsing(true);
        afficherPlanningButton.getStyleClass().add("bouton-planning");
        afficherPlanningButton.setTooltip(new Tooltip("Afficher les réservations du planning"));
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

        // Champ Niveau
        add(niveauLabel, 0, 3);
        HBox niveauBox = new HBox(10);
        niveauBox.getChildren().addAll(niveauDebutant, niveauMoyen, niveauAvance, niveauExpert);
        niveauBox.setAlignment(Pos.CENTER_LEFT);
        add(niveauBox, 1, 3);

        // Alignement à gauche pour le niveau
        GridPane.setHalignment(niveauLabel, HPos.LEFT);
        GridPane.setHalignment(niveauBox, HPos.LEFT);

        // Niveau par défaut
        niveauDebutant.setSelected(true);

        // Champ Horaire - à la ligne suivante
        add(horaireLabel, 0, 4);

        HBox horaireDebutBox = new HBox(5);
        horaireDebutBox.getChildren().addAll(heureDebutCombo, new Label("h"), minuteDebutCombo);

        HBox horaireFinBox = new HBox(5);
        horaireFinBox.getChildren().addAll(heureFinCombo, new Label("h"), minuteFinCombo);

        HBox horaireCompletBox = new HBox(15);
        horaireCompletBox.getChildren().addAll(horaireDebutBox, new Label("à"), horaireFinBox);

        add(horaireCompletBox, 1, 4);

        // Boutons d'action
        HBox buttonsBox = new HBox(15); // Réduit l'espacement à 15px pour accommoder le nouveau bouton
        buttonsBox.setAlignment(Pos.CENTER_RIGHT);
        buttonsBox.getChildren().addAll(afficherPlanningButton, annulerButton, enregistrerButton);

        add(buttonsBox, 0, 5, 2, 1); // Déplacé à la ligne 5

        // Espacement supplémentaire en bas du formulaire
        setPadding(new Insets(20, 20, 30, 20));
    }

    /**
     * Configure les actions et validations des composants
     */
    private void configurerActions() {
        // L'action du bouton enregistrer sera gérée par le contrôleur
        // On ne fait que la validation en temps réel
        annulerButton.setOnAction(e -> annulerSaisie());

        // Configuration du bouton Afficher Planning
        afficherPlanningButton.setOnAction(e -> afficherPlanning());

        // Validation en temps réel
        coursField.textProperty().addListener((obs, oldVal, newVal) -> {
            validerFormulaire();
        });

        // Gestion des changements d'horaire
        heureDebutCombo.setOnAction(e -> validerHoraires());
        minuteDebutCombo.setOnAction(e -> validerHoraires());
        heureFinCombo.setOnAction(e -> validerHoraires());
        minuteFinCombo.setOnAction(e -> validerHoraires());
    }

    /**
     * Configure l'accessibilité des composants
     */
    private void configurerAccessibilite() {
        // Association des labels aux contrôles
        coursLabel.setLabelFor(coursField);
        dateLabel.setLabelFor(datePicker);
        horaireLabel.setLabelFor(heureDebutCombo);
        niveauLabel.setLabelFor(niveauDebutant);

        // Descriptions pour l'accessibilité
        titreFormulaireLabel.setAccessibleText("Formulaire pour créer une nouvelle réservation");
        coursField.setAccessibleHelp("Saisissez le titre de votre réservation");
        datePicker.setAccessibleHelp("Sélectionnez la date de votre réservation");

        niveauDebutant.setAccessibleHelp("Niveau débutant pour votre cours");
        niveauMoyen.setAccessibleHelp("Niveau moyen pour votre cours");
        niveauAvance.setAccessibleHelp("Niveau avancé pour votre cours");
        niveauExpert.setAccessibleHelp("Niveau expert pour votre cours");
    }

    /**
     * Définit le contrôleur qui sera à l'écoute des actions sur le bouton
     * Enregistrer
     * 
     * @param controleur Le contrôleur à associer au bouton Enregistrer
     */
    public void setControleur(Controleur controleur) {
        this.controleur = controleur;
        System.out.println("DEBUG GridPaneFormulaireReservation - setControleur: Contrôleur défini");
        enregistrerButton.setOnAction(controleur);
        System.out.println(
                "DEBUG GridPaneFormulaireReservation - setControleur: Bouton Enregistrer associé au contrôleur");
    }

    /**
     * Met à jour la date dans le DatePicker
     * 
     * @param date La date à afficher dans le DatePicker
     */
    public void setDate(LocalDate date) {
        System.out.println("DEBUG GridPaneFormulaireReservation - setDate: Mise à jour de la date: " + date);
        datePicker.setValue(date);
    }

    /**
     * Valide le formulaire et active/désactive le bouton d'enregistrement en
     * conséquence
     * 
     * @return true si le formulaire est valide, false sinon
     */
    public boolean validerFormulaire() {
        boolean estValide = !coursField.getText().trim().isEmpty() && datePicker.getValue() != null
                && validerHoraires();

        enregistrerButton.setDisable(!estValide);
        return estValide;
    }

    /**
     * Valide les horaires sélectionnés
     * 
     * @return true si les horaires sont valides, false sinon
     */
    private boolean validerHoraires() {
        try {
            int heureDebut = Integer.parseInt(heureDebutCombo.getValue());
            int minuteDebut = Integer.parseInt(minuteDebutCombo.getValue());
            int heureFin = Integer.parseInt(heureFinCombo.getValue());
            int minuteFin = Integer.parseInt(minuteFinCombo.getValue());

            // Vérification que l'heure de fin est après l'heure de début
            boolean estValide = (heureFin > heureDebut) || (heureFin == heureDebut && minuteFin > minuteDebut);

            if (!estValide) {
                // Si les horaires ne sont pas valides, on désactive le bouton d'enregistrement
                enregistrerButton.setDisable(true);
            }

            return estValide;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Annule la saisie et réinitialise le formulaire
     */
    public void annulerSaisie() {
        coursField.clear();
        datePicker.setValue(LocalDate.now());
        heureDebutCombo.setValue("08");
        minuteDebutCombo.setValue("00");
        heureFinCombo.setValue("09");
        minuteFinCombo.setValue("00");
        niveauDebutant.setSelected(true);
    }

    // La méthode enregistrerReservation est maintenant gérée par le contrôleur
    // donc elle est supprimée

    // Getters pour les composants (si nécessaire pour les tests ou autres classes)
    @SuppressWarnings("exports")
    public TextField getCoursField() {
        return coursField;
    }

    @SuppressWarnings("exports")
    public DatePicker getDatePicker() {
        return datePicker;
    }

    @SuppressWarnings("exports")
    public Button getAnnulerButton() {
        return annulerButton;
    }

    @SuppressWarnings("exports")
    public Button getEnregistrerButton() {
        return enregistrerButton;
    }

    // Ajouter des getters pour les combos d'horaires
    @SuppressWarnings("exports")
    public ComboBox<String> getHeureDebutCombo() {
        return heureDebutCombo;
    }

    @SuppressWarnings("exports")
    public ComboBox<String> getMinuteDebutCombo() {
        return minuteDebutCombo;
    }

    @SuppressWarnings("exports")
    public ComboBox<String> getHeureFinCombo() {
        return heureFinCombo;
    }

    @SuppressWarnings("exports")
    public ComboBox<String> getMinuteFinCombo() {
        return minuteFinCombo;
    }

    // Getters pour accéder aux niveaux depuis l'extérieur
    @SuppressWarnings("exports")
    public RadioButton getNiveauDebutant() {
        return niveauDebutant;
    }

    @SuppressWarnings("exports")
    public RadioButton getNiveauMoyen() {
        return niveauMoyen;
    }

    @SuppressWarnings("exports")
    public RadioButton getNiveauAvance() {
        return niveauAvance;
    }

    @SuppressWarnings("exports")
    public RadioButton getNiveauExpert() {
        return niveauExpert;
    }

    @SuppressWarnings("exports")
    public ToggleGroup getNiveauGroup() {
        return niveauGroup;
    }

    /**
     * Affiche le planning hebdomadaire
     */
    private void afficherPlanning() {
        System.out.println("DEBUG GridPaneFormulaireReservation - afficherPlanning: Bouton Planning cliqué");

        // Récupérer et actualiser le planning
        modele.Planning planning = HBoxRoot.getPlanning();
        VBoxAffichagePlanning planningView = HBoxRoot.getPlanningView();

        // Actualiser l'affichage avec les réservations actuelles
        planningView.actualiser(planning);
        System.out.println("DEBUG GridPaneFormulaireReservation - afficherPlanning: Affichage du planning actualisé");
    }
}