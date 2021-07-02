package mvvm;

import model.command.*;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.StringProperty;
import model.Card;
import model.Column;
import model.Mode;

import java.sql.SQLException;

public class ViewModelCard {

    private Card card;

    private final BooleanProperty canMoveDown;

    private final BooleanProperty canMoveUp;

    private final BooleanProperty canMoveRight;

    private final BooleanProperty getCanMoveLeft;

    private CommandHistory commandHistory = new CommandHistory();

    public ViewModelCard(Card card){
        this.card = card;
        this.canMoveDown = card.getCanMoveDown();
        this.canMoveUp = card.getCanMoveUp();
        this.canMoveRight = card.getCanMoveRight();
        this.getCanMoveLeft = card.getCanMoveLeft();

    }

    public StringProperty getContentProperty(){
        return card.getContentProperty();
    }

    public Card getCard() { return card; }

    public void moveTo(Mode d) {
        if(d == Mode.PREVIOUS) {
            this.card.moveTo(d);
            commandHistory.push(new PosCardCommand(card, Mode.NEXT, Mode.PREVIOUS));

        }
        else if(d == Mode.NEXT){
            this.card.moveTo(d);
            commandHistory.push( new PosCardCommand(card, Mode.PREVIOUS, Mode.NEXT));

        }
    }

    public void moveCardToRight() {
        Column oldColumn = card.getColumn();
        Column newColumn = oldColumn.getRightColumn(oldColumn);
        commandHistory.push(new MoveCardCommand(oldColumn, newColumn,card, card.getPos()));
        this.card.moveToRight();


    }
    public void moveCardToLeft() {
        Column oldColumn = card.getColumn();
        Column newColumn = oldColumn.getLeftColumn(oldColumn);
        commandHistory.push(new MoveCardCommand(oldColumn, newColumn,card, card.getPos()));
        this.card.moveToLeft();

    }

    public void deleteCard() {
        commandHistory.push(new DeleteCardCommand(card.getColumn(), card, card.getPos()));
        card.delete();
    }


    public BooleanProperty getCanMoveDown(){
        return canMoveDown;
    }

    public BooleanProperty getCanMoveUp(){
        return canMoveUp;
    }

    public BooleanProperty getCanMoveRight(){
        return canMoveRight;
    }
    public BooleanProperty getGetCanMoveLeft(){
        return getCanMoveLeft;
    }

    public void editCardHistory(Card card, String oldContent)  {
        commandHistory.push(new ContentCardCommand(card, oldContent));
        //this.card.getDb().save();
        this.card.getDao().saveCard(card);
    }


}
