package tiralabra.reitinhakuohjelma.kayttoliittyma;

import java.io.InputStream;
import java.util.Scanner;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.control.*;
import tiralabra.reitinhakuohjelma.rakenne.*;
import tiralabra.reitinhakuohjelma.verkko.*;

public class MainApp extends Application {

    private final Image kartta = new Image(getClass().getResourceAsStream("/kartta.png"));
    private Verkko verkko = new Verkko();
    private RadioButton Dijkstra, AStar;
    private ToggleGroup hakutapa;
    private TextField lahtokaupungit, kohdekaupungit;
    private boolean valinta = false;
    private Jono<Solmu> maalausJono = new Jono<>();
    private Lista<String> kaupungit = new Lista<>();
    private Lista<String> DijkstraHakutulokset = new Lista<>();
    private Lista<String> AStarHakutulokset = new Lista<>();
    private int tilastokerroin = 0;
    private VBox kartanvalikko = new VBox();
    private VBox tilastonvalikko = new VBox();

    /**
     * Lataa solmut tekstitiedostosta Solmut.txt verkon solmuiksi.
     *
     * @param v verkko johon solmut ladataan
     */
    public void lataaSolmut(Verkko v) {
        InputStream is = getClass().getClassLoader().getResourceAsStream("Solmut.txt");
        Scanner lukija = new Scanner(is);
        while (lukija.hasNextLine()) {

            String[] solmunTiedot = lukija.nextLine().split(",");
            double x = Double.parseDouble(solmunTiedot[0]);
            double y = Double.parseDouble(solmunTiedot[1]);
            String nimi = solmunTiedot[2];
            int arvo = Integer.parseInt(solmunTiedot[3]);
            if (!nimi.equals("NULL")) {
                kaupungit.lisaa(nimi);
            }
            v.lisaaSolmu(x, y, nimi, arvo);
        }
    }

    /**
     * Lataa kaaret tekstitiedostosta Kaaret.txt
     *
     * @param v verkko johon kaaret ladataan
     */
    public void lataaKaaret(Verkko v) {
        InputStream is = getClass().getClassLoader().getResourceAsStream("Kaaret.txt");
        Scanner lukija = new Scanner(is);
        while (lukija.hasNextLine()) {
            String[] kaarenTiedot = lukija.nextLine().split(",");
            int arvo1 = Integer.parseInt(kaarenTiedot[0]);
            int arvo2 = Integer.parseInt(kaarenTiedot[1]);
            double paino = Double.parseDouble(kaarenTiedot[2]);
            double kaarenNopeus = Double.parseDouble(kaarenTiedot[3]);
            Solmu solmun1 = v.etsiSolmuArvolla(arvo1);
            Solmu solmun2 = v.etsiSolmuArvolla(arvo2);
            solmun1.lisaaViereinenSolmu(solmun2, paino, kaarenNopeus);
            solmun2.lisaaViereinenSolmu(solmun1, paino, kaarenNopeus);
            v.lisaaKaari(new Kaari(solmun1, solmun2, paino, kaarenNopeus));

        }

    }

    public void lataaHaut() {
        InputStream is = getClass().getClassLoader().getResourceAsStream("DijkstraKaydyt.txt");
        Scanner lukija = new Scanner(is);
        while (lukija.hasNextLine()) {
            String[] rivi = lukija.nextLine().split(",");
            DijkstraHakutulokset.lisaa(rivi[0]);

        }
        is = getClass().getClassLoader().getResourceAsStream("AStarKaydyt.txt");
        lukija = new Scanner(is);
        while (lukija.hasNextLine()) {
            String[] rivi = lukija.nextLine().split(",");
            AStarHakutulokset.lisaa(rivi[0]);
        }
    }

    public Lista<String> haeKaupungit() {
        return this.kaupungit;
    }



    public void haeKaikki() {

        for (int i = 0; i < kaupungit.koko(); i++) {
            for (int j = 0; j < kaupungit.koko(); j++) {
                Solmu a = verkko.etsiSolmuNimella(kaupungit.get(i));
                Solmu b = verkko.etsiSolmuNimella(kaupungit.get(j));
                verkko.getReitinhakija().lyhinReitti(a, b, true);
                int lapikaytyjaSolmuja = verkko.getReitinhakija().getKaydyt().koko();
                System.out.println(a.getNimi().toUpperCase() + " -> " + b.getNimi().toUpperCase());
                System.out.println("Dijkstra läpikäytyjä solmuja: " + lapikaytyjaSolmuja);
                verkko.getReitinhakija().lyhinReitti(a, b, false);
                lapikaytyjaSolmuja = verkko.getReitinhakija().getKaydyt().koko();
                System.out.println("A* läpikäytyjä solmuja: " + lapikaytyjaSolmuja);
                System.out.println("");
            }
        }
    }

