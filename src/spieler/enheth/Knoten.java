package spieler.enheth;

import spieler.Farbe;
import spieler.Zug;

import java.util.ArrayList;

public class Knoten {

    private int bewertung;
    private ArrayList<Knoten> kindKnoten;
    private Knoten elternKnoten;
    private Zug spielzug;
    private Spielbrett spielbrett;
    private Farbe spielzugFarbe;

    public Knoten(int bewertung, ArrayList<Knoten> kindKnoten, Zug spielzug, Farbe spielzugFarbe){
        this.bewertung = bewertung;
        this.kindKnoten = kindKnoten;
        this.spielzug = spielzug;
        this.spielzugFarbe = spielzugFarbe;
    }

    public Knoten(Zug spielzug, Spielbrett brett){
        this.kindKnoten = new ArrayList<>();
        this.spielzug = spielzug;
        this.spielbrett = brett;
    }

    public Knoten getElternKnoten() {
        return elternKnoten;
    }

    public Spielbrett getSpielbrett(){
        return spielbrett;
    }

    public void setBewertung(int bewertung){
        this.bewertung = bewertung;
    }

    public void kindKnotenHinzufuegen(Knoten k){
        k.setElternKnoten(this);
        this.kindKnoten.add(k);
    }

    public void setElternKnoten(Knoten elternKnoten){
        this.elternKnoten = elternKnoten;
    }

    public Farbe getSpielzugFarbe() {
        return spielzugFarbe;
    }

    public int getBewertung() {
        return bewertung;
    }

    public Zug getSpielzug() {
        return spielzug;
    }

    public ArrayList<Knoten> getKindKnoten(){
        return this.kindKnoten;
    }
}
