/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tiralabra.reitinhakuohjelma.rakenne;

/**
 *
 * @author User
 */
public class Joukko {

    private int[] joukko = new int[412];
    private int koko = 0;

    /**
     * Luo uuden joukon ja alustaa sen arvoilla -5.
     */
    public Joukko() {
        alusta();
    }

    /**
     *
     *  Asettaa joukon arrayn jokaiseen kohtaan arvoksi -5.
     */
    private void alusta() {
        for (int i = 0; i < joukko.length; i++) {
            joukko[i] = -5;
        }
        koko = 0;
    }

    /**
     *
     * @param lisattava joukkoon lisättävä luku. Jos lukua ei jo ole joukossa,
     * lisätään se oikeaan kohtaan joukkoon ja lisätään joukon kokoa yhdellä.
     */
    public void lisaa(int lisattava) {
        if (joukko[lisattava] == -5) {
            joukko[lisattava] = lisattava;
            koko++;
        }
    }

    /**
     *
     * @return joukon sisältämien lukujen määrä
     */
    public int koko() {
        return this.koko;
    }

    /**
     * Poistaa numeron joukosta jos se on valmiiksi siellä, ja pienentää joukon
     * kokoa yhdellä. Ei tee mitään jos lukua ei ole joukossa.
     *
     * @param poistettava numero joka poistetaan joukosta.
     */
    public void poista(int poistettava) {
        if (poistettava < joukko.length) {
            if (joukko[poistettava] != -5) {
                joukko[poistettava] = -5;
                koko--;
            }
        }
    }

    /**
     *
     * @param luku luku, jonka olemassaolo joukossa halutaan selvittää.
     * @return palauttaa true jos joukko sisältää parametrina annetun luvun. Muuten false.
     */
    public boolean sisaltaa(int luku) {
        return joukko[luku] != -5;
    }
}
