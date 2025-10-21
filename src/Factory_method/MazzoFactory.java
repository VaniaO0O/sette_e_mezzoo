package Factory_method;
import Mazzo.Carta;
import java.util.List;

public interface MazzoFactory {
        List<Carta> getMazzo();
        Carta daiCarta();
        void setCarte(List<Carta> carte);
        void resettaMazzo();
        void mischiaCarte();
        List<Carta> getRemainingCardsSnapshot();
}
