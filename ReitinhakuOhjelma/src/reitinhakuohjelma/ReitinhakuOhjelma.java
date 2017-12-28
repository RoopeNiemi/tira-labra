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
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import reitinhakuohjelma.rakenne.Kaari;
import reitinhakuohjelma.rakenne.Solmu;
import reitinhakuohjelma.rakenne.Verkko;

/**
 *
 * @author User
 */
public class ReitinhakuOhjelma extends Application {

    private Image kartta = new Image(getClass().getResourceAsStream("kartta.png"));
    private Verkko verkko = new Verkko();
    private String tallennetutSolmut = "Solmut.txt";
    private String tallennetutKaaret = "Kaaret.txt";
    private List<String> kaupungit = new ArrayList<>();
    private Solmu solmu1 = null;
    private Solmu solmu2 = null;
    private double nopeus = 80;
    private boolean valinta = false;
    private ArrayDeque<Solmu> maalausPino = new ArrayDeque<>();
/////Tallentaa solmut tekstitiedostoon jotta ne löytyy ohjelma käynnistettäessä///

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

////Lataa solmut tekstitiedostosta verkon solmuiksi////
    public void lataaSolmut() {
        try {
            Files.lines(Paths.get(tallennetutSolmut)).forEach(s -> {
                String[] solmunTiedot = s.split(",");
                double x = Double.parseDouble(solmunTiedot[0]);
                double y = Double.parseDouble(solmunTiedot[1]);
                String nimi = solmunTiedot[2];
                int arvo = Integer.parseInt(solmunTiedot[3]);
                if (!"NULL".equals(nimi)) {
                    kaupungit.add(nimi);
                }
                verkko.lisaaSolmu(x, y, nimi, arvo);
            });
        } catch (IOException ex) {
            Logger.getLogger(ReitinhakuOhjelma.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void tallennaKaaret() {
        List<Kaari> kaaret = verkko.getKaaret();
        List<String> kaaretTeksti = new ArrayList<>();
        for (int i = 0; i < kaaret.size(); i++) {
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

    @Override
    public void start(Stage primaryStage) {
        lataaSolmut();
        lataaKaaret();

        Canvas tausta = new Canvas(600, 800);
        GraphicsContext gc = tausta.getGraphicsContext2D();
        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(tausta);
        piirraTausta(gc);
        piirraSolmut(gc);

        VBox leftBox = new VBox();
        Label lahto = new Label("Lähtökaupunki");
        TextField lahtoKaupunki = new TextField();
        lahtoKaupunki.setOnAction(event -> {
            solmu1 = verkko.etsiNimella(lahtoKaupunki.getText());
            if (solmu1 != null) {
                piirraTausta(gc);
                piirraSolmut(gc);

            }
        });

        Label maali = new Label("Kohdekaupunki");
        TextField kohdeKaupunki = new TextField();
        kohdeKaupunki.setOnAction(event -> {

            solmu2 = verkko.etsiNimella(kohdeKaupunki.getText());
            if (solmu1 != null && solmu2 != null) {
                maalausPino = verkko.shortestPath(solmu1, solmu2);

                solmu1 = null;
                solmu2 = null;
                valinta = true;
            }

        });
        
        HBox napit = new HBox();
        napit.setAlignment(Pos.CENTER);
        napit.setSpacing(30);

        Button reset = new Button("Reset");
        reset.setOnAction(event -> {
            solmu1 = null;
            solmu2 = null;
            piirraTausta(gc);
            piirraSolmut(gc);
            kohdeKaupunki.setText("");
            lahtoKaupunki.setText("");
        });

        Button hae = new Button("Hae");
        hae.setOnAction(event -> {
            if (!lahtoKaupunki.getText().isEmpty() && !kohdeKaupunki.getText().isEmpty()) {
                solmu1 = verkko.etsiNimella(lahtoKaupunki.getText());
                solmu2 = verkko.etsiNimella(kohdeKaupunki.getText());
                if (solmu1 != null && solmu2 != null) {
                    maalausPino = verkko.shortestPath(solmu1, solmu2);
                    solmu1 = null;
                    solmu2 = null;
                    valinta = true;
                }
            }
        });

        leftBox.setAlignment(Pos.CENTER);
        leftBox.getChildren().add(lahto);
        leftBox.getChildren().add(lahtoKaupunki);
        leftBox.getChildren().add(maali);
        leftBox.getChildren().add(kohdeKaupunki);
        napit.getChildren().add(reset);
        napit.getChildren().add(hae);
        leftBox.getChildren().add(napit);
        borderPane.setLeft(leftBox);

        tausta.setOnMouseClicked(event -> {
            double x = event.getX();
            double y = event.getY();
            if (solmu1 == null) {
                solmu1 = verkko.etsiSolmu(x, y);
                piirraTausta(gc);
                piirraSolmut(gc);
            } else {
                solmu2 = verkko.etsiSolmu(x, y);
                if (solmu2 != null) {
                    maalausPino = verkko.shortestPath(solmu1, solmu2);

                    solmu1 = null;
                    solmu2 = null;
                    valinta = true;
                }
            }
        });

        new AnimationTimer() {
            long prev = 0;

            @Override
            public void handle(long now) {
                if (now - prev < 1000000) {
                    return;
                }
                prev = now;
                if (valinta) {
                    if (!maalausPino.isEmpty()) {
                        Solmu s = maalausPino.pollFirst();
                        piirraSolmu(gc, s);
                    } else {
                        valinta = false;
                    }
                }
            }
        }.start();
        
        Scene scene = new Scene(borderPane, 700, 900);
        primaryStage.setTitle("Reitinhaku");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void piirraTausta(GraphicsContext gc) {
        gc.drawImage(kartta, 0, 0);
    }

    public void maalaaProsessi(GraphicsContext gc, Stack<Solmu> st) {
        while (!st.isEmpty()) {
            Solmu s = st.pop();
            gc.setFill(s.getColor());
            gc.fillOval(s.getX() - 3, s.getY() - 3, 6, 6);
        }
    }

    public void piirraKaaret(GraphicsContext gc) {
        gc.setStroke(Color.BLUE);
        gc.setLineWidth(1);
        for (int i = 0; i < verkko.getKaaret().size(); i++) {
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

    public void piirraSolmu(GraphicsContext gc, Solmu s) {
        gc.setFill(s.getColor());
        gc.fillOval(s.getX() - 3, s.getY() - 3, 6, 6);
    }

    public void piirraSolmut(GraphicsContext gc) {
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

}