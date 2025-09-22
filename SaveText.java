import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class SaveText {
    private static int cont;

    public static void main(String args[]) {
        String file = "text.txt";

        File f = new File(file);

        try {
            FileOutputStream fos = new FileOutputStream(f);
                fos.write("Hola Gent".getBytes());
                fos.write("\n".getBytes());
                    String t = "Som a classe";

                    for (char c: t.toCharArray()) {
                        fos.write(c);
                    }

                    fos.close();
                
                    FileInputStream fis = new FileInputStream(f);

                    int b = 0;

                    while (b!=-1) {
                        b = fis.read();

                        System.out.print((char)b);
                    }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
