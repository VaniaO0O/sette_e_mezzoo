package UI.Panels;

import UI.Component.CenterArea;
import UI.Component.PlayerArea;
import UI.GameManager;

import javax.swing.*;
import java.awt.*;

public class TablePanel extends JPanel {
    private final Image bg = new ImageIcon("out/immagini/background/sfondo.png").getImage();

    public TablePanel(GameManager gm) {
        setLayout(null);
        setPreferredSize(new Dimension(1152, 768));
        

        PlayerArea playerArea = new PlayerArea(gm.getTurnManager().getGiocatori().get(0));
        PlayerArea cpu1Area = new PlayerArea(gm.getTurnManager().getGiocatori().get(1));
        PlayerArea cpu2Area = new PlayerArea(gm.getTurnManager().getGiocatori().get(2));
        PlayerArea cpu3Area = new PlayerArea(gm.getTurnManager().getGiocatori().get(3));

        gm.getTurnManager().addObserver(playerArea);
        gm.getTurnManager().addObserver(cpu1Area);
        gm.getTurnManager().addObserver(cpu2Area);
        gm.getTurnManager().addObserver(cpu3Area);

        gm.getTurnManager().notifyObservers();


        CenterArea centerArea = new CenterArea();


        addComponent(playerArea, 480, 500);
        addComponent(cpu1Area, 480, 20);
        addComponent(cpu2Area, 940, 340);
        addComponent(cpu3Area, 40, 340);
        addComponent(centerArea, 376, 250);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
    }

    private void addComponent(JComponent comp, int x, int y) {
        comp.setBounds(x, y, comp.getPreferredSize().width, comp.getPreferredSize().height);
        add(comp);
    }

}
