package Strategy;

import Observer.Giocatore;
import java.util.List;

public interface ActionStrategy {
    int puntaGettoni(Giocatore giocatore,List<Giocatore> giocatori);
    Action chooseAction(Giocatore giocatore);
}