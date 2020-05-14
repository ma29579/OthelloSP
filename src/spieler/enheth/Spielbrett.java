package spieler.enheth;

import spieler.Farbe;

public class Spielbrett {

    private Farbe[][] spielbrett;

    public Spielbrett(){

        spielbrett = new Farbe[7][7];

        //Initialisierung des Spielbretts
        spielbrett[3][3] = Farbe.WEISS;
        spielbrett[3][4] = Farbe.SCHWARZ;
        spielbrett[4][3]= Farbe.SCHWARZ;
        spielbrett[4][4] = Farbe.WEISS;
    }

}
