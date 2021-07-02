package model.command;

import model.Card;
import model.Column;

public class DeleteCardCommand implements Command {

    private Column column;
    private Card card;
    private int pos;

    public DeleteCardCommand(Column column, Card card, int pos) {
        this.column = column;
        this.card = card;
        this.pos = pos;
    }

    @Override
    public void undo() {
        column.addCard(pos, card);
    }

    @Override
    public void redo() {
        column.deleteCard(card);
    }

    @Override
    public String getName() {
        return "delete card " + card.getContentProperty().getValue() + " from column " + column.getTitleProperty().getValue();
    }
}
