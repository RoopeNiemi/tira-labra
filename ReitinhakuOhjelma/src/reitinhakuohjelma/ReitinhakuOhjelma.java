/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reitinhakuohjelma;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import reitinhakuohjelma.rakenne.Jono;
import verkko.Solmu;
import verkko.Kaari;
import reitinhakuohjelma.rakenne.Lista;
import verkko.Verkko;

/**
 *
 * @author User
 */
public class ReitinhakuOhjelma extends Application {

    private final Image kartta = new Image(getClass().getResourceAsStream("kartta.png"));
    private Verkko verkko = new Verkko();
    private final String tallennetutSolmut = "Solmut.txt";
    private final String tallennetutKaaret = "Kaaret.txt";
    private final String tallennetutDijkstraHaut = "DijkstraKaydyt.txt";
    private final String tallennetutAStarHaut = "AStarKaydyt.txt";
    private Solmu solmu1 = null;
    private Solmu solmu2 = null;
    private Button reset, hae;
    private RadioButton Dijkstra, AStar;
    private ToggleGroup hakutapa;
    private TextField lahtokaupunki, kohdekaupunki;
    private double nopeus = 80;
    private boolean valinta = false;
    private Jono<Solmu> maalausPino = new Jono<>();
    private Lista<String> kaupunki = new Lista<>();
    private Lista<String> DijkstraHakutulokset = new Lista<>();
    private Lista<String> AStarHakutulokset = new Lista<>();
    private int tilastokerroin = 0;
    private VBox kartanvalikko = new VBox();
    private VBox tilastonvalikko = new VBox();

