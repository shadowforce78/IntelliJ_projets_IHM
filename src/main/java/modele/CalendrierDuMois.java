package modele;

import java.util.Collection;
import java.util.TreeSet;

/** Class Description of CalendrierDuMois */
public class CalendrierDuMois {

    /**  Field Description of mois : le mois du calendrier */

    private int mois;

    /** Field Description annee : l'annee du calendrier */
    private int annee;

    /**
     * treeSetDate : la collection des dates du mois en cours
     */
    private Collection <DateCalendrier> treeSetDate;

    public CalendrierDuMois ( int mois, int annee) {
        this.mois = mois;
        this.annee = annee;
        treeSetDate = new TreeSet <DateCalendrier> ();
        DateCalendrier date = new DateCalendrier (1,mois,annee);
        int indiceJour = date.getJourSemaine() ;
        for (int x = indiceJour ; x!=0 ; x--) {
            treeSetDate.add(date);
            date = date.dateDeLaVeille();
        }
        date = new DateCalendrier (2,mois,annee);
        indiceJour = indiceJour % 7 ;
        while (date.getMois () == mois) {
            while(indiceJour<7) {
                treeSetDate.add(date);
                date = date.dateDuLendemain();
                indiceJour++ ;
            }
            indiceJour=0;
        }
    }

    /**
     * accesseur sur le champ collection dates
     * @return
     */
    public Collection <DateCalendrier> getDates() {
        return treeSetDate;
    }

    public String toString () {
        return treeSetDate.size() + " " +treeSetDate.toString();
    }

    public int getMois () {
        return mois;
    }

    public int getAnnee() {
        return annee;
    }

}