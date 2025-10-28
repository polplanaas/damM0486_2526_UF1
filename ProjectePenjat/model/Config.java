package model;

import java.io.*;

public class Config {
    private static final String FILE = "config.bin";

    private String versio;
    private int maxIntents;

    public Config() {
        carregar();
    }

    public void carregar() {
        File f = new File(FILE);
        if (!f.exists()) {
            versio = "1.0";
            maxIntents = 6;
            guardar();
        } else {
            try (DataInputStream in = new DataInputStream(new FileInputStream(f))) {
                versio = in.readUTF();
                maxIntents = in.readInt();
            } catch (IOException e) {
                System.out.println("Error llegint config: " + e.getMessage());
                versio = "1.0";
                maxIntents = 6;
            }
        }
    }

    public void guardar() {
        try (DataOutputStream out = new DataOutputStream(new FileOutputStream(FILE))) {
            out.writeUTF(versio);
            out.writeInt(maxIntents);
        } catch (IOException e) {
            System.out.println("Error guardant config: " + e.getMessage());
        }
    }

    public String getVersio() { return versio; }
    public int getMaxIntents() { return maxIntents; }
    public void setVersio(String versio) { this.versio = versio; }
    public void setMaxIntents(int maxIntents) { this.maxIntents = maxIntents; }
}
