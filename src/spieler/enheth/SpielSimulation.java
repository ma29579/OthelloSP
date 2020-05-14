package spieler.enheth;

import rahmen.OthelloArena;
import spieler.Farbe;
import spieler.OthelloSpieler;
import spieler.Zug;

import java.util.ArrayList;
import java.util.List;

public class SpielSimulation {

    public static void main(String[] args){

        //List<OthelloSpieler> spieler = new ArrayList<OthelloSpieler>();

        //Spieler müssen ergänzt werden

        Spielbrett tmp = new Spielbrett();
        ArrayList<Zug> zuege = tmp.sucheAlleMoeglichenZuge(Farbe.SCHWARZ);
        tmp.spielbrettAusgeben();
        System.out.println("");
        tmp.zugAusfuehren(zuege.get(0),Farbe.SCHWARZ);
        tmp.spielbrettAusgeben();
        zuege= tmp.sucheAlleMoeglichenZuge(Farbe.SCHWARZ);
        int i = 0;


        //new OthelloArena(150,spieler,true);
    }

}
