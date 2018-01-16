/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Testit;

import static org.junit.Assert.assertTrue;
import org.junit.Test;
import tiralabra.reitinhakuohjelma.verkko.Solmu;
import tiralabra.reitinhakuohjelma.verkko.Verkko;

/**
 *
 * @author User
 */
public class VerkkoTesti {

    public VerkkoTesti() {

    }

    @Test
    public void solmunLisaysJaHakuArvolla() {
        Verkko verkko = new Verkko();
        verkko.lisaaSolmu(0, 0, "helsinki", 15);
        assertTrue("helsinki".equals(verkko.etsiSolmuArvolla(15).getNimi()));
    }

    @Test
    public void hakuNimella() {
        Verkko verkko = new Verkko();
        verkko.lisaaSolmu(0, 0, "helsinki", 15);
        assertTrue(verkko.etsiSolmuNimella("helsinki").getArvo() == 15);
    }

    @Test
    public void etaisyydenLaskuToimii() {
        Verkko verkko = new Verkko();
        Solmu solmu = new Solmu(1, 0, 4, "helsinki");
        Solmu verrattava = new Solmu(2, 3, 0, "vantaa");
        assertTrue(verkko.laskeEtaisyys(solmu.getX(), solmu.getY(), verrattava.getX(), verrattava.getY()) == 5.0);

    }

}
