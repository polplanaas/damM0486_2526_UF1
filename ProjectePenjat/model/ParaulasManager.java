package model;
import java.io.*;
import java.util.*;

public class ParaulasManager {
    private static final String FILE = "paraules.txt";

    public List<String> carregar() {
        List<String> llista = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE))) {
            String linia;
            while ((linia = br.readLine()) != null) {
                llista.add(linia.trim().toUpperCase());
            }
        } catch (IOException e) {
            System.out.println("No s'han trobat paraules.");
        }
        return llista;
    }

    public void afegir(String paraula) {
        try (FileWriter fw = new FileWriter(FILE, true)) {
            fw.write(paraula.toUpperCase() + "\n");
        } catch (IOException e) {
            System.out.println("Error afegint la paraula.");
        }
    }
}
