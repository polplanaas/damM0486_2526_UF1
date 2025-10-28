package model;
import java.io.*;
import java.util.*;

public class UserLogin {
    private static final String FILE = "users.dat";
    private ArrayList<User> usuaris = new ArrayList<>();

    public UserLogin() {
        carregar();
    }

    public boolean esBuit() {
        return usuaris.isEmpty();
    }


    public void carregar() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE))) {
            usuaris = (ArrayList<User>) ois.readObject();
        } catch (Exception e) {
            usuaris = new ArrayList<>();
        }
    }

    public void guardar() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE))) {
            oos.writeObject(usuaris);
        } catch (Exception e) {
            System.out.println("Error guardant usuaris.");
        }
    }

    public User login(String user, String pass) {
        for (User u : usuaris) {
            if (u.getUser().equals(user) && u.getPassword().equals(pass))
                return u;
        }
        return null;
    }

    public boolean existeix(String user) {
        return usuaris.stream().anyMatch(u -> u.getUser().equals(user));
    }

    public void registre(User u) {
        usuaris.add(u);
        guardar();
    }
}