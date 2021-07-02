package model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import model.dao.CardDao;
import model.dao.FacadeDao;

import java.sql.SQLException;


public class Card implements Comparable<Card> {
    private Column column;
    private int id;
    private int pos;
    private BooleanProperty canMoveLeft = new SimpleBooleanProperty();
    private BooleanProperty canMoveRight = new SimpleBooleanProperty();
    private BooleanProperty canMoveUp= new SimpleBooleanProperty();
    private BooleanProperty canMoveDown = new SimpleBooleanProperty();
    private int columnId;
    private final StringProperty content;
    private final CardDao db;
    private final FacadeDao dao = new FacadeDao();


    public Card(Column column, String content, int id) {
        this.db = new CardDao();
        this.column = column;
        this.content = new SimpleStringProperty(content);
        this.id = id;

    }

    public Card(Column column, String content) {
        this(column, content, 0);

    }

    public void setContent(String newContent)  {
        this.content.setValue(newContent);
        //db.save();
        dao.saveCard(this);
    }

    public CardDao getDb(){
        return db;
    }

    public FacadeDao getDao() { return dao; }

    public int getId()  {
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public int getColumnId() { return column.getId(); }

    public void setColumn(Column column) {
        this.column = column;
    }

    public int getPos(){
        return this.column.getChildren().indexOf(this);
    }
    public Column getColumn(){
        return column;
    }

    public StringProperty getContentProperty() {
        return content;
    }

    @Override
    public String toString() {
        return content.get();
    }

    public void moveSide(Column newColumn) {
        newColumn.add(this);
        column.delete(this);
        setColumn(newColumn);
        canMoveRight.setValue(newColumn.getCanMoveRight().getValue());
        canMoveLeft.setValue(newColumn.getCanMoveLeft().getValue());
        //this.db.save();
        dao.saveCard(this);

    }

    public void moveSidePos(Column oldColumn, int pos){
        oldColumn.add(pos, this);
        column.delete(this);
        setColumn(oldColumn);
        canMoveRight.setValue(oldColumn.getCanMoveRight().getValue());
        canMoveLeft.setValue(oldColumn.getCanMoveLeft().getValue());
        //this.db.save();
        dao.saveCard(this);
    }

    public int compareTo(Card o) {
        return this.content.get().compareToIgnoreCase(o.content.get());
    }

   public void moveTo(Mode d) {
        this.column.moveTo(d, this);
        //this.db.save();
       dao.saveCard(this);
    }

    public void moveToRight()  {
        this.column.moveCardToRight(this);
        //this.db.save();
        //this.db.updateSideCards(Mode.NEXT);
        dao.saveCard(this);
        dao.updateSideCards( this, Mode.NEXT);
    }
    public void moveToLeft()  {
        this.column.moveCardToLeft(this);
        //this.db.save();
        //this.db.updateSideCards(Mode.PREVIOUS);
        dao.saveCard(this);
        dao.updateSideCards(this, Mode.PREVIOUS);

    }

    public void delete()  {
        column.deleteCard(this);
        //db.delete();
        dao.deleteCard(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Card)) return false;
        Card card = (Card) o;
        return content.equals(card.content) && column.equals(card.column) && column.getPos() == card.column.getPos();
    }

    public BooleanProperty getCanMoveUp() {
        int pos = column.retrieveChildPosition(this);
        this.canMoveUp.setValue(pos>0);
        return canMoveUp;
    }

    public BooleanProperty getCanMoveDown() {
        int pos = column.retrieveChildPosition(this);
        this.canMoveDown.setValue(pos < column.getChildren().size() - 1);
        return canMoveDown;
    }

    public BooleanProperty getCanMoveLeft() {
        this.canMoveLeft.setValue(this.column.getCanMoveLeft().getValue());
        return canMoveLeft;
    }

    public BooleanProperty getCanMoveRight() {
        this.canMoveRight.setValue(this.column.getCanMoveRight().getValue());
        return canMoveRight;
    }




}
