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
        if (joukko[Integer.hashCode(lisattava)] == -5) {
            joukko[Integer.hashCode(lisattava)] = lisattava;
            koko++;
        }
    }

    public int koko() {
        return this.koko;
    }

    public void poista(int poistettava) {
        if (Integer.hashCode(poistettava) < joukko.length) {
            if (joukko[Integer.hashCode(poistettava)] != -5) {
                joukko[Integer.hashCode(poistettava)] = -5;
                koko--;
            }
        }
    }

    public boolean sisaltaa(int luku) {
        return joukko[Integer.hashCode(luku)] != -5;
    }
}
