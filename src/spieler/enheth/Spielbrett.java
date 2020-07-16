package spieler.enheth;

import spieler.Farbe;
import spieler.Zug;

import java.util.ArrayList;

public class Spielbrett {

    private Farbe[][] spielbrett;
    //Stellt eine Bewertungsvorlage für Spielbrettpositionen da
    private int[][] bewertungsmatrix = {{50, -20, 10, 5, 5, 10, -20, 50},
                                        {-20, -30, 1, 1, 1, 1, -30, -20},
                                        {10, 1, 1, 1, 1, 1, 1, 10},
                                        {5, 1, 1, 1, 1, 1, 1, 5},
                                        {5, 1, 1, 1, 1, 1, 1, 5},
                                        {10, 1, 1, 1, 1, 1, 1, 10},
                                        {-20, -30, 1, 1, 1, 1, -30, -20},
                                        {50, -20, 10, 5, 5, 10, -20, 50}};

    /**
     * Initialisiert ein Spielbrett, mit einer Grundbelegung der Spielsteine
     */
    public Spielbrett() {

        spielbrett = new Farbe[8][8];

        for (int i = 0; i <= 7; i++) {
            for (int j = 0; j <= 7; j++) {
                spielbrett[i][j] = Farbe.LEER;
            }
        }

        //Initialisierung des Spielbretts
        spielbrett[3][3] = Farbe.WEISS;
        spielbrett[3][4] = Farbe.SCHWARZ;
        spielbrett[4][3] = Farbe.SCHWARZ;
        spielbrett[4][4] = Farbe.WEISS;

    }

    /**
     * Instanziiert ein neues Spielbrett-Objekt und kopiert in dieses die aktuelle Spielbrettkonstellation
     * @return gibt das neu erstellte Spielbrett zurück, dass die kopierte Spielbrettkonstellation enthält.
     */
    public Spielbrett kopieBereitstellen() {

        Spielbrett ergebnis = new Spielbrett();

        //Zählung der existierenden Spielsteine
        for (int zeile = 0; zeile < 8; zeile++) {
            for (int spalte = 0; spalte < 8; spalte++) {
                ergebnis.spielbrett[zeile][spalte] = this.spielbrett[zeile][spalte];
            }
        }

        return ergebnis;
    }

    /**
     * Sucht alle möglichen Züge für die aktuelle Spielbrettkonstelation und führt dabei eine Vorsortierung der Züge durch
     * Durch die Vorsortierung sollen möglichst früh, viele Cut-Offs erzeugt werden
     * @param zugFarbe, erwartet die Information, über die aktuelle Zug-Farbe
     * @return gibt eine ArrayList von Zug-Objekten zurück, die mögliche Züge repräsentieren
     */
    public ArrayList<Zug> sucheAlleMoeglichenZuge(Farbe zugFarbe) {

        //Speichert die Gesamtheit aller möglichen Züge und wird zurückgegeben
        ArrayList<Zug> moeglicheZuege = new ArrayList<Zug>();

        ArrayList<Zug> guteZuege = new ArrayList<>();
        ArrayList<Zug> schlechteZuege = new ArrayList<>();
        ArrayList<Zug> geringBewerteteZuege = new ArrayList<>();

        for (int zeile = 0; zeile < 8; zeile++) {
            for (int spalte = 0; spalte < 8; spalte++) {
                if(zugErlaubt(zugFarbe, zeile, spalte)){
                    //In Abhängigkeit zu Spielbrettposition wird eine Vorsortierung der Züge durchgeführt
                    if(ueberpruefeGutenZug(zeile,spalte))
                        guteZuege.add(new Zug(zeile,spalte));
                    else if(ueberpruefeSchlechtenZug(zeile,spalte))
                        schlechteZuege.add(new Zug(zeile,spalte));
                    else
                        geringBewerteteZuege.add(new Zug(zeile,spalte));
                }
            }
        }

        //Die gefundenen Züge werden sortiert und dann in einer ausgewählten Sortierung in die Ergebnis-ArrayList-Instanz eingeführt
        //Zunächst folgen die besten Züge, ehe die schlechtesten Züge zu letzt kommen
        guteZuege.sort((Zug a, Zug b) ->  bewertungsmatrix[b.getZeile()][b.getSpalte()] - bewertungsmatrix[a.getZeile()][a.getSpalte()]);
        schlechteZuege.sort((Zug a, Zug b) ->  bewertungsmatrix[b.getZeile()][b.getSpalte()] - bewertungsmatrix[a.getZeile()][a.getSpalte()]);
        moeglicheZuege.addAll(guteZuege);
        moeglicheZuege.addAll(geringBewerteteZuege);
        moeglicheZuege.addAll(schlechteZuege);

        return moeglicheZuege;
    }


