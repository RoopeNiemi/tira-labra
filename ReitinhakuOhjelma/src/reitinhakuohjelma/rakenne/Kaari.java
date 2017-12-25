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
    private long paino;

    public Kaari(Solmu l, Solmu p, long paino) {
        this.lahtoSolmu = l;
        this.paateSolmu = p;
        this.paino = paino;
    }

    public Solmu getLahtoSolmu() {
        return lahtoSolmu;
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

    public long getPaino() {
        return paino;
    }

    public void setPaino(long paino) {
        this.paino = paino;
    }
    
    
}
