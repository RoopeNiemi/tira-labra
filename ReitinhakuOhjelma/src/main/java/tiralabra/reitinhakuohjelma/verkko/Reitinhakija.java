package tiralabra.reitinhakuohjelma.verkko;

import javafx.scene.paint.Color;
import tiralabra.reitinhakuohjelma.rakenne.*;

/**
 *
 * @author User
 */
public class Reitinhakija {

    private Verkko verkko;
    private Prioriteettijono prioriteettijono = new Prioriteettijono();
    private Joukko kaydyt = new Joukko();
    private Pino<Solmu> lyhinReitti;
    private Solmu[] reitti;
    private Jono<Solmu> hakuprosessi = new Jono<>();

    public Reitinhakija(Verkko verkko) {
        this.verkko = verkko;
    }

    /**
     * Alustaa reitinhakijan käyttämään Dijkstran algoritmia. Nollaa
     * algoritmissa tarvittavat tietorakenteet, asettaa jokaisen solmun
     * etäisyydeksi aloitussolmusta 10 000. Lisäksi asettaa jokaisen solmun
     * "muoto" arvoksi false, mikä sallii solmujen vertailussa otettavan
     * huomioon vain etäisyyden lähtösolmusta. Asettaa lähtösolmun etäisyydeksi
     * lähdöstä 0.
     *
     * @param aloitus Solmu josta reittiä haetaan
     */
    public void dijkstraAlustus(Solmu aloitus) {
        prioriteettijono = new Prioriteettijono();
        reitti = new Solmu[412];
        lyhinReitti = new Pino<>();
        hakuprosessi = new Jono<>();
        kaydyt = new Joukko();
        for (int i = 0; i < 412; i++) {
            verkko.getSolmut()[i].setEtaisyysLahdosta(10000);
            verkko.getSolmut()[i].setMuoto(false);
        }
        verkko.getSolmut()[aloitus.getArvo()].setEtaisyysLahdosta(0);
    }

    /**
     * Alustaa reitinhakijan käyttämään A* algoritmia. Nollaa algoritmissa
     * tarvittavat tietorakenteet, asettaa jokaisen solmun etäisyydeksi
     * aloitussolmusta 10 000, sekä laskee etäisyyden kohdesolmusta. Lisäksi
     * asettaa jokaisen solmun "muoto" arvoksi true, mikä sallii solmujen
     * vertailussa ottaa huomioon myös etäisyyden kohdesolmusta. Asettaa
     * lähtösolmun etäisyydeksi lähdöstä ja maalista 0.
     *
     * @param aloitus Solmu josta reittiä haetaan
     * @param etsittava Solmu johon reittiä haetaan
     */
    public void aStarAlustus(Solmu aloitus, Solmu etsittava) {
        kaydyt = new Joukko();
        prioriteettijono = new Prioriteettijono();
        reitti = new Solmu[412];
        lyhinReitti = new Pino<>();
        hakuprosessi = new Jono<>();
        for (int i = 0; i < 412; i++) {
            verkko.getSolmut()[i].setEtaisyysLahdosta(10000);
            verkko.getSolmut()[i].setEtaisyysMaalista(verkko.laskeEtaisyys(verkko.getSolmut()[i].getX(), verkko.getSolmut()[i].getY(), etsittava.getX(), etsittava.getY()));
            verkko.getSolmut()[i].setMuoto(true);
        }
        verkko.getSolmut()[aloitus.getArvo()].setEtaisyysLahdosta(0);
        verkko.getSolmut()[etsittava.getArvo()].setEtaisyysMaalista(0);
    }

    /**
     * Etsii lyhimmän reitin aloitus solmusta kohdesolmuun, käyttäen joko A* tai
     * Dijkstran algoritmia. Kumpaa käytetään riippuu boolean arvosta hakutapa
     *
     * @param aloitus solmu josta reittiä haetaan
     * @param etsittava solmu johon reittiä haetaan
     * @param hakutapa määrittää käytetyn hakutavan. Tämän ollessa true
     * käytetään Dijkstran algoritmia, kun false käytetään A*.
     * @return hakuprosessista syntyneen jonon solmuja. Solmu lisätään jonoon
     * keltaisena kun se löydetään ensimmäisen kerran, lisäksi kun solmu on
     * nostettu prioriteettijonosta se muutetaan punaiseksi ja lisätään jonoon.
     */
    public Jono<Solmu> lyhinReitti(Solmu aloitus, Solmu etsittava, boolean hakutapa) {
        etsiLyhinReitti(aloitus, etsittava, hakutapa);
        Solmu r = etsittava;

        while (true) {
            lyhinReitti.push(r);
            if (r.getArvo() == aloitus.getArvo()) {
                break;
            }
            r = reitti[r.getArvo()];
        }

        return hakuprosessi;
    }

