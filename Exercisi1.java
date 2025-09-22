import java.io.File;

public class Exercisi1 {
    public static void main(String[] args) {

        File path = new File(args[0]);

        File[] list= path.listFiles();

        for(File lists : list) {

            if (lists.isDirectory()) {
                System.out.println("La ruta: " + lists.getName() + " es un directori.");
                
                StringBuilder permisos = new StringBuilder();

                permisos.append("d");

                permisos.append(lists.canRead() ? "r" : "-");
                permisos.append(lists.canWrite() ? "w" : "-");
                permisos.append(lists.canExecute() ? "x" : "-");

                 System.out.println(permisos + " " + lists.getName());

            } else if (lists.isFile()) {
                System.out.println("La ruta: " + lists.getName() + " es un fitxer.");
                
                StringBuilder permisos = new StringBuilder();

                permisos.append("-");

                permisos.append(lists.canRead() ? "r" : "-");
                permisos.append(lists.canWrite() ? "w" : "-");
                permisos.append(lists.canExecute() ? "x" : "-");

                 System.out.println(permisos + " " + lists.getName());

            } else {
                System.out.println("La ruta no existeix.");
            }

        }
    }
}