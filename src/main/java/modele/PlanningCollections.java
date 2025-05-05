package modele;
import java.util.*;

public class PlanningCollections {
    private List<Reservation> chListReservations;
    private Set<Reservation> chSetReservations;

    public PlanningCollections() {
        chListReservations = new ArrayList<Reservation>();
        chSetReservations = new HashSet<Reservation>();
    }

    @Override
    public String toString() {
        return "TreeSet :" + chSetReservations.size() + "-" + chSetReservations + "\n" + "ArrayList :"
                + chListReservations.size() + "-" + chListReservations;
    }

    public void ajouter(Reservation parReservation) throws ExceptionPlanning {
        Iterator<Reservation> iterateur = chListReservations.iterator();
        while (iterateur.hasNext()) {
            Reservation reservation = iterateur.next();
            if (parReservation.compareTo(reservation) == 0) {
                throw new ExceptionPlanning(2);
            }
        }
        chListReservations.add(parReservation);

        // Ajout au TreeSet
        int sizeInitiale = chSetReservations.size();
        chSetReservations.add(parReservation);
        if (sizeInitiale == chSetReservations.size()) {
            throw new ExceptionPlanning(2);
        }
    }
}
