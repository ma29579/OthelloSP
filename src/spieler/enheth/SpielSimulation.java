package spieler.enheth;

import rahmen.OthelloArena;
import spieler.OthelloSpieler;
import spieler.Referenzspieler;
import spieler.Zug;

import java.util.ArrayList;

public class SpielSimulation {

    public static void main(String[] args) {

        ArrayList<OthelloSpieler> spieler = new ArrayList<>();
        spieler.add(new Spieler(10));
        spieler.add(new Referenzspieler(6));

        new OthelloArena(150, spieler, true);
    }

}
