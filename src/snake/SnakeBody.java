package snake;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class SnakeBody extends Sprite{


    //direction the snake part is facing, -1,-2,2,1 = NORTH,WEST,EAST,SOUTH
    protected int dir;
    private boolean isBent;
    //the type of the snake part 0,1,2,3 = head, body, bent, tail


    public SnakeBody(Point cords, int width, int height, BufferedImage image, int dir) {
        super(cords, width, height, image);
        this.dir=dir;
        this.isBent=false;
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(image, cords.x, cords.y, width, height, null);
    }
    public int getDir() {
        return dir;
    }

    @Override
    public String toString() {
        return "SnakeBody{" +
                "dir=" + dir +
                ", isBent=" + isBent +
                ", cords=" + cords +
                '}';
    }
}
