import java.io.Serializable;

public class User implements Serializable {
    String nom;
    String pass;

    public User(String nom, String pass) {
        this.nom = nom;
        this.pass = pass;
    }
}