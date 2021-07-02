package model.dao;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class DBManager {
    private final Connection conn = DbSetup.getConnection();

    public Connection getConnection(){
        return conn;
    }

    //abstract void save() throws SQLException;
    //abstract void delete() throws SQLException;


}
