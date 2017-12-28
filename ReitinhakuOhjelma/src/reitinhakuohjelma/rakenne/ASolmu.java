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
public class ASolmu implements Solmu, Comparable<ASolmu> {

    private int arvo;
    private double etaisyys, etaisyysMaalista;
    private ArrayList<Kaari> vieruslista;
    private double x, y;
    private String nimi = "";
    private Color color = Color.WHITE;

    public ASolmu(int arvo, double x, double y, String nimi) {
        this.etaisyys = Long.MAX_VALUE;
        this.vieruslista = new ArrayList<>();
        this.x = x;
        this.y = y;
        this.nimi = nimi;
        this.arvo = arvo;
        this.etaisyysMaalista = 0;
    }

    @Override
    public int getArvo() {
        return arvo;
    }

    @Override
    public void setEtaisyysMaalista(double et) {
        this.etaisyysMaalista = et;
    }

    @Override
    public double getEtaisyysMaalista() {
        return this.etaisyysMaalista;
    }

    @Override
    public Color getColor() {
        return this.color;
    }

    @Override
    public void setColor(Color c) {
        this.color = c;
    }

    @Override
    public String getNimi() {
        if (nimi.isEmpty()) {
            return "NULL";
        }
        return this.nimi;
    }

    @Override
    public double getX() {
        return this.x;
    }

    @Override
    public double getY() {
        return this.y;
    }

    @Override
    public void setArvo(int arvo) {
        this.arvo = arvo;
    }

    @Override
    public double getEtaisyys() {
        return etaisyys;
    }

    @Override
    public void setEtaisyys(double etaisyys) {
        this.etaisyys = etaisyys;
    }

    @Override
    public ArrayList<Kaari> getVieruslista() {
        return vieruslista;
    }

    @Override
    public void lisaaViereinenSolmu(Solmu s, double paino, double kaarenNopeus) {
        vieruslista.add(new Kaari(this, s, paino, kaarenNopeus));
    }

    @Override
    public int compareTo(ASolmu t) {
        double erotus = (this.getEtaisyys() + this.etaisyysMaalista) - (t.getEtaisyys() + t.getEtaisyysMaalista());
        if (erotus > 0) {
            return 1;
        } else if (erotus == 0) {
            return 0;
        }
        return -1;
    }

}
