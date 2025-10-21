package UI;

import Mazzo.Mazzo;
import Memento.*;
import Observer.*;
import Strategy.*;
import UI.Screens.*;
import Strategy.Action;

import javax.swing.*;
import java.io.File;

public class GameManager {
    private final TurnManager turnManager = new TurnManager();
    private ActionStrategy strategy;
    private final Object cpuLock = new Object();
    private final GameOriginator originator = new GameOriginator();
    private final Caretaker caretaker = new Caretaker();

    //Costruttore nuova partita
    public GameManager(String nome, int gettoni,String strategy) {
        turnManager.aggiungiGiocatore(new Giocatore(nome, gettoni, true));
        turnManager.aggiungiGiocatore(new Giocatore("CPU1", gettoni, false));
        turnManager.aggiungiGiocatore(new Giocatore("CPU2", gettoni, false));
        turnManager.aggiungiGiocatore(new Giocatore("CPU3", gettoni, false));
        Mazzo.getInstance().mischiaCarte();
        setStrategy(strategy);
        inizializzaMano();
    }

    //Costruttore carica partita
    public GameManager(File file) {
        caricaDaFile(file);
    }

    private void inizializzaMano(){
        for (Giocatore g : getTurnManager().getGiocatori()){
            g.addCarta(Mazzo.daiCarta());
        }
    }

    public void calcoloVincitore() {
        JOptionPane.showMessageDialog(null, "Calcolo vincitori");
        StringBuilder text = new StringBuilder();
        Giocatore mazziere = getMazziere();

        for (Giocatore giocatore : getTurnManager().getGiocatoriNoMazziere()) {
            float punteggioMazziere = mazziere.getPunteggioCarte();
            float punteggioAltro = giocatore.getPunteggioCarte();
            boolean mazziereOut = mazziere.isOut();
            boolean altroOut = giocatore.isOut();

            if (mazziereOut && altroOut) {
                text.append(mazziere.getNome())
                        .append(" vince contro ")
                        .append(giocatore.getNome())
                        .append(" (entrambi sballano)\n");
                mazziere.setGettoni(giocatore.getPuntata());
            } else if (altroOut) {
                text.append(mazziere.getNome())
                        .append(" vince contro ")
                        .append(giocatore.getNome())
                        .append(" (")
                        .append(giocatore.getNome())
                        .append(" ha sballato)\n");
                mazziere.setGettoni(giocatore.getPuntata());
            } else if (mazziereOut) {
                text.append(mazziere.getNome())
                        .append(" perde contro ")
                        .append(giocatore.getNome())
                        .append(" (mazziere ha sballato)\n");
                giocatore.setGettoni(mazziere.getPuntata());
            } else if (punteggioMazziere >= punteggioAltro) {
                text.append(mazziere.getNome())
                        .append(" vince contro ")
                        .append(giocatore.getNome())
                        .append(" (")
                        .append(punteggioMazziere)
                        .append(" vs ")
                        .append(punteggioAltro)
                        .append(")\n");
                mazziere.setGettoni(giocatore.getPuntata());
            } else {
                text.append(mazziere.getNome())
                        .append(" perde contro ")
                        .append(giocatore.getNome())
                        .append(" (")
                        .append(punteggioMazziere)
                        .append(" vs ")
                        .append(punteggioAltro)
                        .append(")\n");
                giocatore.setGettoni(mazziere.getPuntata());
            }
        }

        JOptionPane.showMessageDialog(null, text.toString());
        checkExit(0);
    }

    public void checkforObserversInGame(){
        for (Giocatore g : getTurnManager().getGiocatoriNoMazziere()) {
            if(g.noGettoni())
                getTurnManager().removeObserver(g);
        }
    }

    private boolean isOneCPUOut(){
        for (Giocatore g : getTurnManager().getGiocatoriNoMazziere()) {
            if(g.noGettoni())
                return true;
        }
        return false;
    }

    public void checkExit(int code){
        if (isOneCPUOut()){
            JOptionPane.showMessageDialog(null, "Game Over");
            JOptionPane.showMessageDialog(null, "Hai vinto, una CPU non puó continuare a giocare");
            System.exit(code);
        }
        else if (getMazziere().noGettoni()){
            JOptionPane.showMessageDialog(null, "Game Over");
            JOptionPane.showMessageDialog(null,"Hai perso, non hai piu gettoni");
            System.exit(code);
        }
        salvaSuFile();

    }

    public void checkExit(){
        if (gameOver()) {
            JOptionPane.showMessageDialog(null, "Game Over");

            boolean isMazziere = getMazziere().noGettoni();
            if (isMazziere)
                JOptionPane.showMessageDialog(null, getMazziere().getNome() + " ha perso tutti i gettoni aahhahahahah sfigato");
            else
                JOptionPane.showMessageDialog(null, "Tutti gli altri giocatori hanno finito i gettoni. Hai vinto");
            System.exit(0);
        }
        else
            salvaSuFile();
    }

    public boolean gameOver(){
        if (getMazziere().noGettoni())
            return true;
        return getTurnManager().getGiocatori().size() == 1;
    }
    

    public Giocatore getMazziere() {
        for (Giocatore g : turnManager.getGiocatori()) {
            if (g.isMazziere())
                return g;
        }
        return null;
    }

    public void resettaMano() {
        for (Giocatore g : turnManager.getGiocatori()) {
            g.svuotaMano();
            g.resetPuntata();
        }
        inizializzaMano();
    }

    public TurnManager getTurnManager() {
        return this.turnManager;
    }

