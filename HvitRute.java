import java.util.ArrayList;

public class HvitRute extends Rute {
    public HvitRute(int rad, int kolonne, Labyrint labyrint){
        super(rad,kolonne, labyrint);
    }

    @Override
    public char tilTegn(){
        return '.';
    }

    @Override
    public void gaa(ArrayList<Tuppel> sti) {
//        System.out.println("Hvit" + rad + ", " + kolonne);
        ArrayList<Tuppel> nySti = new ArrayList<>(sti);
        nySti.add(new Tuppel(rad, kolonne));
        setSti(nySti);

        //Initialize
        if (nySti.size() <= 1) {
            for (Rute rute : hentNaboer()) {
                rute.gaa(nySti);
            }
        } else {
            Tuppel komFra = nySti.get(nySti.size()-2);
            for (Rute rute : hentNaboer()) {
                if (rute.rad != komFra.getRad() || rute.kolonne != komFra.getKolonne()) {
//                    System.out.println(rute);
                    rute.gaa(nySti);
                }
            }
        }
    }
}