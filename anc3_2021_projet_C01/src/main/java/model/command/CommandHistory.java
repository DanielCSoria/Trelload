package model.command;
import javafx.beans.property.*;
import model.ObservableStack;

import java.sql.SQLException;

public class CommandHistory {

    private final static ObservableStack<Command> toRedo = new ObservableStack<>();
    private final static ObservableStack<Command> toUndo = new ObservableStack<>();
    private final StringProperty undoMessageProperty = new SimpleStringProperty("Undo  ctrl+z");
    private final StringProperty redoMessageProperty = new SimpleStringProperty("Redo ctrl+y");


    public void push(Command command) {
        toUndo.push(command);
        this.updateUndoMsg(command);
        toRedo.emptyAll();
    }

    private void updateUndoMsg(Command command) {
        undoMessageProperty.set("Undo " + (command == null ? "" : command.getName()) + " ctrl+z");
    }

    private void updateRedoMsg(Command command) {
        redoMessageProperty.set("Redo " + (command == null ? "" : command.getName()) + " ctrl+y");
    }

    public void undo() {
        Command command = !toUndo.isEmpty() ? toUndo.pop() : null;
        if (command == null)
            throw new IllegalStateException("You should never get here");
        command.undo();
        toRedo.push(command);
        this.updateUndoMsg(toUndo.peek());
        this.updateRedoMsg(command);
    }

    public void redo() {
        Command command = !toRedo.isEmpty() ? toRedo.pop() : null;
        if (command == null)
            throw new IllegalStateException("You should never get here");
            command.redo();
            toUndo.push(command);
            this.updateRedoMsg(toRedo.peek());
            this.updateUndoMsg(command);

    }

    public BooleanProperty getCanUndoProperty() {
        return toUndo.isEmptyProperty();
    }

    public BooleanProperty getCanRedoProperty() {
        return toRedo.isEmptyProperty();
    }

    public StringProperty getUndoMessageProperty() {
        return undoMessageProperty;
    }

    public StringProperty getRedoMessageProperty() {
        return redoMessageProperty;
    }


}
