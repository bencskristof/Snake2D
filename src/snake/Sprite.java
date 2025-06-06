package snake;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

/**
 *
 * @author bli
 */
public class Sprite {
    /**
     * The coordinates of the top left corner of the sprite
     */
    protected Point cords;
    protected int width;
    protected int height;
    protected BufferedImage image;

    public Sprite(Point cords, int width, int height, BufferedImage image) {
        this.cords=cords;
        this.width = width;
        this.height = height;
        this.image = image;
    }

    public void draw(Graphics g) {
        g.drawImage(image, cords.x, cords.y, width, height, null);
    }

    /**
     * Returns true if this sprite collides with the other sprite
     * @param other
     * @return
     */
    public boolean collides(Sprite other) {
        Rectangle rect = new Rectangle(cords.x+SnakeGUI.CELLSIZE/2, cords.y+SnakeGUI.CELLSIZE/2, SnakeGUI.CELLSIZE/2, SnakeGUI.CELLSIZE/2);
        Rectangle otherRect = new Rectangle(other.cords.x+SnakeGUI.CELLSIZE/2, other.cords.y+SnakeGUI.CELLSIZE/2, SnakeGUI.CELLSIZE/2, SnakeGUI.CELLSIZE/2);
        return rect.intersects(otherRect);
    }


    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Point getCords() {
        return cords;
    }

    public void setCords(Point cords) {
        this.cords = cords;
    }
}
