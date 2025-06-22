
package sn.Fama_Taha.exception;


public class AdministrateurNotFoundException extends Exception {

    public AdministrateurNotFoundException() {
        super(" login ou mot de passe incorrect");
    }
    public AdministrateurNotFoundException(String message) {
        super(message);
    }
}
 