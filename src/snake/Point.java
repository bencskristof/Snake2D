package snake;

import java.util.Objects;

public class Point {
    public int x;
    public int y;
    public Point(int x, int y) {
        this.x=x;
        this.y=y;
    }
    public Point(boolean random, int maxX, int maxY ){
        x = (int)Math.floor(Math.random() *maxX);
        y = (int)Math.floor(Math.random() *maxY);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Point point = (Point) o;

        if (x != point.x) return false;
        return y == point.y;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
