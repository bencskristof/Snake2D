package snake;

import java.sql.Timestamp;

public class HighScore {

    private final String name;
    private final int score;
    private final Timestamp ts;
    public HighScore(String name, int score, Timestamp ts) {
        this.name = name;
        this.score = score;
        this.ts=ts;
    }
    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public Timestamp getTs() {
        return ts;
    }

    @Override
    public String toString() {
        return "HighScore{" + "name=" + name + ", score=" + score + '}';
    }


}