package Mazzo;
import Factory_method.CardFactory;
import Factory_method.MazzoFactory;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MazzoNapoletano implements MazzoFactory {
    private static MazzoNapoletano instance;
    private static final List<Carta> original = CardFactory.createNapoletaneDeck();
    private static final List<Carta> mazzo = CardFactory.createNapoletaneDeck();

    /**
     * @return il mazzo corrente
     */
    @Override
    public List<Carta> getMazzo() {
        return mazzo;
    }
    /**
     * Istanza singleton
     * @return istanza
     */
    public static MazzoNapoletano getInstance() {
        if (instance == null)
            instance = new MazzoNapoletano();

        return instance;
    }

    /**
     * Uso singleton per dare carta
     * @return la carta sopra al mazzo
     * @throws MazzoFinito se il mazzo è vuoto e lo resetta
     */
    @Override
    public Carta daiCarta(){
        try {
            if (mazzo.isEmpty())
                throw new MazzoFinito("Mazzo Finito");
            Carta carta = mazzo.getFirst();
            mazzo.remove(carta);
            return carta;
        }
        catch (MazzoFinito e){
            JOptionPane.showMessageDialog(null,"Mazzo finito. Rimescolamento in corso");
            getInstance().resettaMazzo();
            System.out.println(mazzo);
            getInstance().mischiaCarte();
            return daiCarta();
        }
    }

    @Override
    public void setCarte(List <Carta> carte){
        mazzo.clear();
        mazzo.addAll(carte);
    }

    /**
     * Ripristina mazzo originale
     */
    @Override
    public void resettaMazzo(){
        mazzo.addAll(original);
    }

    /**
     * Mischia il mazzo corrente
     */
    @Override
    public void mischiaCarte() {
        Collections.shuffle(mazzo);
    }


    /**
     *
     * @return lista di carte che rimangono nel mazzo
     */
    @Override
    public List<Carta> getRemainingCards() {
        return new ArrayList<>(mazzo);
    }

    private MazzoNapoletano() {}
}
