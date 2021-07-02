package model.dao;

import javafx.beans.property.SimpleStringProperty;
import model.Board;
import model.Card;
import model.Column;
import model.DataInitializer;

import java.io.File;
import java.sql.*;

public class DbSetup {
    private static String url = "jdbc:sqlite:dbtrello.db";
    protected static Connection conn;




    public static void setupDatabase() {
        try {
            conn = DriverManager.getConnection(url);
            setupDB();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void setupDB() throws SQLException {
        configDB();
        createTables();
        DataInitializer.init();
    }

    private static void configDB() throws SQLException {
        Statement stmt = conn.createStatement();
        String sql = "PRAGMA foreign_keys = ON;";
        stmt.execute(sql);
    }

    private static void createTables() throws SQLException {
        String sql;
        Statement stmt = conn.createStatement();
        sql = "CREATE TABLE IF NOT EXISTS Board ("
                + "	idBoard integer PRIMARY KEY,"
                + "	title text NOT NULL);";
        stmt.execute(sql);
        sql = " CREATE TABLE IF NOT EXISTS Column("
                + " idColumn integer PRIMARY KEY AUTOINCREMENT,"
                + " title text NOT NULL ,"
                + " position integer NOT NULL,"
                + " idBoard integer NOT NULL,"
                + " CONSTRAINT fk_colBoard FOREIGN KEY (idBoard)"
                + " REFERENCES Board (idBoard));";
        stmt.execute(sql);


        stmt.execute(sql);
        sql = " CREATE TABLE IF NOT EXISTS Card ("
                + " idCard integer PRIMARY KEY AUTOINCREMENT,"
                + " content text NOT NULL, "
                + " position integer NOT NULL,"
                + " idColumn integer NOT NULL,"
                + " CONSTRAINT fk_cardCol FOREIGN KEY (idColumn)"
                + " REFERENCES Column (idColumn));";
        stmt.execute(sql);
    }

    public static Connection getConnection() {
        return conn;
    }

    public static Board getBoard() {
        String sql = "SELECT * FROM Board;";
        Board b = null;
        try {
            // get board
            Statement statement = conn.createStatement();
            ResultSet r = statement.executeQuery(sql);
            if (r.next()) {
                b = new Board(r.getInt("idBoard"), new SimpleStringProperty(r.getString("title")));

                // get board columns
                sql = "SELECT * FROM Column";
                r = statement.executeQuery(sql);

                while (r.next()) {
                    b.add(new Column(b, r.getString("title"), r.getInt("idColumn")));
                }

                for (Column c : b.getChildren()) {
                    // get columns cards
                    sql = "SELECT * FROM Card WHERE idColumn = ?;";
                    PreparedStatement ps = conn.prepareStatement(sql);
                    ps.setInt(1, c.getId());
                    ResultSet cards = ps.executeQuery();
                    while (cards.next()) {
                        c.add(new Card(c, cards.getString("content"), cards.getInt("idCard")));
                    }
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return b;
    }
}