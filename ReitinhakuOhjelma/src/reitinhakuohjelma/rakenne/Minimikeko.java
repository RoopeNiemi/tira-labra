/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reitinhakuohjelma.rakenne;

import verkko.Solmu;

/**
 *
 * @author User
 */
public class Minimikeko {

    private Solmu[] taulukko;
    private int koko;

    public Minimikeko() {
        this.taulukko = new Solmu[10];
    }

    public int vasen(int i) {
        return 2 * i;

    }

    public int oikea(int i) {
        return (2 * i) + 1;
    }

    public int vanhempi(int i) {
        return i / 2;
    }

    public boolean onTyhja() {
        return this.koko == 0;
    }

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
        } else if (vasen == koko  && taulukko[i].compareTo(taulukko[vasen]) == -1) {
            Solmu temp = taulukko[i];
            taulukko[i] = taulukko[vasen];
            taulukko[vasen] = temp;
        }
    }

    public Solmu poistaPienin() {
        Solmu pienin = taulukko[1];
        taulukko[1] = taulukko[koko];
        koko--;
        heapify(1);
        return pienin;
    }

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

    public int koko() {
        return this.koko;
    }

    private void kasvata() {
        Solmu[] kopio = new Solmu[2 * taulukko.length];
        kopioiToisestaListasta(kopio);
        taulukko = kopio;
    }

    private void kopioiToisestaListasta(Solmu[] a) {
        System.arraycopy(taulukko, 0, a, 0, taulukko.length);
    }

}
