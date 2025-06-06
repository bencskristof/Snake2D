package snake;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Properties;


public class HighScores {

    int maxScores;
    PreparedStatement insertStatement;
    PreparedStatement deleteStatement;
    Connection connection;

    public HighScores(int maxScores) throws SQLException {
        this.maxScores = maxScores;
        Properties connectionProps = new Properties();
        // Add new user -> MySQL workbench (Menu: Server / Users and priviliges)
        //                             Tab: Administrative roles -> Check "DBA" option
        connectionProps.put("user", "root");
        connectionProps.put("password", "");
        connectionProps.put("serverTimezone", "UTC");
        String dbURL = "jdbc:mysql://localhost:3306/highscores";
        connection = DriverManager.getConnection(dbURL, connectionProps);


        String insertQuery = "INSERT INTO HIGHSCORES (TS, NAME, SCORE) VALUES (?, ?, ?)";
        insertStatement = connection.prepareStatement(insertQuery);
        String deleteQuery = "DELETE FROM HIGHSCORES WHERE TS=?";
        deleteStatement = connection.prepareStatement(deleteQuery);
    }

    public ArrayList<HighScore> getHighScores() throws SQLException {
        String query = "SELECT * FROM HIGHSCORES";
        ArrayList<HighScore> highScores = new ArrayList<>();
        Statement stmt = connection.createStatement();
        ResultSet results = stmt.executeQuery(query);
        while (results.next()) {
            String name = results.getString("NAME");
            int score = results.getInt("SCORE");
            Timestamp ts=results.getTimestamp(("TS"));
            highScores.add(new HighScore(name, score,ts));
        }
        sortHighScores(highScores);
        return highScores;
    }

    public void putHighScore(HighScore hs) throws SQLException {
        ArrayList<HighScore> highScores = getHighScores();
        if (highScores.size() < maxScores) {
            insertScore(hs.getName(), hs.getScore());

        } else {
            sortHighScores(highScores);
            HighScore leastScore = new HighScore(highScores.get(highScores.size() - 1).getName(), highScores.get(highScores.size() - 1).getScore(), highScores.get(highScores.size() - 1).getTs());
            if (leastScore.getScore() <= hs.getScore()) {
                System.out.println("Taaa"+leastScore);
                deleteScores(leastScore);
                insertScore(hs.getName(), hs.getScore());
            }
        }
    }

    private void sortHighScores(ArrayList<HighScore> highScores) {
        Collections.sort(highScores, new Comparator<HighScore>() {
            @Override
            public int compare(HighScore t, HighScore t1) {
                return t1.getScore() - t.getScore();
            }
        });
    }

    private void insertScore(String name, int score) throws SQLException {
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        insertStatement.setTimestamp(1, ts);
        insertStatement.setString(2, name);
        insertStatement.setInt(3, score);
        insertStatement.executeUpdate();
        System.out.println("Score inserted");
    }

    private void deleteScores(HighScore score) throws SQLException {
        System.out.println(score.getTs());
        deleteStatement.setTimestamp(1, score.getTs());
        deleteStatement.executeUpdate();
    }
}
