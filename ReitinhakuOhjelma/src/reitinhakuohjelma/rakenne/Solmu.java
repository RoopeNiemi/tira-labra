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
public interface Solmu {

    /**
     *
     * @return
     */
    public int getArvo();

    /**
     *
     * @param et
     */
    public void setEtaisyysMaalista(double et);

    /**
     *
     * @return
     */
    public double getEtaisyysMaalista();

    /**
     *
     * @return
     */
    public Color getColor();

    /**
     * 
     * @param c
     */
    public void setColor(Color c);

    /**
     *
     * @return
     */
    public String getNimi();

    /**
     *
     * @return
     */
    public double getX();

    /**
     *
     * @return
     */
    public double getY();

    /**
     *
     * @param arvo
     */
    public void setArvo(int arvo);

    /**
     *
     * @return
     */
    public double getEtaisyys();

    /**
     *
     * @param etaisyys
     */
    public void setEtaisyys(double etaisyys);

    /**
     *
     * @return
     */
    public ArrayList<Kaari> getVieruslista();

    /**
     * Lisää solmulle vierussolmun vieruslistaan
     * @param s
     * @param paino
     * @param kaarenNopeus
     */
    public void lisaaViereinenSolmu(Solmu s, double paino, double kaarenNopeus);

}
