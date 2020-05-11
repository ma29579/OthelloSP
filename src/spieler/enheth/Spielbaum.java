package spieler.enheth;

import spieler.Zug;

import java.util.ArrayDeque;
import java.util.List;

public class Spielbaum {

    private Knoten wurzel;
    private ArrayDeque<Knoten> suchagenda;

    public Spielbaum(Knoten wurzel){
        this.wurzel = wurzel;
    }

    public Knoten sucheZug(Zug gesuchterSpielzug){

        suchagenda = new ArrayDeque<>();
        suchagenda.add(this.wurzel);

        while(!suchagenda.isEmpty()){

            Knoten aktuellerKnoten = suchagenda.poll();

            if(aktuellerKnoten.getSpielzug().equals(gesuchterSpielzug))
                return aktuellerKnoten;
        }

        return null;
    }
}
