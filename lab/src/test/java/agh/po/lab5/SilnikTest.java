package agh.po.lab5;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SilnikTest {

    @Test
    public void ograniczaObrotyDoZakresuSilnika() {
        Silnik silnik = new Silnik("", "", 6000);
        silnik.uruchom();

        silnik.zwiekszObroty(10000);
        assertEquals(6000, silnik.obroty);

        silnik.zmniejszObroty(10000);
        assertEquals(0, silnik.obroty);
    }

    @Test
    public void nieUruchamiaSilnikaPonadMaksymalneObroty() {
        Silnik silnik = new Silnik("", "", 500);

        silnik.uruchom();

        assertEquals(500, silnik.obroty);
    }

    @Test(expected = IllegalArgumentException.class)
    public void odrzucaNieDodatniaZmianeObrotow() {
        new Silnik().zwiekszObroty(0);
    }
}
