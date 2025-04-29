package vue;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

/**
 * Classe principale pour l'interface graphique de l'application
 * Elle contient un calendrier à gauche et un formulaire de réservation à droite
 */
public class HBoxRoot extends HBox {

    private VBoxCalendrier calendrier;
    private GridPaneFormulaireReservation formulaire;

    /**
     * Constructeur de la classe HBoxRoot
     */
    public HBoxRoot() {
        super(20); // Espacement horizontal de 20 pixels entre les éléments

        // Configuration du conteneur principal
        setPadding(new Insets(15));
        setAlignment(Pos.CENTER);
        getStyleClass().add("main-container");

        // Création du calendrier
        calendrier = new VBoxCalendrier();

        // Création du formulaire de réservation
        formulaire = new GridPaneFormulaireReservation();

        // Ajout des composants au conteneur HBox
        getChildren().addAll(calendrier, formulaire);

        // Configuration des contraintes de redimensionnement
        HBox.setHgrow(calendrier, Priority.ALWAYS);
        HBox.setHgrow(formulaire, Priority.ALWAYS);

        // Ajustement des largeurs préférées
        calendrier.setPrefWidth(400);
        formulaire.setPrefWidth(350);
    }

    /**
     * Renvoie le calendrier
     * 
     * @return L'instance de VBoxCalendrier
     */
    public VBoxCalendrier getCalendrier() {
        return calendrier;
    }

    /**
     * Renvoie le formulaire de réservation
     * 
     * @return L'instance de GridPaneFormulaireReservation
     */
    public GridPaneFormulaireReservation getFormulaire() {
        return formulaire;
    }
}