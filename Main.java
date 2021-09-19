import java.io.File;
import java.io.FileNotFoundException;

import javax.swing.*;

public class Main {
    private static Labyrint labyrint = null;
    public static void main(String[] arg) {

        //hentet koden fra forelesningen uke 14
        JFileChooser velger = new JFileChooser();
        int resultat = velger.showOpenDialog(null);
        if (resultat != JFileChooser.APPROVE_OPTION) {
            System.exit(1);
        }
        File fil = velger.getSelectedFile();
        //hentet koden fra main metoden fra oblig 6
        try {
            labyrint = new Labyrint(fil);
        } catch (FileNotFoundException e) {
            System.out.printf("FEIL: Kunne ikke lese fra '%s'\n", fil.getName());
            System.exit(1);
        }
    }
}




