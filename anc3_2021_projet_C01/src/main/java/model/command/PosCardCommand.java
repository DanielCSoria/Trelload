package model.command;

import model.Card;
import model.Mode;

import java.sql.SQLException;

public class PosCardCommand implements Command {

    private Card card;
    private Mode oldPos;
    private Mode newPos;

    public PosCardCommand(Card card, Mode oldPos, Mode newPos) {
        this.card = card;
        this.oldPos = oldPos;
        this.newPos = newPos;
    }



    @Override
    public void undo() {
        card.moveTo(oldPos);
    }

    @Override
    public void redo() { card.moveTo(newPos);

    }

    @Override
    public String getName() {
        String res = "move card " + card.getContentProperty().getValue();
        if(newPos == Mode.NEXT){
            res += " up";
        }
        else if(newPos == Mode.PREVIOUS){
            res += " down";
        }
        return res;
    }
}
