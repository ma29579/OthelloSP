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
    private int tiefe;
    private Zug besterZug;

    //Standardkonstruktor
    public Spieler() {
        this.tiefe = 8;
    }

    //Optionaler Konstruktor
    public Spieler(int suchtiefe) {
        this.tiefe = suchtiefe;
    }

    /**
     * Berechnet den nächsten Zug des Spielers
     * @param zug Zug Ihres Gegenspielers. Wenn Sie als Erster ziehen, ist der Wert null
     * @param l Bisher verbrauchte Zeit von Spieler Weiss in ms.
     * @param l1 Bisher verbrauchte Zeit von Spieler Schwarz in ms.
     * @return Zug des Spielers
     * @throws ZugException Kann geschmissen werden, wenn der Gegner einen illegalen Zug vorgenommen hat
     */
    @Override
    public Zug berechneZug(Zug zug, long l, long l1) throws ZugException {
        int alpha = Integer.MIN_VALUE;
        int beta = Integer.MAX_VALUE;

        if (zug != null && !zug.isPassen()) {
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
            besterZug = Zug.passenZug();
            return besterZug;
        }
    }

    /**
     * Konfiguriert den Spieler für ein neues Spiel
     * @param farbe Farbe unseres Spielers
     * @param i Bedenkzeit in Sekunden
     */
    @Override
    /**
     * Initialisiert ein neues Objekt der Spielbrettk-Klasse für das Attribut 'brett'
     * und legt anhand des erhaltenen Parameters die eigene und die gegnersiche Spielfarbe fest.
     */
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

    /**
     * Alpha-Beta-Algorithmus Max Funktion, die jede zweite Baumebene für uns maximiert
     * @param tiefe Baumetiefe, die gerade untersucht wird
     * @param alpha Niedrigster Wert, den wir mindestens erreichen
     * @param beta Höchster Wert, den der Gegner maximal zulässt (er will minimieren)
     * @return maxWert für besten Zug
     */
    private int max(int tiefe, int alpha, int beta){
        if(tiefe == 0 || this.brett.sucheAlleMoeglichenZuge(this.eigeneFarbe).size() == 0) //Wenn wir auf unterster Baumebene angekommen sind oder keine Züge mehr vorhanden sind
            return this.brett.brettBewerten(this.eigeneFarbe);  //gebe die Bewertung des Zuges zurück

        Spielbrett aktuellesBrett = this.brett.kopieBereitstellen(); //Kopie des Bretts für später speichern

        int maxWert = alpha;    //Maxwert ist der aktuelle Mindestwert, der sich bereits ergeben hat

        ArrayList<Zug> zugListe = this.brett.sucheAlleMoeglichenZuge(this.eigeneFarbe); //Alle Züge suchen
        for(Zug zug : zugListe){
            this.brett.zugAusfuehren(zug, this.eigeneFarbe); //Zugs ausführen
            int wert = min(tiefe-1, maxWert, beta); //Wert des Zuges rekursiv in min Funktion bestimmen
            this.brett = aktuellesBrett.kopieBereitstellen(); //Zug rückgängig machen

            if(wert > maxWert){ //Wenn neuer Wert größer als Maxwert
                maxWert = wert; //Maxwert überschreibe

                if(tiefe == this.tiefe) //Wenn wir eine Bewertung im Wurzelknoten haben
                    besterZug = zug; //Besten Zug speichern, um ihn gleich zurückzugeben

                if(maxWert >= beta) //Beta-Cutoff, der Wert ist größer als der maximale Wert, den der Gegner zulassen wird (Beta)
                    break;
            }
        }
        return maxWert; //Maxwert zurückgeben
    }

    /**
     * Alpha-Beta-Algorithmus Min Funktion, die jede zweite Baumebende für den Gegner minimiert
     * @param tiefe Baumetiefe, die gerade untersucht wird
     * @param alpha Niedrigster Wert, den wir mindestens erreichen
     * @param beta Höchster Wert, den der Gegner maximal zulässt (er will minimieren)
     * @return minWert für besten Zug des Gegners (der Gegner minimiert)
     */
    private int min(int tiefe, int alpha, int beta){
        if(tiefe == 0 || this.brett.sucheAlleMoeglichenZuge(this.gegnerischeFarbe).size() == 0)
            return this.brett.brettBewerten(this.eigeneFarbe); //auch hier wird die Bewertung für unsere eigene Farbe aufgerufen, da der Gegner in diesem Teil des Algorithmus diesen Wert quasi minimieren will.

        Spielbrett aktuellesBrett = this.brett.kopieBereitstellen();

        int minWert = beta;

        ArrayList<Zug> zugListe = this.brett.sucheAlleMoeglichenZuge(this.gegnerischeFarbe);
        for(Zug zug : zugListe){
            this.brett.zugAusfuehren(zug, this.gegnerischeFarbe);
            int wert = max(tiefe-1, alpha, minWert);
            this.brett = aktuellesBrett.kopieBereitstellen();

            if(wert < minWert){
                minWert = wert;

                if(minWert <= alpha) //Alpha-Cutoff, der Wert ist kleiner als unser zugesicherter Mindestwert Alpha
                    break;
            }
        }
        return minWert;
    }

    /**
     * Gibt den Namen des Spielers zurück
     * @return der Name
     */
    @Override
    public String meinName() {
        return "enheth";
    }
}
