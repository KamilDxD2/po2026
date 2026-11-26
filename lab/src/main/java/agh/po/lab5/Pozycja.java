package agh.po.lab5;

public class Pozycja {
    private double x;
    private double y;

    public Pozycja() {
        this(0, 0);
    }

    public Pozycja(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public String getPozycja() {
        return "(" + x + ", " + y + ")";
    }

    public void aktualizujPozycje(double deltaX, double deltaY) {
        this.x += deltaX;
        this.y += deltaY;
    }

    public void przemiesc(double dt, double V, Pozycja cel) {
        double deltaX = cel.x - this.x;
        double deltaY = cel.y - this.y;
        double odleglosc = Math.hypot(deltaX, deltaY);
        double dlugoscKroku = dt * V;

        if (odleglosc == 0) {
            return;
        }

        if (odleglosc <= dlugoscKroku) {
            this.x = cel.x;
            this.y = cel.y;
            return;
        }

        aktualizujPozycje(
                deltaX / odleglosc * dlugoscKroku,
                deltaY / odleglosc * dlugoscKroku
        );
    }

}
