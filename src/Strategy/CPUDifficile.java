package Strategy;

import Observer.Giocatore;
import java.util.List;

public class CPUDifficile implements ActionStrategy {

    /**
     * Sceglie il numero di gettoni che la cpu deve puntare
     * @param cpu la cpu che deve puntare
     * @param giocatori la lista di tutti i giocatori
     * @return il numero di gettoni puntati (pari al doppio del punteggio carte, ma non inferiore a 3 e non superiore ai gettoni posseduti)
     */
    @Override
    public int puntaGettoni(Giocatore cpu, List<Giocatore> giocatori) {
        int base = (int) (cpu.getPunteggioCarte() * 2);
        return Math.min(cpu.getGettoni(), Math.max(3, base));
    }

    /**
     * Sceglie quale azione effettuare una cpu
     * @param cpu il giocatore che deve eseguire l'azione
     * @return pesca o passa a seconda di quanto è distante a 7.5 ;
     */
    @Override
    public Action chooseAction(Giocatore cpu) {
        float punteggio = cpu.getPunteggioCarte();
        float distanza = 7.5F - punteggio;

        // se la distanza è > 2 → rischio basso:  pesca
        if (distanza > 2.0F) return Action.PESCA;

        // se la distanza è tra 1 e 2: pesca con probabilità 50%
        if (distanza > 1.0F && Math.random() < 0.5) return Action.PESCA;

        // altrimenti passa
        return Action.PASSA;
    }
}
