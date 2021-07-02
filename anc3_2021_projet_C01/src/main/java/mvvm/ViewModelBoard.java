package mvvm;

import javafx.beans.property.BooleanProperty;
import model.command.AddColumnCommand;
import model.command.CommandHistory;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import model.Board;
import model.Column;
import model.command.TitleBoardCommand;
import model.dao.DbSetup;

import java.sql.SQLException;

public class ViewModelBoard {
    private Board board = DbSetup.getBoard();
    private ObservableList<Column> columns;
    private StringProperty title = new SimpleStringProperty();
    private CommandHistory commandHistory = new CommandHistory();

    public ViewModelBoard() {
        this.columns = this.board.getChildren();
        this.title.bindBidirectional(board.getTitle());
    }

    public Board getBoard() {
        return board;
    }

    public ObservableList<Column> getColumnsVM() {
        return board.getChildren();
    }

    public StringProperty getTitleProperty() {
        return this.title;
    }

    public BooleanProperty getCanUndoProperty() {
        return commandHistory.getCanUndoProperty();
    }

    public BooleanProperty getCanRedoProperty() {
        return commandHistory.getCanRedoProperty();
    }

    public StringProperty getUndoMessageProperty() {
        return commandHistory.getUndoMessageProperty();
    }

    public StringProperty getRedoMessageProperty() {
        return commandHistory.getRedoMessageProperty();
    }

    public void addNewColumn() {
        Column c = new Column(board, "Colonne " + (columns.size() + 1));
        board.addColumn(c);
        commandHistory.push(new AddColumnCommand(getBoard()));
    }

    public void undo() {
        commandHistory.undo();
    }

    public void redo() {
        commandHistory.redo();
    }

    public void editTitleHistory(Board board, String oldContent) {
        commandHistory.push(new TitleBoardCommand(board, oldContent));
        //this.board.getDb().save();
        this.board.getDao().saveBoard(board);
    }




}
