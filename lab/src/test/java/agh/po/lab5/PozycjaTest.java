package agh.po.lab5;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PozycjaTest {

    @Test
    public void przemieszczaOJedenKrokWKierunkuCelu() {
        Pozycja pozycja = new Pozycja(0, 0);

        pozycja.przemiesc(2, 1, new Pozycja(3, 4));

        assertEquals(1.2, pozycja.getX(), 0.000001);
        assertEquals(1.6, pozycja.getY(), 0.000001);
    }

    @Test
    public void niePrzemieszczaDalejNizDoCelu() {
        Pozycja pozycja = new Pozycja(0, 0);

        pozycja.przemiesc(10, 1, new Pozycja(3, 4));

        assertEquals(3, pozycja.getX(), 0);
        assertEquals(4, pozycja.getY(), 0);
    }
}
