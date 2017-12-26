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
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
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
                if (!"NULL".equals(nimi)) {
                    kaupungit.add(nimi);
                }
                verkko.lisaaSolmu(x, y, nimi);
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
        BorderPane borderPane = new BorderPane();
        Canvas tausta = new Canvas(600, 800);
        GraphicsContext gc = tausta.getGraphicsContext2D();
        borderPane.setCenter(tausta);
        piirraTausta(gc);
        piirraSolmut(gc);
        piirraKaaret(gc);
//--------------------------------------------/////
        ///Käytetään vain solmuja luodessa////
        VBox leftBox = new VBox();
        ToggleGroup nopeudet = new ToggleGroup();
        RadioButton kuus = new RadioButton("60");
        kuus.setToggleGroup(nopeudet);
        kuus.setOnAction(event -> {
            nopeus = 60;
        });
        RadioButton seit = new RadioButton("70");
        seit.setToggleGroup(nopeudet);
        seit.setOnAction(event -> {
            nopeus = 70;
        });
        RadioButton kasi = new RadioButton("80");
        kasi.setToggleGroup(nopeudet);
        kasi.setOnAction(event -> {
            nopeus = 80;
        });
        RadioButton sata = new RadioButton("100");
        sata.setToggleGroup(nopeudet);
        sata.setOnAction(event -> {
            nopeus = 100;
        });
        RadioButton sataK = new RadioButton("120");
        sataK.setToggleGroup(nopeudet);
        sataK.setOnAction(event -> {
            nopeus = 120;
        });

        // TextField nimiTeksti = new TextField();
        Button tallenna = new Button("Tallenna");
        tallenna.setOnAction(event -> {
            tallennaKaaret();
        });
        //      leftBox.getChildren().add(nimiTeksti);
        leftBox.getChildren().add(kuus);
        leftBox.getChildren().add(seit);
        leftBox.getChildren().add(kasi);
        leftBox.getChildren().add(sata);
        leftBox.getChildren().add(sataK);
        leftBox.getChildren().add(tallenna);
        leftBox.setAlignment(Pos.CENTER);
        borderPane.setLeft(leftBox);
        //////////////////////////////

        Scene scene = new Scene(borderPane, 700, 900);
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.F) {
                solmu1 = null;
                solmu2 = null;
                piirraTausta(gc);
                piirraSolmut(gc);
                piirraKaaret(gc);
            }
        });
        tausta.setOnMouseClicked(event -> {
            //   String nimi = "";
            //  if (!nimiTeksti.getText().isEmpty()) {
            //        nimi = nimiTeksti.getText();
            //  }
            double x = event.getX();
            double y = event.getY();
            if (solmu1 == null) {
                solmu1 = verkko.etsiSolmu(x, y);
            } else {
                solmu2 = verkko.etsiSolmu(x, y);
                if (solmu2 != null) {
                    double paino = verkko.laskeEtaisyys(solmu1.getX(), solmu1.getY(), solmu2.getX(), solmu2.getY());

                    solmu1.lisaaViereinenSolmu(solmu2, paino, nopeus);
                    solmu2.lisaaViereinenSolmu(solmu1, paino, nopeus);
                    verkko.lisaaKaari(new Kaari(solmu1, solmu2, paino, nopeus));
                    solmu1 = null;
                    solmu2 = null;

                }
            }

            //verkko.lisaaSolmu(x, y, "");
            piirraTausta(gc);
            piirraSolmut(gc);
            piirraKaaret(gc);
            //   nimiTeksti.setText("");

        });
//--------------------------------------------/////
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    public void piirraTausta(GraphicsContext gc) {
        gc.drawImage(kartta, 0, 0);
    }

    public void piirraKaaret(GraphicsContext gc) {
        gc.setStroke(Color.BLUE);
        gc.setLineWidth(3);
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

    public void piirraSolmut(GraphicsContext gc) {
        Solmu[] solmut = verkko.getSolmut();
        gc.setFill(Color.RED);
        for (int i = 0; i < verkko.getVerkonKoko(); i++) {
            if (solmut[i].getNimi().equals("NULL")) {
                gc.setFill(Color.YELLOW);
            } else {
                gc.setFill(Color.RED);
            }
            gc.fillOval(solmut[i].getX() - 3, solmut[i].getY() - 3, 6, 6);
        }
        if (solmu1 != null) {
            gc.setFill(Color.AQUA);
            gc.fillOval(solmu1.getX() - 3, solmu1.getY() - 3, 6, 6);
        }

    }

}
