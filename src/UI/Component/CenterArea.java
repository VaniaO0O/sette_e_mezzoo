package UI.Component;

import UI.Panels.ImageObject;

import javax.swing.*;
import java.awt.*;

public class CenterArea extends JPanel {
    public CenterArea() {
        setLayout(null);
        setOpaque(false);
        setPreferredSize(new Dimension(400, 250));


        ImageObject mazzo = new ImageObject("out/immagini/png_carte/retro.png", 70, 20);
        mazzo.setScale(0.42);

        add(mazzo);

    }
}