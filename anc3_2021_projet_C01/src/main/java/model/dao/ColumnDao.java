package model.dao;
import model.Board;
import model.Card;
import model.Column;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ColumnDao extends DBManager implements Dao<Column>{


    public ColumnDao(){
    }

    //need to improve the save method.
    @Override
    public void save(Column c) {
        if (c.getId() > 0) {
            update(c.getBoard());
        }
        else {
           insert(c);
        }
    }

    @Override
    public void delete(Column c) {
            try {
                String sql;
                sql = "DELETE FROM Column WHERE idColumn = ?;";
                PreparedStatement p = getConnection().prepareStatement(sql);
                p.setInt(1, c.getId());
                p.execute();
                c.setId(0);
            }
            catch(SQLException e){
                e.printStackTrace();
            }
    }


    public void update(Board b){
        try {
            String sql = "UPDATE Column SET title =?, position=? WHERE idColumn=?;";
            for(Column column : b.getChildren()) {
                PreparedStatement p = getConnection().prepareStatement(sql);
                p.setString(1, column.getTitleProperty().get());
                p.setInt(2, column.getPos());
                System.out.println(column.getPos());
                p.setInt(3, column.getId());
                p.execute();
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }




    private void updateIdFromDbInsert(PreparedStatement p, Column c){
        try {
            ResultSet rs = p.getGeneratedKeys();
            rs.next();
            c.setId(rs.getInt(1));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void insert(Column c){
        try {
            String sql;
            sql = " INSERT INTO Column(title, position, idBoard) VALUES(?,?,?);";
            PreparedStatement p = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            p.setString(1, c.getTitleProperty().get());
            p.setInt(2, c.getPos());
            p.setInt(3, 1);
            p.execute();
            this.updateIdFromDbInsert(p, c);

        }
        catch(SQLException e){
            e.printStackTrace();
        }

    }




}
