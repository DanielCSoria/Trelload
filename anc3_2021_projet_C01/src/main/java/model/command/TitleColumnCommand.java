package model.command;

import model.Column;

import java.sql.SQLException;

public class TitleColumnCommand implements Command {

    private Column column;
    private String oldTitle;
    private String newTitle;

    public TitleColumnCommand(Column column, String oldTitle) {
        this.column = column;
        this.oldTitle = oldTitle;
        this.newTitle = column.getTitleProperty().getValue();
    }



    @Override
    public void undo() {
        column.setTitle(oldTitle);
    }

    @Override
    public void redo() { column.setTitle(newTitle);

    }

    @Override
    public String getName() {
        return " title column " + column.getTitleProperty().get();
    }
}