    /**
     * Hakutavasta riippuen kutsuu oikeaa alustusmetodia, jonka jälkeen
     * lähtösolmu lisätään prioriteettijonoon. Niin kauan kun prioriteettijono
     * ei ole tyhjä, 1. nostetaan prioriteettijonon pienin solmu pois
     * prioriteettijonosta 2. Jos se on haettava solmu, lopetetaan haku 3. jos
     * sitä ei ole aiemmin nostettu prioriteettijonosta, sen vierussolmut
     * käydään läpi ja niiden etäisyydet lähdöstä päivitetään relax-metodin
     * avulla. Jos vierussolmua ei ole lisätty hakuprosessijonoon, muutetaan
     * solmu keltaiseksi ja lisätään se hakuprosessijonoon. 4. Kun vierussolmut
     * käyty läpi, muutetaan solmu punaiseksi ja lisätään hakuprosessijonoon, ja
     * siirrytään takaisin kohtaan 1.
     *
     * @param aloitus Solmu josta reittiä haetaan
     * @param etsittava Solmu johon reittiä haetaan
     * @param hakutapa määrittää kumpaa hakualgoritmia käytetään. Kun true
     * käytetään Dijkstraa, kun false käytetään A*.
     */
    public void etsiLyhinReitti(Solmu aloitus, Solmu etsittava, boolean hakutapa) {
        if (hakutapa) {
            dijkstraAlustus(aloitus);
        } else {
            aStarAlustus(aloitus, etsittava);
        }
        prioriteettijono.lisaaKekoon(aloitus);

        while (true) {
            if (prioriteettijono.onTyhja()) {
                break;
            }
            Solmu u = prioriteettijono.poistaPienin();
            if (u.getArvo() == etsittava.getArvo()) {
                break;
            }
            if (!kaydyt.sisaltaa(u.getArvo())) {

                for (int i = 0; i < u.getVieruslista().koko(); i++) {
                    Solmu v = u.getVieruslista().get(i).getPaateSolmu();
                    relax(u, v, u.getVieruslista().get(i).getPaino());
                    if (!kaydyt.sisaltaa(v.getArvo())) {
                        Solmu kopio = new Solmu(v.getArvo(), v.getX(), v.getY(), v.getNimi());
                        kopio.setColor(Color.YELLOW);
                        hakuprosessi.lisaaJonoon(kopio);
                    }

                }
                Solmu kopio = new Solmu(u.getArvo(), u.getX(), u.getY(), u.getNimi());
                kopio.setColor(Color.RED);
                hakuprosessi.lisaaJonoon(kopio);
                kaydyt.lisaa(u.getArvo());
            }
        }
    }

    private void relax(Solmu u, Solmu v, double paino) {
        if (v.getEtaisyysLahdosta() > u.getEtaisyysLahdosta() + paino) {
            v.setEtaisyysLahdosta(u.getEtaisyysLahdosta() + paino);
            reitti[v.getArvo()] = u;
        }
        prioriteettijono.lisaaKekoon(v);
    }

    /**
     * Poistaa yksitellen lyhimmän reitin pinosta solmut ja lisää ne annettuun
     * jonoon
     *
     * @param s Jono johon lyhin reitti lisätään
     */
    public void lisaaReittiMaalausjonoon(Jono<Solmu> s) {
        while (!lyhinReitti.onTyhja()) {
            Solmu lisattava = lyhinReitti.pop();
            lisattava.setColor(Color.BLUE);
            s.lisaaJonoon(lisattava);
        }

    }

    public Prioriteettijono getPrioriteettijono() {
        return prioriteettijono;
    }

    public Joukko getKaydyt() {
        return kaydyt;
    }

    public Pino<Solmu> getLyhinReitti() {
        return lyhinReitti;
    }

    public Solmu[] getReitti() {
        return reitti;
    }

    public Jono<Solmu> getHakuprosessi() {
        return hakuprosessi;
    }

    public Verkko getVerkko() {
        return this.verkko;
    }

    public void resetoiProsessijono() {
        this.hakuprosessi = new Jono<>();
    }
}
