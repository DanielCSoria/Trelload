package model.command;

import model.Card;
import model.Column;

public class MoveCardCommand implements Command {

    private Column oldcolumn;
    private Column newColumn;
    private Card card;
    private int pos;

    public MoveCardCommand(Column oldcolumn, Column newColumn, Card card, int pos) {
        this.oldcolumn = oldcolumn;
        this.newColumn = newColumn;
        this.card = card;
        this.pos = pos;
    }

    @Override
    public void undo() {
        card.moveSidePos(oldcolumn, pos);
    }

    @Override
    public void redo() {
        card.moveSide(newColumn);
    }

    @Override
    public String getName() {
        return " move card " + card.getContentProperty().getValue() + " from Column " + oldcolumn.getTitleProperty().getValue() + " to column " + newColumn.getTitleProperty().getValue();
    }
}
