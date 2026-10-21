package agh.po.lab3;

import org.junit.Test;
import static org.junit.Assert.*;

public class CodingBatTest {

    CodingBat bat = new CodingBat();

    @Test
    public void sleepIn() {
        assertEquals(true, bat.sleepIn(false, false));
        assertEquals(false, bat.sleepIn(true, false));
        assertEquals(true, bat.sleepIn(false, true));
    }

    @Test
    public void diff21() {
        assertEquals(2, bat.diff21(19));
        assertEquals(11, bat.diff21(10));
        assertEquals(0, bat.diff21(21));
        assertEquals(2, bat.diff21(22));
        assertEquals(8, bat.diff21(25));
    }

    @Test
    public void countEvens() {
        assertEquals(3, bat.countEvens(new int[]{2, 1, 2, 3, 4}));
        assertEquals(0, bat.countEvens(new int[]{1, 3, 5}));
        assertEquals(1, bat.countEvens(new int[]{2}));
    }

    @Test
    public void helloName() {
        assertEquals("Hello Bob!", bat.helloName("Bob"));
        assertEquals("Hello Alice!", bat.helloName("Alice"));
        assertEquals("Hello X!", bat.helloName("X"));
    }
}