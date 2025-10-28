package view;
import java.util.Scanner;

public class Vista {
    private Scanner in = new Scanner(System.in);

    public int menuInicial() {
        System.out.println("\n--- WORDLE CATALÀ ---");
        System.out.println("1 - Login");
        System.out.println("2 - Registrar-se");
        System.out.print("Opcio: ");
        int opcio = in.nextInt();
        in.nextLine();
        return opcio;
    }

    public String demanarText(String msg) {
        System.out.print(msg);
        return in.nextLine();
    }

    public int menuUsuari(boolean admin) {
        System.out.println("\n--- MENÚ PRINCIPAL ---");
        System.out.println("1 - Jugar");
        if (admin) {
            System.out.println("2 - Afegir paraules");
            System.out.println("3 - Editar configuració");
        }
        System.out.println("0 - Sortir");
        System.out.print("Opcio: ");
        int opcio = in.nextInt();
        in.nextLine();
        return opcio;
    }

    public int demanarInt(String msg) {
        System.out.print(msg);
        int num = in.nextInt();
        in.nextLine();
        return num;
    }

    public void mostrarMissatge(String msg) {
        System.out.println(msg);
    }
}
