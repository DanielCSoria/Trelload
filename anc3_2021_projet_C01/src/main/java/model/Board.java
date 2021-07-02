package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import model.dao.BoardDao;
import model.dao.FacadeDao;

import java.sql.SQLException;


public class Board extends Container<Column>  {

    private StringProperty title = new SimpleStringProperty();
    private int id;
    //private BoardDao db ;
    private FacadeDao dao = new FacadeDao();

    public Board(int id,StringProperty title) {
        //this.db = new BoardDao(this);
        this.title= title;
        this.id = id;
    }
    public Board(){ }

    public int getId(){
        return id;
    }

    public FacadeDao getDao() { return dao; }

    //public BoardDao getDb(){return db;}

    public StringProperty getTitle() {
        return title;
    }

    public void setTitle(String newName){
        this.title.setValue(newName);
        //db.save();
        dao.saveBoard(this);
    }

    public int retrieveChildPosition(Column child){
        int childIndex = this.getChildren().indexOf(child);
        if(childIndex == -1)
            throw new RuntimeException("Child is not a member of this board");
        return childIndex;
    }
    public void addColumn(Column c) {
        add(c);
        //c.getDb().save();
        dao.saveBoard(this);
        dao.saveColumn(c);
    }

    public void addColumn(int pos, Column c){
        add(pos, c);
        //c.getDb().save();
        dao.saveBoard(this);
        dao.saveColumn(c);
    }

    public void deleteColumn(Column c){
        delete(c);
        //c.getDb().delete();

        dao.deleteColumn(c);
        //dao.saveBoard(this);
        //dao.saveColumn(c);

    }

    public void setColumn(int pos, Column c){
        set(pos, c);
        //c.getDb().save();
        dao.saveBoard(this);
        dao.saveColumn(c);
    }






}
