package controleur;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import modele.Planning;
import modele.PlanningCollections;
import vue.GridPaneFormulaireReservation;
import vue.HBoxRoot;

public class Controleur implements EventHandler{
    @Override
    public void handle(Event event){
        Planning planning = HBoxRoot.getPlanning();
        GridPaneFormulaireReservation reservationPane = HBoxRoot.getFormulaire();

        // la source de event est un toggleButton du calendrier
        if (event.getSource() instanceof ToggleButton){
            // a completer question 5
        }

        // la source de event est le bouton enregistrer du formulaire de réservation
        if (event.getSource() instanceof Button){
            // a completer question 6
        }
    }
}
