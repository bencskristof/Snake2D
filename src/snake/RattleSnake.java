package snake;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;


public class RattleSnake extends Sprite{
    private int vel;
    private int dir;

    protected int length;
    protected ArrayList<SnakeBody> snakeBody;
    protected ArrayList<TurnPoint> turns;
    private final BufferedImage bodyImage=ImageIO.read(new File("data/body.png"));
    private final BufferedImage bentImage=ImageIO.read(new File("data/bent.png"));
    private final BufferedImage tailImage=ImageIO.read(new File("data/tail.png"));

    public RattleSnake(Point cords, int width, int height, BufferedImage image,int vel, int dir) throws IOException {
        super(cords, width, height, image);
        this.dir=dir;
        this.vel = vel;
        this.turns=new ArrayList<>();
        this.snakeBody= new ArrayList<>();
        SnakeBody tail = new SnakeBody(cordShift(cords,(-dir),SnakeGUI.CELLSIZE),SnakeGUI.CELLSIZE,SnakeGUI.CELLSIZE,tailImage,dir);
        snakeBody.add(tail);
        this.length=2;
    }

    protected void grow(){
        SnakeBody body = new SnakeBody(cordShift(cords,(-dir),SnakeGUI.CELLSIZE),SnakeGUI.CELLSIZE,SnakeGUI.CELLSIZE,bodyImage,dir);
        shiftBody();
        snakeBody.add(0,body);
        length++;
    }
    @Override
    public void draw(Graphics g) {
        directionalDraw(this.dir,g,this);
        for(SnakeBody snakePart : snakeBody){
            directionalDraw(snakePart.dir,g,snakePart);
        }
    }
    public void directionalDraw(int dir,Graphics g, Sprite sprite){
        switch (dir){
            case -1:g.drawImage(sprite.image, sprite.cords.x, sprite.cords.y, sprite.width, sprite.height, null) ;break;
            case 1:g.drawImage(rotateImage(sprite.image,180), sprite.cords.x, sprite.cords.y, sprite.width, sprite.height, null) ;break;
            case -2:g.drawImage(rotateImage(sprite.image,270), sprite.cords.x, sprite.cords.y, sprite.width, sprite.height, null) ;break;
            case 2: g.drawImage(rotateImage(sprite.image,90), sprite.cords.x, sprite.cords.y, sprite.width, sprite.height, null) ;break;
        }

    }

    public void move() {
        System.out.println(this);
        turn();

        if (dir == -2 && cords.x >=0) {
            cords.x -= vel;
        }
        if(dir == 2 && cords.x + SnakeGUI.CELLSIZE <= SnakeGUI.dim.width){
            cords.x += vel;
        }
        if (dir == -1 && cords.y >=0) {
            cords.y -= vel;
        }
        if(dir == 1 && cords.y + SnakeGUI.CELLSIZE <= SnakeGUI.dim.height) {
            cords.y += vel;
        }
        moveBody();
    }
    private void turn(){
        int index=0;
        boolean done=false;
        for(TurnPoint turn : turns){
            if(turn.cords.x==cords.x && cords.y == turn.cords.y){
                dir=turn.dir;
                turn.length--;
                if(turn.length==0){
                    index=turns.indexOf(turn);
                    done=true;
                }
            }
        }
        if(done){
            turns.remove(index);
        }
    }

    private void turnBody(SnakeBody body){
        int index=0;
        boolean done=false;
        for(TurnPoint turn : turns){
            if(turn.cords.x==body.cords.x && body.cords.y == turn.cords.y){
                body.dir=turn.dir;
                if(snakeBody.indexOf(body) == snakeBody.size()-1){
                    index=turns.indexOf(turn);
                    done=true;
                }
            }
        }
        if(done){
            turns.remove(index);
        }
    }
    private Point cordShift(Point p, int dir, int dist){
        Point shiftedPoint = new Point(p.x, p.y);
        switch (dir){
            case -1:
                shiftedPoint.y-=dist;break;
            case 1:
                shiftedPoint.y+=dist;break;
            case -2:
                shiftedPoint.x-=dist;break;
            case 2:
                shiftedPoint.x+=dist;break;
        }
        return shiftedPoint;
    }

    private void moveBody(){
        for(SnakeBody body : snakeBody){
            turnBody(body);
            switch (body.getDir()){
                case -2:body.cords.x-=vel;break;
                case 2:body.cords.x+=vel;break;
                case -1:body.cords.y-=vel;break;
                case 1:body.cords.y+=vel;break;
            }
        }
    }

    private void shiftBody(){
        for(SnakeBody body : snakeBody){
            turnBody(body);
            switch (-body.getDir()){
                case -2:body.cords.x-=SnakeGUI.CELLSIZE;break;
                case 2:body.cords.x+=SnakeGUI.CELLSIZE;break;
                case -1:body.cords.y-=SnakeGUI.CELLSIZE;break;
                case 1:body.cords.y+=SnakeGUI.CELLSIZE;break;
            }
        }
    }



    public void turn(int dir){

    }

    public void setDir(int dir) {
        this.dir = dir;
    }

    public int getDir() {
        return dir;
    }


    @Override
    public String toString() {
        return "RattleSnake{" +
                "vel=" + vel +
                ", dir=" + dir +
                ", snakeBody=" + snakeBody +
                ", cords=" + cords +
                '}';
    }



    public static BufferedImage rotateImage(BufferedImage src, int rotationAngle) {
        double theta = (Math.PI * 2) / 360 * rotationAngle;
        int width = src.getWidth();
        int height = src.getHeight();
        BufferedImage dest;
        if (rotationAngle == 90 || rotationAngle == 270) {
            dest = new BufferedImage(src.getHeight(), src.getWidth(), src.getType());
        } else {
            dest = new BufferedImage(src.getWidth(), src.getHeight(), src.getType());
        }

        Graphics2D graphics2D = dest.createGraphics();

        if (rotationAngle == 90) {
            graphics2D.translate((height - width) / 2, (height - width) / 2);
            graphics2D.rotate(theta, height / 2, width / 2);
        } else if (rotationAngle == 270) {
            graphics2D.translate((width - height) / 2, (width - height) / 2);
            graphics2D.rotate(theta, height / 2, width / 2);
        } else {
            graphics2D.translate(0, 0);
            graphics2D.rotate(theta, width / 2, height / 2);
        }
        graphics2D.drawRenderedImage(src, null);
        return dest;
    }
}
