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
public class Pino<T> {

    private T[] array;
    private int koko = 0;
    private int paallimmainen = -1;

    public Pino() {
        this.array = (T[]) new Object[10];
    }

    public void push(T a) {
        paallimmainen++;
        if (paallimmainen >= array.length) {
            kasvata();
        }
        array[paallimmainen] = a;

    }

    public int arraynKoko() {
        return this.array.length;
    }

    public void tyhjenna() {
        this.array = (T[]) new Object[10];
        this.koko = 0;
        this.paallimmainen = -1;
    }

    public boolean onTyhja() {
        return this.paallimmainen == -1;
    }

    public T pop() {
        if (onTyhja()) {
            throw new IndexOutOfBoundsException("Pino on tyhjä");
        }

        T a = this.array[paallimmainen];
        paallimmainen--;
        return a;

    }

    public T peek() {
        if (onTyhja()) {
            throw new IndexOutOfBoundsException("Pino on tyhjä");
        }
        T a = this.array[paallimmainen];
        return a;
    }

    private void kasvata() {
        T[] kopio = (T[]) new Object[array.length * 2];
        kopioiToisestaListasta(kopio);
        array = kopio;

    }

    private void kopioiToisestaListasta(T[] a) {
        System.arraycopy(array, 0, a, 0, array.length);
    }

}
