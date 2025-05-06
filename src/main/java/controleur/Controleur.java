package controleur;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import modele.*;
import vue.GridPaneFormulaireReservation;
import vue.HBoxRoot;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Controleur implements EventHandler {
    @Override
    public void handle(Event event) {
        Planning planning = HBoxRoot.getPlanning();
        GridPaneFormulaireReservation reservationPane = HBoxRoot.getFormulaire();

        System.out.println("DEBUG Controleur - handle: Événement reçu: " + event.getSource().getClass().getName());

        // la source de event est le bouton enregistrer du formulaire de réservation
        if (event.getSource() instanceof Button && ((Button) event.getSource()).getText().contains("Enregistrer")) {
            System.out.println("DEBUG Controleur - handle: Bouton Enregistrer cliqué");

            if (reservationPane.validerFormulaire()) {
                System.out.println("DEBUG Controleur - handle: Formulaire valide");

                // Récupérer les données du formulaire
                String titre = reservationPane.getCoursField().getText().trim();
                LocalDate date = reservationPane.getDatePicker().getValue();
                String dateFormatee = date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

                System.out.println("DEBUG Controleur - handle: Titre = " + titre);
                System.out.println("DEBUG Controleur - handle: Date = " + dateFormatee);

                // Récupérer le niveau sélectionné
                String niveau = "Débutant"; // Par défaut
                if (reservationPane.getNiveauMoyen().isSelected()) {
                    niveau = "Moyen";
                } else if (reservationPane.getNiveauAvance().isSelected()) {
                    niveau = "Avancé";
                } else if (reservationPane.getNiveauExpert().isSelected()) {
                    niveau = "Expert";
                }
                System.out.println("DEBUG Controleur - handle: Niveau = " + niveau);

                // Créer les horaires
                Horaire horaireDebut = new Horaire(
                        Integer.parseInt(reservationPane.getHeureDebutCombo().getValue()),
                        Integer.parseInt(reservationPane.getMinuteDebutCombo().getValue()));

                Horaire horaireFin = new Horaire(
                        Integer.parseInt(reservationPane.getHeureFinCombo().getValue()),
                        Integer.parseInt(reservationPane.getMinuteFinCombo().getValue()));

                System.out.println("DEBUG Controleur - handle: Horaire début = " + horaireDebut);
                System.out.println("DEBUG Controleur - handle: Horaire fin = " + horaireFin);

                try {
                    // Créer la plage horaire
                    System.out.println("DEBUG Controleur - handle: Création de la plage horaire");
                    PlageHoraire plageHoraire = new PlageHoraire(horaireDebut, horaireFin);

                    // Créer la réservation
                    System.out.println("DEBUG Controleur - handle: Création de la réservation");
                    Reservation reservation = new Reservation(titre, plageHoraire, dateFormatee);

                    // Ajouter la réservation au planning
                    System.out.println("DEBUG Controleur - handle: Ajout au planning");
                    Exception resultat = planning.ajout(reservation);

                    if (resultat == null) {
                        // La réservation a été ajoutée avec succès
                        System.out.println("DEBUG Controleur - handle: Réservation ajoutée avec succès");
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Réservation enregistrée");
                        alert.setHeaderText("Réservation créée avec succès !");
                        alert.setContentText(
                                "Titre: " + titre + "\n" +
                                        "Date: " + dateFormatee + "\n" +
                                        "Niveau: " + niveau + "\n" +
                                        "Horaire: " + horaireDebut + " à " + horaireFin);
                        alert.showAndWait();

                        // Réinitialiser le formulaire
                        reservationPane.annulerSaisie();
                    } else {
                        // Une erreur s'est produite lors de l'ajout
                        System.out.println(
                                "DEBUG Controleur - handle: Erreur lors de l'ajout - " + resultat.getMessage());
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Erreur d'enregistrement");
                        alert.setHeaderText("Impossible d'enregistrer la réservation");
                        alert.setContentText(resultat.getMessage());
                        alert.showAndWait();
                    }
                } catch (Exception e) {
                    System.out.println("DEBUG Controleur - handle: Exception lors de la création de la réservation - "
                            + e.getMessage());
                    e.printStackTrace();

                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur d'enregistrement");
                    alert.setHeaderText("Exception lors de la création de la réservation");
                    alert.setContentText(e.getMessage());
                    alert.showAndWait();
                }
            } else {
                System.out.println("DEBUG Controleur - handle: Formulaire non valide");
            }
        }
    }

    /**
     * Gère la sélection d'une date dans le calendrier
     * 
     * @param dateCalendrier La date sélectionnée dans le calendrier
     */
    public void handleDateSelection(DateCalendrier dateCalendrier) {
        System.out.println("DEBUG Controleur - handleDateSelection: Date sélectionnée = " +
                dateCalendrier.getJour() + "/" + dateCalendrier.getMois() + "/" + dateCalendrier.getAnnee());

        GridPaneFormulaireReservation reservationPane = HBoxRoot.getFormulaire();
        if (reservationPane == null) {
            System.out.println("DEBUG Controleur - handleDateSelection: ERREUR - formulaire null");
            return;
        }

        try {
            // Convertir DateCalendrier en LocalDate pour le DatePicker
            LocalDate selectedDate = LocalDate.of(
                    dateCalendrier.getAnnee(),
                    dateCalendrier.getMois(),
                    dateCalendrier.getJour());

            System.out.println("DEBUG Controleur - handleDateSelection: Date convertie en LocalDate = " + selectedDate);

            // Mettre à jour la date dans le formulaire
            reservationPane.setDate(selectedDate);
            System.out.println("DEBUG Controleur - handleDateSelection: Date mise à jour dans le formulaire");
        } catch (Exception e) {
            System.out.println(
                    "DEBUG Controleur - handleDateSelection: ERREUR lors de la conversion de date - " + e.getMessage());
            e.printStackTrace();
        }
    }
}
