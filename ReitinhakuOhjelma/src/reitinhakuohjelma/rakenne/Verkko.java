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

    /**
     *
     */
    public Verkko() {

    }

    /**
     * Lisää verkolle kaaren kaarilistaan. Kyseistä kaarilistaa käytetään
     * ainoastaan kun piirretään ruudulle kaikki kaaret
     *
     * @param k lisättävä kaari
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
     * Lisää verkolle solmun
     *
     * @param x solmun x-koordinaatti
     * @param y solmun y-koordinaatti
     * @param s solmun nimi
     * @param arvo solmun arvo
     */
    public void lisaaSolmu(double x, double y, String s, int arvo) {
        verkko[arvo] = new Solmu(arvo, x, y, s);
        viimeisinSolmu++;
    }

    /**
     *  * Dijkstraa hyödyntävä lyhin reitti metodi. Alustaa aluksi verkon
     * jokaiselle solmulle etäisyydeksi lähtö- solmusta 10 000 (aloitussolmulle
     * 0).Tyhjentää alustuksessa prioriteettijonon ja luodut reitit. Etsii
     * lyhimmän reitin Dijkstra- metodin avulla solmusta a solmuun b. Lisää
     * solmuja erilliseen maalausjonoon jota käytetään hakuprosessia
     * piirrettäessä. Solmun löytyessä ensimmäistä kertaa luodaan siitä kopio,
     * joka muutetaan keltaiseksi ja lisätään maalausjonoon. Kun solmun kaikki
     * vierus- solmut käyty läpi, luodaan siitä kopio joka muutetaan punaiseksi
     * ja lisätään maalausjonoon. Kun Dijkstra-algoritmin toiminta loppuu,
     * käydään vielä lyhin reitti läpi, jonka jokainen solmu muutetaan mustaksi
     * ja lisätään maalausjonoon.
     *
     * @param a solmu josta haetaan lyhintä reittiä
     * @param b solmu johon haetaan lyhin reitti
     * @return hakuprosessin aikana syntynyt maalausjono
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
     * AStaria hyödyntävä lyhin reitti metodi. Alustaa aluksi verkon jokaiselle
     * solmulle etäisyydeksi lähtö- solmusta 10 000 (aloitussolmulle 0). Laskee
     * joka solmun etäisyyden kohdesolmuun ja päivittää tämän avulla solmun
     * etäisyysMaalista attribuutin. Tyhjentää alustuksessa prioriteettijonon ja
     * luodut reitit. Etsii lyhimmän reitin AStar-metodin avulla solmusta a
     * solmuun b. Lisää solmuja erilliseen maalausjonoon jota käytetään
     * hakuprosessia piirrettäessä. Solmun löytyessä ensimmäistä kertaa luodaan
     * siitä kopio, joka muutetaan keltaiseksi ja lisätään maalausjonoon. Kun
     * solmun kaikki vierus- solmut käyty läpi, luodaan siitä kopio joka
     * muutetaan punaiseksi ja lisätään maalausjonoon. Kun AStar-algoritmin
     * toiminta loppuu, käydään vielä lyhin reitti läpi, jonka jokainen solmu
     * muutetaan mustaksi ja lisätään maalausjonoon.
     *
     * @param a solmu josta haetaan lyhintä reittiä
     * @param b solmu johon haetaan lyhin reitti
     * @return hakuprosessin aikana syntynyt maalausjono
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
     * Etsii solmua sen nimellä verkosta. Jos parametrin nimistä solmua ei ole
     * palautetaan null.
     *
     * @param nimi - Etsittävän solmun nimi
     * @return
     */
    public Solmu etsiSolmuNimella(String nimi) {
        for (int i = 0; i < getVerkonKoko(); i++) {
            Solmu s = verkko[i];
            if (s.getNimi().equalsIgnoreCase(nimi)) {
                return s;
            }
        }
        return null;
    }

    /**
     * Laskee kahden pisteen välisen etäisyyden kaavalla c^2=a^2+b^2, missä
     * a=itseisarvo erotuksesta solmun2 x-koordinaatti-solmun1 x-koordinaatti,
     * ja b=itseisarvo erotuksesta solmun2 y-koordinaatti-solmun1
     * y-koordinaatti. Lopuksi palautetaan c:n neliöjuuri, joka on siis nyt
     * etäisyys pikseleinä.
     *
     * @param x1 solmun 1 x-koordinaatti
     * @param y1 solmun 1 y-koordinaatti
     * @param x2 solmun 2 x-koordinaatti
     * @param y2 solmun 2 y-koordinaatti
     * @return solmujen välinen etäisyys
     */
    public double laskeEtaisyys(double x1, double y1, double x2, double y2) {
        double y3 = Math.abs(y2 - y1);
        double x3 = Math.abs(x2 - x1);
        double length = Math.sqrt(Math.pow(x3, 2) + Math.pow(y3, 2));
        return length;

    }

    /**
     *
     * @return verkon solmujen määrän
     */
    public int getVerkonKoko() {
        return this.viimeisinSolmu;
    }

    private void alustaDijkstra(Solmu aloitus) {
        jono = new PriorityQueue<>();
        reitti = new Solmu[getVerkonKoko()];
        for (int i = 0; i < getVerkonKoko(); i++) {
            verkko[i].setEtaisyysLahdosta(10000);
            verkko[i].setMuoto(false);
        }
        verkko[aloitus.getArvo()].setEtaisyysLahdosta(0);
    }

    /**
     * Etsii verkosta solmua jonka arvo annetaan parametrina. Jos ei löydy,
     * palauttaa null
     *
     * @param arvo etsittävän solmun arvo
     * @return Solmu jonka arvo==etsittävän solmun arvo.  Null, jos solmua ei löydy.
     */
    public Solmu etsiSolmuArvolla(int arvo) {
        for (int i = 0; i < getVerkonKoko(); i++) {
            Solmu s = verkko[i];
            if (s.getArvo() == arvo) {
                return s;
            }
        }
        return null;
    }

    /**
     * palauttaa jonossa solmuja, jotka lisätty Dijkstran ja AStarin suorituksen
     * yhteydessä maalausjonoon.
     *
     * @return
     */
    public ArrayDeque<Solmu> getProsessi() {
        return this.prosessi;
    }

    /**
     * Palauttaa verkon kaaret. Käytetään vain kun halutaan piirtää kaikki
     * kaaret.
     *
     * @return lista verkon kaarista
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

    private void alustaAStar(Solmu aloitus, Solmu etsittava) {
        kaydyt = new HashSet<>();
        jono = new PriorityQueue<>();
        reitti = new Solmu[getVerkonKoko()];
        for (int i = 0; i < getVerkonKoko(); i++) {
            verkko[i].setEtaisyysLahdosta(10000);
            verkko[i].setEtaisyysMaalista(laskeEtaisyys(verkko[i].getX(), verkko[i].getY(), etsittava.getX(), etsittava.getY()));
            verkko[i].setMuoto(true);
        }
        verkko[aloitus.getArvo()].setEtaisyysLahdosta(0);
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

    /**
     *
     * @return verkon kaikki solmut arrayssa
     */
    public Solmu[] getSolmut() {
        return this.verkko;
    }

    private void relax(Solmu u, Solmu v, double paino) {
        if (v.getEtaisyysLahdosta() > u.getEtaisyysLahdosta() + paino) {

            v.setEtaisyysLahdosta(u.getEtaisyysLahdosta() + paino);
            reitti[v.getArvo()] = u;
        }
        jono.add(v);
    }
}
