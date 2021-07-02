package mvvm;

import model.command.*;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.*;
import javafx.collections.ObservableList;
import model.Card;
import model.Column;
import model.Mode;
import view.CardView;

import java.sql.SQLException;


public class ViewModelColumn {

    private Column column;
    private ObservableList<Card> cards;
    private BooleanProperty canMoveRight;
    private BooleanProperty canMoveLeft;
    private StringProperty statusProperty;
    private CommandHistory commandHistory = new CommandHistory();

    public ViewModelColumn(Column col) {
        this.column = col;
        this.canMoveRight = column.getCanMoveRight();
        this.canMoveLeft = column.getCanMoveLeft();
        this.statusProperty = new SimpleStringProperty(col.getTitleProperty().get());
        this.cards = this.column.getChildren();

    }

    public Column getColumn() { return column; }
    public BooleanProperty getCanMoveRight() {
        return canMoveRight;
    }
    public BooleanProperty getCanMoveLeft() {
        return canMoveLeft;
    }
    public ObservableList<Card> getCards() {
        return this.column.getChildren();
    }
    public StringProperty getStatusProperty() {
        return column.getTitleProperty();

    }


    public void moveTo(Mode mode) {
        if(mode == Mode.PREVIOUS){
            this.column.moveTo(mode);
            commandHistory.push(new PosColumnCommand(getColumn(), Mode.NEXT, Mode.PREVIOUS));
        }
        else if(mode == Mode.NEXT){
            this.column.moveTo(mode);
            commandHistory.push( new PosColumnCommand(getColumn(), Mode.PREVIOUS, Mode.NEXT));
        }
    }

    public DoubleBinding getSizeProperty(){
        return Bindings.size(this.getCards()).multiply(CardView.CARD_HEIGHT + 6).add(25);
    }

    public void addNewCard() {
        Card newCard = new Card(column, " ");
        this.column.addCard(newCard);
        commandHistory.push(new AddCardCommand(getColumn()));
    }
    public void undo() {
        commandHistory.undo();
    }

    public void deleteColumn() {
        commandHistory.push(new DeleteColumnCommand(column.getBoard(), column,  column.getPos()));
        column.delete();
    }

    public void editColumnHistory(Column column, String oldContent) {
        commandHistory.push(new TitleColumnCommand(column, oldContent));
        //this.column.getDb().save();
        this.column.getDao().saveColumn(column);


    }



}
