package spieler.enheth;

import spieler.Farbe;
import spieler.Zug;

import java.util.ArrayList;

public class Spielbrett {

    private Farbe[][] spielbrett;

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

    public static Farbe gegenteilFarbe(Farbe f){
        if(f == Farbe.SCHWARZ)
            return Farbe.WEISS;
        else
            return Farbe.SCHWARZ;
    }

    public Spielbrett brettSimulationBereitstellen(Zug z, Farbe f){

        Spielbrett ergebnis = new Spielbrett();

        //Zählung der existierenden Spielsteine
        for (int zeile = 0; zeile < 8; zeile++) {
            for (int spalte = 0; spalte < 8; spalte++) {
                ergebnis.spielbrett[zeile][spalte] = this.spielbrett[zeile][spalte];
            }
        }
        ergebnis.zugAusfuehren(z,f);

        return ergebnis;
    }


    public ArrayList<Zug> sucheAlleMoeglichenZuge(Farbe zugFarbe) {

        ArrayList<Zug> moeglicheZuege = new ArrayList<Zug>();

        boolean spielsteinFremdeFarbe = false;

        for (int zeile = 0; zeile < 8; zeile++) {
            for (int spalte = 0; spalte < 8; spalte++) {

                spielsteinFremdeFarbe = false;

                if (spielbrett[zeile][spalte] != Farbe.LEER)
                    continue;

                //Überprüfung, horizontal rechts
                for (int j = spalte + 1; j < 8 && spielbrett[zeile][j] != Farbe.LEER; j++) {
                    if (spielbrett[zeile][j] != zugFarbe)
                        spielsteinFremdeFarbe = true;

                    if (spielbrett[zeile][j] == zugFarbe) {
                        if (spielsteinFremdeFarbe)
                            moeglicheZuege.add(new Zug(zeile, spalte));
                        else
                            break;
                    }

                }

                spielsteinFremdeFarbe = false;

                //Überprüfung, horizontal links
                for (int j = spalte - 1; j >= 0 && spielbrett[zeile][j] != Farbe.LEER; j--) {
                    if (spielbrett[zeile][j] != zugFarbe)
                        spielsteinFremdeFarbe = true;

                    if (spielbrett[zeile][j] == zugFarbe) {
                        if (spielsteinFremdeFarbe)
                            moeglicheZuege.add(new Zug(zeile, spalte));
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
                            moeglicheZuege.add(new Zug(zeile, spalte));
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
                            moeglicheZuege.add(new Zug(zeile, spalte));
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
                            moeglicheZuege.add(new Zug(zeile, spalte));
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
                            moeglicheZuege.add(new Zug(zeile, spalte));
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
                            moeglicheZuege.add(new Zug(zeile, spalte));
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
                            moeglicheZuege.add(new Zug(zeile, spalte));
                        else
                            break;
                    }

                }

                spielsteinFremdeFarbe = false;

            }
        }

        this.spielbrettAusgeben();
        System.out.println("__________________");
        return moeglicheZuege;
    }

    //Bewertet einen übergebenen Zug, anhand der Feldverteilung der Spielfarben
    public int brettBewerten(Zug input, Farbe zugFarbe) {

        int schwarz = 0;
        int weiss = 0;

        //Zählung der existierenden Spielsteine
        for (int zeile = 0; zeile < 8; zeile++) {
            for (int spalte = 0; spalte < 8; spalte++) {
                if (spielbrett[zeile][spalte] == Farbe.SCHWARZ)
                    schwarz++;
                else if (spielbrett[zeile][spalte] == Farbe.WEISS)
                    weiss++;
            }
        }

        if (zugFarbe == Farbe.SCHWARZ)
            return schwarz - weiss;
        else
            return weiss - schwarz;

    }

    public void spielbrettAusgeben() {

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (spielbrett[i][j] == Farbe.LEER)
                    System.out.print(" 🟥 ");
                else if (spielbrett[i][j] == Farbe.WEISS)
                    System.out.print(" 🟤 ");
                else
                    System.out.print(" 🟡 ");
            }

