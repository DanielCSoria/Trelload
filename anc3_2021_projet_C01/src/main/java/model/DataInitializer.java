package model;

import model.dao.DbSetup;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class DataInitializer {


    public static void init() throws SQLException {
        Connection conn = DbSetup.getConnection();
        if (DbSetup.getBoard() == null) {
            seedBoard(conn);
            seedColumn(conn);
            seedCard(conn);
        }
    }


    private static void clearDB() throws SQLException {
        Statement statement = DbSetup.getConnection().createStatement();
        String sql;
        sql = "DELETE FROM Card;";
        statement.execute(sql);
        sql = "DELETE FROM Column;";
        statement.execute(sql);
        sql = "DELETE FROM Board;";
        statement.execute(sql);

    }

    private static void seedBoard(Connection conn) throws SQLException {
        String sql = "INSERT INTO Board(idBoard, title) VALUES(?,?);";
        PreparedStatement p = conn.prepareStatement(sql);
        p.setInt(1, 1);
        p.setString(2, "Welcome to Trello !");
        p.execute();
    }

    private static void seedColumn(Connection conn) throws SQLException {
        String sql = " INSERT INTO Column(title, position, idBoard) VALUES(?,?,?);";
        PreparedStatement p = conn.prepareStatement(sql);
        p.setString(1, "Col 1");
        p.setInt(2, 0);
        p.setInt(3, 1);
        p.execute();

        p.setString(1, "Col 2");
        p.setInt(2, 1);
        p.setInt(3, 1);
        p.execute();

        p.setString(1, "Col 3");
        p.setInt(2, 2);
        p.setInt(3, 1);
        p.execute();

        p.setString(1, "Col 4");
        p.setInt(2, 3);
        p.setInt(3, 1);
        p.execute();
    }

    private static void seedCard(Connection conn) throws SQLException {
        String sql = " INSERT INTO Card (content, position, idColumn) VALUES (?,?,?);";
        PreparedStatement p = conn.prepareStatement(sql);
        p.setString(1, "Card 1");
        p.setInt(2, 0);
        p.setInt(3, 1);
        p.execute();

        p.setString(1, "Card 2");
        p.setInt(2, 1);
        p.setInt(3, 1);
        p.execute();

        p.setString(1, "Card 3");
        p.setInt(2, 0);
        p.setInt(3, 2);
        p.execute();

        p.setString(1, "Card 4");
        p.setInt(2, 1);
        p.setInt(3, 2);
        p.execute();

        p.setString(1, "Card 5");
        p.setInt(2, 2);
        p.setInt(3, 1);
        p.execute();
    }
}
