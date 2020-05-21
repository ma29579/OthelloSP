package spieler.enheth;

import rahmen.OthelloArena;
import spieler.Farbe;
import spieler.OthelloSpieler;
import spieler.Zug;
import spieler.ZugException;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SpielSimulation {

    public static void main(String[] args){

        //List<OthelloSpieler> spieler = new ArrayList<OthelloSpieler>();

        //Spieler müssen ergänzt werden

        Spieler tmp2 = new Spieler();
        tmp2.neuesSpiel(Farbe.SCHWARZ,0);
        try {
            tmp2.berechneZug(null,0,0);
        } catch (ZugException e) {
            e.printStackTrace();
        }

        Spielbrett tmp = new Spielbrett();
        ArrayList<Zug> zuege = tmp.sucheAlleMoeglichenZuge(Farbe.SCHWARZ);
        tmp.spielbrettAusgeben();
        System.out.println("");
        tmp.zugAusfuehren(zuege.get(0),Farbe.SCHWARZ);
        tmp.spielbrettAusgeben();
        zuege= tmp.sucheAlleMoeglichenZuge(Farbe.SCHWARZ);
        tmp.zugBewerten(zuege.get(1),Farbe.SCHWARZ);
        System.out.println("");
        tmp.spielbrettAusgeben();


        //new OthelloArena(150,spieler,true);
    }

}
