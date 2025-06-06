package snake;

public class TurnPoint {
    public Point cords;
    public int dir;
    public int length;

    public TurnPoint(Point cords, int dir, int length){
        this.cords=cords;
        this.dir=dir;
        this.length=length;
    }

    @Override
    public String toString() {
        return "TurnPoint{" +
                "cords=" + cords +
                ", dir=" + dir +
                ", length=" + length +
                '}';
    }
}
