package modele;
public class Reservation {
    private PlageHoraire chPlageHoraire;
    private String chTtitre;
    private String chDate;
    private int niveau;

    public Reservation(String parTitre, PlageHoraire parPlageHoraire, String parDate) {
        this.chPlageHoraire = parPlageHoraire;
        this.chTtitre = parTitre;
        this.chDate = parDate;
    }

    public PlageHoraire getPlageHoraire() {
        return chPlageHoraire;
    }

    public String getTitre() {
        return chTtitre;
    }

    public String getDate() {
        return chDate;
    }

    /**
     * Retourne le niveau de la réservation
     * @return le niveau
     */
    public int getNiveau() {
        return niveau;
    }

    /**
     * Définit le niveau de la réservation
     * @param niveau le niveau à définir
     */
    public void setNiveau(int niveau) {
        this.niveau = niveau;
    }

    public String toString() {
        return chTtitre + " le " + chDate + " de " + chPlageHoraire;
    }

    public int compareTo(Reservation autre) {
        int compareDate = this.chDate.compareTo(autre.getDate());
        if (compareDate != 0) {
            return compareDate;
        }
        return chPlageHoraire.compareTo(autre.getPlageHoraire());
    }
}
