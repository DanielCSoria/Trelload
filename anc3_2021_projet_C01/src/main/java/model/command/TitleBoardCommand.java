package model.command;

import model.Board;

public class TitleBoardCommand implements Command{

    private Board board;
    private String oldTitle;
    private String newTitle;

    public TitleBoardCommand(Board board, String oldTitle){
        this.board = board;
        this.oldTitle = oldTitle;
        this.newTitle = board.getTitle().getValue();

    }



    @Override
    public void undo() {
        board.setTitle(oldTitle);
    }

    @Override
    public void redo() { board.setTitle(newTitle);

    }

    @Override
    public String getName() {
        return " edit board title";
    }
}
