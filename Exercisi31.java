import java.io.*;
import java.util.Random;

public class Exercisi31 {
    public static void main(String[] args) {
        String nomFitxer = "secret.bin";
        Random random = new Random();

        try (DataOutputStream out = new DataOutputStream(new FileOutputStream(nomFitxer))) {
            int codi = random.nextInt(3) + 1;

            for (int i = 0; i < 10; i++) {
                StringBuilder secret = new StringBuilder();
                for (int j = 0; j < 3; j++) {
                    char lletra = (char) ('a' + random.nextInt(26));
                    secret.append(lletra);
                }

                out.writeInt(codi);
                out.writeChars(secret.toString());

                System.out.println(codi + ":" + secret);

                codi += random.nextInt(3) + 1;
            }

            System.out.println("Fitxer " + nomFitxer + " generat correctament!");

        } catch (IOException e) {
            System.out.println("Error en escriure el fitxer.");
            e.printStackTrace();
        }
    }
}
