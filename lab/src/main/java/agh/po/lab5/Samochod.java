package agh.po.lab5;

public class Samochod {
    Silnik silnik;
    SkrzyniaBiegow skrzyniaBiegow;
    Pozycja pozycja;

    public Samochod() {
        silnik = new Silnik();
        skrzyniaBiegow = new SkrzyniaBiegow();
        pozycja = new Pozycja();
    }

    public void wlacz() {
        silnik.uruchom();
    }

    public void wylacz() {
        silnik.zatrzymaj();
        skrzyniaBiegow.aktualnyBieg = 0;
    }

}
