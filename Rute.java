import java.util.ArrayList;

public abstract class Rute {
    private final Labyrint labyrint;
    protected Rute nord;
    protected Rute sor;
    protected Rute ost;
    protected Rute vest;
    protected int rad;
    protected int kolonne;
    private ArrayList<Tuppel> sti;
    private Rute[] naboer;

    public Rute(int rad, int kolonne, Labyrint labyrint){
        this.rad = rad;
        this.kolonne = kolonne;
        this.labyrint = labyrint;
    }

    protected abstract char tilTegn();

    protected abstract void gaa(ArrayList<Tuppel> sti);

    public Rute[] hentNaboer() {
        return naboer;
    }

    public void setNaboer(Rute[] naboer) {
        this.naboer = naboer;
    }

    public Labyrint getLabyrint() {
        return labyrint;
    }

    public void setSti(ArrayList<Tuppel> sti) {
        this.sti = sti;
    }

    public void finnUtvei(){
        gaa(new ArrayList<>());
    }

    @Override
    public String toString() {
        return "(" + kolonne + ", " + rad + ")";
    }
}
