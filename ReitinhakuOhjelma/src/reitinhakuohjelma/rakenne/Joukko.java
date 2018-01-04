/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reitinhakuohjelma.rakenne;

/**
 *
 * @author User
 */
public class Joukko {

    private int[] joukko = new int[412];
    private int koko = 0;

    public Joukko() {
        alusta();
    }

    private void alusta() {
        for (int i = 0; i < joukko.length; i++) {
            joukko[i] = -5;
        }
        koko = 0;
    }

    public void lisaa(int lisattava) {
        if (joukko[lisattava] == -5) {
            joukko[lisattava] = lisattava;
            koko++;
        }
    }

    public int koko() {
        return this.koko;
    }

    public void poista(int poistettava) {
        if (poistettava < joukko.length) {
            if (joukko[poistettava] != -5) {
                joukko[poistettava] = -5;
                koko--;
            }
        }
    }

    public boolean sisaltaa(int luku) {
        return joukko[luku] != -5;
    }
}
