package spieler.enheth;

import spieler.Farbe;
import spieler.OthelloSpieler;
import spieler.Zug;
import spieler.ZugException;

import java.util.ArrayList;

public class Spieler implements OthelloSpieler {

    //Speichert das Spielbrett
    private Spielbrett brett;
    private Farbe eigeneFarbe;
    private Farbe gegnerischeFarbe;
    private int tiefe = 4;

    //Standardkonstruktor
    Spieler() {
    }

    //Optionaler Konstruktor
    Spieler(int suchtiefe) {
    }

    @Override
    public Zug berechneZug(Zug zug, long l, long l1) throws ZugException {

        ArrayList<Knoten> letzteEbene = new ArrayList<>();
        Spielbaum spielbaum;
        Farbe lezteFarbe = eigeneFarbe;

        if (zug != null) {
            //Durchführung des erhaltenen gegnerischen Spielzugs
            brett.zugAusfuehren(zug, gegnerischeFarbe);
            lezteFarbe = gegnerischeFarbe;
        }

        spielbaum = new Spielbaum(new Knoten(zug, this.brett));

        letzteEbene.add(spielbaum.getWurzel());

        for (int aktuelleTiefe = 1; aktuelleTiefe <= tiefe; aktuelleTiefe++) {

            Farbe aktuelleSpielzugfarbe = aktuelleTiefe % 2 == 0 ? gegnerischeFarbe : eigeneFarbe;

            ArrayList<Knoten> tmp = new ArrayList<>(letzteEbene);

            for (Knoten k : tmp) {

                ArrayList<Zug> moeglicheZuege = k.getSpielbrett().sucheAlleMoeglichenZuge(aktuelleSpielzugfarbe);
                letzteEbene.remove(k);

                for(Zug aktuellerZug : moeglicheZuege){

                    Knoten neuerKindknoten = new Knoten(aktuellerZug, this.brett.brettSimulationBereitstellen(aktuellerZug, aktuelleSpielzugfarbe));
                    k.kindKnotenHinzufuegen(neuerKindknoten);
                    letzteEbene.add(neuerKindknoten);
                }
            }

        }

        for (Knoten k : letzteEbene) {
            Spielbrett aktuellesBrett = k.getSpielbrett();
            k.setBewertung(aktuellesBrett.zugBewerten(k.getSpielzug(), eigeneFarbe));
        }

        int i = 0;

        return null;
    }

    @Override
    public void neuesSpiel(Farbe farbe, int i) {

        //Spielbrett anlegen
        brett = new Spielbrett();

        //Eigene Farbe festlegen
        this.eigeneFarbe = farbe;

        if (farbe == Farbe.SCHWARZ) {
            gegnerischeFarbe = Farbe.WEISS;
        } else {
            gegnerischeFarbe = Farbe.SCHWARZ;
        }

    }

    @Override
    public String meinName() {
        return "enheth";
    }
}