    /**
     * Überprüft, ob es sich bei der erhaltenen Spielbrettposition, um eine als 'gut'-bewertete Position handelt.
     * @param zeile, entspricht der Zeile der zu überprüfenden Spielbrett-Position
     * @param spalte, entspricht der Spalte der zu überprüfenden Spielbrett-Position
     * @return true, wenn die Spielbrettposition in der Bewertungsmatrix mit mehr als einem Punkt bewertet wird. Andernfalls false
     */
    private boolean ueberpruefeGutenZug(int zeile, int spalte){

        if((zeile == 0 || zeile == 7) && (spalte != 1 && spalte != 6))
            return true;
        else if((zeile != 1 && zeile != 6) && (spalte == 0 || spalte == 7))
            return true;
        else
            return false;

    }

    /**
     * Überprüft, ob es sich bei der erhaltenen Spielbrettposition, um eine als 'schlecht'-bewertete Position handelt.
     * @param zeile, entspricht der Zeile der zu überprüfenden Spielbrett-Position
     * @param spalte, entspricht der Spalte der zu überprüfenden Spielbrett-Position
     * @return true, wenn die Spielbrettposition in der Bewertungsmatrix mit weniger als einem Punkt bewertet wird. Andernfalls false
     */
    private boolean ueberpruefeSchlechtenZug(int zeile, int spalte){

        if((zeile == 0 || zeile == 7) && (spalte == 1 || spalte == 6))
            return true;
        else if((zeile == 1 || zeile == 6) && (spalte < 2 || spalte > 5))
            return true;
        else
            return false;

    }

