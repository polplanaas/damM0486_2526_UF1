package controller;
import java.util.*;
import model.*;
import view.Vista;

public class Controlador {
    private UserLogin userLogin;
    private ParaulasManager paraulasManager;
    private Vista vista;
    private Random rand = new Random();
    private Config config;

    public Controlador() {
        userLogin = new UserLogin();
        paraulasManager = new ParaulasManager();
        vista = new Vista();
        config = new Config();
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
                vista.mostrarMissatge("Opció no valida.");
        }
    }

    private void login() {
        String usuari = vista.demanarText("Usuari: ");
        String pass = vista.demanarText("Contrasenya: ");
        User u = userLogin.login(usuari, pass);
        if (u != null) {
            vista.mostrarMissatge("Benvingut " + u.getName() + ". Tens " + u.getPunts() + " punts.");
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

        boolean admin = userLogin.esBuit();

        userLogin.registre(new User(nom, usuari, pass, admin));

        if (admin)
            vista.mostrarMissatge("Usuari registrat com a ADMIN!");
        else
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
                case 3:
                    if (u.isAdmin()) {
                        editarConfiguracio();
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

        if (!existeixParaulaCatala(nova.toLowerCase())) {
            vista.mostrarMissatge("La paraula '" + nova + "' no sembla existir en catala.");
            return;
        }

        paraulasManager.afegir(nova);
            vista.mostrarMissatge("Paraula afegida correctament");
    }


    private void jugar(User u) {
        List<String> paraules = paraulasManager.carregar();

        if (paraules.isEmpty()) {
            vista.mostrarMissatge("No hi ha paraules disponibles.");
            return;
        }

        String secreta = paraules.get(rand.nextInt(paraules.size()));
        int mida = secreta.length();

        vista.mostrarMissatge("Comença el joc, La paraula te " + mida + " lletres.");
        int intents = config.getMaxIntents();

        while (intents > 0) {
            String intent = vista.demanarText("Introdueix una paraula: ").trim().toUpperCase();

            if (!existeixParaulaCatala(intent.toLowerCase())) {
                vista.mostrarMissatge("La paraula '" + intent + "' no existeix en catala.");
                continue;
            }


            if (intent.length() != mida) {
                vista.mostrarMissatge("La paraula ha de tenir " + mida + " lletres!!!");
                continue;
            }

            if (intent.equals(secreta)) {
                vista.mostrarMissatge("FELICITAAATS!!!😎 L'HAS ENCERTAT :)\n La paraula correcte era: ---" + secreta + " ---");
                u.addPunts(10);
                userLogin.guardar();
                return;
            }

            mostrarPistes(secreta, intent);
            intents--;
            vista.mostrarMissatge("Et queden " + intents + " intents.");
        }

        vista.mostrarMissatge("HAS PERDUT!!!😂 La paraula era: " + secreta);
    }

    private void mostrarPistes(String secreta, String intent) {
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

    private void editarConfiguracio() {
        vista.mostrarMissatge("\n--- CONFIGURACIÓ ACTUAL ---");
        vista.mostrarMissatge("VersiO: " + config.getVersio());
        vista.mostrarMissatge("Max intents: " + config.getMaxIntents());

        String novaVersio = vista.demanarText("Nova versiO (buit per mantenir): ").trim();
        String nousIntents = vista.demanarText("Nous intents (buit per mantenir): ").trim();

        if (!novaVersio.isEmpty()) config.setVersio(novaVersio);

        if (!nousIntents.isEmpty()) {
            try {
                config.setMaxIntents(Integer.parseInt(nousIntents));
            } catch (NumberFormatException e) {
                vista.mostrarMissatge("Valor d’intents no valid.");
            }
        }

        config.guardar();
        vista.mostrarMissatge("Configuracio actualitzada!");
    }

    private boolean existeixParaulaCatala(String paraula) {
        try {
            String urlStr = "https://api.mymemory.translated.net/get?q=" + paraula + "&langpair=ca|es";
            java.net.URL url = new java.net.URL(urlStr);
            java.net.HttpURLConnection conn = (java.net.HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            java.io.BufferedReader reader = new java.io.BufferedReader(
                    new java.io.InputStreamReader(conn.getInputStream(), "UTF-8"));
            StringBuilder resposta = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                resposta.append(line);
            }
            reader.close();

            String jsonText = resposta.toString();
            int start = jsonText.indexOf("\"translatedText\":\"");
            if (start == -1) return false;
            start += "\"translatedText\":\"".length();
            int end = jsonText.indexOf("\"", start);
            if (end == -1) return false;

            String traduccio = jsonText.substring(start, end);

            return !(traduccio.equalsIgnoreCase(paraula) || traduccio.isEmpty());

        } catch (Exception e) {
            System.err.println("Error comprovant la paraula: " + e.getMessage());
            return false;
        }
    }

    public static void main(String[] args) { 
        new Controlador().iniciar(); 
    }
}