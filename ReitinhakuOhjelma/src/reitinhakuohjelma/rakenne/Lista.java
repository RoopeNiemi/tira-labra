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
public class Lista<T> {

    private T[] array;
    private int loppu = 0;

    public Lista() {
        this.array = (T[]) new Object[10];
        loppu = 0;
    }

    public int arraynKoko() {
        return this.array.length;
    }

    public void tulostaLista() {
        for (int i = 0; i < array.length; i++) {
            System.out.println(array[i]);
        }
    }

    private void kopioiToisestaListasta(T[] a) {
        System.arraycopy(array, 0, a, 0, array.length);
    }

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

    public T get(int i) {
        if (i < koko()) {
            return array[i];
        }
        throw new IndexOutOfBoundsException();

    }

    public int koko() {
        return this.loppu;
    }

    public void poistaIndeksi(int index) {
        array[index] = null;
        pienenna();
    }

    private void pienenna() {
        int nulls = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i] == null) {
                nulls++;
            }
        }
        T[] copy = (T[]) new Object[array.length - nulls];
        int copyIndex = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i] != null) {
                copy[copyIndex] = array[i];
                copyIndex++;
            }
        }
        array = copy;
        loppu--;
    }

    @Override
    public String toString() {
        return Arrays.toString(array);
    }
}
