package Memento;

import Mazzo.Carta;
import Observer.Giocatore;

import java.io.Serial;
import java.util.List;
import java.io.Serializable;

/**
 * Classe che contiene tutti i dati da salvare
 */
public class GameData implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private final List<Giocatore> giocatore;
    private final List<Carta> mazzo;
    private final String modalita;

    public GameData(List<Giocatore> giocatori,List<Carta> mazzo, String modalita) {
        this.giocatore = giocatori;
        this.mazzo = mazzo;
        this.modalita = modalita;
    }

    public String getModalita() {
        return this.modalita;
    }

    public List<Giocatore> getGiocatore() {
        return this.giocatore;
    }

    public List<Carta> getMazzo() {
        return this.mazzo;
    }
}
