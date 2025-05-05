package modele;
public class Reservation {
    private PlageHoraire chPlageHoraire;
    private String chTtitre;
    private String chDate;

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