    @Override
    public void start(Stage primaryStage) {
        lataaSolmut(verkko);
        lataaKaaret(verkko);
        lataaHaut();
        //haeKaikki();

        Canvas tausta = new Canvas(600, 800);
        Canvas tilasto = new Canvas(600, 800);

        BorderPane ikkuna = new BorderPane();
        ikkuna.setCenter(tausta);
        piirraTausta(tausta.getGraphicsContext2D());
        luoKartanValikko(ikkuna, tausta.getGraphicsContext2D(), tilasto);

        luoTilastovalikko(tausta, tilasto, ikkuna);

        new AnimationTimer() {
            long prev = 0;

            @Override
            public void handle(long now
            ) {
                if (now - prev < 1000000) {
                    return;
                }
                prev = now;
                if (valinta) {
                    if (!maalausJono.onTyhja()) {
                        Solmu s = maalausJono.poistaJonosta();
                        piirraSolmu(tausta.getGraphicsContext2D(), s);
                    } else {
                        piirraLapikaytyjenSolmujenMaara(tausta.getGraphicsContext2D());
                        valinta = false;
                    }
                }
            }
        }.start();
        Scene scene = new Scene(ikkuna, 800, 900);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Reitinhaku");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void luoKartanValikko(BorderPane ikkuna, GraphicsContext kartanGrafiikka, Canvas tilasto) {

        kartanvalikko.setAlignment(Pos.CENTER);
        kartanvalikko.setSpacing(10);
        luoTekstikentat(kartanvalikko);
        luoRadioNapit(kartanvalikko);
        luoNapit(kartanvalikko, kartanGrafiikka, ikkuna, tilasto);
        ikkuna.setLeft(kartanvalikko);

    }

    private void luoTekstikentat(VBox valikko) {
        Label lahto = new Label("Lähtökaupunki");
        lahtokaupungit = new TextField();
        Label maali = new Label("Kohdekaupunki");
        this.kohdekaupungit = new TextField();
        valikko.getChildren().add(lahto);
        valikko.getChildren().add(lahtokaupungit);
        valikko.getChildren().add(maali);
        valikko.getChildren().add(kohdekaupungit);
    }

