/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Testit;

import java.util.Random;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import tiralabra.reitinhakuohjelma.MainApp;
import tiralabra.reitinhakuohjelma.rakenne.Lista;
import tiralabra.reitinhakuohjelma.rakenne.Pino;
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
    public void tuottavatSamanTuloksen() {
        MainApp paaOhjelma = new MainApp();
        Verkko verkko = new Verkko();
        paaOhjelma.lataaSolmut(verkko);
        paaOhjelma.lataaKaaret(verkko);
        Lista<String> kaupungit = paaOhjelma.haeKaupungit();
        /*
        for (int i = 0; i < kaupungit.koko(); i++) {
            for (int j = 0; j < kaupungit.koko(); j++) {*/
        Solmu a = verkko.etsiSolmuNimella(kaupungit.get(new Random().nextInt(kaupungit.koko())));
        Solmu b = verkko.etsiSolmuNimella(kaupungit.get(new Random().nextInt(kaupungit.koko())));

        verkko.shortestPath(a, b);
        Pino<Solmu> DijkstraPino = verkko.haeLyhinReitti();
        verkko.AStarShortestPath(a, b);
        Pino<Solmu> AStarPino = verkko.haeLyhinReitti();
        if (DijkstraPino.koko() != AStarPino.koko()) {
            assertTrue(false);
        }
        while (!DijkstraPino.onTyhja()) {
            if (DijkstraPino.pop().getArvo() != AStarPino.pop().getArvo()) {
                assertTrue(false);
            }
        }
        /*
            }
        }*/
        assertTrue(true);
    }

    @Test
    public void AStarNopeampi() {
        MainApp paaOhjelma = new MainApp();
        Verkko verkko = new Verkko();
        paaOhjelma.lataaSolmut(verkko);
        paaOhjelma.lataaKaaret(verkko);
        Lista<String> kaupungit = paaOhjelma.haeKaupungit();
        /*
        for (int i = 0; i < kaupungit.koko(); i++) {
            for (int j = 0; j < kaupungit.koko(); j++) {*/
        Solmu a = verkko.etsiSolmuNimella(kaupungit.get(new Random().nextInt(kaupungit.koko())));
        Solmu b = verkko.etsiSolmuNimella(kaupungit.get(new Random().nextInt(kaupungit.koko())));

        verkko.shortestPath(a, b);
        int DijkstraKaydyt = verkko.getKaydyt().koko();
        verkko.AStarShortestPath(a, b);
        int AStarKaydyt = verkko.getKaydyt().koko();
        if (AStarKaydyt > DijkstraKaydyt) {
            assertTrue(false);
        }/*
            }
        }*/
        assertTrue(true);
    }
}