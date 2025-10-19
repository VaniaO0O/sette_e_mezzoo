package UI.Component;

import Observer.Giocatore;
import Observer.TurnObserver;
import UI.GameManager;
import UI.Panels.ImageObject;

import javax.swing.*;
import java.awt.*;

public class PlayerArea extends JPanel implements TurnObserver {
    private final Giocatore giocatore;
    private final JLabel punteggioMano,gettoni,puntata;
    private final JPanel centro = new JPanel(new BorderLayout());
    private JLabel cartaLabel;
    private final ImageObject immagineCarta = getPlaceholder();
    //private GameManager gm;

    public PlayerArea(Giocatore giocatore) {
        this.giocatore = giocatore;
        //this.gm = gm;

        setLayout(new BorderLayout());
        setOpaque(true);
        setBackground(new Color(0, 60, 0)); // verde da tavolo
        setBorder(BorderFactory.createLineBorder(Color.black, 3));

        // Nome del giocatore
        JLabel nomeGiocatore = new JLabel(giocatore.getNome(), SwingConstants.CENTER);
        nomeGiocatore.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
        nomeGiocatore.setForeground(Color.WHITE);
        add(nomeGiocatore, BorderLayout.NORTH);

        // --- centro: carta + punteggi
        centro.setOpaque(false);

        cartaLabel = new JLabel();
        cartaLabel.setPreferredSize(new Dimension(80, 110));
        centro.add(cartaLabel, BorderLayout.WEST);

        JPanel punteggiPanel = new JPanel();
        punteggiPanel.setOpaque(false);
        punteggiPanel.setLayout(new BoxLayout(punteggiPanel, BoxLayout.Y_AXIS));

        immagineCarta.setPreferredSize(new Dimension(80, 110));
        centro.add(immagineCarta, BorderLayout.WEST);
        immagineCarta.setScale(0.45);


        punteggioMano = new JLabel("Punteggio carte: " + giocatore.getPunteggioCarte());
        punteggioMano.setForeground(Color.ORANGE);
        puntata = new JLabel("Puntata corrente: " + giocatore.getPuntata());
        puntata.setForeground(Color.ORANGE);


        punteggiPanel.add(Box.createVerticalStrut(5));
        punteggiPanel.add(punteggioMano);
        punteggiPanel.add(Box.createVerticalStrut(5));
        punteggiPanel.add(puntata);
        centro.add(punteggiPanel, BorderLayout.CENTER);

        add(centro, BorderLayout.CENTER);

        // Info in basso
        JPanel info = new JPanel(new GridLayout(1, 2));
        gettoni = new JLabel("Gettoni: " + giocatore.getGettoni(), SwingConstants.CENTER);
        info.setOpaque(false);
        gettoni.setForeground(Color.ORANGE);
        info.add(gettoni);
        JLabel mazziere = new JLabel("Mazziere: " + (giocatore.isMazziere() ? "Sì" : "No"), SwingConstants.CENTER);
        mazziere.setForeground(Color.ORANGE);
        info.add(mazziere);
        add(info, BorderLayout.SOUTH);
    }

    @Override
    public void onTurnChanged(Giocatore corrente) {
        boolean mioTurno = (corrente == giocatore);

        // Evidenzia il giocatore corrente
        if (mioTurno) {
            setBackground(new Color(0, 120, 0)); // più chiaro
            setBorder(BorderFactory.createLineBorder(Color.YELLOW, 4));
        } else {
            setBackground(new Color(0, 60, 0));
            setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        }

        aggiornaDati();
        repaint();
    }

    public void aggiornaDati() {
        puntata.setText("Puntata: " + giocatore.getPuntata());
        gettoni.setText("Gettoni: " + giocatore.getGettoni());
        punteggioMano.setText("Punteggio carte: " + giocatore.getPunteggioCarte());
        aggiungiCarta(giocatore);
    }

    public ImageObject getPlaceholder() {
        return new ImageObject("out/immagini/png_carte/placeholder.png",80,110);
    }

    public void aggiungiCarta(Giocatore giocatore) {
        try {
            var mano = giocatore.getMano();
            if (mano == null || mano.isEmpty()) {
                return;
            }
            centro.remove(cartaLabel);
            ImageObject immagineCarta = new ImageObject("out/immagini/png_carte/" + giocatore.getMano().getLast().toString() + ".png", 80, 110);
            cartaLabel = immagineCarta;
            centro.add(immagineCarta, BorderLayout.WEST);
            immagineCarta.setScale(0.45);
            centro.revalidate();
            centro.repaint();
        }
        catch (Exception e) {
            System.out.println("Errore nel caricamento immagine: " + e.getClass().getName() + " - " + e.getMessage());
        }
    }

}
