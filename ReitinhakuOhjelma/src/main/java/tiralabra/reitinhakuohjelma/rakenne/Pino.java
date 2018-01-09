/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tiralabra.reitinhakuohjelma.rakenne;

/**
 *
 * @author User
 * @param <T> Pinon sisältämien alkioiden luokka
 */
public class Pino<T> {

    private T[] array;

    private int paallimmainen = -1;

    public Pino() {
        this.array = (T[]) new Object[10];
    }

    /**
     *
     * @param a lisää pinon päällimmäiseksi alkion a
     */
    public void push(T a) {
        paallimmainen++;
        if (paallimmainen >= array.length) {
            kasvata();
        }
        array[paallimmainen] = a;

    }

    /**
     *
     * @return integer pinon taustalla olevan arrayn koko. Voi olla suurempi
     * kuin pinon koko
     */
    public int arraynKoko() {
        return this.array.length;
    }

    /**
     *
     * @return integer pinon koko
     */
    public int koko() {
        return this.paallimmainen + 1;
    }

    /**
     * tyhjentää pinon
     */
    public void tyhjenna() {
        this.array = (T[]) new Object[10];
        this.paallimmainen = -1;
    }

    /**
     *
     * @return true jos pino tyhjä, muuten false
     */
    public boolean onTyhja() {
        return this.paallimmainen == -1;
    }

    /**
     *  Poistaa pinon päällimmäisen alkion
     * @return pinon päällimmäisen alkion. Jos pino tyhjä, heittää poikkeuksen.
     */
    public T pop() {
        if (onTyhja()) {
            throw new IndexOutOfBoundsException("Pino on tyhjä");
        }

        T a = this.array[paallimmainen];
        paallimmainen--;
        return a;

    }

    /**
     *
     * @return pinon päällimmäisen alkion. Ei poista sitä pinosta.
     */
    public T peek() {
        if (onTyhja()) {
            throw new IndexOutOfBoundsException("Pino on tyhjä");
        }
        T a = this.array[paallimmainen];
        return a;
    }

    /**
     * kasvattaa pinon arrayn kokoa kaksinkertaiseksi
     */
    private void kasvata() {
        T[] kopio = (T[]) new Object[array.length * 2];
        kopioiToisestaListasta(kopio);
        array = kopio;

    }

    /**
     * kopioi pinon alkiot toiseen arrayhin
     *
     * @param a array johon pinon alkiot kopioidaan
     */
    public void kopioiToisestaListasta(T[] a) {
        for (int i = 0; i < paallimmainen; i++) {
            a[i] = array[i];
        }
    }

}
