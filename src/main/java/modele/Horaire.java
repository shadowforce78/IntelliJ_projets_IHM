package modele;

public class Horaire {
    private int heure;
    private int minutes;

    public Horaire(int heure, int minutes) {
        if (heure < 0 || heure > 23) {
            throw new IllegalArgumentException("L'heure doit être entre 0 et 23");
        }
        if (minutes < 0 || minutes > 59) {
            throw new IllegalArgumentException("Les minutes doivent être entre 0 et 59");
        }
        this.heure = heure;
        this.minutes = minutes;
    }

    public int getHeure() {
        return heure;
    }

    public int getMinutes() {
        return minutes;
    }

    public int toMinutes() {
        return heure * 60 + minutes;
    }

    public void setHeure(int heure) {
        if (heure < 0 || heure > 23) {
            throw new IllegalArgumentException("L'heure doit être entre 0 et 23");
        }
        this.heure = heure;
    }

    public void setQuartHeure(int quartHeure) {
        if (quartHeure < 0 || quartHeure > 3) {
            throw new IllegalArgumentException("Les quarts d'heure doivent être entre 0 et 3");
        }
        this.minutes = quartHeure * 15;
    }

    @Override
    public String toString() {
        return String.format("%02d:%02d", heure, minutes);
    }
}