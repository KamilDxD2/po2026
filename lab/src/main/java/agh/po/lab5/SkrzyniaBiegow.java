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

    public void zwiekszBieg() {
        if (aktualnyBieg < iloscBiegow) {
            aktualnyBieg++;
        }
    }

    public void zmniejszBieg() {
        if (aktualnyBieg > 0) {
            aktualnyBieg--;
        }
    }
}
