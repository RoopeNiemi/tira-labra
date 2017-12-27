/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reitinhakuohjelma.rakenne;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Stack;
import javafx.scene.paint.Color;

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
    private Solmu[] reitti = new Solmu[2000];
    private Stack<Solmu> rt = new Stack<>();
    private ArrayDeque<Solmu> prosessi = new ArrayDeque<>();

    public Verkko() {

    }

    public void lisaaKaari(Kaari k) {
        kaaret.add(k);
    }

    public void lisaaSolmu(double x, double y, String s, int arvo) {
        verkko[arvo] = new Solmu(arvo, x, y, s);
        viimeisinSolmu++;
    }

    public ArrayDeque<Solmu> shortestPath(Solmu a, Solmu b) {
        rt = new Stack<>();
        Dijkstra(viimeisinSolmu - 1, a, b);
        Solmu r = b;
        while (r != a) {
            rt.push(r);
            r = reitti[r.getArvo()];
        }

        while (!rt.isEmpty()) {
            Solmu ss = rt.pop();
            ss.setColor(Color.BLACK);
            prosessi.addLast(ss);
        }
        return prosessi;

    }

    public Solmu etsiNimella(String nimi) {
        for (int i = 0; i < getVerkonKoko(); i++) {
            Solmu s = verkko[i];
            if (s.getNimi().equalsIgnoreCase(nimi)) {
                return s;
            }
        }
        return null;
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

    public void ISS(Solmu aloitus) {
        jono = new PriorityQueue<>();
        reitti = new Solmu[getVerkonKoko()];
        for (int i = 0; i < getVerkonKoko(); i++) {
            verkko[i].setEtaisyys(10000);
        }
        verkko[aloitus.getArvo()].setEtaisyys(0);
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

    public ArrayDeque<Solmu> getProsessi() {
        return this.prosessi;
    }

    public List<Kaari> getKaaret() {
        return this.kaaret;
    }

    private void Dijkstra(int n, Solmu aloitus, Solmu etsittava) {
        kaydyt = new HashSet<>();
        ISS(aloitus);

        jono.add(aloitus);
        while (true) {
            if (jono.isEmpty()) {
                break;
            }
            Solmu u = jono.poll();

            if (!kaydyt.contains(u.getArvo())) {

                for (int i = 0; i < u.getVieruslista().size(); i++) {
                    Solmu v = u.getVieruslista().get(i).getPaateSolmu();
                    relax(u, v, u.getVieruslista().get(i).getPaino());
                    if (!kaydyt.contains(v.getArvo())) {
                        Solmu kopio = new Solmu(v.getArvo(), v.getX(), v.getY(), v.getNimi());
                        kopio.setColor(Color.YELLOW);
                        prosessi.addLast(kopio);
                    }

                }
                Solmu kopio = new Solmu(u.getArvo(), u.getX(), u.getY(), u.getNimi());
                kopio.setColor(Color.RED);
                prosessi.addLast(kopio);
                kaydyt.add(u.getArvo());
                if (u.getArvo() == etsittava.getArvo()) {
                    break;
                }

            }

        }

    }

    public Solmu[] getSolmut() {
        return this.verkko;
    }

    private void relax(Solmu u, Solmu v, double paino) {
        if (v.getEtaisyys() > u.getEtaisyys() + paino) {

            v.setEtaisyys(u.getEtaisyys() + paino);
            reitti[v.getArvo()] = u;
        }
        jono.add(v);
    }
}
