package model.command;

import model.Column;
import model.Mode;

import java.sql.SQLException;

public class PosColumnCommand implements Command {

    private Column column;
    private Mode oldPos;
    private Mode newPos;

    public PosColumnCommand(Column column, Mode oldPos, Mode newPos) {
        this.column = column;
        this.oldPos = oldPos;
        this.newPos = newPos;
    }



    @Override
    public void undo() {
        column.moveTo(oldPos);
    }

    @Override
    public void redo() { column.moveTo(newPos);

    }

    @Override
    public String getName() {
        String res = "move column " + column.getTitleProperty().getValue();
        if(newPos == Mode.NEXT){
            res += " to the right";
        }
        else if(newPos == Mode.PREVIOUS){
            res += " to the left";
        }
        return res;
    }
}
