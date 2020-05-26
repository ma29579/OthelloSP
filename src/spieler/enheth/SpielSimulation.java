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

        Spieler tmp = new Spieler();
        tmp.neuesSpiel(Farbe.SCHWARZ, 0);
        try {
            tmp.berechneZug(null,0,0);
        } catch (ZugException e) {
            e.printStackTrace();
        }

/*        Spielbrett tmp = new Spielbrett();
        tmp.zugAusfuehren(new Zug(2,3), Farbe.SCHWARZ);
        tmp.spielbrettAusgeben();
        System.out.println("-------------");
        ArrayList<Zug> moeglicheZuegeWEISS = tmp.sucheAlleMoeglichenZuge(Farbe.WEISS);
        tmp.zugAusfuehren(new Zug(2,2),Farbe.WEISS);
        tmp.spielbrettAusgeben();
        System.out.println("-------------");
        ArrayList<Zug> moeglicheZuegeSCHWARZ = tmp.sucheAlleMoeglichenZuge(Farbe.SCHWARZ);
        tmp.zugAusfuehren(new Zug(3,2),Farbe.SCHWARZ);
        tmp.spielbrettAusgeben();
        System.out.println("-------------");
        moeglicheZuegeWEISS = tmp.sucheAlleMoeglichenZuge(Farbe.WEISS);*/

        int i = 0;


        //new OthelloArena(150,spieler,true);
    }

}
