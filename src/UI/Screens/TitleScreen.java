package UI.Screens;

import UI.*;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class TitleScreen implements Screen {
    private final JPanel panel;

    public TitleScreen(MainFrame frame) {
        panel = new JPanel() {
            private final Image bg = new ImageIcon("out/immagini/background/sfondo.png").getImage();

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
            }
        };

        panel.setLayout(new BorderLayout());


        JLabel titolo = new JLabel(new ImageIcon("out/immagini/background/titolo2.png"));
        titolo.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(titolo, BorderLayout.NORTH);


        JPanel bottom = getPanel(frame);
        panel.add(bottom, BorderLayout.CENTER);
    }

    private JPanel getPanel(MainFrame frame) {
        JButton startButton = new JButton("NUOVA PARTITA");
        JButton loadButton = new JButton("CARICA PARTITA");
        loadButton.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
        startButton.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
        startButton.setOpaque(false);
        loadButton.setOpaque(false);

        startButton.addActionListener(_ -> {
            String nome = JOptionPane.showInputDialog("Nome Giocatore");
            if (nome == null || nome.isEmpty()) {
                nome = "🚘";
            }
            String g = "";
            while (g.isEmpty() || Integer.parseInt(g) <= 0) {
                g = JOptionPane.showInputDialog("Inserisci Gettoni Giocatore");
            }
            int gettoni = Integer.parseInt(g);
            String[] opzioni = {"Normale", "Difficile"};
            JComboBox<String> comboBox = new JComboBox<>(opzioni);
            int result = JOptionPane.showConfirmDialog(null, comboBox, "Seleziona difficoltá CPU", JOptionPane.OK_CANCEL_OPTION);
            String strategy = "";
            if (result == JOptionPane.OK_OPTION)
                strategy = (String) comboBox.getSelectedItem();
            frame.setScreen(new GameScreen(nome, gettoni, strategy));
        });

        loadButton.addActionListener(_ -> {
            JFileChooser chooser = new JFileChooser();
            chooser.setDialogTitle("Carica stato partita");
            int userSelection = chooser.showOpenDialog(null);
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File file = chooser.getSelectedFile();
                frame.setScreen(new GameScreen(file));
            }
        });


        JPanel bottom = new JPanel();
        bottom.setOpaque(false);
        bottom.setLayout(new BoxLayout(bottom, BoxLayout.Y_AXIS));


        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loadButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        bottom.add(startButton);
        bottom.add(Box.createRigidArea(new Dimension(0, 20))); // spazio verticale
        bottom.add(loadButton);

        return bottom;
    }

    @Override
    public JPanel getPanel() {
        return panel;
    }

    @Override
    public void onShow() {}
}
