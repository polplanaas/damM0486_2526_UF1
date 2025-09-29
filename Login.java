import java.io.*;
import java.util.Scanner;

public class Login {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        System.out.print("Usuari: ");
        String nom = in.nextLine();

        System.out.print("Contrasenya: ");
        String pass = in.nextLine();

        String nomFitxer = nom + ".usr";
        File fitxer = new File(nomFitxer);

        if (fitxer.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fitxer))) {
                User u = (User) ois.readObject();

                if (u.pass.equals(pass)) {
                    System.out.println("Accés correcte al sistema");
                } else {
                    System.out.println("Accés no concedit: La contrasenya no és correcta");
                }
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Error en llegir l'usuari");
                e.printStackTrace();
            }
        } else {
            System.out.println("No s’ha trobat l’usuari, vols registrar-te? (si/no)");
            String resposta = in.nextLine();

            if (resposta.equals("si")) {
                User nouUsuari = new User(nom, pass);

                try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fitxer))) {
                    oos.writeObject(nouUsuari);
                    System.out.println("Usuari registrat correctament!");
                } catch (IOException e) {
                    System.out.println("Error en guardar l'usuari");
                    e.printStackTrace();
                }
            } else {
                System.out.println("No s'ha registrat cap usuari.");
            }
        }
        in.close();
    }
}
