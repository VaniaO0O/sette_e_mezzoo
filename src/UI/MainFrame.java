package UI;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.File;
import java.io.IOException;

public class MainFrame extends JFrame {
    private Screen currentScreen;

    public MainFrame() {
        setTitle("Sei e tre mezzi");
        setSize(1152, 768);
        try {
            setIconImage(ImageIO.read(new File("out/immagini/background/titolo.png")));
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }

    public void setScreen(Screen screen) {
        if (currentScreen != null) {
            remove(currentScreen.getPanel());
        }
        currentScreen = screen;
        add(screen.getPanel());
        revalidate();
        repaint();
        screen.onShow();
    }
}
