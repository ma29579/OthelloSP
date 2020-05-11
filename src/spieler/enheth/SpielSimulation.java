package spieler.enheth;

import rahmen.OthelloArena;
import spieler.OthelloSpieler;

import java.util.ArrayList;
import java.util.List;

public class SpielSimulation {

    public static void main(String[] args){

        List<OthelloSpieler> spieler = new ArrayList<OthelloSpieler>();

        //Spieler müssen ergänzt werden

        new OthelloArena(150,spieler,true);
    }

}
