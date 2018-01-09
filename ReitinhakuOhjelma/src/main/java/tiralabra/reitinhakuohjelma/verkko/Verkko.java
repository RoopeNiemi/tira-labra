/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tiralabra.reitinhakuohjelma.verkko;

import tiralabra.reitinhakuohjelma.Reitinhakija;
import javafx.scene.paint.Color;
import tiralabra.reitinhakuohjelma.rakenne.*;

/**
 *
 * @author User
 */
public class Verkko {

    private Solmu[] verkko = new Solmu[412];
    private Lista<Kaari> kaaret = new Lista<>();
    private Pino<Solmu> viimeisinReitti = new Pino<>();
    private Lista<Long> hakuajat = new Lista<>();
    private Reitinhakija reitinhakija;

    /**
     *
     */
    public Verkko() {
        reitinhakija = new Reitinhakija(this);
    }

    public Reitinhakija getReitinhakija() {
        return this.reitinhakija;
    }

    public Pino<Solmu> viimeisinHaku() {
        return this.viimeisinReitti;
    }

    public void lisaaKaari(Kaari k) {
        kaaret.lisaa(k);
    }

    public Jono<Solmu> hae(Solmu aloitus, Solmu etsittava, boolean hakutapa) {
        Jono<Solmu> palautettava = reitinhakija.lyhinReitti(aloitus, etsittava, hakutapa);
        this.viimeisinReitti = reitinhakija.getLyhinReitti();
        return palautettava;
    }

    /**
     * Resetoi lyhimmän reitin pinon sekä maalausjonon.
     */
    public void resetoiMaalausjono() {
        this.viimeisinReitti.tyhjenna();
        this.reitinhakija.resetoiProsessijono();
    }

    public Jono<Solmu> lisaaReittiMaalausjonoon(Jono<Solmu> s) {
        while (!viimeisinReitti.onTyhja()) {
            Solmu lisattava = viimeisinReitti.pop();
            lisattava.setColor(Color.BLUE);
            s.lisaaJonoon(lisattava);
        }
        return s;
    }

    /**
     * Lisää verkolle solmun
     *
     * @param x solmun x-koordinaatti
     * @param y solmun y-koordinaatti
     * @param s solmun nimi
     * @param arvo solmun arvo
     */
    public void lisaaSolmu(double x, double y, String s, int arvo) {
        verkko[arvo] = new Solmu(arvo, x, y, s);
    }

    public Solmu etsiSolmuNimella(String nimi) {
        if (nimi.equalsIgnoreCase("null")) {
            return null;
        }
        for (int i = 0; i < 412; i++) {
            Solmu s = verkko[i];
            if (s.getNimi().equalsIgnoreCase(nimi)) {
                return s;
            }
        }
        return null;
    }

    /**
     * Laskee kahden pisteen välisen etäisyyden kaavalla c^2=a^2+b^2, missä
     * a=itseisarvo erotuksesta solmun2 x-koordinaatti-solmun1 x-koordinaatti,
     * ja b=itseisarvo erotuksesta solmun2 y-koordinaatti-solmun1
     * y-koordinaatti. Lopuksi palautetaan c:n neliöjuuri, joka on siis nyt
     * etäisyys pikseleinä.
     *
     * @param x1 solmun 1 x-koordinaatti
     * @param y1 solmun 1 y-koordinaatti
     * @param x2 solmun 2 x-koordinaatti
     * @param y2 solmun 2 y-koordinaatti
     * @return solmujen välinen etäisyys
     */
    public double laskeEtaisyys(double x1, double y1, double x2, double y2) {
        double y3 = Math.abs(y2 - y1);
        double x3 = Math.abs(x2 - x1);
        double length = Math.sqrt(Math.pow(x3, 2) + Math.pow(y3, 2));
        return length;

    }

    public Solmu etsiSolmuArvolla(int arvo) {
        for (int i = 0; i < 412; i++) {
            Solmu s = verkko[i];
            if (s.getArvo() == arvo) {
                return s;
            }
        }
        return null;
    }

    public Solmu[] getSolmut() {
        return this.verkko;
    }

}
