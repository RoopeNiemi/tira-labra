package reitinhakuohjelma.rakenne;

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
    private double paino,nopeus,pituus;

    public Kaari(Solmu l, Solmu p, double pituus,double nopeus) {
        this.lahtoSolmu = l;
        this.paateSolmu = p;
        this.pituus = pituus;
        this.nopeus=nopeus;
        this.paino=0;
    }
    
    

    public Solmu getLahtoSolmu() {
        return lahtoSolmu;
    }

    public double getNopeus(){
        return this.nopeus;
    }
    
    public void setLahtoSolmu(Solmu lahtoSolmu) {
        this.lahtoSolmu = lahtoSolmu;
    }

    public Solmu getPaateSolmu() {
        return paateSolmu;
    }

    public void setPaateSolmu(Solmu paateSolmu) {
        this.paateSolmu = paateSolmu;
    }

    public double getPaino() {
        return pituus/60*nopeus;
    }

    public void setPaino(long paino) {
        this.paino = paino;
    }
    
    
}
