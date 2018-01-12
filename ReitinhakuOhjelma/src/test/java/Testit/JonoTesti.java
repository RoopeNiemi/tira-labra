package Testit;


import static org.junit.Assert.assertTrue;
import org.junit.Test;
import tiralabra.reitinhakuohjelma.rakenne.Jono;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author User
 */
public class JonoTesti {

    public JonoTesti() {

    }

    @Test
    public void testaaLisays() {
        Jono<Integer> jono = new Jono<>();
        jono.lisaaJonoon(1);
        assertTrue(jono.peek() == 1);
    }
    
    @Test
    public void testaaKoko(){
        Jono<Integer>jono=new Jono<>();
        jono.lisaaJonoon(1);
        assertTrue(jono.getKoko()==1);
    }

    @Test
    public void testaaPoisto() {
        Jono<Integer> jono = new Jono<>();
        jono.lisaaJonoon(1);
        jono.poistaJonosta();
        assertTrue(jono.onTyhja());
    }

    @Test
    public void testaaJarjestys() {
        Jono<Integer> jono = new Jono<>();
        for (int i = 0; i < 4; i++) {
            jono.lisaaJonoon(i);
        }
        assertTrue(jono.poistaJonosta() == 0);
        assertTrue(jono.poistaJonosta() == 1);
        assertTrue(jono.poistaJonosta() == 2);
    }

    @Test
    public void testaaArraynKasvatus() {
        Jono<Integer> jono = new Jono<>();
        for (int i = 0; i < 11; i++) {
            jono.lisaaJonoon(i);
        }
        assertTrue(jono.getArraynKoko() == 20);
        for (int i = 0; i < 11; i++) {
            jono.lisaaJonoon(10 + i);
        }
        assertTrue(jono.getArraynKoko() == 40);
    }

    @Test
    public void testaaTyhjennys() {
        Jono<Integer> jono = new Jono<>();
        for (int i = 0; i < 11; i++) {
            jono.lisaaJonoon(i);
        }
        jono.tyhjennaJono();
        assertTrue(jono.onTyhja());
    }
}
