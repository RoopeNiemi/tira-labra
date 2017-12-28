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

    public int getArvo();

    public void setEtaisyysMaalista(double et);

    public double getEtaisyysMaalista();

    public Color getColor();

    public void setColor(Color c);

    public String getNimi();

    public double getX();

    public double getY();

    public void setArvo(int arvo);

    public double getEtaisyys();

    public void setEtaisyys(double etaisyys);

    public ArrayList<Kaari> getVieruslista();

    public void lisaaViereinenSolmu(Solmu s, double paino, double kaarenNopeus);

}
