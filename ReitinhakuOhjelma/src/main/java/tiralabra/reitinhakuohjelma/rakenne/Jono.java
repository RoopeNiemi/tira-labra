package tiralabra.reitinhakuohjelma.rakenne;

/**
 *
 * @author User
 * @param <T> Jonon sisältämien alkioiden luokka
 */
public class Jono<T> {

    private T[] jono;
    private int eka, vika, koko;

    /**
     *
     */
    public Jono() {
        this.koko = 10;
        this.eka = 0;
        this.vika = 0;
        this.jono = (T[]) new Object[10];
    }

    /**
     * Kopioi jonon alkiot toiseen arrayhin.
     */
    private void kopioiToisestaListasta(T[] a) {
        for (int i = 0; i < vika; i++) {
            a[i] = jono[i];
        }
    }

    /**
     * Lisää jonon perälle annetun alkion
     *
     * @param a jonoon lisättävä alkio
     */
    public void lisaaJonoon(T a) {

        jono[vika] = a;
        vika++;
        if (vika == koko) {
            kasvata();
        }
    }

    /**
     * Tyhjentää jonon ja asettaa sen taustalla olevan arrayn kooksi 10
     */
    public void tyhjennaJono() {
        this.eka = 0;
        this.vika = 0;
        this.jono = (T[]) new Object[10];
        this.koko = 10;
    }

    /**
     * Poistaa annetun alkion jonosta. Jos jono on tyhjä ei tee mitään.
     *
     * @return jonosta poistettu alkio
     */
    public T poistaJonosta() {

        T e = jono[eka];

        eka++;
        if (eka == koko) {
            eka = 0;
        }
        return e;

    }

    public int getArraynKoko() {
        return this.koko;
    }

    /**
     * tarkistaa onko jono tyhjä
     *
     * @return true, jos jono on tyhjä, muuten false
     */
    public boolean onTyhja() {
        return this.eka == this.vika;
    }

    /**
     *
     * @return jonon ensimmäisen alkion. Ei poista sitä jonosta.
     */
    public T peek() {
        return jono[eka];
    }

    /**
     *
     * Luo tyhjän arrayn mikä on kaksinkertainen pituudeltaan nykyiseen jonon
     * arrayhin nähden. Kopioi jonon alkiot tähän ja asettaa tämän jonon uudeksi
     * arrayksi.
     */
    private void kasvata() {
        T[] kopio = (T[]) new Object[jono.length * 2];
        kopioiToisestaListasta(kopio);
        jono = kopio;
        koko = jono.length;

    }

    public int getKoko() {
        return vika - eka;
    }

}
