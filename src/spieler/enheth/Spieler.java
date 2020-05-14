package spieler.enheth;

import spieler.Farbe;
import spieler.OthelloSpieler;
import spieler.Zug;
import spieler.ZugException;

public class Spieler implements OthelloSpieler {

    //Speichert das Spielbrett
    private Spielbrett brett;

    //Standardkonstruktor
    Spieler(){
    }

    //Optionaler Konstruktor
    Spieler(int suchtiefe){
    }

    @Override
    public Zug berechneZug(Zug zug, long l, long l1) throws ZugException {
        return null;
    }

    @Override
    public void neuesSpiel(Farbe farbe, int i) {

        //Spielbrett anlegen
        brett = new Spielbrett();

        //Spielbaum anlegen initialisieren
    }

    @Override
    public String meinName() {
        return "enheth";
    }
}
