package tiralabra.reitinhakuohjelma;

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
        try {
            Files.lines(Paths.get("src/main/resources/Solmut.txt")).forEach(s -> {
                String[] solmunTiedot = s.split(",");
                double x = Double.parseDouble(solmunTiedot[0]);
                double y = Double.parseDouble(solmunTiedot[1]);
                String nimi = solmunTiedot[2];
                int arvo = Integer.parseInt(solmunTiedot[3]);
                if (!nimi.equals("NULL")) {
                    kaupungit.lisaa(nimi);
                }
                v.lisaaSolmu(x, y, nimi, arvo);
            });
        } catch (IOException ex) {
            Logger.getLogger(MainApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Lataa kaaret tekstitiedostosta Kaaret.txt
     *
     * @param v verkko johon kaaret ladataan
     */
    public void lataaKaaret(Verkko v) {
        try {
            Files.lines(Paths.get("src/main/resources/Kaaret.txt")).forEach(k -> {
                String[] kaarenTiedot = k.split(",");
                int arvo1 = Integer.parseInt(kaarenTiedot[0]);
                int arvo2 = Integer.parseInt(kaarenTiedot[1]);
                double paino = Double.parseDouble(kaarenTiedot[2]);
                double kaarenNopeus = Double.parseDouble(kaarenTiedot[3]);
                Solmu solmun1 = v.etsiSolmuArvolla(arvo1);
                Solmu solmun2 = v.etsiSolmuArvolla(arvo2);
                solmun1.lisaaViereinenSolmu(solmun2, paino, kaarenNopeus);
                solmun2.lisaaViereinenSolmu(solmun1, paino, kaarenNopeus);
                v.lisaaKaari(new Kaari(solmun1, solmun2, paino, kaarenNopeus));
            });
        } catch (IOException ex) {
            Logger.getLogger(MainApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void lataaHaut() {
        try {
            Files.lines(Paths.get("src/main/resources/DijkstraKaydyt.txt")).forEach(k -> {
                String[] rivi = k.split(",");
                DijkstraHakutulokset.lisaa(rivi[0]);
            });
        } catch (IOException ex) {
            Logger.getLogger(MainApp.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            Files.lines(Paths.get("src/main/resources/AStarKaydyt.txt")).forEach(k -> {
                String[] rivi = k.split(",");
                AStarHakutulokset.lisaa(rivi[0]);
            });
        } catch (IOException ex) {
            Logger.getLogger(MainApp.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public Lista<String> haeKaupungit() {
        return this.kaupungit;
    }
    
    public void tallenna(List<String> lista, String o) {
        try {
            Files.write(Paths.get(o), lista, Charset.forName("UTF-8"));
        } catch (IOException ex) {
            Logger.getLogger(MainApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void haeKaikki() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < kaupungit.koko(); i++) {
            for (int j = 0; j < kaupungit.koko(); j++) {
                Solmu a = verkko.etsiSolmuNimella(kaupungit.get(i));
                Solmu b = verkko.etsiSolmuNimella(kaupungit.get(j));
                verkko.hae(a, b, true);
                list.add(verkko.getReitinhakija().getKaydyt().koko() + "," + a.getNimi() + "-" + b.getNimi());
            }
        }
        tallenna(list, "src/main/resources/DijkstraKaydyt.txt");
        list = new ArrayList<>();
        for (int i = 0; i < kaupungit.koko(); i++) {
            for (int j = 0; j < kaupungit.koko(); j++) {
                Solmu a = verkko.etsiSolmuNimella(kaupungit.get(i));
                Solmu b = verkko.etsiSolmuNimella(kaupungit.get(j));
                verkko.hae(a, b, false);
                list.add(verkko.getReitinhakija().getKaydyt().koko() + "," + a.getNimi() + "-" + b.getNimi());
            }
        }
        tallenna(list, "src/main/resources/AStarKaydyt.txt");
    }
    
    @Override
    public void start(Stage primaryStage) {
        lataaSolmut(verkko);
        lataaKaaret(verkko);
        lataaHaut();
        hakujenKeskiarvo();
        Canvas tausta = new Canvas(600, 800);
        GraphicsContext gc = tausta.getGraphicsContext2D();
        Canvas tilasto = new Canvas(600, 800);
        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(tausta);
        piirraTausta(gc);
        
        luoKartanValikko(borderPane, gc, tilasto);
        Scene scene = new Scene(borderPane, 800, 900);
        
        luoTilastovalikko(tausta, tilasto, borderPane);
        
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
    
    public void hakujenKeskiarvo() {
        double keskiarvoDijkstra = 0;
        double keskiarvoAStar = 0;
        for (int i = 0; i < DijkstraHakutulokset.koko(); i++) {
            keskiarvoDijkstra += Double.parseDouble(DijkstraHakutulokset.get(i));
            keskiarvoAStar += Double.parseDouble(AStarHakutulokset.get(i));
        }
        keskiarvoDijkstra /= DijkstraHakutulokset.koko();
        keskiarvoAStar /= AStarHakutulokset.koko();
        System.out.println("Dijkstra keskiarvo: " + keskiarvoDijkstra + " solmua per haku");
        System.out.println("AStar keskiarvo: " + keskiarvoAStar + " solmua per haku");
    }
    
    private void luoKartanValikko(BorderPane ikkuna, GraphicsContext gc, Canvas tilasto) {
        
        kartanvalikko.setAlignment(Pos.CENTER);
        kartanvalikko.setSpacing(10);
        luoTekstikentat(kartanvalikko, gc);
        luoRadioNapit(kartanvalikko);
        luoNapit(kartanvalikko, gc, ikkuna, tilasto);
        ikkuna.setLeft(kartanvalikko);
        
    }
    
    private void luoTekstikentat(VBox valikko, GraphicsContext gc) {
        Label lahto = new Label("Lähtökaupungit");
        lahtokaupungit = new TextField();
        Label maali = new Label("Kohdekaupungit");
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
        
        Button reset = new Button("Reset");
        reset.setOnAction(event -> {
            piirraTausta(gc);
            valinta = false;
            verkko.resetoiMaalausjono();
        });
        
        Button hae = new Button("Hae");
        hae.setOnAction(event -> {
            // Toimii vain jos sekä lähtö- että kohdekaupungit nimetty
            if (!lahtokaupungit.getText().isEmpty() && !kohdekaupungit.getText().isEmpty()) {
                piirraTausta(gc);
                //Hakutavaksi valittu Dijkstra
                if (hakutapa.getSelectedToggle() == Dijkstra) {
                    Solmu solmu1 = verkko.etsiSolmuNimella(lahtokaupungit.getText());
                    Solmu solmu2 = verkko.etsiSolmuNimella(kohdekaupungit.getText());
                    
                    if (solmu1 != null && solmu2 != null) {
                        
                        maalausJono = verkko.hae(solmu1, solmu2, true);
                        verkko.lisaaReittiMaalausjonoon(maalausJono);
                        valinta = true;
                    }
                    //Hakutavaksi valittu AStar
                } else {
                    Solmu solmu1 = verkko.etsiSolmuNimella(lahtokaupungit.getText());
                    Solmu solmu2 = verkko.etsiSolmuNimella(kohdekaupungit.getText());
                    if (solmu1 != null && solmu2 != null) {
                        maalausJono = verkko.hae(solmu1, solmu2, false);
                        verkko.lisaaReittiMaalausjonoon(maalausJono);
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
    
    private void piirraTausta(GraphicsContext gc) {
        gc.drawImage(kartta, 0, 0);
    }
    
    private void piirraTilasto(GraphicsContext tilastoGc) {
        piirraTilastonTausta(tilastoGc);
        piirraKaupunginNimi(tilastoGc);
        piirraHakualgoritmienNimet(tilastoGc);
        piirraKuvaajat(DijkstraHakutulokset, tilastoGc, Color.YELLOW, 412);
        piirraKuvaajat(AStarHakutulokset, tilastoGc, Color.LAWNGREEN, 412);
    }
    
    private void piirraTilastonTausta(GraphicsContext tilastoGc) {
        tilastoGc.setFill(Color.GRAY);
        tilastoGc.fillRect(0, 0, 600, 800);
        tilastoGc.setStroke(Color.BLACK);
        double y = 30;
        for (int i = 0; i <= 10; i++) {
            tilastoGc.setFont(new Font(16));
            tilastoGc.setStroke(Color.BEIGE);
            tilastoGc.strokeText(i * 41 + "", 0, y);
            tilastoGc.setStroke(Color.BLACK);
            tilastoGc.strokeLine(30, y, 600, y);
            y += 74;
        }
    }
    
    private void piirraKaupunginNimi(GraphicsContext tilastoGc) {
        tilastoGc.setStroke(Color.BEIGE);
        tilastoGc.setFont(new Font(20));
        int kaupunginIndeksi = tilastokerroin / 118;
        Label kaupunginnimi = new Label(kaupungit.get(kaupunginIndeksi));
        tilastoGc.strokeText(kaupunginnimi.getText(), 270, 25);
    }
    
    private void piirraHakualgoritmienNimet(GraphicsContext tilastoGc) {
        tilastoGc.setStroke(Color.YELLOW);
        tilastoGc.strokeText("Dijkstra", 30, 790);
        tilastoGc.setStroke(Color.LAWNGREEN);
        tilastoGc.strokeText("AStar", 120, 790);
    }
    
    private void piirraKuvaajat(Lista<String> tuloksia, GraphicsContext gc, Color c, int maara) {
        double x1 = 30;
        double y1 = 0;
        double x2 = 35.084745;
        double y2 = 0;
        gc.setStroke(c);
        for (int i = tilastokerroin; i < tilastokerroin + 117; i++) {
            int tulos = Integer.parseInt(tuloksia.get(i));
            int tulos2 = Integer.parseInt(tuloksia.get(i + 1));
            
            y1 = ((double) tulos / maara) * 740;
            y2 = ((double) tulos2 / maara) * 740;
            y1 += 30;
            y2 += 30;
            
            gc.strokeLine(x1, y1, x2, y2);
            x1 += 5.084745;
            x2 += 5.084745;
        }
    }
    
    private void piirraSolmu(GraphicsContext gc, Solmu s) {
        gc.setFill(s.getColor());
        gc.fillOval(s.getX() - 3, s.getY() - 3, 6, 6);
    }
    
    private void luoTilastovalikko(Canvas tausta, Canvas tilasto, BorderPane borderPane) {
        
        tilastonvalikko.setAlignment(Pos.CENTER);
        tilastonvalikko.setSpacing(30);
        
        Button naytaKartta = new Button("Näytä kartta");
        naytaKartta.setOnAction(event -> {
            borderPane.setCenter(tausta);
            borderPane.setLeft(kartanvalikko);
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
