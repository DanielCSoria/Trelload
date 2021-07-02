package model.command;

import model.Board;
import model.Column;

import java.sql.SQLException;

public class AddColumnCommand implements Command{

    private Board board;
    private Column column;
    private int colPos;

    public AddColumnCommand(Board board) {
        this.board = board;
    }

    @Override
    public void undo() {
        colPos = board.getChildren().size()-1;
        column = board.getChildren().get(colPos);
        board.deleteColumn(column);
    }

    @Override
    public void redo() {
        if(colPos >= board.getChildren().size())
             board.addColumn(column);
        else
            board.setColumn(colPos,column);

    }

    @Override
    public String getName() {
        return "add new Column";
    }
}
