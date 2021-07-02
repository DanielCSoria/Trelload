package model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import model.dao.ColumnDao;
import model.dao.FacadeDao;

import java.sql.SQLException;


public class Column extends Container<Card> implements Comparable<Column> {
    private Board board;
    private int id;
    private int pos;
    private final StringProperty title;
    //private final ColumnDao db;
    private final FacadeDao dao = new FacadeDao();

    public Column(Board board, String status, int id) {
        //this.db = new ColumnDao(this);
        this.board = board;
        this.title = new SimpleStringProperty(status);
        this.id = id;
    }

    public Column(Board board, String status) {
        this(board, status, 0);

    }

    public int getId() {
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public Board getBoard() {
        return board;
    }

    public FacadeDao getDao() { return dao; }

    //public ColumnDao getDb(){
    //     return db;
    //}

    public void setTitle(String newStatus){
        title.setValue(newStatus);
        dao.saveColumn(this);
    }

    public String toString() {
        return this.title.get();
    }

    public StringProperty getTitleProperty() {
        return title;
    }

    public BooleanProperty getCanMoveLeft() {
        int pos = board.retrieveChildPosition(this);
        return new SimpleBooleanProperty(pos > 0);
    }

    public BooleanProperty getCanMoveRight() {
        int pos = board.retrieveChildPosition(this);
        return new SimpleBooleanProperty(pos < board.getChildren().size() - 1);
    }

    public Column getRightColumn(Column column) {
        return board.getChildren().get(column.getPos() + 1);
    }

    public Column getLeftColumn(Column column) {
        return board.getChildren().get(column.getPos() - 1);
    }

    public void moveTo(Mode d)  {
        this.board.moveTo(d, this);
        this.dao.saveColumn(this);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Column) {
            Column c = (Column) o;
            return this.title.equals(c.title);
        } else {
            return false;
        }
    }

    @Override
    public int compareTo(Column other) {
        return this.title.get().compareToIgnoreCase(other.title.get());
    }

    public int getPos() {
        return board.getChildren().indexOf(this);
    }

    public void moveCardToLeft(Card child) {
        if (getPos() == 0)
            throw new RuntimeException("Can't move card left");
        Column newColumn = board.getChildren().get(getPos() - 1);

        child.moveSide(newColumn);
    }

    public void moveCardToRight(Card child) {
        if (getPos() == board.getChildren().size() - 1)
            throw new RuntimeException("Can't move card right");
        Column newColumn = board.getChildren().get(getPos() + 1);
        child.moveSide(newColumn);
    }

    public int retrieveChildPosition(Card child) {
        int childIndex = this.getChildren().indexOf(child);
        if (childIndex == -1)
            throw new RuntimeException("Child is not a member of this board");
        return childIndex;
    }

    public void delete() {
        Column column = this;
        board.delete(column);
        //db.delete();
        dao.deleteColumn(this);
        //dao.saveColumn(this);

    }

    public void deleteCard(Card c){
        delete(c);
        //c.getDb().delete();
        dao.deleteCard(c);


    }

    public void addCard(Card c) {
        add(c);
        c.setColumn(this);
        //c.getDb().save();
        dao.saveCard(c);
    }
    public void addCard(int pos, Card c){
        add(pos, c);
        //c.getDb().save();

        dao.saveCard(c);

    }
    public void setCard(int pos, Card c){
        set(pos, c);
        //c.getDb().save();

        dao.saveCard(c);
    }



}

        //this.board.moveTo(d, this);
        // this.db.save();