import java.io.*;
import java.util.Scanner;

public class Exercisi22 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String fileName = "usertext.txt";

        try {
            FileOutputStream fos = new FileOutputStream(fileName);
            OutputStreamWriter writer = new OutputStreamWriter(fos);
            BufferedWriter bw = new BufferedWriter(writer);

            while (true) {
                System.out.print("Introdueix una cadena ('quit' per sortir): ");
                String input = in.nextLine();

                if (input.equalsIgnoreCase("quit")) {
                    break;
                }

                bw.write(input);
                bw.newLine();
            }

            bw.close();

            FileInputStream fis = new FileInputStream(fileName);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);

            System.out.println("\n--- Contingut del fitxer ---");
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }

            br.close();
            in.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

