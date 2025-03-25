package modele;

import java.util.Calendar;

public class DateCalendrier extends Date implements JoursSemaine, Mois, Comparable<Date> {

    private int jourSemaine;
    private int weekOfYear;

    /**
     * retourne la date d'aujourd'hui
     *
     */
    public DateCalendrier() {
        // GregorianCalendar dateAuj = new GregorianCalendar ();
        Calendar dateAuj = Calendar.getInstance();
        chAnnee = dateAuj.get(Calendar.YEAR);
        chMois = dateAuj.get(Calendar.MONTH) + 1;
        chJour = dateAuj.get(Calendar.DAY_OF_MONTH);
        jourSemaine = dateAuj.get(Calendar.DAY_OF_WEEK);
        if (jourSemaine == 1)
            jourSemaine = 7;
        else
            jourSemaine -= 1;
        weekOfYear = dateAuj.get(Calendar.WEEK_OF_YEAR);
    }

    public DateCalendrier(int parJour, int parMois, int parAnnee) {
        super(parJour, parMois, parAnnee);
        Calendar date = Calendar.getInstance();
        date.set(chAnnee, chMois - 1, chJour);
        jourSemaine = date.get(Calendar.DAY_OF_WEEK);
        if (jourSemaine == 1)
            jourSemaine = 7;
        else
            jourSemaine -= 1;
        weekOfYear = date.get(Calendar.WEEK_OF_YEAR);
    }

    public String toString() {
        return JOURS_SEMAINE[jourSemaine - 1] + " " + chJour + " " + MOIS[chMois - 1];
    }

    public int getJourSemaine() {
        return jourSemaine;
    }

    public boolean isToday() {
        return this.compareTo(new DateCalendrier()) == 0;
    }

    public int getWeekOfYear() {
        return weekOfYear;
    }

    public DateCalendrier dateDuLendemain() {
        Date dateLendemain = super.dateDuLendemain();
        return new DateCalendrier(dateLendemain.chJour, dateLendemain.chMois, dateLendemain.chAnnee);
    }

    public DateCalendrier dateDeLaVeille() {
        Date dateVeille = super.dateDeLaVeille();
        return new DateCalendrier(dateVeille.chJour, dateVeille.chMois, dateVeille.chAnnee);
    }

    // @Override
    // public int compareTo(Date o) {
    // return 0;
    // }
}