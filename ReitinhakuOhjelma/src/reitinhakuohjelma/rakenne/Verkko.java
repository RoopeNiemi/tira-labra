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
    private ASolmu[] verkko2 = new ASolmu[2000];
    private int viimeisinSolmu = 0;
    private PriorityQueue<Solmu> jono;
    private HashSet<Integer> kaydyt;
    private List<Kaari> kaaret = new ArrayList<>();
    private Solmu[] reitti = new Solmu[2000];
    private Stack<Solmu> rt = new Stack<>();
    private ArrayDeque<Solmu> prosessi = new ArrayDeque<>();

    /**
     *
     */
    public Verkko() {

    }

    /**
     * Lisää verkolle kaaren kaarilistaan. Kyseistä kaarilistaa käytetään
     * ainoastaan kun piirretään ruudulle kaikki kaaret
     *
     * @param k
     */
    public void lisaaKaari(Kaari k) {
        kaaret.add(k);
    }

    /**
     * Resetoi lyhimmän reitin pinon sekä maalausjonon.
     */
    public void resetoiMaalausjono() {
        this.rt.clear();
        this.prosessi.clear();
    }

    /**
     * Lisää verkoille solmut, AStar solmuille ja DijkstraSolmuille omat verkot
     *
     * @param x
     * @param y
     * @param s
     * @param arvo
     */
    public void lisaaSolmu(double x, double y, String s, int arvo) {
        verkko[arvo] = new DijkstraSolmu(arvo, x, y, s);
        verkko2[arvo] = new ASolmu(arvo, x, y, s);
        viimeisinSolmu++;
    }

    /**
     *  * Dijkstraa hyödyntävä lyhin reitti metodi. Etsii lyhimmän reitin Dijkstra-
     * metodin avulla solmusta a solmuun b. Lisää solmuja erilliseen maalausjonoon jota käytetään
     * hakuprosessia piirrettäessä. Solmun löytyessä ensimmäistä kertaa luodaan siitä kopio, joka 
     * muutetaan keltaiseksi ja lisätään maalausjonoon. Kun solmun kaikki vierus-
     * solmut käyty läpi, luodaan siitä kopio joka muutetaan punaiseksi ja lisätään maalausjonoon. 
     * Kun Dijkstra-algoritmin toiminta loppuu, käydään vielä lyhin reitti läpi, jonka jokainen solmu muutetaan mustaksi
     * ja lisätään maalausjonoon.
     *
     * @param a
     * @param b
     * @return
     */
    public ArrayDeque<Solmu> shortestPath(Solmu a, Solmu b) {
        rt = new Stack<>();
        Dijkstra(viimeisinSolmu - 1, a, b);
        Solmu r = b;
        while (true) {
            rt.push(r);
            if (r == a) {
                break;
            }
            r = reitti[r.getArvo()];
        }

        while (!rt.isEmpty()) {
            Solmu ss = rt.pop();
            ss.setColor(Color.BLACK);
            prosessi.addLast(ss);
        }
        return prosessi;

    }

    /**
     * AStaria hyödyntävä lyhin reitti metodi. Etsii lyhimmän reitin AStar-
     * metodin avulla solmusta a solmuun b. Lisää solmuja erilliseen maalausjonoon jota käytetään
     * hakuprosessia piirrettäessä. Solmun löytyessä ensimmäistä kertaa luodaan siitä kopio, joka 
     * muutetaan keltaiseksi ja lisätään maalausjonoon. Kun solmun kaikki vierus-
     * solmut käyty läpi, luodaan siitä kopio joka muutetaan punaiseksi ja lisätään maalausjonoon. 
     * Kun AStar-algoritmin toiminta loppuu, käydään vielä lyhin reitti läpi, jonka jokainen solmu muutetaan mustaksi
     * ja lisätään maalausjonoon.
     *
     * @param a
     * @param b
     * @return
     */
    public ArrayDeque<Solmu> AStarShortestPath(Solmu a, Solmu b) {
        rt = new Stack<>();
        AStar(a, b);
        Solmu r = b;
        while (true) {
            rt.push(r);
            if (r == a) {
                break;
            }
            r = reitti[r.getArvo()];
        }

        while (!rt.isEmpty()) {
            Solmu ss = rt.pop();
            ss.setColor(Color.BLACK);
            prosessi.addLast(ss);
        }
        return prosessi;

    }

    /**
     * Etsii solmua sen nimellä verkosta, jossa vain Dijkstrassa käytettävät
     * solmut. Jos parametrin nimistä solmua ei ole palautetaan null.
     *
     * @param nimi
     * @return
     */
    public Solmu etsiDijkstraSolmuNimella(String nimi) {
        for (int i = 0; i < getVerkonKoko(); i++) {
            Solmu s = verkko[i];
            if (s.getNimi().equalsIgnoreCase(nimi)) {
                return s;
            }
        }
        return null;
    }

    /**
     * Etsii solmua sen nimellä verkosta, jossa vain AStaria käytettävät solmut.
     * Jos parametrin nimistä solmua ei ole palautetaan null.
     *
     * @param nimi
     * @return
     */
    public ASolmu EtsiASolmuNimella(String nimi) {
        for (int i = 0; i < getVerkonKoko(); i++) {
            ASolmu s = verkko2[i];
            if (s.getNimi().equalsIgnoreCase(nimi)) {
                return s;
            }
        }
        return null;
    }

    /**
     * Laskee kahden pisteen välisen etäisyyden kaavalla c^2=a^2+b^2, missä
     * a=itseisarvo erotuksesta solmun1 x-koordinaatti-solmun2 x-koordinaatti,
     * ja b=itseisarvo erotuksesta solmun1 y-koordinaatti-solmun2
     * y-koordinaatti. Lopuksi palautetaan c:n neliöjuuri, joka on siis nyt
     * etäisyys pikseleinä.
     *
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @return
     */
    public double laskeEtaisyys(double x1, double y1, double x2, double y2) {
        double y3 = Math.abs(y2 - y1);
        double x3 = Math.abs(x2 - x1);
        double length = Math.sqrt(Math.pow(x3, 2) + Math.pow(y3, 2));
        return length;

    }

    /**
     * palauttaa verkon solmujen määrän
     *
     * @return
     */
    public int getVerkonKoko() {
        return this.viimeisinSolmu;
    }

    /**
     * nollaa prioriteettijonon, ja reitti Arrayn. Asettaa jokaisen Dijkstrasolmun
     * etäisyydeksi 10000, ja aloitussolmun etäisyydeksi 0.
     * @param aloitus
     */
    public void alustaDijkstra(Solmu aloitus) {
        jono = new PriorityQueue<>();
        reitti = new Solmu[getVerkonKoko()];
        for (int i = 0; i < getVerkonKoko(); i++) {
            verkko[i].setEtaisyys(10000);
        }
        verkko[aloitus.getArvo()].setEtaisyys(0);
    }

    /**
     * Etsii verkosta, jossa vain Dijkstrassa käytettäviä solmuja, solmua jonka arvo
     * annetaan parametrina.  Jos ei löydy, palauttaa null
     * @param arvo
     * @return
     */
    public Solmu etsiDijkstraSolmuArvolla(int arvo) {
        for (int i = 0; i < getVerkonKoko(); i++) {
            Solmu s = verkko[i];
            if (s.getArvo() == arvo) {
                return s;
            }
        }
        return null;
    }

    /**
     * Etsii verkosta, jossa vain AStarissa käytettäviä solmuja, solmua jonka arvo
     * annetaan parametrina.  Jos ei löydy, palauttaa null
     * @param arvo
     * @return
     */
    public Solmu etsiASolmuArvolla(int arvo) {
        for (int i = 0; i < getVerkonKoko(); i++) {
            Solmu s = verkko2[i];
            if (s.getArvo() == arvo) {
                return s;
            }
        }
        return null;
    }

    /**
     * palauttaa jonossa solmuja, jotka lisätty Dijkstran ja AStarin suorituksen 
     * yhteydessä maalausjonoon. 
     * @return
     */
    public ArrayDeque<Solmu> getProsessi() {
        return this.prosessi;
    }

    /**
     * Palauttaa verkon kaaret. Käytetään vain kun halutaan piirtää kaikki kaaret.
     * @return
     */
    public List<Kaari> getKaaret() {
        return this.kaaret;
    }

    private void AStar(Solmu aloitus, Solmu etsittava) {
        alustaAStar(aloitus, etsittava);

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
                        Solmu kopio = new ASolmu(v.getArvo(), v.getX(), v.getY(), v.getNimi());
                        kopio.setColor(Color.YELLOW);
                        prosessi.addLast(kopio);
                    }
                }

                Solmu kopio = new ASolmu(u.getArvo(), u.getX(), u.getY(), u.getNimi());
                kopio.setColor(Color.RED);
                prosessi.addLast(kopio);
                kaydyt.add(u.getArvo());
                if (u.getArvo() == etsittava.getArvo()) {
                    break;
                }
            }
        }
    }

    private void alustaAStar(Solmu aloitus, Solmu etsittava) {
        kaydyt = new HashSet<>();
        jono = new PriorityQueue<>();
        reitti = new Solmu[getVerkonKoko()];
        for (int i = 0; i < getVerkonKoko(); i++) {
            verkko2[i].setEtaisyys(10000);
            verkko2[i].setEtaisyysMaalista(laskeEtaisyys(verkko2[i].getX(), verkko2[i].getY(), etsittava.getX(), etsittava.getY()));
        }
        verkko2[aloitus.getArvo()].setEtaisyys(0);
    }

    private void Dijkstra(int n, Solmu aloitus, Solmu etsittava) {
        kaydyt = new HashSet<>();
        alustaDijkstra(aloitus);

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
                        Solmu kopio = new DijkstraSolmu(v.getArvo(), v.getX(), v.getY(), v.getNimi());
                        kopio.setColor(Color.YELLOW);
                        prosessi.addLast(kopio);
                    }

                }
                Solmu kopio = new DijkstraSolmu(u.getArvo(), u.getX(), u.getY(), u.getNimi());
                kopio.setColor(Color.RED);
                prosessi.addLast(kopio);
                kaydyt.add(u.getArvo());
                if (u.getArvo() == etsittava.getArvo()) {
                    break;
                }

            }

        }

    }

    /**
     *
     * @return
     */
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
