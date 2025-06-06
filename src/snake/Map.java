package snake;


import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
public class Map {

    private final int ROCK_COUNT = 10;
    protected ArrayList<Rock> rocks;

    private ArrayList<Point> rockCords;

    protected Apple apple;

    public Map() throws IOException {
        ArrayList<Point> rockCords =new ArrayList<>();
        int rockCordCount=0;
        while(rockCordCount!=ROCK_COUNT){
            Point randomPoint = new Point(true, SnakeGUI.dim.width/SnakeGUI.CELLSIZE, SnakeGUI.dim.height/SnakeGUI.CELLSIZE);
            if((randomPoint.x<((SnakeGUI.dim.width/SnakeGUI.CELLSIZE)/2)-3 || randomPoint.x>((SnakeGUI.dim.width/SnakeGUI.CELLSIZE)/2)+3 && (randomPoint.y< ((SnakeGUI.dim.height/SnakeGUI.CELLSIZE)/2)-3 || randomPoint.y> ((SnakeGUI.dim.height/SnakeGUI.CELLSIZE)/2)+3) && (!(rockCords.contains(randomPoint))))){
                rockCords.add(randomPoint);
                rockCordCount++;
            }
            this.rockCords=rockCords;
        }
        while (apple==null){
            Point randomPoint=new Point(true, SnakeGUI.dim.width/SnakeGUI.CELLSIZE, SnakeGUI.dim.height/SnakeGUI.CELLSIZE);
            if(!(rockCords.contains(randomPoint))){
                BufferedImage image = ImageIO.read(new File("data/apple.png"));
                apple=new Apple(new Point (randomPoint.x * SnakeGUI.CELLSIZE, randomPoint.y * SnakeGUI.CELLSIZE), SnakeGUI.CELLSIZE, SnakeGUI.CELLSIZE, image);
            }
        }

        rocks=new ArrayList<Rock>();
        for (Point p : rockCords) {
            System.out.println(p.x +";"+p.y);
            BufferedImage image = ImageIO.read(new File("data/rock.png"));
            rocks.add(new Rock(new Point(p.x * SnakeGUI.CELLSIZE, p.y * SnakeGUI.CELLSIZE), SnakeGUI.CELLSIZE, SnakeGUI.CELLSIZE, image));
        }


    }

    protected void spawnApple(RattleSnake snake) throws IOException {
        Point randomPoint=new Point(true, SnakeGUI.dim.width/SnakeGUI.CELLSIZE, SnakeGUI.dim.height/SnakeGUI.CELLSIZE);
        Apple testApple=new Apple(new Point (randomPoint.x * SnakeGUI.CELLSIZE, randomPoint.y * SnakeGUI.CELLSIZE), SnakeGUI.CELLSIZE, SnakeGUI.CELLSIZE, null);
        boolean notSafe=true;
        if (!testApple.collides(snake) && !rockCords.contains(randomPoint) && notSafe) {
            do {
                System.out.println("loopin");
                int count = snake.length-1;
                for (SnakeBody body : snake.snakeBody) {
                    if (body.cords != randomPoint) {
                        count--;
                    }
                }
                if (count == 0) {
                    notSafe = false;
                }
            } while (!testApple.collides(snake) && !rockCords.contains(randomPoint) && notSafe);
        }
        BufferedImage image = ImageIO.read(new File("data/apple.png"));
        apple=new Apple(new Point (randomPoint.x * SnakeGUI.CELLSIZE, randomPoint.y * SnakeGUI.CELLSIZE), SnakeGUI.CELLSIZE, SnakeGUI.CELLSIZE, image);
    }

    public void draw(Graphics g) {
        for (Rock rock : rocks) {
            rock.draw(g);
        }
        apple.draw(g);
    }
}
