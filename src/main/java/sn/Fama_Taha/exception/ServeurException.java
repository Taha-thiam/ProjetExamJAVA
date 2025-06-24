package sn.Fama_Taha.exception;

public class ServeurException extends Exception {

    public ServeurException() {
        super("Erreur de connexion au serveur");
    }

    public ServeurException(String message) {
        super(message);
    }
}
