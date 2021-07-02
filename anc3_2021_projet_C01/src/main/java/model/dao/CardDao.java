package model.dao;
import model.Card;
import model.Column;
import model.Mode;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class CardDao extends DBManager implements Dao<Card>{




    //need to improve the save method.
    @Override
    public void save(Card c) {
        String sql;
        if (c.getId() > 0) {
            // we update all the cards from this column because the position(of others cards) might change in case of up and down
            update(c.getColumn());
        }
        else {
           insert(c);
        }
    }

    @Override
    public void delete (Card c) {
        try {
            String sql = "DELETE  FROM Card WHERE idCard = ?;";
            PreparedStatement p = getConnection().prepareStatement(sql);
            p.setInt(1, c.getId());
            p.execute();
            c.setId(0);  // We need to set id to 0 because once deleted id no longer exist and we'll need to rattritribute one in case of undo delete
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void updateSideCards(Mode d, Card c) {
            Column column;
            if (d == Mode.NEXT) {
                column = c.getColumn().getLeftColumn(c.getColumn());
            } else {
                column = c.getColumn().getRightColumn(c.getColumn());
            }
            update(column);
    }

    public void update(Column column){
        try {
            String sql = "UPDATE Card SET content =?, position=?, idColumn=? WHERE idCard=?;";
            for(Card card : column.getChildren()) {
                PreparedStatement p = getConnection().prepareStatement(sql);
                p.setString(1, card.getContentProperty().get());
                p.setInt(2, card.getPos());
                p.setInt(3, card.getColumn().getId());
                p.setInt(4, card.getId());
                p.execute();
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    private void updateIdFromDbInsert(PreparedStatement p, Card c){
        try {
            ResultSet rs = p.getGeneratedKeys();
            rs.next();
            c.setId(rs.getInt(1));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void insert(Card c){
        try {
            String sql;
            sql = " INSERT INTO Card (content, position, idColumn) VALUES (?,?,?);";
            PreparedStatement p = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            p.setString(1, c.getContentProperty().get());
            p.setInt(2, c.getPos());
            p.setInt(3, c.getColumnId());
            p.execute();
            /* This is mandatory if you don't wanna count max(*) etc at every insert.
             PS : this is the first google search answer :
             https://stackoverflow.com/questions/4224228/preparedstatement-with-statement-return-generated-keys */
            this.updateIdFromDbInsert(p, c);
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void deleteCardsColumn(Column c){
        String sql;
        try {
            if (c.getChildren().size() > 0) {
                for (Card card : c.getChildren()) {
                    sql = "DELETE FROM Card where idColumn = ? AND idCard=?;";
                    PreparedStatement p = getConnection().prepareStatement(sql);
                    p.setInt(1, c.getId());
                    p.setInt(2, card.getId());
                    p.execute();
                    card.setId(0);
                }
            }
        }
        catch(SQLException e){
                e.printStackTrace();
            }

    }






}

