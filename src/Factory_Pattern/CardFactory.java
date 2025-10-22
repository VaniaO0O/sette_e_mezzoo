package Factory_Pattern;
import Mazzo.Carta;
import Mazzo.Seme;
import Mazzo.Valore;

import java.util.ArrayList;
import java.util.List;
public class CardFactory {
    /**
     * @return la lista delle singole carte che compongono un mazzo
     */
    public static List<Carta> createNapoletaneDeck() {
        List<Carta> deck = new ArrayList<>(40);
        for (Seme s : Seme.values()) {
            for (Valore v : Valore.values()) {
                deck.add(new Carta(s, v));
            }
        }
        return deck;
    }
}