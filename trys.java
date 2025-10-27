import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class trys {
    public static void main(String[] args) {
       
   
        System.out.println("Benvingut al joc!");
       
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("users.dat")));
            User usr = new User("alex", "1234");
            oos.writeObject(usr);
            System.out.println("Usuari creat: " + usr.nom);
            usr = new User("pepe", "1234");
            oos.writeObject(usr);
            System.out.println("Usuari creat: " + usr.nom);
            usr = new User("toto", "1234");
            oos.writeObject(usr);
            System.out.println("Usuari creat: " + usr.nom);
            oos.writeObject(usr);
            oos.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File("users.dat")));
            User u = (User) ois.readObject();
            System.out.println(u.nom);
            // .println("Usuari llegit: " + u.nom);
            u = (User) ois.readObject();
            u = (User) ois.readObject();
            System.out.println(u.nom);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}