    /**
     * Azione Pesca
     */
    public void onPesca() {
        Giocatore giocatore = getTurnManager().getGiocatoreCorrente();
        if (!(giocatore.isMazziere()) || giocatore.getPuntata() != 0) {
            if (!giocatore.isOut())
                giocatore.addCarta(Mazzo.daiCarta());
            else
                JOptionPane.showMessageDialog(null, giocatore.getNome() + " ha sballato");
            this.getTurnManager().notifyObservers();
            System.out.println(giocatore.getMano());
        }
        else
            JOptionPane.showMessageDialog(null, "Punta prima di pescare");
    }

    /**
     * Azione Passa
     */
    public void onPassa() {
        try {
            Giocatore giocatore = getTurnManager().getGiocatoreCorrente();
            if (!(giocatore.isMazziere()) || giocatore.getPuntata() != 0) {
                giocatore.setPuntato(false);
                getTurnManager().nextTurn();
                eseguiTurnoCPU();
            }
            else {
                JOptionPane.showMessageDialog(null, "Punta prima di passare");
            }
        } catch (IndexOutOfBoundsException e) {
            getTurnManager().resetTurni();
            calcoloVincitore();
            resettaMano();
            salvaStato();
            System.out.println("Stato salvato");
            getTurnManager().notifyObservers();
        }
    }

    /**
     * Azione Punta
     */
    public void onPunta() {
        Giocatore giocatore = getTurnManager().getGiocatoreCorrente();
        SpinnerNumberModel mod = new SpinnerNumberModel(1, 1, giocatore.getGettoni(), 1);
        JSpinner spinner = new JSpinner(mod);
        int result = JOptionPane.showConfirmDialog(
                null,
                spinner,
                giocatore.getNome() + ": Inserisci numero di gettoni da puntare",
                JOptionPane.OK_CANCEL_OPTION
        );
        if (result == JOptionPane.OK_OPTION) {
            int puntata = (int) spinner.getValue();
            if (!giocatore.punta(puntata))
                JOptionPane.showMessageDialog(null, "Non hai abbastanza gettoni");
        }
        this.getTurnManager().notifyObservers();

    }

    private String getStringStrategy(){
        return this.strategy instanceof CPUDifficile ? "Difficile" : "Normale";
    }


    /**
     * @param mode stabilisce la modalità
     */
    public void setStrategy(String mode) {
        this.strategy = mode.equals("Difficile") ? new CPUDifficile() : new CPU();
    }

    /**
     * Fa eseguire automaticamente il turno alla CPU.
     */
    public void eseguiTurnoCPU() {
        Giocatore corrente = getTurnManager().getGiocatoreCorrente();
        if (!corrente.isMazziere()) {
            new Thread(() -> {
                synchronized (cpuLock) {
                    try {
                        System.out.println(corrente.getNome() + " sta pensando...");
                        Thread.sleep(1500);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        return;
                    }

                    System.out.println(corrente.getNome() + " ha puntato " +  corrente.haPuntato());
                    if (!corrente.haPuntato()) {
                        System.out.println(corrente.getNome() + "Sto puntando...");
                        corrente.punta(strategy.puntaGettoni(corrente, getTurnManager().getGiocatori()));
                        corrente.setPuntato(true);
                    }
                Action action = strategy.chooseAction(corrente);
                System.out.println(corrente.getNome() + " sceglie " + action);

                switch (action) {
                    case PESCA -> {
                        onPesca();
                        if (!corrente.isOut()) {
                            eseguiTurnoCPU();
                        } else {
                            onPassa();
                        }
                    }
                    case PASSA -> {
                        corrente.setPuntato(false);
                        System.out.println(corrente.getNome() + "Ho finito il turno ho puntato = " +  corrente.haPuntato());
                        onPassa();
                    }
                }
              }
            }).start();
        }
    }

    /**
     * Salva un GameData e lo aggiunge ad un originator
     */
    public void salvaStato() {
        GameData data = new GameData(turnManager.getGiocatori(), Mazzo.getInstance().getRemainingCardsSnapshot(),getStringStrategy());
        System.out.println(data);
        originator.setState(data);
        caretaker.addMemento(originator.saveStateToMemento());
    }

    /**
     * Prompt grafico per salvare su file il memento preso da salvaStato()
     */
    public void salvaSuFile() {
        try {
            GameData data = new GameData(turnManager.getGiocatori(), Mazzo.getInstance().getRemainingCardsSnapshot(), getStringStrategy());
            originator.setState(data);
            GameMemento memento = originator.saveStateToMemento();

            JFileChooser chooser = new JFileChooser();
            chooser.setDialogTitle("Salva stato partita");
            int userSelection = chooser.showSaveDialog(null);

            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File file = chooser.getSelectedFile();
                FileManager.salvaSuFile(memento, file);
                JOptionPane.showMessageDialog(null, "Partita salvata in:\n" + file.getAbsolutePath());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Errore durante il salvataggio: " + e.getMessage());
        }
    }

    /**
     * Carica da file un memento salvato in precedenza
     * @param file Il file selezionato
     */
    private void caricaDaFile(File file) {
        try {
                GameMemento loaded = FileManager.caricaDaFile(file);
                originator.restoreFrom(loaded);

                GameData restored = originator.getGameData();
                turnManager.setGiocatori(restored.getGiocatore());
                Mazzo.getInstance().setCarte(restored.getMazzo());
                setStrategy(restored.getModalita());
                JOptionPane.showMessageDialog(null, "Partita caricata da:\n" + file.getAbsolutePath());

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Errore durante il caricamento: " + e.getMessage());
        }
    }
}
