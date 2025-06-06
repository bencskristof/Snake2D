package snake;


import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.*;

public class GameEngine extends JPanel {

    private final int FPS = 500;
    private boolean paused = false;
    private final Image background= new ImageIcon("data/background.png").getImage();
    private final Image gameoverImage= new ImageIcon("data/gameover.png").getImage();
    protected Timer newFrameTimer;
    protected HighScores highScores;
    private Map map;
    private final int SNAKE_MOVEMENT = 2;

    private RattleSnake rattleSnake;

    public GameEngine() throws IOException, SQLException {
        super();
        this.highScores = new HighScores(10);
        this.getInputMap().put(KeyStroke.getKeyStroke("A"), "pressed left");
        this.getActionMap().put("pressed left", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if((rattleSnake.getDir()==-1)){
                    rattleSnake.turns.add(new TurnPoint(new Point(rattleSnake.cords.x,rattleSnake.cords.y  - (rattleSnake.cords.y % SnakeGUI.CELLSIZE)),-2,rattleSnake.length ));
                }
                if(rattleSnake.getDir()==1){
                    rattleSnake.turns.add(new TurnPoint(new Point(rattleSnake.cords.x,(rattleSnake.cords.y + SnakeGUI.CELLSIZE) - (rattleSnake.cords.y % SnakeGUI.CELLSIZE)),-2,rattleSnake.length ) );
                }
            }
        });
        this.getInputMap().put(KeyStroke.getKeyStroke("D"), "pressed right");
        this.getActionMap().put("pressed right", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if((rattleSnake.getDir()==-1)){
                    rattleSnake.turns.add(new TurnPoint(new Point(rattleSnake.cords.x,rattleSnake.cords.y  - (rattleSnake.cords.y % SnakeGUI.CELLSIZE)),2,rattleSnake.length ));
                }
                if(rattleSnake.getDir()==1){
                    rattleSnake.turns.add(new TurnPoint(new Point(rattleSnake.cords.x,(rattleSnake.cords.y + SnakeGUI.CELLSIZE) - (rattleSnake.cords.y % SnakeGUI.CELLSIZE)),2,rattleSnake.length ) );
                }
            }
        });
        this.getInputMap().put(KeyStroke.getKeyStroke("S"), "pressed down");
        this.getActionMap().put("pressed down", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if((rattleSnake.getDir()==-2)){
                    rattleSnake.turns.add(new TurnPoint(new Point(rattleSnake.cords.x  - (rattleSnake.cords.x % SnakeGUI.CELLSIZE),rattleSnake.cords.y),1,rattleSnake.length ));
                }
                if(rattleSnake.getDir()==2){
                    rattleSnake.turns.add(new TurnPoint(new Point((rattleSnake.cords.x + SnakeGUI.CELLSIZE) - (rattleSnake.cords.x % SnakeGUI.CELLSIZE),rattleSnake.cords.y),1,rattleSnake.length ) );
                }
            }
        });
        this.getInputMap().put(KeyStroke.getKeyStroke("W"), "pressed up");
        this.getActionMap().put("pressed up", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if((rattleSnake.getDir()==-2)){
                    rattleSnake.turns.add(new TurnPoint(new Point(rattleSnake.cords.x  - (rattleSnake.cords.x % SnakeGUI.CELLSIZE),rattleSnake.cords.y),-1,rattleSnake.length ));
                }
                if(rattleSnake.getDir()==2){
                    //Point turn =new Point((rattleSnake.cords.x + SnakeGUI.CELLSIZE) - (rattleSnake.cords.x % SnakeGUI.CELLSIZE),rattleSnake.cords.y);
                    rattleSnake.turns.add(new TurnPoint(new Point((rattleSnake.cords.x + SnakeGUI.CELLSIZE) - (rattleSnake.cords.x % SnakeGUI.CELLSIZE),rattleSnake.cords.y),-1,rattleSnake.length ) );
                }
            }
        });
        this.getInputMap().put(KeyStroke.getKeyStroke("ESCAPE"), "escape");
        this.getActionMap().put("escape", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                paused = !paused;
            }
        });
        this.getInputMap().put(KeyStroke.getKeyStroke("SPACE"), "space");
        this.getActionMap().put("space", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    restart();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        restart();
        newFrameTimer = new Timer(1, new NewFrameListener());
        newFrameTimer.start();
    }
    public void restart() throws IOException{
        paused=false;
        map = new Map();
        Random rand = new Random();
        int dir = rand.nextInt(1,3) * (rand.nextBoolean() ? -1 : 1);
        rattleSnake = new RattleSnake(new Point((SnakeGUI.dim.width/SnakeGUI.CELLSIZE)/2*SnakeGUI.CELLSIZE,(SnakeGUI.dim.height/SnakeGUI.CELLSIZE)/2*SnakeGUI.CELLSIZE),SnakeGUI.CELLSIZE,SnakeGUI.CELLSIZE, ImageIO.read(new File("data/head.png")),SNAKE_MOVEMENT,dir);
    }

    @Override
    protected void paintComponent(Graphics grphcs) {
        super.paintComponent(grphcs);
        grphcs.drawImage(background, 0, 0, SnakeGUI.dim.width, SnakeGUI.dim.height, null);
        map.draw(grphcs);
        rattleSnake.draw(grphcs);
        grphcs.drawString("Score: "+(rattleSnake.length-2),0,20);
        try {
            if(highScores.getHighScores().size()!=0){
                grphcs.drawString("HighScore: " + highScores.getHighScores().getFirst().getScore(),0,40);
            }else {
                grphcs.drawString("HighScore: 0",0,40);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if(isOver()){
            grphcs.drawImage(gameoverImage,0,0,SnakeGUI.dim.width,SnakeGUI.dim.height,null);
        }

    }
    public void checkScore() throws SQLException {
        if(!highScores.getHighScores().isEmpty()){
            if((rattleSnake.length-2) < highScores.getHighScores().getLast().getScore() && highScores.getHighScores().size()>highScores.maxScores) {
                return;
            }
        }
        String name = JOptionPane.showInputDialog("Your score is in the best 10. Input your name:",JOptionPane.QUESTION_MESSAGE);
        if(name!=null){
            while(!(name.length()>2)) {
                name = JOptionPane.showInputDialog("Your score is in the best 10. Input your name:\n(name must be at lest 3 characters)",JOptionPane.QUESTION_MESSAGE);
            }
        }
        if(name!=null){
            highScores.putHighScore(new HighScore(name,(rattleSnake.length-2),new Timestamp(System.currentTimeMillis())));
        }

    }

    public boolean isOver(){
        for (SnakeBody body : rattleSnake.snakeBody){
            if(body.collides(rattleSnake)){
                return true;
            }
        }
        for(Rock rock : map.rocks){
            if(rock.collides(rattleSnake)){
                return true;
            }
        }
        if(rattleSnake.cords.x<=0 || rattleSnake.cords.x+SnakeGUI.CELLSIZE > SnakeGUI.dim.width){
            return true;
        }
        if(rattleSnake.cords.y<=0 || rattleSnake.cords.y+SnakeGUI.CELLSIZE > SnakeGUI.dim.height){
            return true;
        }
        return false;
    }

    class NewFrameListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            if (!paused) {
                rattleSnake.move();
                if(rattleSnake.collides(map.apple)){
                    rattleSnake.grow();
                    map.apple=null;
                    try {
                        map.spawnApple(rattleSnake);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                if(isOver()){
                    paused=!paused;
                    try {
                        checkScore();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }

            }
            repaint();
        }

    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }
}
