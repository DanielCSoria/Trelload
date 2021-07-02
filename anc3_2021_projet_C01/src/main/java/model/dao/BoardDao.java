package model.dao;

import model.Board;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BoardDao extends DBManager implements Dao<Board> {


    public BoardDao() {

    }

    //need to improve the save method.
    @Override
    public void save(Board b) {
        try {
            String sql = "UPDATE Board SET title =? WHERE idBoard=1;";
            PreparedStatement p = getConnection().prepareStatement(sql);
            p.setString(1, b.getTitle().get());
            p.execute();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Board b) {
        try {
            String sql = "DELETE FROM Board WHERE idBoard = ?;";
            PreparedStatement p = getConnection().prepareStatement(sql);
            p.setInt(1, b.getId());
            p.execute();
        }
        catch(SQLException e){
            e.printStackTrace();
        }

    }

}
