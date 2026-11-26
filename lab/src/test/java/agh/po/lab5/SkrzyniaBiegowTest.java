package agh.po.lab5;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SkrzyniaBiegowTest {

    @Test
    public void zwiekszenieBieguBezSprzeglaZglaszaWyjatek() throws Exception {
        SkrzyniaBiegow skrzynia = new SkrzyniaBiegow("", "", 5);

        try {
            skrzynia.zwiekszBieg();
        } catch (Exception expected) {
            assertEquals(0, skrzynia.aktualnyBieg);
            return;
        }

        throw new AssertionError("Zmiana biegu bez sprzegla powinna zglosic wyjatek.");
    }

    @Test
    public void zmniejszenieBieguBezSprzeglaZglaszaWyjatek() throws Exception {
        SkrzyniaBiegow skrzynia = new SkrzyniaBiegow("", "", 5);
        skrzynia.aktualnyBieg = 1;

        try {
            skrzynia.zmniejszBieg();
        } catch (Exception expected) {
            assertEquals(1, skrzynia.aktualnyBieg);
            return;
        }

        throw new AssertionError("Zmiana biegu bez sprzegla powinna zglosic wyjatek.");
    }

    @Test
    public void zmieniaBiegPoWcisnieciuSprzegla() throws Exception {
        SkrzyniaBiegow skrzynia = new SkrzyniaBiegow("", "", 5);
        skrzynia.sprzeglo.wcisnij();

        skrzynia.zwiekszBieg();
        assertEquals(1, skrzynia.aktualnyBieg);

        skrzynia.zmniejszBieg();
        assertEquals(0, skrzynia.aktualnyBieg);
    }
}
