import java.util.ArrayList;

public class Aapning extends HvitRute {
    public Aapning(int rad, int kolonne, Labyrint labyrint){
        super(rad,kolonne, labyrint);
    }

    public char tilTegn() {
        return '.';
    }

    @Override
    public void gaa(ArrayList<Tuppel> sti) {
        ArrayList<Tuppel> nySti = new ArrayList<>(sti);
        nySti.add(new Tuppel(rad, kolonne));
        setSti(nySti);
        getLabyrint().leggTilUtvei(nySti);
    }
}

