package controller;
import java.util.*;
import model.*;
import view.Vista;

public class Controlador {
    private UserLogin userLogin;
    private ParaulasManager paraulasManager;
    private Vista vista;
    private Random rand = new Random();

    public Controlador() {
        userLogin = new UserLogin();
        paraulasManager = new ParaulasManager();
        vista = new Vista();
    }

    public void iniciar() {
        int opcio = vista.menuInicial();
        switch (opcio) {
            case 1:
                login();
                break;
            case 2: 
                registre();
                break;
            default:
                vista.mostrarMissatge("Opció no vàlida.");
        }
    }

    private void login() {
        String usuari = vista.demanarText("Usuari: ");
        String pass = vista.demanarText("Contrasenya: ");
        User u = userLogin.login(usuari, pass);
        if (u != null) {
            vista.mostrarMissatge("Benvingut " + u.getName() + "!");
            menuPrincipal(u);
        } else {
            vista.mostrarMissatge("Usuari o contrasenya incorrectes.");
        }
    }

    private void registre() {
        String nom = vista.demanarText("Nom: ");
        String usuari = vista.demanarText("Usuari: ");
        if (userLogin.existeix(usuari)) {
            vista.mostrarMissatge("Aquest usuari ja existeix.");
            return;
        }
        String pass = vista.demanarText("Contrasenya: ");
        String resposta = vista.demanarText("Vols ser admin? (s/n): ");
        boolean admin = resposta.trim().equalsIgnoreCase("s");
        userLogin.registre(new User(nom, usuari, pass, admin));
        vista.mostrarMissatge("Usuari registrat correctament!");
    }

    private void menuPrincipal(User u) {
        int opcio;
        do {
            opcio = vista.menuUsuari(u.isAdmin());
            switch (opcio) {
                case 1:
                    jugar(u);
                    break;
                case 2:
                if (u.isAdmin()) {
                    afegirParaula();
                }
                break;
            }
        } while (opcio != 0);
    }

    private void afegirParaula() {
        List<String> paraules = paraulasManager.carregar();
        vista.mostrarMissatge("\nLlista de paraules:");
        paraules.forEach(System.out::println);

        String nova = vista.demanarText("Introdueix una nova paraula: ").trim().toUpperCase();

        if (nova.isEmpty()) {
            vista.mostrarMissatge("La paraula no pot estar buida.");
            return;
        }
        if (paraules.contains(nova)) {
            vista.mostrarMissatge("Aquesta paraula ja existeix.");
            return;
        }

        paraulasManager.afegir(nova);
        vista.mostrarMissatge("Paraula afegida correctament!");
    }


    private void jugar(User u) {
        List<String> paraules = paraulasManager.carregar();
        if (paraules.isEmpty()) {
            vista.mostrarMissatge("No hi ha paraules disponibles.");
            return;
        }

        String secreta = paraules.get(rand.nextInt(paraules.size()));
        int mida = secreta.length();

        vista.mostrarMissatge("Comença el joc! La paraula té " + mida + " lletres.");
        int intents = 6;

        while (intents > 0) {
            String intent = vista.demanarText("Introdueix una paraula: ").trim().toUpperCase();

            if (intent.length() != mida) {
                vista.mostrarMissatge("La paraula ha de tenir " + mida + " lletres!");
                continue;
            }

            if (intent.equals(secreta)) {
                vista.mostrarMissatge("FELICITAAATS!! L'HAS ENCERTAT :)\n La paraula correcte era: ---" + secreta + " ---");
                u.addPunts(10);
                userLogin.guardar();
                return;
            }

            mostrarPistes(secreta, intent);
            intents--;
            vista.mostrarMissatge("Et queden " + intents + " intents.");
        }

        vista.mostrarMissatge("Has perdut! La paraula era: " + secreta);
    }


    private void mostrarPistes(String secreta, String intent) {
        // Si no faig servir el reset despues de cada lletra surt tota la terminal colorida del color de l'ultima lletra
        // La codificacio de emojis no funciona i per tant he agafat aquesta alternativa
        final String RESET = "\u001B[0m";
        final String VERD = "\u001B[42m";
        final String GROC = "\u001B[43m";
        final String GRIS = "\u001B[47m";

        for (int i = 0; i < intent.length(); i++) {
            char c = intent.charAt(i);
            if (i < secreta.length() && c == secreta.charAt(i)) {
                System.out.print(VERD + " " + c + " " + RESET);
            } else if (secreta.contains(String.valueOf(c))) {
                System.out.print(GROC + " " + c + " " + RESET);
            } else {
                System.out.print(GRIS + " " + c + " " + RESET);
            }
        }
        System.out.println("\n");
    }


    public static void main(String[] args) { 
        new Controlador().iniciar(); 
    }
}
