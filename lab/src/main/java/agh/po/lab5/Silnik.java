package agh.po.lab5;

public class Silnik extends Komponent {
    int maxObroty;
    int obroty;

    public Silnik() {
        super();
    }

    public Silnik(String producent, String model, int maxObroty) {
        super(producent, model);
        this.maxObroty = maxObroty;
    }

    public void uruchom() {
        obroty = 1000;
    }

    public void zatrzymaj() {
        obroty = 0;
    }

}
