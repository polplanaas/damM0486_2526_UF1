package model;
import java.io.Serializable;

public class User implements Serializable {
    private String name;
    private String user;
    private String password;
    private boolean admin;
    private int punts;

    public User(String name, String user, String password, boolean admin) {
        this.name = name;
        this.user = user;
        this.password = password;
        this.admin = admin;
        this.punts = 0;
    }

    public String getName() { return name; }
    public String getUser() { return user; }
    public String getPassword() { return password; }
    public boolean isAdmin() { return admin; }
    public int getPunts() { return punts; }
    public void addPunts(int p) { this.punts += p; }

    @Override
    public String toString() {
        return user + " (" + (admin ? "admin" : "usuari") + ") - punts: " + punts;
    }
}