package Strategy;

import Observer.Giocatore;

import java.util.List;
import java.util.Random;

public class CPU implements ActionStrategy {

    /**
     * Sceglie il numero di gettoni che la cpu deve puntare
     * @param cpu la cpu che deve puntare
     * @param giocatori la lista di tutti i giocatori
     * @return il numero di gettoni puntato secondo un intervallo compreso tra [-20%(media),+20%(meida)] prima di cpu
     */
    @Override
    public int puntaGettoni(Giocatore cpu, List<Giocatore> giocatori) {
        int somma = 0, indexCPU = giocatori.indexOf(cpu);
        for (int i = 0; i < indexCPU; i++) {
            Giocatore g = giocatori.get(i);
            somma += g.getPuntata();
        }
        int media = somma / indexCPU;

        Random random = new Random();
        double fattore = 0.8 + (random.nextDouble() * 0.4);
        int puntata = (int) Math.round(media * fattore);

        return Math.min(Math.max(puntata, 1), cpu.getGettoni());
    }


    /**
     * Sceglie quale azione effettuare una cpu
     * @param cpu il giocatore che deve eseguire l'azione
     * @return Pesca se ha un punteggio carte inferiore a 5.5 altrimenti passa
     */
    @Override
    public Action chooseAction(Giocatore cpu) {
        return cpu.getPunteggioCarte() >= 5.5 ? Action.PASSA : Action.PESCA;
    }
}
