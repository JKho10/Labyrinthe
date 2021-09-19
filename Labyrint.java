import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.*;

public class Labyrint {
    private Rute[][] ruteArray;
    private int rader;
    private int kolonner;
    private ArrayList<ArrayList<Tuppel>> utveiListe;
    private ArrayList<ArrayList<Tuppel>> utveier;
    private JButton[][] buttons;
    private JLabel antallMuligUtveier;

    private Labyrint(Rute[][] ruteArray, int rader, int kolonner) {
        this.rader = rader;
        this.kolonner = kolonner;
        this.ruteArray = ruteArray;
    }

    public Labyrint(File fil) throws FileNotFoundException {
        lesFil(fil);
        System.out.println(this);
    }

    private Labyrint lesFil(File filnavn) throws FileNotFoundException {
        Scanner scanner = new Scanner(filnavn);
        String linje = scanner.nextLine();
        String[] data = linje.split(" ");
        rader = Integer.parseInt(data[0]);
        kolonner = Integer.parseInt(data[1]);
        ruteArray = new Rute[rader][kolonner];

        lagRute(scanner);

        Labyrint labyrint = new Labyrint(ruteArray, rader, kolonner);

        //finn og sett naboer
        settNaboer(labyrint);
        return labyrint;
    }

    private void lagRute(Scanner scanner) {
        //tilpasset koden fra forelesningen uke 14-15/plenum uke 15
        JFrame vindu = new JFrame("Labyrint");
        vindu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel basis = new JPanel();
        JPanel rutenett = new JPanel();
        rutenett.setLayout(new GridLayout(rader, kolonner));
        buttons = new JButton[rader][kolonner];
        JButton button = null;

        //tilpasset koden fra forelesningen uke 15/plenum uke 15
        JButton resetKnapp = new JButton("Reset");
        resetKnapp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int rad = 0; rad < rader; rad++) {
                    for (int kolonne = 0; kolonne < kolonner; kolonne++) {
                        char tegn = ' ';
                        if (buttons[rad][kolonne].getText().equals("*")) {
                            buttons[rad][kolonne].setBackground(Color.WHITE);
                            buttons[rad][kolonne].setText(" ");
                        }
                    }
                }
                antallMuligUtveier.setText(" ");
            }
        });

        //hentet og tilpasset koden fra forelesningen uke 14-15/plenum uke 15
        JButton exitKnapp = new JButton("Exit");
        exitKnapp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        //lag rute
        for (int rad = 0; rad < rader; rad++) {
            String linje = scanner.nextLine();
            for (int kolonne = 0; kolonne < kolonner; kolonne++) {
                char tegn = linje.charAt(kolonne);
                if (tegn == '#') {
                    button = new JButton("#");
                    button.setBackground(Color.BLACK);
                    ruteArray[rad][kolonne] = new SortRute(rad, kolonne, this);
                } else if (sjekkAapning(rad, kolonne, tegn)) {
                    button = new JButton(" ");
                    button.setBackground(Color.WHITE);
                    ruteArray[rad][kolonne] = new Aapning(rad, kolonne, this);
                } else {
                    button = new JButton(" ");
                    button.setBackground(Color.WHITE);
                    ruteArray[rad][kolonne] = new HvitRute(rad, kolonne, this);
                }
                //hentet og tilpasset koden fra plenum uke 15
                button.addActionListener(new Trykkbehandler(rad, kolonne));
                buttons[rad][kolonne] = button;
                rutenett.add(button);
            }
        }
        antallMuligUtveier = new JLabel("Antall mulig utveier: ");
        basis.add(resetKnapp);
        basis.add(exitKnapp);
        basis.add(antallMuligUtveier);
        basis.add(rutenett);
        vindu.add(basis);
        vindu.setVisible(true);
        vindu.pack();
    }

    //hentet og tilpasset koden fra plenum uke 15
    class Trykkbehandler implements ActionListener {
        int rad;
        int kolonne;

        public Trykkbehandler(int rad, int kolonne) {
            this.rad = rad;
            this.kolonne = kolonne;
        }
        @Override
        public void actionPerformed(ActionEvent e){
            System.out.println("Start koordinater: (rad, kol): (" + rad + ", " + kolonne + ")");
            //hentet og tilpasset koden fra main metoden fra oblig 6
            System.out.println("Utveier:");
            utveiListe = finnUtveiFra(kolonne, rad);
            antallMuligUtveier.setText("Antall utveier: " + utveiListe.size());
            for (ArrayList<Tuppel> lis: utveiListe) {
                for(Tuppel t: lis) {
                    buttons[t.getRad()][t.getKolonne()].setBackground(Color.YELLOW);
                    buttons[t.getRad()][t.getKolonne()].setText("*");
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException interruptedException) {
                        System.out.println(interruptedException);
                    }
                    System.out.println(t);
                }
                System.out.println();
            }
            System.out.println();
        }
    }

    private void settNaboer(Labyrint labyrint) {
        for (int rad = 0; rad < rader; rad++) {
            for (int kolonne = 0; kolonne < kolonner; kolonne++) {
                Rute[][] ruter = labyrint.ruteArray;
                Rute rute = ruter[rad][kolonne];

                if (!erNordkant(rad)) {
                    rute.nord = ruteArray[rad-1][kolonne];
                }
                if (!erSorkant(rad)) {
                    rute.sor = ruteArray[rad+1][kolonne];
                }
                if (!erVestkant(kolonne)) {
                    rute.vest = ruteArray[rad][kolonne-1];
                }
                if (!erOstkant(kolonne)) {
                    rute.ost = ruteArray[rad][kolonne+1];
                }
                rute.setNaboer(new Rute[]{rute.nord,rute.ost, rute.sor, rute.vest});
            }
        }
    }

    private boolean sjekkAapning(int rad, int kolonne, char tegn) {
        return erKant(rad, kolonne) && tegn == '.';
    }

    private boolean erKant(int rad, int kolonne) {
        return erNordkant(rad) || erVestkant(kolonne) || erSorkant(rad) || erOstkant(kolonne);
    }

    private boolean erOstkant(int kolonne) {
        return kolonne == kolonner - 1;
    }

    private boolean erSorkant(int rad) {
        return rad == rader - 1;
    }

    private boolean erVestkant(int kolonne) {
        return kolonne == 0;
    }

    private boolean erNordkant(int rad) {
        return rad == 0;
    }


    public ArrayList<ArrayList<Tuppel>> finnUtveiFra(int kolonne, int rad){
        utveier = new ArrayList<>();
        ruteArray[rad][kolonne].finnUtvei();
        return utveier;
    }

    public String toString() {
        StringBuilder rute = new StringBuilder("\n");
        for (int rad = 0; rad < rader; rad ++) {
            for (int kolonne = 0; kolonne < kolonner; kolonne ++) {
                rute.append(ruteArray[rad][kolonne].tilTegn());
            }
            rute.append("\n");
        }
        return rute.toString();
    }

    public void leggTilUtvei(ArrayList<Tuppel> sti) {
        utveier.add(sti);
    }
}


