package vue;

import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import modele.DateCalendrier;
import modele.Planning;
import modele.Reservation;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Classe représentant l'affichage du planning hebdomadaire
 * Affiche les réservations de la semaine dans une TableView
 */
public class VBoxAffichagePlanning extends VBox {

    private Label semaineTitreLabel;
    private TableView<Reservation> tableReservations;
    private int numeroSemaine;
    private int annee;

    /**
     * Constructeur de la classe VBoxAffichagePlanning
     */
    public VBoxAffichagePlanning() {
        super(15); // Espacement vertical de 15 pixels entre les éléments

        System.out.println("DEBUG VBoxAffichagePlanning - Constructeur: Initialisation du planning");

        // Configuration de base de la VBox
        setPadding(new Insets(15));
        setAlignment(Pos.TOP_CENTER);
        getStyleClass().add("planning-container");

        // Initialiser les composants
        initialiserComposants();

        // Utiliser la semaine courante comme semaine initiale
        LocalDate aujourdhui = LocalDate.now();
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        numeroSemaine = aujourdhui.get(weekFields.weekOfWeekBasedYear());
        annee = aujourdhui.getYear();

        // Mettre à jour le titre avec la semaine courante
        semaineTitreLabel.setText("Semaine " + numeroSemaine + " - " + annee);

        System.out.println(
                "DEBUG VBoxAffichagePlanning - Constructeur: Planning initialisé pour la semaine " + numeroSemaine);
    }

