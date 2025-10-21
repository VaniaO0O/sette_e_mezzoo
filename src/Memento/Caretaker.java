package Memento;

import java.util.ArrayList;
import java.util.List;


public class Caretaker {
    private final List<Memento> mementos = new ArrayList<>();

    /**
     * Aggiunge un memento
     * @param m memento da aggiungere
     */
    public void addMemento(Memento m) {
        mementos.add(m);
    }
}
