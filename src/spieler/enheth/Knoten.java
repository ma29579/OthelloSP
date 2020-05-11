package spieler.enheth;

import spieler.Farbe;
import spieler.Zug;

import java.util.ArrayList;

public class Knoten {

    private int bewertung;
    private ArrayList<Knoten> KindKnoten;
    private Zug spielzug;
    private Farbe spielzugFarbe;

    public Farbe getSpielzugFarbe() {
        return spielzugFarbe;
    }

    public int getBewertung() {
        return bewertung;
    }

    public Zug getSpielzug() {
        return spielzug;
    }
}