            System.out.println("");
        }

    }

    public void zugAusfuehren(Zug input, Farbe zugFarbe) {

        spielfeldVeraendernNachZug(input, zugFarbe, this.spielbrett);

    }

    private void spielfeldVeraendernNachZug(Zug input, Farbe zugFarbe, Farbe zuVeraenderndesSpielbrett[][]) {

        zuVeraenderndesSpielbrett[input.getZeile()][input.getSpalte()] = zugFarbe;

        //vertikal nach unten
        for (int zeile = input.getZeile() + 1; zeile < 8; zeile++) {

            if (zuVeraenderndesSpielbrett[zeile][input.getSpalte()] == Farbe.LEER)
                break;

            if (zuVeraenderndesSpielbrett[zeile][input.getSpalte()] == zugFarbe) {

                for (int i = input.getZeile(); i <= zeile; i++) {
                    zuVeraenderndesSpielbrett[i][input.getSpalte()] = zugFarbe;
                }

                break;

            }
        }

        //vertikal nach oben
        for (int zeile = input.getZeile() - 1; zeile >= 0; zeile--) {

            if (zuVeraenderndesSpielbrett[zeile][input.getSpalte()] == Farbe.LEER)
                break;

            if (zuVeraenderndesSpielbrett[zeile][input.getSpalte()] == zugFarbe) {

                for (int i = input.getZeile(); i >= zeile; i--) {
                    zuVeraenderndesSpielbrett[i][input.getSpalte()] = zugFarbe;
                }

                break;

            }
        }

        //horizontal nach links
        for (int spalte = input.getSpalte() - 1; spalte >= 0; spalte--) {

            if (zuVeraenderndesSpielbrett[input.getZeile()][spalte] == Farbe.LEER)
                break;

            if (zuVeraenderndesSpielbrett[input.getZeile()][spalte] == zugFarbe) {

                for (int i = input.getSpalte(); i >= spalte; i--) {
                    zuVeraenderndesSpielbrett[input.getZeile()][i] = zugFarbe;
                }

                break;

            }
        }


        //horizontal nach rechts
        for (int spalte = input.getSpalte() + 1; spalte < 8; spalte++) {

            if (zuVeraenderndesSpielbrett[input.getZeile()][spalte] == Farbe.LEER)
                break;

            if (zuVeraenderndesSpielbrett[input.getZeile()][spalte] == zugFarbe) {

                for (int i = input.getSpalte(); i <= spalte; i++) {
                    zuVeraenderndesSpielbrett[input.getZeile()][i] = zugFarbe;
                }

                break;
            }
        }

        //Diagonal nach unten rechts
        for (int zeile = input.getZeile() + 1, spalte = input.getSpalte() + 1; zeile < 8 && spalte < 8; zeile++, spalte++) {

            if (zuVeraenderndesSpielbrett[zeile][spalte] == Farbe.LEER)
                break;

            if (zuVeraenderndesSpielbrett[zeile][spalte] == zugFarbe) {

                for (int i = input.getZeile(), j = input.getSpalte(); i <= zeile && j <= spalte; i++, j++) {
                    zuVeraenderndesSpielbrett[i][j] = zugFarbe;
                }

                break;
            }

        }

        //Diagonal nach unten links
        for (int zeile = input.getZeile() + 1, spalte = input.getSpalte() - 1; zeile < 8 && spalte >= 0; zeile++, spalte--) {

            if (zuVeraenderndesSpielbrett[zeile][spalte] == Farbe.LEER)
                break;

            if (zuVeraenderndesSpielbrett[zeile][spalte] == zugFarbe) {

                for (int i = input.getZeile(), j = input.getSpalte(); i <= zeile && j >= spalte; i++, j--) {
                    zuVeraenderndesSpielbrett[i][j] = zugFarbe;
                }

                break;
            }

        }

        //Diagonal nach oben links
        for (int zeile = input.getZeile() - 1, spalte = input.getSpalte() - 1; zeile >= 0 && spalte >= 0; zeile--, spalte--) {

            if (zuVeraenderndesSpielbrett[zeile][spalte] == Farbe.LEER)
                break;

            if (zuVeraenderndesSpielbrett[zeile][spalte] == zugFarbe) {

                for (int i = input.getZeile(), j = input.getSpalte(); i >= zeile && j >= spalte; i--, j--) {
                    zuVeraenderndesSpielbrett[i][j] = zugFarbe;
                }

                break;
            }

        }

        //Diagonal nach oben rechts
        for (int zeile = input.getZeile() - 1, spalte = input.getSpalte() + 1; zeile >= 0 && spalte < 8; zeile--, spalte++) {

            if (zuVeraenderndesSpielbrett[zeile][spalte] == Farbe.LEER)
                break;

            if (zuVeraenderndesSpielbrett[zeile][spalte] == zugFarbe) {

                for (int i = input.getZeile(), j = input.getSpalte(); i >= 0 && j >= spalte; i--, j++) {
                    zuVeraenderndesSpielbrett[i][j] = zugFarbe;
                }

                break;
            }

        }

    }
}
