package snake;

import java.awt.Dimension;
import java.io.IOException;
import java.sql.SQLException;
import javax.swing.*;

public class SnakeGUI {

    public static final Dimension dim = new Dimension(800,600);
    public static final int CELLSIZE= dim.height/30;
    private JFrame frame;
    private GameEngine gameArea;
    private HighScorePanel highScorePanel;

    public SnakeGUI() throws IOException, SQLException {
        frame = new JFrame("Snake");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel temp=new JPanel();

        temp.setPreferredSize(dim);
        frame.add(temp);
        frame.pack();

        gameArea = new GameEngine();
        frame.getContentPane().add(gameArea);
        highScorePanel=new HighScorePanel(gameArea);

        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);
        JMenuItem gameMenuItem = new JMenuItem("Game");
        menuBar.add(gameMenuItem);
        gameMenuItem.addActionListener(ae -> {
            frame.getContentPane().remove(highScorePanel);
            temp.setPreferredSize(dim);
            frame.add(temp);
            frame.pack();
            try {
                gameArea.setPaused(true);
                gameArea.newFrameTimer.stop();
                gameArea = new GameEngine();
            } catch (IOException | SQLException e) {
                throw new RuntimeException(e);
            }
            frame.getContentPane().add(gameArea);

        });

        JMenuItem scoreMenuItem = new JMenuItem("Score");
        menuBar.add(scoreMenuItem);
        scoreMenuItem.addActionListener(ae -> {
            gameArea.setPaused(true);
            gameArea.newFrameTimer.stop();
            frame.getContentPane().remove(gameArea);
            try {
                highScorePanel=new HighScorePanel(gameArea);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            frame.getContentPane().add(highScorePanel);
            frame.pack();
        });





        frame.setResizable(false);
        frame.setVisible(true);
    }
}
