/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tiralabra.reitinhakuohjelma.rakenne;

import tiralabra.reitinhakuohjelma.verkko.Solmu;

/**
 *
 * @author User
 */
public class Prioriteettijono {

    private Solmu[] taulukko;
    private int koko;

    public Prioriteettijono() {
        this.taulukko = new Solmu[10];
    }

    /**
     * laskee indeksin vasemman lapsen indeksin
     *
     * @param i indeksi jonka vasemman lapsen sijainti halutaan tietää
     * @return vasemman lapsen indeksi
     */
    public int vasen(int i) {
        return 2 * i;
    }

    /**
     * laskee indeksin oikean lapsen indeksin
     *
     * @param i indeksi jonka oikean lapsen sijainti halutaan tietää
     * @return oikean lapsen indeksi
     */
    public int oikea(int i) {
        return (2 * i) + 1;
    }

    /**
     * laskee indeksin vanhemman indeksin
     *
     * @param i indeksi jonka vanhemman indeksi halutaan tietää
     * @return vanhemman indeksi
     */
    public int vanhempi(int i) {
        return i / 2;
    }

    /**
     *
     * @return true, jos prioriteettijono on tyhjä, muuten false.
     */
    public boolean onTyhja() {
        return this.koko == 0;
    }

    /**
     *
     * @param i indeksi josta heapify aloitetaan. Etsii ensin vasemman lapsen ja
     * oikean lapsen indeksin. Jos oikean lapsen indeksi pysyy keon koon
     * sisällä, selvittää kumpi lapsi on pienempi, ja vaihtaa indeksin i alkion
     * ja lapsista pienimmän alkioiden paikkaa keskenään. Kutsuu rekursiivisesti
     * heapifyta indeksillä, johon indeksin i alkio vaihdettiin. Jos oikea lapsi
     * ei ole keon koon sisällä, vertaa onko vasen lapsi keon koon sisällä. Jos
     * on, vaihtaa indeksissä i olevan alkion paikkaa vasemman lapsen kanssa,
     * jos vasen lapsi on pienempi
     */
    public void heapify(int i) {
        int vasen = vasen(i);
        int oikea = oikea(i);
        int pienin = 0;
        if (oikea <= koko) {
            if (taulukko[vasen].compareTo(taulukko[oikea]) == -1) {
                pienin = vasen;
            } else {
                pienin = oikea;
            }
            if (taulukko[i].compareTo(taulukko[pienin]) == 1) {
                Solmu temp = taulukko[i];
                taulukko[i] = taulukko[pienin];
                taulukko[pienin] = temp;
                heapify(pienin);
            }
        } else if (vasen == koko && taulukko[i].compareTo(taulukko[vasen]) == 1) {
            Solmu temp = taulukko[i];
            taulukko[i] = taulukko[vasen];
            taulukko[vasen] = temp;
        }
    }

    /**
     *
     * @return
     */
    public Solmu poistaPienin() {
        if (!onTyhja()) {
            Solmu pienin = taulukko[1];
            taulukko[1] = taulukko[koko];
            koko--;
            heapify(1);
            return pienin;
        }
        throw new IndexOutOfBoundsException("Keko on tyhjä");
    }

    /**
     * lisää keon kokoa yhdellä, lisää annetun solmun keon perälle. Jos keon
     * koko on yksi lisäyksen jälkeen, lopettaa metodin toiminnan. Jos keon koko
     * on enemmän kuin yksi, vaihdetaan lisätty alkio vanhempansa kanssa kunnes
     * kekoehto ei rikkoudu.
     *
     * @param s kekoon lisättävä solmu
     */
    public void lisaaKekoon(Solmu s) {

        koko++;
        if (koko == 1) {
            taulukko[koko] = s;
            return;
        }
        if (koko >= taulukko.length) {
            kasvata();
        }
        int i = koko;
        while (i > 1 && taulukko[vanhempi(i)].compareTo(s) == 1) {
            taulukko[i] = taulukko[vanhempi(i)];
            i = vanhempi(i);
        }
        taulukko[i] = s;
    }

    /**
     *
     * @return prioriteettijonon koko integerinä.
     */
    public int koko() {
        return this.koko;
    }

    /**
     *
     * kaksinkertaistaa prioriteettijonon taustalla olevan arrayn koon, kopioi
     * prioriteettijonon tähän uuteen arrayhin ja asettaa tämän
     * prioriteettijonon uudeksi tausta-arrayksi.
     */
    private void kasvata() {
        Solmu[] kopio = new Solmu[2 * taulukko.length];
        kopioiToisestaListasta(kopio);
        taulukko = kopio;
    }

    /**
     *
     * kopioi prioriteettijonon sisällön toiseen arrayhin.
     *
     * @param a array johon prioriteettijono kopioidaan
     */
    public void kopioiToisestaListasta(Solmu[] a) {
        for (int i = 0; i < koko; i++) {
            a[i] = taulukko[i];
        }
    }

}
