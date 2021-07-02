package model.command;

import javafx.beans.property.StringProperty;

import java.sql.SQLException;

public interface Command {


    void undo();

    void redo();

        String getName();
}
