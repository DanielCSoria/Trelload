package model.command;

import model.Card;
import model.Column;

import java.sql.SQLException;

public class AddCardCommand implements Command {

    private Column column;
    private Card card;
    private int cardPos;

    public AddCardCommand(Column column) {
        this.column = column;
    }

    @Override
    public void undo() {
        cardPos = column.getChildren().size() - 1;
        card = column.getChildren().get(cardPos);
        column.deleteCard(card);
    }

    @Override
    public void redo()  {
        if (cardPos >= column.getChildren().size())
            column.addCard(card);
        else
            column.setCard(cardPos, card);
    }

    @Override
    public String getName() {
        return  "Add card to column " + column.getPos();
    }
}