    /**
     * Tallentaa solmut tekstitiedostoon Solmut.txt.
     */
    public void tallennaSolmut() {
        List<String> lista = new ArrayList<>();
        Solmu[] solmut = verkko.getSolmut();
        for (int i = 0; i < verkko.getVerkonKoko(); i++) {
            double x = solmut[i].getX();
            double y = solmut[i].getY();
            String nimi = solmut[i].getNimi();
            int arvo = solmut[i].getArvo();
            String s = x + "," + y + "," + nimi + "," + arvo;
            lista.add(s);
        }

        try {
            Files.write(Paths.get(tallennetutSolmut), lista, Charset.forName("UTF-8"));
        } catch (IOException ex) {
            Logger.getLogger(ReitinhakuOhjelma.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Lataa solmut tekstitiedostosta Solmut.txt verkon solmuiksi.
     */
    public void lataaSolmut() {
        try {
            Files.lines(Paths.get(tallennetutSolmut)).forEach(s -> {
                String[] solmunTiedot = s.split(",");
                double x = Double.parseDouble(solmunTiedot[0]);
                double y = Double.parseDouble(solmunTiedot[1]);
                String nimi = solmunTiedot[2];
                int arvo = Integer.parseInt(solmunTiedot[3]);
                if (!nimi.equals("NULL")) {
                    kaupunki.lisaa(nimi);
                }
                verkko.lisaaSolmu(x, y, nimi, arvo);
            });
        } catch (IOException ex) {
            Logger.getLogger(ReitinhakuOhjelma.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Tallentaa kaaret tekstitiedostoon Kaaret.txt
     */
    public void tallennaKaaret() {
        Lista<Kaari> kaaret = verkko.getKaaret();
        List<String> kaaretTeksti = new ArrayList<>();
        for (int i = 0; i < kaaret.koko(); i++) {
            int arvo1 = kaaret.get(i).getLahtoSolmu().getArvo();
            int arvo2 = kaaret.get(i).getPaateSolmu().getArvo();
            double paino = kaaret.get(i).getPaino();
            double kaarenNopeus = kaaret.get(i).getNopeus();
            String kaari = arvo1 + "," + arvo2 + "," + paino + "," + kaarenNopeus;
            kaaretTeksti.add(kaari);

        }

        try {
            Files.write(Paths.get(tallennetutKaaret), kaaretTeksti, Charset.forName("UTF-8"));
        } catch (IOException ex) {
            Logger.getLogger(ReitinhakuOhjelma.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Lataa kaaret tekstitiedostosta Kaaret.txt
     */
    public void lataaKaaret() {
        try {
            Files.lines(Paths.get(tallennetutKaaret)).forEach(k -> {
                String[] kaarenTiedot = k.split(",");
                int arvo1 = Integer.parseInt(kaarenTiedot[0]);
                int arvo2 = Integer.parseInt(kaarenTiedot[1]);
                double paino = Double.parseDouble(kaarenTiedot[2]);
                double kaarenNopeus = Double.parseDouble(kaarenTiedot[3]);
                Solmu solmun1 = verkko.etsiSolmuArvolla(arvo1);
                Solmu solmun2 = verkko.etsiSolmuArvolla(arvo2);
                solmun1.lisaaViereinenSolmu(solmun2, paino, kaarenNopeus);
                solmun2.lisaaViereinenSolmu(solmun1, paino, kaarenNopeus);
                verkko.lisaaKaari(new Kaari(solmun1, solmun2, paino, kaarenNopeus));
            });
        } catch (IOException ex) {
            Logger.getLogger(ReitinhakuOhjelma.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void lataaHaut() {
        try {
            Files.lines(Paths.get(tallennetutDijkstraHaut)).forEach(k -> {
                String[] rivi = k.split(",");
                DijkstraHakutulokset.lisaa(rivi[0]);
            });
        } catch (IOException ex) {
            Logger.getLogger(ReitinhakuOhjelma.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            Files.lines(Paths.get(tallennetutAStarHaut)).forEach(k -> {
                String[] rivi = k.split(",");
                AStarHakutulokset.lisaa(rivi[0]);
            });
        } catch (IOException ex) {
            Logger.getLogger(ReitinhakuOhjelma.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void start(Stage primaryStage) {
        lataaSolmut();
        lataaKaaret();
        lataaHaut();

        Canvas tausta = new Canvas(600, 800);
        GraphicsContext gc = tausta.getGraphicsContext2D();
        Canvas tilasto = new Canvas(600, 800);
        GraphicsContext tilastoGc = tilasto.getGraphicsContext2D();

        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(tausta);
        piirraTausta(gc);
        //  piirraSolmut(gc);

        luoKartanValikko(borderPane, gc, tilasto);
        Scene scene = new Scene(borderPane, 800, 900);
        luoTilastovalikko(tausta, tilasto, borderPane);

        new AnimationTimer() {
            long prev = 0;

            @Override
            public void handle(long now) {
                if (now - prev < 1000000) {
                    return;
                }
                prev = now;
                if (valinta) {
                    if (!maalausPino.onTyhja()) {
                        Solmu s = maalausPino.poistaJonosta();
                        piirraSolmu(gc, s);
                    } else {
                        valinta = false;
                    }
                }
            }
        }.start();

        primaryStage.setResizable(false);
        primaryStage.setTitle("Reitinhaku");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void luoKartanValikko(BorderPane ikkuna, GraphicsContext gc, Canvas tilasto) {

        kartanvalikko.setAlignment(Pos.CENTER);
        kartanvalikko.setSpacing(10);
        luoTekstikentat(kartanvalikko, gc);
        luoRadioNapit(kartanvalikko, gc);
        luoNapit(kartanvalikko, gc, ikkuna, tilasto);
        ikkuna.setLeft(kartanvalikko);

    }

    private void luoTekstikentat(VBox valikko, GraphicsContext gc) {
        Label lahto = new Label("Lähtökaupunki");
        lahtokaupunki = new TextField();
        lahtokaupunki.setOnAction(event -> {
            solmu1 = verkko.etsiSolmuNimella(lahtokaupunki.getText());
            if (solmu1 != null) {
                piirraTausta(gc);
                piirraSolmut(gc);

            }
        });

        Label maali = new Label("Kohdekaupunki");
        this.kohdekaupunki = new TextField();
        this.kohdekaupunki.setOnAction(event -> {

            solmu2 = verkko.etsiSolmuNimella(kohdekaupunki.getText());
            if (solmu1 != null && solmu2 != null) {
                maalausPino = verkko.shortestPath(solmu1, solmu2);

                solmu1 = null;
                solmu2 = null;
                valinta = true;
            }

        });
        valikko.getChildren().add(lahto);
        valikko.getChildren().add(lahtokaupunki);
        valikko.getChildren().add(maali);
        valikko.getChildren().add(kohdekaupunki);
    }

    private void luoRadioNapit(VBox valikko, GraphicsContext gc) {
        HBox radioNapitBox = new HBox();
        radioNapitBox.setAlignment(Pos.CENTER);
        hakutapa = new ToggleGroup();
        AStar = new RadioButton("AStar");
        AStar.setToggleGroup(hakutapa);
        Dijkstra = new RadioButton("Dijkstra");
        Dijkstra.setToggleGroup(hakutapa);
        hakutapa.selectToggle(Dijkstra);
        radioNapitBox.getChildren().add(Dijkstra);
        radioNapitBox.getChildren().add(AStar);
        valikko.getChildren().add(radioNapitBox);
    }

    private void luoNapit(VBox valikko, GraphicsContext gc, BorderPane bp, Canvas tilasto) {

        HBox napit = new HBox();
        napit.setAlignment(Pos.CENTER);
        napit.setSpacing(30);
        Button naytaTilasto = new Button("Näytä tilasto");
        naytaTilasto.setOnAction(event -> {
            bp.setCenter(tilasto);
            bp.setLeft(tilastonvalikko);
            piirraTilasto(tilasto.getGraphicsContext2D());

        });

        reset = new Button("Reset");
        reset.setOnAction(event -> {
            solmu1 = null;
            solmu2 = null;
            piirraTausta(gc);
            //  piirraSolmut(gc);
            valinta = false;
            verkko.resetoiMaalausjono();
        });

        hae = new Button("Hae");
        hae.setOnAction(event -> {
            if (!lahtokaupunki.getText().isEmpty() && !kohdekaupunki.getText().isEmpty()) {
                piirraTausta(gc);
                //    piirraSolmut(gc);
                if (hakutapa.getSelectedToggle() == Dijkstra) {
                    solmu1 = verkko.etsiSolmuNimella(lahtokaupunki.getText());
                    solmu2 = verkko.etsiSolmuNimella(kohdekaupunki.getText());

                    if (solmu1 != null && solmu2 != null) {

                        maalausPino = verkko.shortestPath(solmu1, solmu2);
                        solmu1 = null;
                        solmu2 = null;
                        valinta = true;
                    }
                } else {
                    solmu1 = verkko.etsiSolmuNimella(lahtokaupunki.getText());
                    solmu2 = verkko.etsiSolmuNimella(kohdekaupunki.getText());
                    if (solmu1 != null && solmu2 != null) {
                        maalausPino = verkko.AStarShortestPath(solmu1, solmu2);
                        solmu1 = null;
                        solmu2 = null;
                        valinta = true;
                    }
                }
            }

        });

        napit.getChildren().add(reset);
        napit.getChildren().add(hae);
        valikko.getChildren().add(napit);
        valikko.getChildren().add(naytaTilasto);
    }

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Piirtää taustakuvaksi kartta.png -kuvan
     *
     * @param gc Canvas piirtoalustan GraphicsContext
     */
    private void piirraTausta(GraphicsContext gc) {
        gc.drawImage(kartta, 0, 0);
    }

    /**
     * Piirtää verkon kaaret kartalle. Ei tällä hetkellä käytössä
     *
     * @param gc Canvas piirtoalustan GraphicsContext
     */
    private void piirraKaaret(GraphicsContext gc) {
        gc.setStroke(Color.BLUE);
        gc.setLineWidth(1);
        for (int i = 0; i < verkko.getKaaret().koko(); i++) {
            double x1 = verkko.getKaaret().get(i).getLahtoSolmu().getX();
            double y1 = verkko.getKaaret().get(i).getLahtoSolmu().getY();
            double x2 = verkko.getKaaret().get(i).getPaateSolmu().getX();
            double y2 = verkko.getKaaret().get(i).getPaateSolmu().getY();
            double nopeus = verkko.getKaaret().get(i).getNopeus();
            if (nopeus == 60) {
                gc.setStroke(Color.BLACK);
            } else if (nopeus == 80) {
                gc.setStroke(Color.BLUE);
            } else {
                gc.setStroke(Color.RED);
            }
            gc.strokeLine(x1, y1, x2, y2);

        }
    }

    private void piirraTilasto(GraphicsContext tilastoGc) {
        tilastoGc.setFill(Color.WHITE);
        tilastoGc.fillRect(0, 0, 600, 800);
        tilastoGc.setStroke(Color.GRAY);
        double y = 30;
        for (int i = 0; i < 10; i++) {
            tilastoGc.strokeLine(0, y, 600, y);
            y += 74;
        }
        double x = 30;
        for (int i = 0; i <= 59; i++) {
            tilastoGc.strokeLine(x, 0, x, 800);
            x += 9.15254;
        }
        piirraKuvaajat(DijkstraHakutulokset, tilastoGc, Color.RED);
        piirraKuvaajat(AStarHakutulokset, tilastoGc, Color.BLUE);
    }

    private void piirraKuvaajat(Lista<String> tuloksia, GraphicsContext gc, Color c) {
        double x1 = 30;
        double y1 = 0;
        double x2 = 35.4;
        double y2 = 0;
        gc.setStroke(c);
        for (int i = tilastokerroin; i < tilastokerroin + 117; i++) {
            int tulos = Integer.parseInt(tuloksia.get(i));
            int tulos2 = Integer.parseInt(tuloksia.get(i + 1));

            y1 = ((double) tulos / 412) * 740;
            y2 = ((double) tulos2 / 412) * 740;
            x1 += 4.57567;
            x2 += 4.57567;
            gc.strokeLine(x1, y1, x2, y2);
        }
    }

    /**
     * Piirtää yksittäisen solmun. Käytetään reitinhakualgoritmin käytön
     * jälkeen.
     *
     * @param gc Canvas piirtoalustan GraphicsContext
     * @param s Piirrettävä solmu
     */
    private void piirraSolmu(GraphicsContext gc, Solmu s) {

        gc.setFill(s.getColor());
        gc.fillOval(s.getX() - 3, s.getY() - 3, 6, 6);
    }

    /**
     * Piirtää kartalle kaikki solmut valkoisina.
     *
     * @param gc Canvas piirtoalustan GraphicsContext
     */
    private void piirraSolmut(GraphicsContext gc) {
        Solmu[] solmut = verkko.getSolmut();
        gc.setFill(Color.RED);
        for (int i = 0; i < verkko.getVerkonKoko(); i++) {

            gc.setFill(Color.WHITE);

            gc.fillOval(solmut[i].getX() - 3, solmut[i].getY() - 3, 6, 6);
        }
        if (solmu1 != null) {
            gc.setFill(Color.BROWN);
            gc.fillOval(solmu1.getX() - 3, solmu1.getY() - 3, 6, 6);
        }

    }

    private void luoTilastovalikko(Canvas tausta, Canvas tilasto, BorderPane borderPane) {

        tilastonvalikko.setAlignment(Pos.CENTER);
        tilastonvalikko.setSpacing(30);
        Label solmutRuudulla = new Label("Helsinki");
        Button naytaKartta = new Button("Näytä kartta");
        naytaKartta.setOnAction(event -> {
            borderPane.setCenter(tausta);
            borderPane.setLeft(kartanvalikko);
            piirraTausta(tausta.getGraphicsContext2D());

        });
        HBox liikkumisnapit = new HBox();
        liikkumisnapit.setAlignment(Pos.CENTER);
        Button seuraava = new Button("Seuraava");
        seuraava.setOnAction(event -> {
            tilastokerroin += 118;
            if (tilastokerroin > 13176) {
                tilastokerroin = 13176;
            }
            int kaupunginIndeksi = tilastokerroin / 118;
            solmutRuudulla.setText(kaupunki.get(kaupunginIndeksi));
            piirraTilasto(tilasto.getGraphicsContext2D());
        });
        Button edellinen = new Button("Edellinen");
        edellinen.setOnAction(event -> {
            tilastokerroin -= 118;
            if (tilastokerroin < 0) {
                tilastokerroin = 0;

            }
            int kaupunginIndeksi = tilastokerroin / 118;
            solmutRuudulla.setText(kaupunki.get(kaupunginIndeksi));
            piirraTilasto(tilasto.getGraphicsContext2D());
        });
        liikkumisnapit.getChildren().add(edellinen);
        liikkumisnapit.getChildren().add(seuraava);
        tilastonvalikko.getChildren().add(solmutRuudulla);
        tilastonvalikko.getChildren().add(naytaKartta);
        tilastonvalikko.getChildren().add(liikkumisnapit);
    }

}
