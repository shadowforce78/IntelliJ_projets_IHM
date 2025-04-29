package modele;

public class ExceptionPlanning extends Exception implements ConstantesErreur {
    private int chCodeErreur;

    public ExceptionPlanning(int codeErreur) {
        super(ERREUR_PLANNING[codeErreur]);
        this.chCodeErreur = codeErreur;
    }

    public int getChCodeErreur() {
        return chCodeErreur;
    }

    @Override
    public String toString() {
        return "ExceptionPlanning: " + ERREUR_PLANNING[chCodeErreur] + " (code: " + chCodeErreur + ")";
    }
}
