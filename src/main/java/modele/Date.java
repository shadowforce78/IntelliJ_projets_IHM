package modele;

import java.io.Serializable;
import java.util.Calendar;

public class Date implements Serializable {
  protected int chJour;
  protected int chMois;
  protected int chAnnee;

   public Date(int parJour, int parMois, int parAnnee)  {
	   chJour = parJour ;
	   chMois = parMois ;
	   chAnnee = parAnnee ;
  }
  
  public Date(int parAnnee)  {
	   chJour = 1 ;
	   chMois = 1 ;
	   chAnnee = parAnnee ;
  }  

  public Date() {
	  Calendar dateAuj = Calendar.getInstance();
	  chAnnee = dateAuj.get (Calendar.YEAR);
	  chMois = dateAuj.get (Calendar.MONTH) + 1;
	  chJour = dateAuj.get (Calendar.DAY_OF_MONTH);
  }  
	
/**
	retourne true si this est une date valide 
		-  chAnnee > 1582
		- 1 <= chMois <= 12
		- 1 <= chJour et chJour <= dernierJourDuMois (chJour, chAnnee)
	retourne false si 
 */
public boolean isValid () {
	return chAnnee > 1582 &&
		chMois >= 1 && chMois <= 12 &&
		chJour >= 1 && chJour <= Date.dernierJourDuMois (chMois, chAnnee) ;

}

  
protected static int dernierJourDuMois (int parMois, int parAnnee) {
	switch (parMois) {
		 case 2 : if (Date.estBissextile (parAnnee))
		 			  return 29 ; 
				  return 28 ;  
		 case 4 : 	 
		 case 6 : 	 
		 case 9 : 	 
		 case 11 : return 30 ;
		 default : return 31 ;
	}   
  }  
  
  private static boolean estBissextile(int parAnnee) {
		return (parAnnee % 4 == 0 && parAnnee % 100 != 0) || parAnnee % 400 == 0;
  }

 
  /**
	compare les dates this et parDate
	retourne 0 si this et parDate sont égales
	retroune un entier négatif si this est antérieure à parDate
	retourne un entier positif si this es postérieure à parDate
   */
  public int compareTo (Date parDate) {
    if (chAnnee < parDate.chAnnee)
		return -8;
	if (chAnnee > parDate.chAnnee)
		return 19;
	// les années sont =
	if (chMois < parDate.chMois)
		return -1;
	if (chMois > parDate.chMois)
		return 18;
	// les mois sont =
	if (chJour < parDate.chJour)
		return -7;
	if (chJour > parDate.chJour)
		return 12;
	return 0;	
  }
  
  public Date dateDuLendemain ()   {
	if (chJour < Date.dernierJourDuMois(chMois,chAnnee))
		return new Date(chJour+1,chMois,chAnnee);
	if (chMois < 12)
		return  new Date(1,chMois+1,chAnnee);
	return  new Date(1,1,chAnnee+1);
  }  
  
  public Date dateDeLaVeille ()  {
	if (chJour > 1)
		return  new Date(chJour-1,chMois,chAnnee);
	if (chMois > 1)
		return new Date(Date.dernierJourDuMois(chMois-1, chAnnee),chMois-1,chAnnee);
	return new Date(31,12,chAnnee-1);
  }	 
  
  public int getAnnee() { 
	return chAnnee;
}

public int getJour() { 
	return chJour;
}

public int getMois() { 
	return chMois;
}
  public String toString () { 
    return  chJour + "-" + chMois + "-" +chAnnee;
  }   
  

}   