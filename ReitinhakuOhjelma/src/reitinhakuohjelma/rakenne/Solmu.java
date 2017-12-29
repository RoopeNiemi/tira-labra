/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reitinhakuohjelma.rakenne;

import java.util.ArrayList;
import javafx.scene.paint.Color;

/**
 *
 * @author User
 */
public class Solmu implements Comparable<Solmu> {

    private int arvo;
    private double etaisyysLahdosta, etaisyysMaalista;
    private ArrayList<Kaari> vieruslista;
    private double x, y;
    private String nimi = "";
    private Color color = Color.WHITE;
    private boolean muoto = false;

    /**
     *
     * @param arvo Solmun arvo
     * @param x Solmun x-koordinaatti
     * @param y Solmun y-koordinaatti
     * @param nimi Solmun nimi
     */
    public Solmu(int arvo, double x, double y, String nimi) {
        this.etaisyysLahdosta = Long.MAX_VALUE;
        this.vieruslista = new ArrayList<>();
        this.x = x;
        this.y = y;
        this.nimi = nimi;
        this.arvo = arvo;
        this.etaisyysMaalista = 0;
    }

    /**
     *
     * @return solmun muoto, eli boolean arvo, jolla selvitetään kummalla tavalla
     * solmua verrataan toisiin solmuihin. Kun muoto on false verrataan toisiin solmuihin
     *  hyödyntäen vain etäisyys (etäisyys lähtösolmusta) attribuuttia. Kun true, hyödynnetään
     * vertailussa summaa (etäisyys lähtösolmusta+etäisyys maalisolmusta).
     */
    public boolean getMuoto() {
        return this.muoto;
    }

    /**
     *Asettaa solmun muoto-attribuutiksi annetun boolean arvon
     * @param b boolean arvo joksi muoto halutaan muuttaa
     */
    public void setMuoto(boolean b) {
        this.muoto = b;
    }


    public int getArvo() {
        return arvo;
    }

  
    public void setEtaisyysMaalista(double et) {
        this.etaisyysMaalista = et;
    }

  
    public double getEtaisyysMaalista() {
        return this.etaisyysMaalista;
    }

   
    public Color getColor() {
        return this.color;
    }

  
    public void setColor(Color c) {
        this.color = c;
    }

   
    public String getNimi() {
        if (nimi.isEmpty()) {
            return "NULL";
        }
        return this.nimi;
    }


    public double getX() {
        return this.x;
    }


    public double getY() {
        return this.y;
    }

 
    public void setArvo(int arvo) {
        this.arvo = arvo;
    }

    
    public double getEtaisyysLahdosta() {
        return etaisyysLahdosta;
    }

    public void setEtaisyysLahdosta(double etaisyys) {
        this.etaisyysLahdosta = etaisyys;
    }


    public ArrayList<Kaari> getVieruslista() {
        return vieruslista;
    }

    /**
     *
     * @param s lisättävä vierussolmu
     * @param pituus kaaren pituus
     * @param kaarenNopeus kaaren nopeus
     */
    public void lisaaViereinenSolmu(Solmu s, double pituus, double kaarenNopeus) {
        vieruslista.add(new Kaari(this, s, pituus, kaarenNopeus));
    }

    /**
     * Riippuen boolean muoto-attribuutista, vertailee joko vain etäisyys attribuuttien avulla 
     * (Dijkstran tapaan), tai etäisyys lähdöstä + etäisyys maalista attribuuttien avulla (AStarin tapaan).
     * @param t solmu johon verrataan
     * @return
     */
    @Override
    public int compareTo(Solmu t) {
        if (!muoto) {
            double erotus = this.getEtaisyysLahdosta() - t.getEtaisyysLahdosta();
            if (erotus > 0) {
                return 1;
            } else if (erotus == 0) {
                return 0;
            }
            return -1;
        } else {
            double erotus = (this.getEtaisyysLahdosta() + this.etaisyysMaalista) - (t.getEtaisyysLahdosta() + t.getEtaisyysMaalista());
            if (erotus > 0) {
                return 1;
            } else if (erotus == 0) {
                return 0;
            }
            return -1;
        }
    }

}
