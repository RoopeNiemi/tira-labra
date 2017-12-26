package reitinhakuohjelma.rakenne;

import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author User
 */
public class Solmu implements Comparable<Solmu> {

    private int arvo;
    private double etaisyys;
    private ArrayList<Kaari> vieruslista;
    private double x, y;
    private String nimi = "";

    public Solmu(int arvo, double x, double y, String nimi) {
        this.etaisyys = Long.MAX_VALUE;
        this.vieruslista = new ArrayList<>();
        this.x = x;
        this.y = y;
        this.nimi = nimi;
        this.arvo = arvo;
    }

    public int getArvo() {
        return arvo;
    }

    public String getNimi() {
        if(nimi.isEmpty()){
            return "NULL";
        }
        return this.nimi;
    }

    public double getX(){
        return this.x;
    }
    
    public double getY(){
        return this.y;
    }
    
    public void setArvo(int arvo) {
        this.arvo = arvo;
    }

    public double getEtaisyys() {
        return etaisyys;
    }

    public void setEtaisyys(double etaisyys) {
        this.etaisyys = etaisyys;
    }

    public ArrayList<Kaari> getVieruslista() {
        return vieruslista;
    }

    public void lisaaViereinenSolmu(Solmu s, double paino,double kaarenNopeus) {
        vieruslista.add(new Kaari(this, s, paino,kaarenNopeus));
    }

    @Override
    public int compareTo(Solmu t) {
        double erotus = this.getEtaisyys() - t.getEtaisyys();
        if (erotus > 0) {
            return 1;
        } else if (erotus == 0) {
            return 0;
        }
        return -1;
    }

}
