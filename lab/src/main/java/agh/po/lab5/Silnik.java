package agh.po.lab5;

public class Silnik extends Komponent {
    int maxObroty;
    int obroty;

    public Silnik() {
        super();
        maxObroty = 6000;
    }

    public Silnik(String producent, String model, int maxObroty) {
        super(producent, model);
        if (maxObroty < 0) {
            throw new IllegalArgumentException("Maksymalne obroty nie moga byc ujemne.");
        }
        this.maxObroty = maxObroty;
    }

    public void uruchom() {
        obroty = Math.min(1000, maxObroty);
    }

    public void zatrzymaj() {
        obroty = 0;
    }

    public void zwiekszObroty(int zmiana) {
        sprawdzZmianeObrotow(zmiana);
        obroty = Math.min(maxObroty, obroty + zmiana);
    }

    public void zmniejszObroty(int zmiana) {
        sprawdzZmianeObrotow(zmiana);
        obroty = Math.max(0, obroty - zmiana);
    }

    private void sprawdzZmianeObrotow(int zmiana) {
        if (zmiana <= 0) {
            throw new IllegalArgumentException("Zmiana obrotow musi byc dodatnia.");
        }
    }

}
