package Testit;


import static org.junit.Assert.assertTrue;
import org.junit.Test;
import tiralabra.reitinhakuohjelma.rakenne.Prioriteettijono;
import tiralabra.reitinhakuohjelma.verkko.Solmu;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author User
 */
public class PrioriteettijonoTesti {

    public PrioriteettijonoTesti() {

    }

    @Test
    public void testaaTyhja() {
        Prioriteettijono jono = new Prioriteettijono();
        assertTrue(jono.onTyhja());
    }

    @Test
    public void testaaLisaysJaKoko() {
        Prioriteettijono jono = new Prioriteettijono();
        jono.lisaaKekoon(new Solmu(0, 0, 0, ""));
        assertTrue(jono.koko() == 1);
        jono.lisaaKekoon(new Solmu(1, 0, 0, ""));
        assertTrue(jono.koko() == 2);
    }

    @Test
    public void testaaPoisto(){
        Prioriteettijono jono= new Prioriteettijono();
        jono.lisaaKekoon(new Solmu(1,0,0,""));
        jono.poistaPienin();
        assertTrue(jono.onTyhja());
    }
    
    @Test
    public void testaaKekoehdonTarkistus() {
        Prioriteettijono jono = new Prioriteettijono();
        Solmu ensimmainen = new Solmu(0, 0, 0, "");
        ensimmainen.setEtaisyysLahdosta(1);
        jono.lisaaKekoon(ensimmainen);
        
        Solmu toinen = new Solmu(3, 0, 0, "");
        toinen.setEtaisyysLahdosta(2);
        jono.lisaaKekoon(toinen);
        assertTrue(jono.koko() == 2);
        jono.poistaPienin();
        
        assertTrue(jono.koko() == 1);
        assertTrue(jono.poistaPienin().getArvo() == 3);

    }

}
