package snake;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;

public class HighScorePanel extends JPanel {

    public HighScorePanel(GameEngine gameEngine) throws SQLException {
        setLayout(new GridLayout(10, 1, 10, 10));

        ArrayList<HighScore> scores=gameEngine.highScores.getHighScores();

        for (HighScore hs : scores) {
            add(createScorePanel(hs.getName(), hs.getScore()));
        }
    }

    private JPanel createScorePanel(String name, int score) {
        JPanel scorePanel = new JPanel();
        scorePanel.setLayout(new BorderLayout(10, 0));
        scorePanel.add(new JLabel(name), BorderLayout.WEST);
        scorePanel.add(new JLabel(String.valueOf(score)), BorderLayout.EAST);
        return scorePanel;
    }
}
