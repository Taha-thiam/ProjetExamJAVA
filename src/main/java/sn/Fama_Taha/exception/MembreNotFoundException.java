package sn.Fama_Taha.exception;

public class MembreNotFoundException extends Exception {
    public MembreNotFoundException() {
        super("Membre non trouvé");
    }

    public MembreNotFoundException(String message) {
        super(message);
    }
}