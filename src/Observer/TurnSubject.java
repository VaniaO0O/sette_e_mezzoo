package Observer;

public interface TurnSubject {
    void addObserver(TurnObserver observer);
    void notifyObservers();
}
