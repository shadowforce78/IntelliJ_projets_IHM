module org.example.intellij_projets_ihm {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens org.example.intellij_projets_ihm to javafx.fxml;
    exports org.example.intellij_projets_ihm;
    exports vue;
    exports modele;
}