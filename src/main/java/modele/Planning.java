package modele;
public class Planning {
    // Tablea d'objets de la classe Reservation
    private Reservation[] reservations;
    // Entier constant qui contient la taille du tableau
    private static final int TAILLE_TAB = 100;

    // Constructeur de la classe Planing
    public Planning() {
        reservations = new Reservation[TAILLE_TAB];
    }

    // Méthode qui retourne la première réservation
    public String getFirstResa() {
        for (Reservation reservation : reservations) {
            if (reservation != null) {
                return reservation.toString();
            }
        }
        return "Aucune réservation";
    }

    // Méthode qui retourne un tableau de réservations
    public Reservation[] getReservations() {
        return reservations;
    }

    // Méthode d'ajout avec gestion d'exception (sans boolean)
    public Exception ajout(Reservation parReservation) {
        for (int i = 0; i < reservations.length; i++) {
            if (reservations[i] == null) {
                reservations[i] = parReservation;
                return null;
            }
        }
        return new Exception("Tableau plein");
    }

    // Méthode qui affiche les réservations
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Reservation reservation : reservations) {
            if (reservation != null) {
                sb.append(reservation).append("\n");
            }
        }
        return sb.toString();
    }

    // Méthode qui trie les réservations par date
    public void triSelection() {
        for (int i = 0; i < TAILLE_TAB - 1; i++) {
            int minIdx = i;
            for (int j = i + 1; j < TAILLE_TAB; j++) {
                if (reservations[j] != null && reservations[minIdx] != null) {
                    if (reservations[j].getDate().compareTo(reservations[minIdx].getDate()) < 0) {
                        minIdx = j;
                    }
                }
            }
            if (minIdx != i) {
                echanger(i, minIdx);
            }
        }
    }

    // Méthode privée pour échanger deux éléments du tableau
    private void echanger(int i, int j) {
        Reservation temp = reservations[i];
        reservations[i] = reservations[j];
        reservations[j] = temp;
    }
}
