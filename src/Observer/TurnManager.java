package Observer;


import java.util.ArrayList;
import java.util.List;

public class TurnManager implements TurnSubject {
    private final List<TurnObserver> observers = new ArrayList<>(4);
    private final List<Giocatore> giocatore = new ArrayList<>(4);
    private int index = 0;

    public void aggiungiGiocatore(Giocatore giocatore) {
        this.giocatore.add(giocatore);
        addObserver(giocatore);
    }

    public void setGiocatori(List<Giocatore> giocatore) {
        this.giocatore.clear();
        this.giocatore.addAll(giocatore);
    }

    public void resetTurni() {
        this.index = 0;
        notifyObservers();
    }

    public void nextTurn() {
        if (giocatore.get(index).getNome().isEmpty()) {
            nextTurn();
        }
            index++;
            notifyObservers();
    }

    public List<Giocatore> getGiocatori() {
        return this.giocatore;
    }

    public List<Giocatore> getGiocatoriNoMazziere(){
        List<Giocatore> giocatoreNoMazziere = new ArrayList<>();
        for (Giocatore giocatore : getGiocatori())
            if (!giocatore.isMazziere())
                giocatoreNoMazziere.add(giocatore);
        return giocatoreNoMazziere;
    }

    public Giocatore getGiocatoreCorrente() {
        return giocatore.get(index);
    }

    /**
     *
     * @param observer aggiungi osservatore selezionato
     */
    @Override
    public void addObserver(TurnObserver observer) {
        observers.add(observer);
    }

    /**
     * Notifica tutti gli osservatori che lo stato è cambiato
     */
    @Override
    public void notifyObservers() {
        Giocatore corrente = getGiocatoreCorrente();
        for (TurnObserver observer : observers) {
                observer.onTurnChanged(corrente);
        }
    }
}
