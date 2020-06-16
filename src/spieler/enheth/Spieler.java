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
    private int tiefe = 8;
    private Zug besterZug;

    //Standardkonstruktor
    
    Spieler() {
    }

    //Optionaler Konstruktor
    Spieler(int suchtiefe) {
        this.tiefe = suchtiefe;
    }

    @Override
    public Zug berechneZug(Zug zug, long l, long l1) throws ZugException {
        int alpha = Integer.MIN_VALUE;
        int beta = Integer.MAX_VALUE;

        if (zug != null && !zug.getPassen()) {
            //Durchführung des erhaltenen gegnerischen Spielzugs
            brett.zugAusfuehren(zug, gegnerischeFarbe);
        }

        this.besterZug = null;

        max(tiefe,Integer.MIN_VALUE,Integer.MAX_VALUE);

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

        //In Abhängigkeit zu der eigenen Farbe, wird das Attribut 'gegnerischeFarbe' festgelegt
        if (farbe == Farbe.SCHWARZ) {
            gegnerischeFarbe = Farbe.WEISS;
        } else {
            gegnerischeFarbe = Farbe.SCHWARZ;
        }

    }


    private int max(int tiefe, int alpha, int beta){
        if(tiefe == 0 || this.brett.sucheAlleMoeglichenZuge(this.eigeneFarbe).size() == 0)
            return this.brett.brettBewerten(this.eigeneFarbe);

        Spielbrett aktuellesBrett = this.brett.kopieBereitstellen();

        int maxWert = alpha;

        ArrayList<Zug> zugListe = this.brett.sucheAlleMoeglichenZuge(this.eigeneFarbe);
        for(Zug zug : zugListe){
            this.brett.zugAusfuehren(zug, this.eigeneFarbe);
            int wert = min(tiefe-1, maxWert, beta);
            this.brett = aktuellesBrett.kopieBereitstellen();

            if(wert > maxWert){
                maxWert = wert;

                if(tiefe == this.tiefe)
                    besterZug = zug;

                if(maxWert >= beta)
                    break;
            }
        }
        return maxWert;
    }

    private int min(int tiefe, int alpha, int beta){
        if(tiefe == 0 || this.brett.sucheAlleMoeglichenZuge(this.gegnerischeFarbe).size() == 0)
            return this.brett.brettBewerten(this.eigeneFarbe);

        Spielbrett aktuellesBrett = this.brett.kopieBereitstellen();

        int minWert = beta;

        ArrayList<Zug> zugListe = this.brett.sucheAlleMoeglichenZuge(this.gegnerischeFarbe);
        for(Zug zug : zugListe){
            this.brett.zugAusfuehren(zug, this.gegnerischeFarbe);
            int wert = max(tiefe-1, alpha, minWert);
            this.brett = aktuellesBrett.kopieBereitstellen();

            if(wert < minWert){
                minWert = wert;

                if(minWert <= alpha)
                    break;
            }
        }
        return minWert;
    }


    @Override
    public String meinName() {
        return "enheth";
    }
}
