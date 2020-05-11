package spieler.enheth;

import spieler.Farbe;
import spieler.OthelloSpieler;
import spieler.Zug;
import spieler.ZugException;

public class Spieler implements OthelloSpieler {

    //Speichert das Spielbrett
    private Farbe [][] spielbrett;

    //Standardkonstruktor
    Spieler(){
        spielbrett = new Farbe[7][7];
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

        //Initialisierung des Spielbretts
        spielbrett[3][3] = Farbe.WEISS;
        spielbrett[3][4] = Farbe.SCHWARZ;
        spielbrett[4][3]= Farbe.SCHWARZ;
        spielbrett[4][4] = Farbe.WEISS;
    }

    @Override
    public String meinName() {
        return "enheth";
    }
}
