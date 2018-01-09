package tiralabra.reitinhakuohjelma.verkko;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author User
 */
public class Kaari {

    private Solmu lahtoSolmu, paateSolmu;
    private double paino, nopeus, pituus;

    /**
     * Kaari solmusta l solmuun p. Kaaren pituus laskettu Verkko-luokan metodilla 
     * laskeEtaisyys. Kaaret luodaan tekstitiedostosta Kaaret.txt ohjelma käynnistettäessä. 
     * Kaikki kaaren parametrit on otettu suoraan tekstitiedostoista. 
     * @param l Lähtösolmu
     * @param p Päätesolmu
     * @param pituus Kaaren pituus
     * @param nopeus Kaaren nopeus
     */
    public Kaari(Solmu l, Solmu p, double pituus, double nopeus) {
        this.lahtoSolmu = l;
        this.paateSolmu = p;
        this.pituus = pituus;
        this.nopeus = nopeus;
        this.paino = 0;
    }

    /**
     *
     * @return
     */
    public Solmu getLahtoSolmu() {
        return lahtoSolmu;
    }

    /**
     *
     * @return
     */
    public double getNopeus() {
        return this.nopeus;
    }

    /**
     *
     * @param lahtoSolmu
     */
    public void setLahtoSolmu(Solmu lahtoSolmu) {
        this.lahtoSolmu = lahtoSolmu;
    }

    /**
     *
     * @return
     */
    public Solmu getPaateSolmu() {
        return paateSolmu;
    }

    /**
     *
     * @param paateSolmu
     */
    public void setPaateSolmu(Solmu paateSolmu) {
        this.paateSolmu = paateSolmu;
    }

    /**
     * palauttaa kaaren painon, lasketaan kaavalla kaarenpituus/60*nopeus
     * @return
     */
    public double getPaino() {
        return pituus *(100 /nopeus);
    }

    /**
     *
     * @param paino
     */
    public void setPaino(long paino) {
        this.paino = paino;
    }

}
