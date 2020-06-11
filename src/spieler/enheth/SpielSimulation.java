package spieler.enheth;

import rahmen.OthelloArena;
import spieler.OthelloSpieler;
import spieler.Referenzspieler;
import spieler.Zug;

import java.util.ArrayList;

public class SpielSimulation {

    public static void main(String[] args) {

        ArrayList<OthelloSpieler> spieler = new ArrayList<>();
        spieler.add(new Spieler());
        spieler.add(new Referenzspieler(6));

        new OthelloArena(150, spieler, true);
    }

}

//2320 (1. Runde), 2500 (2.Runde)