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
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
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
    private List<String> kaupungit=new ArrayList<>();
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
               if(!"NULL".equals(nimi)){
                   kaupungit.add(nimi);
               }
                verkko.lisaaSolmu(x, y, nimi);
            });
        } catch (IOException ex) {
            Logger.getLogger(ReitinhakuOhjelma.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void start(Stage primaryStage) {
        lataaSolmut();
        BorderPane borderPane = new BorderPane();
        Canvas tausta = new Canvas(600, 800);
        GraphicsContext gc = tausta.getGraphicsContext2D();
        borderPane.setCenter(tausta);
        piirraTausta(gc);
        piirraSolmut(gc);

        ///Käytetään vain solmuja luodessa////
        VBox leftBox = new VBox();
        TextField nimiTeksti = new TextField();
        Button tallenna = new Button("Tallenna");
        tallenna.setOnAction(event -> {
            tallennaSolmut();
        });
        leftBox.getChildren().add(nimiTeksti);
        leftBox.getChildren().add(tallenna);
        leftBox.setAlignment(Pos.CENTER);
        borderPane.setLeft(leftBox);
        //////////////////////////////

        Scene scene = new Scene(borderPane, 700, 900);
        tausta.setOnMouseClicked(event -> {
            String nimi = "";
            if (!nimiTeksti.getText().isEmpty()) {
                nimi = nimiTeksti.getText();
            }
            double x = event.getX();
            double y = event.getY();
            verkko.lisaaSolmu(x, y, nimi);
            piirraTausta(gc);
            piirraSolmut(gc);
            nimiTeksti.setText("");

        });

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
    }

}
