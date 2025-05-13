package vue;

import controleur.Controleur;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import modele.Planning;
import modele.PlanningCollections;

/**
 * Classe principale pour l'interface graphique de l'application
 * Elle contient un calendrier à gauche et un formulaire de réservation à droite
 */
public class HBoxRoot extends HBox { // Planning, controleur et vue calendrierPane et reservationPane
    private static Planning planning;
    private static Controleur controller;
    private static VBoxCalendrier calendrier;
    private static GridPaneFormulaireReservation formulaire;
    private static VBoxAffichagePlanning planningView;

    /**
     * Constructeur de la classe HBoxRoot
     */
    public HBoxRoot() {
        super(20); // Espacement horizontal de 20 pixels entre les éléments

        System.out.println("DEBUG HBoxRoot - Constructeur: Initialisation de l'application");

        planning = new Planning(); // Initialisation du planning
        System.out.println("DEBUG HBoxRoot - Constructeur: Planning créé");

        calendrier = new VBoxCalendrier(); // Initialisation du calendrier
        System.out.println("DEBUG HBoxRoot - Constructeur: VBoxCalendrier créé");
        formulaire = new GridPaneFormulaireReservation(); // Initialisation du formulaire de réservation
        System.out.println("DEBUG HBoxRoot - Constructeur: GridPaneFormulaireReservation créé");

        planningView = new VBoxAffichagePlanning(); // Initialisation de la vue planning
        System.out.println("DEBUG HBoxRoot - Constructeur: VBoxAffichagePlanning créé");

        controller = new Controleur();
        System.out.println("DEBUG HBoxRoot - Constructeur: Controleur créé");

        // Connecter le contrôleur aux vues
        calendrier.setControleur(controller);
        System.out.println("DEBUG HBoxRoot - Constructeur: Contrôleur associé au calendrier");
        formulaire.setControleur(controller);
        System.out.println("DEBUG HBoxRoot - Constructeur: Contrôleur associé au formulaire");

        // Initialiser le planning avec les données actuelles
        planningView.afficherSemaineCourante(planning);
        System.out.println("DEBUG HBoxRoot - Constructeur: Planning initialisé avec la semaine courante");

        // Configuration du conteneur principal
        setPadding(new Insets(15));
        setAlignment(Pos.CENTER);
        getStyleClass().add("main-container"); // Ajout des composants au conteneur HBox
        getChildren().addAll(calendrier, formulaire, planningView);

        // Configuration des contraintes de redimensionnement
        HBox.setHgrow(calendrier, Priority.ALWAYS);
        HBox.setHgrow(formulaire, Priority.ALWAYS);
        HBox.setHgrow(planningView, Priority.ALWAYS);

        // Ajustement des largeurs préférées
        calendrier.setPrefWidth(300);
        formulaire.setPrefWidth(300);
        planningView.setPrefWidth(400);

        System.out.println("DEBUG HBoxRoot - Constructeur: Interface graphique initialisée");
    }

    /**
     * Renvoie le calendrier
     * 
     * @return L'instance de VBoxCalendrier
     */
    public static VBoxCalendrier getCalendrier() {
        return calendrier;
    }

    /**
     * Renvoie le formulaire de réservation
     * 
     * @return L'instance de GridPaneFormulaireReservation
     */
    public static GridPaneFormulaireReservation getFormulaire() {
        return formulaire;
    }

    public static Planning getPlanning() {
        return planning;
    }

    public static Controleur getControleur() {
        return controller;
    }

    /**
     * Renvoie la vue du planning hebdomadaire
     * 
     * @return L'instance de VBoxAffichagePlanning
     */
    public static VBoxAffichagePlanning getPlanningView() {
        return planningView;
    }

}