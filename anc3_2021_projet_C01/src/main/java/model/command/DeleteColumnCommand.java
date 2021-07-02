package model.command;

import model.Board;
import model.Column;

public class DeleteColumnCommand implements Command {

    private Board board;
    private Column column;
    private int pos;

    public DeleteColumnCommand(Board board, Column column, int pos) {
        this.board = board;
        this.column = column;
        this.pos = pos;
    }

    @Override
    public void undo() {
        board.addColumn(pos, column);
    }

    @Override
    public void redo() {
        board.deleteColumn(column);
    }

    @Override
    public String getName() {
        return "delete column " + column.getTitleProperty().getValue();
    }
}