    /**
     * Initialise les composants de l'interface
     */
    private void initialiserComposants() {
        // Titre pour la semaine
        semaineTitreLabel = new Label("Semaine");
        semaineTitreLabel.setFont(Font.font("System", FontWeight.BOLD, 16));
        semaineTitreLabel.getStyleClass().add("titre-planning");

        // Création de la table des réservations
        tableReservations = new TableView<>();
        tableReservations.setPlaceholder(new Label("Aucune réservation pour cette semaine"));
        tableReservations.setEditable(false);
        tableReservations.setPrefHeight(400);

        // Création des colonnes
        // Colonne Date
        TableColumn<Reservation, String> dateColumn = new TableColumn<>("Date");
        dateColumn.setMinWidth(100);
        dateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDate()));
        dateColumn.setResizable(false);
        dateColumn.setSortable(false);

        // Colonne Cours/Titre
        TableColumn<Reservation, String> titreColumn = new TableColumn<>("Cours");
        titreColumn.setMinWidth(150);
        titreColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitre()));
        titreColumn.setResizable(false);
        titreColumn.setSortable(false);

        // Colonne Horaire
        TableColumn<Reservation, String> horaireColumn = new TableColumn<>("Horaire");
        horaireColumn.setMinWidth(200);
        horaireColumn.setCellValueFactory(
                cellData -> new SimpleStringProperty(cellData.getValue().getPlageHoraire().toString()));
        horaireColumn.setResizable(false);
        horaireColumn.setSortable(false);

        // Ajouter les colonnes à la table
        tableReservations.getColumns().addAll(dateColumn, titreColumn, horaireColumn);

        // Ajouter les composants à la VBox
        getChildren().addAll(semaineTitreLabel, tableReservations);
    }

    /**
     * Met à jour l'affichage du planning avec les réservations de la semaine
     * spécifiée
     * 
     * @param planning      Le planning contenant toutes les réservations
     * @param numeroSemaine Le numéro de la semaine à afficher
     * @param annee         L'année concernée
     */
    public void afficherReservationsSemaine(Planning planning, int numeroSemaine, int annee) {
        this.numeroSemaine = numeroSemaine;
        this.annee = annee;

        // Mettre à jour le titre
        semaineTitreLabel.setText("Semaine " + numeroSemaine + " - " + annee);

        System.out.println(
                "DEBUG VBoxAffichagePlanning - afficherReservationsSemaine: Affichage des réservations pour la semaine "
                        + numeroSemaine);

        // Récupérer les réservations
        List<Reservation> reservationsSemaine = filtrerReservationsSemaine(planning, numeroSemaine, annee);

        // Mettre à jour la table
        tableReservations.getItems().clear();
        if (!reservationsSemaine.isEmpty()) {
            tableReservations.getItems().addAll(reservationsSemaine);
            System.out.println("DEBUG VBoxAffichagePlanning - afficherReservationsSemaine: "
                    + reservationsSemaine.size() + " réservations trouvées");
        } else {
            System.out.println("DEBUG VBoxAffichagePlanning - afficherReservationsSemaine: Aucune réservation trouvée");
        }
    }

    /**
     * Filtre les réservations pour ne garder que celles de la semaine spécifiée
     * 
     * @param planning      Le planning contenant toutes les réservations
     * @param numeroSemaine Le numéro de la semaine à filtrer
     * @param annee         L'année concernée
     * @return Une liste de réservations filtrées pour la semaine spécifiée
     */
    private List<Reservation> filtrerReservationsSemaine(Planning planning, int numeroSemaine, int annee) {
        List<Reservation> reservationsSemaine = new ArrayList<>();

        // Récupérer toutes les réservations du planning
        Reservation[] toutesReservations = planning.getReservations();

        if (toutesReservations != null) {
            for (Reservation reservation : toutesReservations) {
                if (reservation != null) {
                    try {
                        // Convertir la date de la réservation en LocalDate
                        String dateStr = reservation.getDate();
                        LocalDate dateReservation = LocalDate.parse(
                                dateStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));

                        // Récupérer le numéro de semaine de la date
                        WeekFields weekFields = WeekFields.of(Locale.getDefault());
                        int semaineReservation = dateReservation.get(weekFields.weekOfWeekBasedYear());
                        int anneeReservation = dateReservation.getYear();

                        // Si la réservation est dans la semaine demandée, l'ajouter à la liste
                        if (semaineReservation == numeroSemaine && anneeReservation == annee) {
                            reservationsSemaine.add(reservation);
                        }
                    } catch (Exception e) {
                        System.out.println(
                                "DEBUG VBoxAffichagePlanning - filtrerReservationsSemaine: Erreur lors du parsing de la date: "
                                        + e.getMessage());
                    }
                }
            }
        }

        return reservationsSemaine;
    }

    /**
     * Met à jour l'affichage du planning pour la semaine correspondant à la date
     * spécifiée
     * 
     * @param planning Le planning contenant toutes les réservations
     * @param date     La date dont on veut afficher la semaine
     */
    public void afficherSemainePourDate(Planning planning, DateCalendrier date) {
        // Convertir la date en LocalDate
        LocalDate localDate = LocalDate.of(date.getAnnee(), date.getMois(), date.getJour());

        // Récupérer le numéro de semaine
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        int semaine = localDate.get(weekFields.weekOfWeekBasedYear());
        int annee = localDate.getYear();

        System.out.println("DEBUG VBoxAffichagePlanning - afficherSemainePourDate: Affichage de la semaine " +
                semaine + " pour la date " + date.getJour() + "/" + date.getMois() + "/" + date.getAnnee());

        // Mettre à jour l'affichage
        afficherReservationsSemaine(planning, semaine, annee);
    }

    /**
     * Met à jour l'affichage avec les réservations actuelles
     * 
     * @param planning Le planning contenant toutes les réservations
     */
    public void actualiser(Planning planning) {
        System.out.println("DEBUG VBoxAffichagePlanning - actualiser: Mise à jour de l'affichage du planning");
        afficherReservationsSemaine(planning, numeroSemaine, annee);
    }

    /**
     * Affiche la semaine courante
     * 
     * @param planning Le planning contenant toutes les réservations
     */
    public void afficherSemaineCourante(Planning planning) {
        // Obtenir la date d'aujourd'hui
        LocalDate aujourdhui = LocalDate.now();

        // Récupérer le numéro de semaine
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        int semaine = aujourdhui.get(weekFields.weekOfWeekBasedYear());
        int annee = aujourdhui.getYear();

        System.out.println(
                "DEBUG VBoxAffichagePlanning - afficherSemaineCourante: Affichage de la semaine courante " + semaine);

        // Mettre à jour l'affichage
        afficherReservationsSemaine(planning, semaine, annee);
    }
}
