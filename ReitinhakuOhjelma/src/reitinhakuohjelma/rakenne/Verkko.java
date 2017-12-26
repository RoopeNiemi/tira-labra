/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reitinhakuohjelma.rakenne;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;

/**
 *
 * @author User
 */
public class Verkko {

    private Solmu[] verkko = new Solmu[2000];
    private int viimeisinSolmu = 0;
    private PriorityQueue<Solmu> jono;
    private HashSet<Integer> kaydyt;
    private List<Kaari> kaaret = new ArrayList<>();

    public Verkko() {

    }

    public void lisaaKaari(Kaari k) {
        kaaret.add(k);
    }

    public void lisaaSolmu(double x, double y, String s) {
        verkko[viimeisinSolmu] = new Solmu(viimeisinSolmu, x, y, s);
        viimeisinSolmu++;
    }

    public double shortestPath() {

        Dijkstra(this.verkko.length);

        if (verkko[this.verkko.length - 1].getEtaisyys() == Long.MAX_VALUE) {
            return -1;
        }
        return verkko[this.verkko.length - 1].getEtaisyys();

    }

    public double laskeEtaisyys(double x1, double y1, double x2, double y2) {
        double y3 = Math.abs(y2 - y1);
        double x3 = Math.abs(x2 - x1);
        double length = Math.sqrt(Math.pow(x3, 2) + Math.pow(y3, 2));
        return length;

    }

    public int getVerkonKoko() {
        return this.viimeisinSolmu;
    }

    public Solmu etsiSolmu(double x, double y) {
        for (int i = 0; i < getVerkonKoko(); i++) {
            Solmu solmu = verkko[i];
            if (x >= solmu.getX() && x <= solmu.getX() + 6) {
                if (y >= solmu.getY() && y <= solmu.getY() + 6) {
                    return solmu;
                }
            }
        }
        return null;
    }

    public Solmu etsiSolmuArvolla(int arvo) {
        for (int i = 0; i < getVerkonKoko(); i++) {
            Solmu s = verkko[i];
            if (s.getArvo() == arvo) {
                return s;
            }
        }
        return null;
    }

    public List<Kaari> getKaaret() {
        return this.kaaret;
    }

    private void Dijkstra(int n) {

        while (true) {
            if (jono.isEmpty()) {
                break;
            }
            Solmu u = jono.poll();

            if (!kaydyt.contains(u.getArvo())) {
                for (int i = 0; i < u.getVieruslista().size(); i++) {
                    Solmu v = verkko[u.getArvo()].getVieruslista().get(i).getPaateSolmu();
                    relax(u, v, u.getVieruslista().get(i).getPaino());

                }
                kaydyt.add(u.getArvo());

            }

            if (kaydyt.size() == n) {
                break;
            }
        }

    }

    public Solmu[] getSolmut() {
        return this.verkko;
    }

    private void relax(Solmu u, Solmu v, double paino) {
        if (v.getEtaisyys() > u.getEtaisyys() + paino && paino != 30000000L) {

            v.setEtaisyys(u.getEtaisyys() + paino);

        }
        jono.add(v);
    }
}