    /**
     * Überprüft, ob ein Zug erlaubt ist.
     * @param zugFarbe, repräsentiert die Farbe des zu überprüfenden Zugs
     * @param zeile, entspricht der Zeile der zu überprüfenden Spielbrett-Position
     * @param spalte, entspricht der Spalte der zu überprüfenden Spielbrett-Position
     * @return true, falls der Zug durchführbar ist, andernfalls false
     */
    private boolean zugErlaubt(Farbe zugFarbe, int zeile, int spalte){
        boolean spielsteinFremdeFarbe = false; //Variable um Abzuspeichern, dass mindestens ein gegnerischer Stein auf dem Weg liegt

        if (spielbrett[zeile][spalte] != Farbe.LEER) //wenn das Feld bereits belegt ist, kann hier kein Zug stattfinden
            return false;

        //Überprüfung, horizontal rechts
        for (int j = spalte + 1; j < 8 && spielbrett[zeile][j] != Farbe.LEER; j++) { //Iteriere über Zeile bis Spielfeldende und solange das Feld nicht leer ist
            if (spielbrett[zeile][j] != zugFarbe) //Wenn ein Gegnerstein auf dem Weg, Variable setzen
                spielsteinFremdeFarbe = true;

            if (spielbrett[zeile][j] == zugFarbe) { //Wenn ein eigener Stein erreicht wird
                if (spielsteinFremdeFarbe)          //und bereits ein Gegnerstein auf dem Weg lag
                    return true;                    //dann ist der Zug erlaubt
                else
                    break;                          //wenn kein Gegnerstein auf dem Weg lag, ist er verboten
            }

        }

        spielsteinFremdeFarbe = false; //Variable zurücksetzen

        //Überprüfung, horizontal links
        for (int j = spalte - 1; j >= 0 && spielbrett[zeile][j] != Farbe.LEER; j--) { //Gleicher Ablauf, wie in erster Schleife nur in anderer Richtung
            if (spielbrett[zeile][j] != zugFarbe)
                spielsteinFremdeFarbe = true;

            if (spielbrett[zeile][j] == zugFarbe) {
                if (spielsteinFremdeFarbe)
                    return true;
                else
                    break;
            }

        }

        spielsteinFremdeFarbe = false;


        //Überprüfung, vertikal nach oben
        for (int i = zeile - 1; i >= 0 && spielbrett[i][spalte] != Farbe.LEER; i--) {
            if (spielbrett[i][spalte] != zugFarbe)
                spielsteinFremdeFarbe = true;

            if (spielbrett[i][spalte] == zugFarbe) {
                if (spielsteinFremdeFarbe)
                    return true;
                else
                    break;
            }

        }

        spielsteinFremdeFarbe = false;

        //Überprüfung, vertikal nach unten
        for (int i = zeile + 1; i < 8 && spielbrett[i][spalte] != Farbe.LEER; i++) {
            if (spielbrett[i][spalte] != zugFarbe)
                spielsteinFremdeFarbe = true;

            if (spielbrett[i][spalte] == zugFarbe) {
                if (spielsteinFremdeFarbe)
                    return true;
                else
                    break;
            }

        }

        spielsteinFremdeFarbe = false;


        //Überprüfung, diagonal nach unten rechts
        for (int i = zeile + 1, j = spalte + 1; i < 8 && j < 8 && spielbrett[i][j] != Farbe.LEER; i++, j++) {
            if (spielbrett[i][j] != zugFarbe)
                spielsteinFremdeFarbe = true;

            if (spielbrett[i][j] == zugFarbe) {
                if (spielsteinFremdeFarbe)
                    return true;
                else
                    break;
            }

        }

        spielsteinFremdeFarbe = false;


        //Überprüfung, diagonal nach unten links
        for (int i = zeile + 1, j = spalte - 1; i < 8 && j >= 0 && spielbrett[i][j] != Farbe.LEER; i++, j--) {
            if (spielbrett[i][j] != zugFarbe)
                spielsteinFremdeFarbe = true;

            if (spielbrett[i][j] == zugFarbe) {
                if (spielsteinFremdeFarbe)
                    return true;
                else
                    break;
            }

        }

        spielsteinFremdeFarbe = false;

        //Überprüfung, diagonal nach oben rechts
        for (int i = zeile - 1, j = spalte + 1; i >= 0 && j < 8 && spielbrett[i][j] != Farbe.LEER; i--, j++) {
            if (spielbrett[i][j] != zugFarbe)
                spielsteinFremdeFarbe = true;

            if (spielbrett[i][j] == zugFarbe) {
                if (spielsteinFremdeFarbe)
                    return true;
                else
                    break;
            }

        }

        spielsteinFremdeFarbe = false;

        //Überprüfung, diagonal nach oben links
        for (int i = zeile - 1, j = spalte - 1; i >= 0 && j >= 0 && spielbrett[i][j] != Farbe.LEER; i--, j--) {
            if (spielbrett[i][j] != zugFarbe)
                spielsteinFremdeFarbe = true;

            if (spielbrett[i][j] == zugFarbe) {
                if (spielsteinFremdeFarbe)
                    return true;
                else
                    break;
            }

        }

        return false;
    }

    /**
     * Bewertet die aktuelle Spielsituation auf dem Spielbrett in Verbindung mit der bereitgestellten Bewertungsmatrix
     * @param zugFarbe, übermittelt die Spieler-Farbe für die Bewertung
     * @return gibt einen ganzzahligen Wert zurück, der aus der Summe der einzelnen Spielsteinpositionen besteht, die durch die Bewertungsmatrix gewichtet werden
     */
    public int brettBewerten(Farbe zugFarbe) {

        int schwarz = 0;
        int weiss = 0;

        //Zählung der existierenden Spielsteine
        for (int zeile = 0; zeile < 8; zeile++) {
            for (int spalte = 0; spalte < 8; spalte++) {
                if (spielbrett[zeile][spalte] == Farbe.SCHWARZ)
                    schwarz+= bewertungsmatrix[zeile][spalte];
                else if (spielbrett[zeile][spalte] == Farbe.WEISS)
                    weiss+= bewertungsmatrix[zeile][spalte];
            }
        }

        if (zugFarbe == Farbe.SCHWARZ)
            return schwarz - weiss;
        else
            return weiss - schwarz;

    }

