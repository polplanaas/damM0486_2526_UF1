import java.io.*;
import java.util.Scanner;

public class Exercisi32 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Introdueix el codi a buscar: ");
        int codiBuscat = sc.nextInt();

        boolean trobat = false;

        try (DataInputStream in = new DataInputStream(new FileInputStream("secret.bin"))) {
            while (true) {
                int codi = in.readInt();
                StringBuilder secret = new StringBuilder();

                for (int i = 0; i < 3; i++) {
                    char lletra = in.readChar();
                    secret.append(lletra);
                }

                if (codi == codiBuscat) {
                    System.out.println("El secret és: " + secret);
                    trobat = true;
                    break;
                }
            }
        } catch (EOFException e) {
            if (!trobat) {
                System.out.println("Codi no trobat");
            }
        } catch (IOException e) {
            System.out.println("Error en llegir el fitxer.");
            e.printStackTrace();
        }

        sc.close();
    }
}
