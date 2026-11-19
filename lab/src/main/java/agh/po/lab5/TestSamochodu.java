package agh.po.lab5;

public class TestSamochodu {
    public static void main(String[] args) {
        Samochod samochod = new Samochod();

        samochod.wlacz();

        samochod.skrzyniaBiegow.iloscBiegow = 5;

        samochod.skrzyniaBiegow.zwiekszBieg();
        samochod.skrzyniaBiegow.zwiekszBieg();
        samochod.skrzyniaBiegow.zmniejszBieg();

        samochod.skrzyniaBiegow.sprzeglo.wcisnij();
        samochod.skrzyniaBiegow.sprzeglo.zwolnij();
    }
}