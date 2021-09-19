public class Tuppel {
    int rad;
    int kolonne;

    public Tuppel(int rad, int kolonne){
        this.rad = rad;
        this.kolonne = kolonne;
    }

    public int getRad() {
        return rad;
    }

    public void setRad(int rad) {
        this.rad = rad;
    }

    public int getKolonne() {
        return kolonne;
    }

    public void setKolonne(int kolonne) {
        this.kolonne = kolonne;
    }

    @Override
    public String toString() {
        return "(" + kolonne + ", " + rad + ")";
    }
}