    /**
     * Führt einen erhaltenen Zug auf dem aktuellen Spielbrett durch und verändert das Spielbrett dementsprechend
     * @param input, stellt den anzuwendenden Zug da
     * @param zugFarbe, übermittelt die Farbe des anzuwendenden zugs
     */
    public void zugAusfuehren(Zug input, Farbe zugFarbe) {

        this.spielbrett[input.getZeile()][input.getSpalte()] = zugFarbe; //Stein an Position setzen

        //vertikal nach unten
        for (int zeile = input.getZeile() + 1; zeile < 8; zeile++) { //Feldspalte nach unten durchgehen bis zum Ende durchgehen

            if (this.spielbrett[zeile][input.getSpalte()] == Farbe.LEER) //Wenn ein leeres Feld kommt abbrechen
                break;

            if (this.spielbrett[zeile][input.getSpalte()] == zugFarbe) { //Wenn ein Feld mit dem eigenen Stein besetzt ist, müssen alle Steine dazwischen nun auch zu eigenen werden

                for (int i = input.getZeile(); i <= zeile; i++) {       //Vom neu gesetzten Stein bis zu diesem Stein iterieren
                    this.spielbrett[i][input.getSpalte()] = zugFarbe;   //und dort alle Steine auf die eigene Farbe setzen
                }

                break; //Vorgang in diese Richtung fertig

            }
        }

        //vertikal nach oben
        for (int zeile = input.getZeile() - 1; zeile >= 0; zeile--) { //Gleicher Ablauf, wie in erster Schleife nur in anderer Richtung

            if (this.spielbrett[zeile][input.getSpalte()] == Farbe.LEER)
                break;

            if (this.spielbrett[zeile][input.getSpalte()] == zugFarbe) {

                for (int i = input.getZeile(); i >= zeile; i--) {
                    this.spielbrett[i][input.getSpalte()] = zugFarbe;
                }

                break;

            }
        }

        //horizontal nach links
        for (int spalte = input.getSpalte() - 1; spalte >= 0; spalte--) {

            if (this.spielbrett[input.getZeile()][spalte] == Farbe.LEER)
                break;

            if (this.spielbrett[input.getZeile()][spalte] == zugFarbe) {

                for (int i = input.getSpalte(); i >= spalte; i--) {
                    this.spielbrett[input.getZeile()][i] = zugFarbe;
                }

                break;

            }
        }


        //horizontal nach rechts
        for (int spalte = input.getSpalte() + 1; spalte < 8; spalte++) {

            if (this.spielbrett[input.getZeile()][spalte] == Farbe.LEER)
                break;

            if (this.spielbrett[input.getZeile()][spalte] == zugFarbe) {

                for (int i = input.getSpalte(); i <= spalte; i++) {
                    this.spielbrett[input.getZeile()][i] = zugFarbe;
                }

                break;
            }
        }

        //Diagonal nach unten rechts
        for (int zeile = input.getZeile() + 1, spalte = input.getSpalte() + 1; zeile < 8 && spalte < 8; zeile++, spalte++) {

            if (this.spielbrett[zeile][spalte] == Farbe.LEER)
                break;

            if (this.spielbrett[zeile][spalte] == zugFarbe) {

                for (int i = input.getZeile(), j = input.getSpalte(); i <= zeile && j <= spalte; i++, j++) {
                    this.spielbrett[i][j] = zugFarbe;
                }

                break;
            }

        }

        //Diagonal nach unten links
        for (int zeile = input.getZeile() + 1, spalte = input.getSpalte() - 1; zeile < 8 && spalte >= 0; zeile++, spalte--) {

            if (this.spielbrett[zeile][spalte] == Farbe.LEER)
                break;

            if (this.spielbrett[zeile][spalte] == zugFarbe) {

                for (int i = input.getZeile(), j = input.getSpalte(); i <= zeile && j >= spalte; i++, j--) {
                    this.spielbrett[i][j] = zugFarbe;
                }

                break;
            }

        }

        //Diagonal nach oben links
        for (int zeile = input.getZeile() - 1, spalte = input.getSpalte() - 1; zeile >= 0 && spalte >= 0; zeile--, spalte--) {

            if (this.spielbrett[zeile][spalte] == Farbe.LEER)
                break;

            if (this.spielbrett[zeile][spalte] == zugFarbe) {

                for (int i = input.getZeile(), j = input.getSpalte(); i >= zeile && j >= spalte; i--, j--) {
                    this.spielbrett[i][j] = zugFarbe;
                }

                break;
            }

        }

        //Diagonal nach oben rechts
        for (int zeile = input.getZeile() - 1, spalte = input.getSpalte() + 1; zeile >= 0 && spalte < 8; zeile--, spalte++) {

            if (this.spielbrett[zeile][spalte] == Farbe.LEER)
                break;

            if (this.spielbrett[zeile][spalte] == zugFarbe) {

                for (int i = input.getZeile(), j = input.getSpalte(); i >= 0 && j <= spalte; i--, j++) {
                    this.spielbrett[i][j] = zugFarbe;
                }

                break;
            }

        }

    }
}
