/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reitinhakuohjelma.rakenne;

import java.util.HashSet;
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

    public Verkko() {

    }

    public void lisaaSolmu(double x, double y, String s) {
        verkko[viimeisinSolmu] = new Solmu(viimeisinSolmu, x, y, s);
        viimeisinSolmu++;
    }

    public long shortestPath() {

        Dijkstra(this.verkko.length);

        if (verkko[this.verkko.length - 1].getEtaisyys() == Long.MAX_VALUE) {
            return -1;
        }
        return verkko[this.verkko.length - 1].getEtaisyys();

    }
    
    public int getVerkonKoko(){
        return this.viimeisinSolmu;
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

    private void relax(Solmu u, Solmu v, long paino) {
        if (v.getEtaisyys() > u.getEtaisyys() + paino && paino != 30000000L) {

            v.setEtaisyys(u.getEtaisyys() + paino);

        }
        jono.add(v);
    }
}