    private void luoRadioNapit(VBox valikko) {
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

    private void luoNapit(VBox valikko, GraphicsContext kartanGrafiikka, BorderPane ikkuna, Canvas tilasto) {
        HBox napit = new HBox();
        napit.setAlignment(Pos.CENTER);
        napit.setSpacing(30);
        Button naytaTilasto = new Button("Näytä tilasto");
        naytaTilasto.setOnAction(event -> {
            ikkuna.setCenter(tilasto);
            ikkuna.setLeft(tilastonvalikko);
            piirraTilasto(tilasto.getGraphicsContext2D());

        });

        Button reset = new Button("Reset");
        reset.setOnAction(event -> {
            piirraTausta(kartanGrafiikka);
            valinta = false;
            verkko.resetoiMaalausjono();
        });

        Button hae = new Button("Hae");
        hae.setOnAction(event -> {
            // Toimii vain jos sekä lähtö- että kohdekaupungit nimetty
            if (!lahtokaupungit.getText().isEmpty() && !kohdekaupungit.getText().isEmpty()) {
                piirraTausta(kartanGrafiikka);
                //Hakutavaksi valittu Dijkstra
                if (hakutapa.getSelectedToggle() == Dijkstra) {
                    Solmu solmu1 = verkko.etsiSolmuNimella(lahtokaupungit.getText().trim());
                    Solmu solmu2 = verkko.etsiSolmuNimella(kohdekaupungit.getText().trim());

                    if (solmu1 != null && solmu2 != null) {

                        maalausJono = verkko.getReitinhakija().lyhinReitti(solmu1, solmu2, true);
                        verkko.getReitinhakija().lisaaReittiMaalausjonoon(maalausJono);
                        valinta = true;
                    }
                    //Hakutavaksi valittu AStar
                } else {
                    Solmu solmu1 = verkko.etsiSolmuNimella(lahtokaupungit.getText().trim());
                    Solmu solmu2 = verkko.etsiSolmuNimella(kohdekaupungit.getText().trim());
                    if (solmu1 != null && solmu2 != null) {
                        maalausJono = verkko.getReitinhakija().lyhinReitti(solmu1, solmu2, false);
                        verkko.getReitinhakija().lisaaReittiMaalausjonoon(maalausJono);
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

    public static void main(String[] args) {
        launch(args);
    }

    private void piirraTausta(GraphicsContext kartanGrafiikka) {
        kartanGrafiikka.drawImage(kartta, 0, 0);
        kartanGrafiikka.setFont(new Font(16));
        kartanGrafiikka.strokeText("Solmuja käytiin läpi: ", 20, 783);
    }

    private void piirraLapikaytyjenSolmujenMaara(GraphicsContext kartanGrafiikka) {
        kartanGrafiikka.strokeText(verkko.getReitinhakija().getKaydyt().koko() + "", 165, 783);
    }

    private void piirraTilasto(GraphicsContext tilastoGrafiikka) {
        piirraTilastonTausta(tilastoGrafiikka);
        piirraKaupunginNimi(tilastoGrafiikka);
        piirraHakualgoritmienNimet(tilastoGrafiikka);
        piirraKuvaajat(DijkstraHakutulokset, tilastoGrafiikka, Color.YELLOW, 412);
        piirraKuvaajat(AStarHakutulokset, tilastoGrafiikka, Color.LAWNGREEN, 412);
    }

    private void piirraTilastonTausta(GraphicsContext tilastoGrafiikka) {
        tilastoGrafiikka.setFill(Color.GRAY);
        tilastoGrafiikka.fillRect(0, 0, 600, 800);
        tilastoGrafiikka.setStroke(Color.BLACK);
        double y = 30;
        for (int i = 0; i <= 10; i++) {
            tilastoGrafiikka.setFont(new Font(16));
            tilastoGrafiikka.setStroke(Color.BEIGE);
            tilastoGrafiikka.strokeText(i * 41 + "", 0, y);
            tilastoGrafiikka.setStroke(Color.BLACK);
            tilastoGrafiikka.strokeLine(30, y, 600, y);
            y += 74;
        }
    }

    private void piirraKaupunginNimi(GraphicsContext tilastoGrafiikka) {
        tilastoGrafiikka.setStroke(Color.BEIGE);
        tilastoGrafiikka.setFont(new Font(20));
        int kaupunginIndeksi = tilastokerroin / 118;
        Label kaupunginnimi = new Label(kaupungit.get(kaupunginIndeksi));
        tilastoGrafiikka.strokeText(kaupunginnimi.getText(), 270, 25);
    }

    private void piirraHakualgoritmienNimet(GraphicsContext tilastoGrafiikka) {
        tilastoGrafiikka.setStroke(Color.YELLOW);
        tilastoGrafiikka.strokeText("Dijkstra", 30, 790);
        tilastoGrafiikka.setStroke(Color.LAWNGREEN);
        tilastoGrafiikka.strokeText("AStar", 120, 790);
    }

    private void piirraKuvaajat(Lista<String> tuloksia, GraphicsContext tilastoGrafiikka, Color c, int maara) {
        double x1 = 30;
        double y1 = 0;
        double x2 = 35.084745;
        double y2 = 0;
        tilastoGrafiikka.setStroke(c);
        for (int i = tilastokerroin; i < tilastokerroin + 117; i++) {
            int tulos = Integer.parseInt(tuloksia.get(i));
            int tulos2 = Integer.parseInt(tuloksia.get(i + 1));

            y1 = ((double) tulos / maara) * 740;
            y2 = ((double) tulos2 / maara) * 740;
            y1 += 30;
            y2 += 30;

            tilastoGrafiikka.strokeLine(x1, y1, x2, y2);
            x1 += 5.084745;
            x2 += 5.084745;
        }
    }

    private void piirraSolmu(GraphicsContext kartanGrafiikka, Solmu s) {
        kartanGrafiikka.setFill(s.getColor());
        kartanGrafiikka.fillOval(s.getX() - 3, s.getY() - 3, 6, 6);
    }

    private void luoTilastovalikko(Canvas tausta, Canvas tilasto, BorderPane ikkuna) {

        tilastonvalikko.setAlignment(Pos.CENTER);
        tilastonvalikko.setSpacing(30);

        Button naytaKartta = new Button("Näytä kartta");
        naytaKartta.setOnAction(event -> {
            ikkuna.setCenter(tausta);
            ikkuna.setLeft(kartanvalikko);
            piirraTausta(tausta.getGraphicsContext2D());

        });
        HBox liikkumisnapit = new HBox();
        liikkumisnapit.setAlignment(Pos.CENTER);
        liikkumisnapit.setPadding(new Insets(0, 0, 0, 20));

        Button seuraava = new Button("Seuraava");
        seuraava.setOnAction(event -> {
            tilastokerroin += 118;
            if (tilastokerroin > 13176) {
                tilastokerroin = 13176;
            }

            piirraTilasto(tilasto.getGraphicsContext2D());
        });
        Button edellinen = new Button("Edellinen");
        edellinen.setOnAction(event -> {
            tilastokerroin -= 118;
            if (tilastokerroin < 0) {
                tilastokerroin = 0;

            }
            piirraTilasto(tilasto.getGraphicsContext2D());
        });
        liikkumisnapit.getChildren().add(edellinen);
        liikkumisnapit.getChildren().add(seuraava);
        tilastonvalikko.getChildren().add(naytaKartta);
        tilastonvalikko.getChildren().add(liikkumisnapit);
    }

}
