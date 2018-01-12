/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Testit;

import java.util.Random;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import tiralabra.reitinhakuohjelma.kayttoliittyma.MainApp;
import tiralabra.reitinhakuohjelma.rakenne.Lista;
import tiralabra.reitinhakuohjelma.rakenne.Pino;
import tiralabra.reitinhakuohjelma.verkko.Solmu;
import tiralabra.reitinhakuohjelma.verkko.Verkko;

/**
 *
 * @author User
 */
public class ReitinhakijaTesti {

    public ReitinhakijaTesti() {

    }

    @Test
    public void tuottavatSamanTuloksen() {
        MainApp paaOhjelma = new MainApp();
        Verkko verkko = new Verkko();
        paaOhjelma.lataaSolmut(verkko);
        paaOhjelma.lataaKaaret(verkko);
        Lista<String> kaupungit = paaOhjelma.haeKaupungit();

        for (int i = 0; i < kaupungit.koko(); i++) {
            for (int j = 0; j < kaupungit.koko(); j++) {
                Solmu a = verkko.etsiSolmuNimella(kaupungit.get(new Random().nextInt(kaupungit.koko())));
                Solmu b = verkko.etsiSolmuNimella(kaupungit.get(new Random().nextInt(kaupungit.koko())));

                verkko.getReitinhakija().lyhinReitti(a, b, true);
                Pino<Solmu> DijkstraPino = verkko.viimeisinHaku();
                verkko.getReitinhakija().lyhinReitti(a, b, false);
                Pino<Solmu> AStarPino = verkko.viimeisinHaku();
                if (DijkstraPino.koko() != AStarPino.koko()) {
                    assertTrue(false);
                }
                while (!DijkstraPino.onTyhja()) {
                    if (DijkstraPino.pop().getArvo() != AStarPino.pop().getArvo()) {
                        assertTrue(false);
                    }
                }

            }
        }
        assertTrue(true);
    }

    @Test
    public void AStarKayVahemmanSolmuja() {
        MainApp paaOhjelma = new MainApp();
        Verkko verkko = new Verkko();
        paaOhjelma.lataaSolmut(verkko);
        paaOhjelma.lataaKaaret(verkko);
        Lista<String> kaupungit = paaOhjelma.haeKaupungit();

        for (int i = 0; i < kaupungit.koko(); i++) {
            for (int j = 0; j < kaupungit.koko(); j++) {
                Solmu a = verkko.etsiSolmuNimella(kaupungit.get(new Random().nextInt(kaupungit.koko())));
                Solmu b = verkko.etsiSolmuNimella(kaupungit.get(new Random().nextInt(kaupungit.koko())));

                verkko.getReitinhakija().lyhinReitti(a, b, true);
                int DijkstraKaydyt = verkko.getReitinhakija().getKaydyt().koko();
                verkko.getReitinhakija().lyhinReitti(a, b, false);
                int AStarKaydyt = verkko.getReitinhakija().getKaydyt().koko();
                if (AStarKaydyt > DijkstraKaydyt) {
                    assertTrue(false);
                }

            }
            assertTrue(true);
        }
    }
}
