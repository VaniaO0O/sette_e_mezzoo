package UI.Screens;

import javax.swing.*;

import java.awt.*;
import java.io.File;
import UI.*;
import UI.Panels.PlayerControlsPanel;
import UI.Panels.TablePanel;
public class GameScreen implements Screen {
    private final JPanel panel;

    public GameScreen(String nome,int gettoni,String strategy) {
        panel = new JPanel(new BorderLayout());
        GameManager controller = new GameManager(nome, gettoni, strategy);
        TablePanel table = new TablePanel(controller);

        PlayerControlsPanel controls = new PlayerControlsPanel(controller);

        panel.add(table, BorderLayout.CENTER);
        panel.add(controls, BorderLayout.SOUTH);
    }

    public GameScreen(File file) {
        panel = new JPanel(new BorderLayout());
        GameManager controller = new GameManager(file);
        TablePanel table = new TablePanel(controller);

        PlayerControlsPanel controls = new PlayerControlsPanel(controller);

        panel.add(table, BorderLayout.CENTER);
        panel.add(controls, BorderLayout.SOUTH);
    }

    @Override
    public JPanel getPanel() {
        return panel;
    }

    @Override
    public void onShow() {

    }

}
