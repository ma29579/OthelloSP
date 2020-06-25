package spieler.enheth;

import rahmen.OthelloArena;
import spieler.OthelloSpieler;
import spieler.Referenzspieler;
import spieler.Zug;

import java.util.ArrayList;

public class SpielSimulation {

    public static void main(String[] args) {

        ArrayList<String> spieler = new ArrayList<>();
        spieler.add("spieler.Referenzspieler:6");
        //spieler.add("spieler.enheth.Spieler:");

        new OthelloArena(150, spieler, "spiel.txt",false);
    }

}
