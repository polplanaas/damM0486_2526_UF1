import java.io.*;
import java.util.Scanner;

public class Exercisi21 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        System.out.print("Introdueix el nom del fitxer: ");
        String nomFitxer = in.nextLine();

        int comptador = 0;

        try (FileInputStream fis = new FileInputStream(nomFitxer)) {
            int byteLlegit;

            while ((byteLlegit = fis.read()) != -1) {
                char c = (char) byteLlegit;
                if (c == 'a') {
                    comptador++;
                }
            }

            System.out.println("El fitxer conte " + comptador + " lletres A.");

        } catch (FileNotFoundException e) {
            System.out.println("Error: el fitxer no existeix.");
        } catch (IOException e) {
            System.out.println("Error de lectura del fitxer.");
        }

        in.close();
    }
}