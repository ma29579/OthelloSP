package spieler.enheth;

import spieler.Farbe;
import spieler.OthelloSpieler;
import spieler.Zug;
import spieler.ZugException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Spieler implements OthelloSpieler {

    //Speichert das Spielbrett
    private Spielbrett brett;
    private Farbe eigeneFarbe;
    private Farbe gegnerischeFarbe;
    private int tiefe = 2;

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

        //Konstruktion des Spielbaums
        spielbaum = new Spielbaum(new Knoten(zug, this.brett));
        letzteEbene.add(spielbaum.getWurzel());

        for (int aktuelleTiefe = 1; aktuelleTiefe <= tiefe; aktuelleTiefe++) {

            Farbe aktuelleSpielzugfarbe = aktuelleTiefe % 2 == 0 ? gegnerischeFarbe : eigeneFarbe;

            ArrayList<Knoten> tmp = new ArrayList<>(letzteEbene);

            for (Knoten k : tmp) {

                ArrayList<Zug> moeglicheZuege = k.getSpielbrett().sucheAlleMoeglichenZuge(aktuelleSpielzugfarbe);
                letzteEbene.remove(k);

                for (Zug aktuellerZug : moeglicheZuege) {

                    Knoten neuerKindknoten = new Knoten(aktuellerZug, k.getSpielbrett().brettSimulationBereitstellen(aktuellerZug, aktuelleSpielzugfarbe));
                    k.kindKnotenHinzufuegen(neuerKindknoten);
                    letzteEbene.add(neuerKindknoten);
                }
            }

        }

        //Bewertung der letzten Ebene des Spielbaums
        for (Knoten k : letzteEbene) {
            Spielbrett aktuellesBrett = k.getSpielbrett();
            k.setBewertung(aktuellesBrett.brettBewerten(k.getSpielzug(), eigeneFarbe));
        }

        //Anwendung des Minimax-Algorithmus
        Set<Knoten> elternEbene = new HashSet<Knoten>();
        ArrayList<Knoten> kindEbene = new ArrayList<>(letzteEbene);
        int bedingung = 0;

        while (!elternEbene.contains(spielbaum.getWurzel()) && kindEbene.size() > 0) {

            elternEbene.clear();

            //Hinzufügen aller Elterknoten, von Knoten in der aktuell betrachteten Ebene
            for (Knoten k : kindEbene) {
                elternEbene.add(k.getElternKnoten());
            }

            //Betrachtung aller Kindknoten und Bewertung der dazugehörigen Elternknoten
            for (Knoten k : elternEbene) {

                ArrayList<Knoten> kindKnoten = k.getKindKnoten();

                //Maximieren
                if (bedingung % 2 == 0) {

                    if (kindKnoten.size() > 1) {

                        int bewertung = kindKnoten.get(0).getBewertung();

                        for (int i = 1; i < kindKnoten.size(); i++) {
                            if (bewertung < kindKnoten.get(i).getBewertung())
                                bewertung = kindKnoten.get(i).getBewertung();
                        }

                        k.setBewertung(bewertung);

                    } else {

                        k.setBewertung(kindKnoten.get(0).getBewertung());

                    }

                }
                //Minimieren
                else {

                    if (kindKnoten.size() > 1) {

                        int bewertung = kindKnoten.get(0).getBewertung();

                        for (int i = 1; i < kindKnoten.size(); i++) {
                            if (bewertung > kindKnoten.get(i).getBewertung())
                                bewertung = kindKnoten.get(i).getBewertung();
                        }

                        k.setBewertung(bewertung);

                    } else {

                        k.setBewertung(kindKnoten.get(0).getBewertung());

                    }

                }

            }

            //Inkrement der betrachteten Ebene
            bedingung++;
            //Bisherige Liste der Kindknoten wird geleert
            kindEbene.clear();
            //Bei den neuen Kindknoten handelt es sich nun um die vorherigen Elternknoten
            kindEbene.addAll(elternEbene);
        }

        Zug besterZug = null;

        for (Knoten k : spielbaum.getWurzel().getKindKnoten()) {
            if (k.getBewertung() == spielbaum.getWurzel().getBewertung())
                besterZug = k.getSpielzug();
        }

        if (besterZug != null) {
            this.brett.zugAusfuehren(besterZug, eigeneFarbe);
            return besterZug;
        } else {
            besterZug = new Zug(1,1);
            besterZug.setPassen();
            return besterZug;
        }
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
