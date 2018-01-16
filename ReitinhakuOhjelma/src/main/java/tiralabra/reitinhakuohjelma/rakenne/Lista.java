/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tiralabra.reitinhakuohjelma.rakenne;

/**
 *
 * @author User
 * @param <T> Listan sisältämien alkioiden luokka
 */
public class Lista<T> {

    private T[] array;
    private int loppu = 0;

    public Lista() {
        this.array = (T[]) new Object[10];
        loppu = 0;
    }

    /**
     *
     * @return listan taustalla olevan arrayn koko integerinä. Voi olla suurempi
     * kuin listan koko.
     */
    public int arraynKoko() {
        return this.array.length;
    }

    /**
     * Kopioi listan sisällön toiseen arrayhin.
     *
     * @param a array johon listan sisältö kopioidaan
     */
    public void kopioiToisestaListasta(T[] a) {
        for (int i = 0; i < loppu; i++) {
            a[i] = array[i];
        }
    }

    /**
     * lisää listaan alkion. Jos alkio ei mahdu arrayhyn, arrayn koko
     * kaksinkertaistetaan jonka jälkeen alkio lisätään.
     *
     * @param a listaan lisättävä alkio
     */
    public void lisaa(T a) {
        if (loppu >= array.length) {
            T[] copy = (T[]) new Object[array.length * 2];
            kopioiToisestaListasta(copy);
            copy[loppu] = (T) a;
            array = copy;
            loppu++;
        } else {
            array[loppu] = a;
            loppu++;
        }

    }

    /**
     * palauttaa annetussa listan indeksissä olevan alkion, jos indeksi on
     * listan sisällä
     *
     * @param i indeksi josta alkio haetaan
     * @return annetussa indeksissä olevan alkion
     */
    public T get(int i) {
        if (i < koko()) {
            return array[i];
        }
        throw new IndexOutOfBoundsException();

    }

    public int koko() {
        return this.loppu;
    }

}
