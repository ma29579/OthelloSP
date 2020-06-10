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
    private int tiefe = 6;
    private Zug besterZug;

    //Standardkonstruktor
    
    Spieler() {
    }

    //Optionaler Konstruktor
    Spieler(int suchtiefe) {
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

        miniMax(eigeneFarbe,tiefe,alpha,beta);

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

    private int miniMax(Farbe zugFarbe, int tiefe, int alpha, int beta){

        if(tiefe == 0 || this.brett.sucheAlleMoeglichenZuge(zugFarbe).size() == 0)
            return this.brett.brettBewerten(zugFarbe);

        Spielbrett aktuellesBrett = this.brett.kopieBereitstellen();

        int maxWert = alpha;
        ArrayList<Zug> zugListe = this.brett.sucheAlleMoeglichenZuge(zugFarbe);

        for(Zug zug : zugListe){
            this.brett.zugAusfuehren(zug,zugFarbe);
            int wert = -miniMax(Spielbrett.gegenteilFarbe(zugFarbe),tiefe-1,-1 * beta,-1 * maxWert);
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

//    private int alphabetaTest(Farbe zugFarbe, int tiefe, int alpha, int beta){
//        if(tiefe == 0 || this.brett.sucheAlleMoeglichenZuge(zugFarbe).size() == 0)
//            return this.brett.brettBewerten(zugFarbe);
//
//        int maxWert = 0;
//
//        ArrayList<Zug> zugListe = this.brett.sucheAlleMoeglichenZuge(zugFarbe);
//
//        if(zugFarbe == this.eigeneFarbe){
//            maxWert = Integer.MIN_VALUE;
//            for(Zug zug : zugListe){
//                Spielbrett aktuellesBrett = this.brett.kopieBereitstellen();
//                this.brett.zugAusfuehren(zug,zugFarbe);
//                int wert = alphabetaTest(Spielbrett.gegenteilFarbe(zugFarbe), tiefe - 1, alpha, beta);
//                if(wert > maxWert){
//                    maxWert = wert;
//                    if(tiefe == this.tiefe)
//                        besterZug = zug;
//                }
//                this.brett = aktuellesBrett.kopieBereitstellen();
//                alpha = Integer.max(alpha, maxWert);
//                if(alpha >= beta)
//                    break;
//            }
//            return maxWert;
//        } else {
//            maxWert = Integer.MAX_VALUE;
//            for(Zug zug : zugListe){
//                Spielbrett aktuellesBrett = this.brett.kopieBereitstellen();
//                this.brett.zugAusfuehren(zug,zugFarbe);
//                maxWert = Integer.min(maxWert, alphabetaTest(Spielbrett.gegenteilFarbe(zugFarbe), tiefe - 1, alpha, beta));
//                this.brett = aktuellesBrett.kopieBereitstellen();
//                beta = Integer.min(beta, maxWert);
//                if(beta <= alpha)
//                    break;
//            }
//            return maxWert;
//        }
//    }

    @Override
    public String meinName() {
        return "enheth";
    }
}
