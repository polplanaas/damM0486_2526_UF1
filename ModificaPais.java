import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;

public class ModificaPais {

    static final int MIDA_NOM = 20;
    static final int MIDA_CAPITAL = 20;
    static final int MIDA_REGISTRE = 4 + (MIDA_NOM * 2) + 4 + (MIDA_CAPITAL * 2);

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        try (RandomAccessFile fitxer = new RandomAccessFile("paisos.dat", "rw")) {
            System.out.print("Introdueix l'índex del registre que vols modificar: ");
            int index = in.nextInt();
            in.nextLine();

            System.out.print("Quin camp vols modificar (nom, codi, poblacio, capital)? ");
            String camp = in.nextLine().toLowerCase();

            System.out.print("Introdueix el nou valor: ");
            String valor = in.nextLine();

            long posicio = index * MIDA_REGISTRE;
            fitxer.seek(posicio);

            int codi = fitxer.readInt();
            String nom = llegirTextFix(fitxer, MIDA_NOM);
            int poblacio = fitxer.readInt();
            String capital = llegirTextFix(fitxer, MIDA_CAPITAL);

            System.out.println("\nRegistre original:");
            System.out.println("Codi: " + codi);
            System.out.println("Nom: " + nom);
            System.out.println("Població: " + poblacio);
            System.out.println("Capital: " + capital);

            // Modifiquem el camp que toqui
            switch (camp) {
                case "codi":
                    codi = Integer.parseInt(valor);
                    break;
                case "nom":
                    nom = valor;
                    break;
                case "poblacio":
                    poblacio = Integer.parseInt(valor);
                    break;
                case "capital":
                    capital = valor;
                    break;
                default:
                    System.out.println("Camp no vàlid!");
                    return;
            }

            fitxer.seek(posicio);
            fitxer.writeInt(codi);
            escriureTextFix(fitxer, nom, MIDA_NOM);
            fitxer.writeInt(poblacio);
            escriureTextFix(fitxer, capital, MIDA_CAPITAL);

            System.out.println("\nRegistre actualitzat correctament!");

        } catch (IOException e) {
            System.out.println("Error d'entrada/sortida");
        } catch (NumberFormatException e) {
            System.out.println("Error: valor numèric no vàlid.");
        }
    }

    private static void escriureTextFix(RandomAccessFile fitxer, String text, int mida) throws IOException {
        StringBuilder sb = new StringBuilder(text);
        if (sb.length() > mida) {
            sb.setLength(mida);
        } else {
            while (sb.length() < mida) sb.append(' ');
        }
        fitxer.writeChars(sb.toString());
    }

    private static String llegirTextFix(RandomAccessFile fitxer, int mida) throws IOException {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < mida; i++) {
            sb.append(fitxer.readChar());
        }
        return sb.toString().trim();
    }
}