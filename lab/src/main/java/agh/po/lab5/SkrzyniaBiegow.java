package agh.po.lab5;

public class SkrzyniaBiegow extends Komponent {
    int aktualnyBieg;
    int iloscBiegow;
    Sprzeglo sprzeglo;

    public SkrzyniaBiegow() {
        super();
        sprzeglo = new Sprzeglo();
    }

    public SkrzyniaBiegow(String producent, String model, int iloscBiegow) {
        super(producent, model);
        this.iloscBiegow = iloscBiegow;
        sprzeglo = new Sprzeglo();
    }

    public void zwiekszBieg() throws Exception {
        sprawdzSprzegloWcisniete();
        if (aktualnyBieg < iloscBiegow) {
            aktualnyBieg++;
        }
    }

    public void zmniejszBieg() throws Exception {
        sprawdzSprzegloWcisniete();
        if (aktualnyBieg > 0) {
            aktualnyBieg--;
        }
    }

    private void sprawdzSprzegloWcisniete() throws Exception {
        if (!sprzeglo.stanSprzegla) {
            throw new Exception("Zmiana biegu wymaga wcisnietego sprzegla.");
        }
    }
}
