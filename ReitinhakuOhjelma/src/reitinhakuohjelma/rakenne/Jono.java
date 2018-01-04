/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reitinhakuohjelma.rakenne;

import java.util.Arrays;

/**
 *
 * @author User
 */
public class Jono<T> {

    private T[] jono;
    private int eka, vika, koko;

    public Jono() {
        this.koko = 10;
        this.eka = 0;
        this.vika = 0;
        this.jono = (T[]) new Object[10];
    }

    private void kopioiToisestaListasta(T[] a) {
        System.arraycopy(jono, 0, a, 0, jono.length);
    }

    public void lisaaJonoon(T i) {

        jono[vika] = i;
        vika++;
        if (vika == koko) {
            kasvata();
        }
    }

 

    public void tyhjennaJono() {
        this.eka = 0;
        this.vika = 0;
        this.jono = (T[]) new Object[10];
        this.koko=10;
    }

    public T poistaJonosta() {

        T e = jono[eka];

        eka++;
        if (eka == koko) {
            eka = 0;
        }
        return e;

    }
    
    public int getArraynKoko(){
        return this.koko;
    }

    public boolean onTyhja() {
        return this.eka == this.vika;
    }

    public boolean taynna() {
        int tailnext = vika + 1;
        if (tailnext == koko) {
            tailnext = 0;

        }
        return eka == tailnext;
    }

    public T peek() {
        return jono[eka];
    }

    private void kasvata() {
        T[] kopio = (T[]) new Object[jono.length * 2];
        kopioiToisestaListasta(kopio);
        jono = kopio;
        koko = jono.length;

    }
    
    public int getKoko(){
        return this.jono.length;
    }

    @Override
    public String toString() {
        return Arrays.toString(jono);
    }

}

