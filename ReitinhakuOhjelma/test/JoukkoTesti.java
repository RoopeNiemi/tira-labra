
import static junit.framework.Assert.assertTrue;
import org.junit.Test;
import reitinhakuohjelma.rakenne.Joukko;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author User
 */
public class JoukkoTesti {

    public JoukkoTesti() {

    }

    @Test
    public void sisaltaaTest() {
        Joukko joukko = new Joukko();
        assertTrue(!joukko.sisaltaa(10));
        joukko.lisaa(10);
        assertTrue(joukko.sisaltaa(10));
    }

    @Test
    public void testaaLisays() {
        Joukko joukko = new Joukko();
        joukko.lisaa(1);
        assertTrue(joukko.koko() == 1);
        joukko.lisaa(1);
        assertTrue(joukko.koko() == 1);
        joukko.lisaa(2);
        assertTrue(joukko.koko() == 2);
    }

    @Test
    public void testaaPoisto() {
        Joukko joukko = new Joukko();
        joukko.lisaa(10);
        joukko.poista(10);
        assertTrue(!joukko.sisaltaa(10));
    }

    @Test
    public void testaaKoko() {
        Joukko joukko = new Joukko();
        joukko.lisaa(15);
        assertTrue(joukko.koko() == 1);
        joukko.poista(15);
        assertTrue(joukko.koko() == 0);
    }

}
