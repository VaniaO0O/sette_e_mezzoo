package Observer;

import Mazzo.Carta;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Giocatore implements TurnObserver, Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private final String nome;
    private List<Carta> mano = new ArrayList<>();
    private int gettoni, puntata = 0;
    private final boolean mazziere;
    private boolean puntato = false;

    public Giocatore(Giocatore giocatore) {
        this.nome = giocatore.nome;
        this.mano = giocatore.mano;
        this.mazziere = giocatore.mazziere;
        this.puntata = giocatore.puntata;
        this.gettoni = giocatore.gettoni;
        this.puntato = giocatore.puntato;
    }

    public boolean haPuntato(){
        return this.puntato;
    }

    public void setPuntato(boolean puntato){
        this.puntato = puntato;
    }

    public void resetPuntata(){
        this.puntata = 0;
    }

    public boolean noGettoni(){
        return this.getGettoni() <= 0;
    }

    public void svuotaMano(){
        mano = new ArrayList<>();
    }
    public void addCarta(Carta c) {
        mano.add(c);
    }

    public boolean isOut(){
        return this.getPunteggioCarte() > 7.5;
    }

    public boolean isMazziere() {
        return mazziere;
    }
    public void setGettoni(int gettoni) {
        this.gettoni += gettoni;
    }

    public int getGettoni() {
        return gettoni;
    }

    public Giocatore(String nome,int gettoni,boolean mazziere) {
        this.mazziere = mazziere;
        this.nome = nome;
        this.gettoni = gettoni;
    }

    public List<Carta> getMano() {
        return mano;
    }

    private float getPunteggioNoMatta(){
        float punteggio = 0F;
        for (Carta c : getMano()){
            if (c.isMatta())
                continue;
            punteggio += c.getRealVal();
        }
        return punteggio;
    }

    public float getPunteggioCarte(){
        float punteggio = getPunteggioNoMatta();
        boolean haMatta = false;
        for (Carta c : getMano()) {
            if (c.isMatta()) {
                haMatta = true;
                break;
            }
        }
        if (!haMatta) return punteggio;

        float migliore = punteggio + 0.5F;
        for (int i = 1; i <= 7; i++) {
            float temp = punteggio + i;
            if (temp <= 7.5F && temp > migliore) {
                migliore = temp;
            }
        }
        return migliore;
    }

    public boolean punta(int puntata){
        if (this.puntata + puntata <= this.getGettoni()) {
            this.puntata += puntata;
            this.gettoni -= puntata;
            return true;
        }
        return false;
    }

    public int getPuntata(){
        return this.puntata;
    }

    public String getNome() {
        return this.nome;
    }

    /**
     * Notifica il turno del giocatore corrente
     * @param currentPlayer il giocatore corrente
     */
    @Override
    public void onTurnChanged(Giocatore currentPlayer) {
        if (this == currentPlayer) {
            System.out.println("\n👉 È il turno di " + this.nome);
        }
    }
}
