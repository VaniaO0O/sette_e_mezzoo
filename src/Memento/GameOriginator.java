package Memento;

public class GameOriginator {
    private GameData gameData;

    /**
     *
     * @param data GameData da salvare
     */
    public void setState(GameData data) {
        this.gameData = data;
    }

    /**
     *
     * @return il memento da aggiungere al caretaker
     */
    public GameMemento saveStateToMemento() {
        return new GameMemento(gameData.getGiocatore(),gameData.getMazzo(), gameData.getModalita());
    }

    /**
     *
     * @param memento Il memento usato per ripristinare i dati
     */
    public void restoreFrom(Memento memento) {
        if (memento instanceof GameMemento gm)
            this.gameData = gm.restoreState();
    }

    public GameData getGameData() {
        return this.gameData;
    }
}
