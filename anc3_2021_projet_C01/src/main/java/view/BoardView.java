package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.util.Callback;
import main.Main;
import model.Column;
import mvvm.ViewModelBoard;

import java.sql.SQLException;

public class BoardView extends VBox {

    private ViewModelBoard viewModelBoard;
    private ListView<Column> columns = new ListView();
    private EditableLabel labelTitle;
    private Menu folder = new Menu("Folder");
    private Menu edition = new Menu("Edition");
    private MenuBar menuBar = new MenuBar();
    private MenuItem newColumn = new MenuItem("New column");
    private MenuItem quit = new MenuItem("Quit");
    private MenuItem undo = new MenuItem();
    private MenuItem redo = new MenuItem();
    private Main main = new Main();


    public BoardView(ViewModelBoard vm) {
        this.viewModelBoard = vm;
        this.initLayout();
        this.beautify();
    }

    private void initCol() {
        columns.setItems(this.viewModelBoard.getColumnsVM());
        columns.setCellFactory(new Callback<ListView<Column>, ListCell<Column>>() {
            @Override
            public ListCell<Column> call(ListView<Column> columnListView) {
                return new ColumnCellView();
            }
        });
        VBox.setVgrow(columns, Priority.ALWAYS);
        VBox.setMargin(columns, new Insets(10, 10, 10, 10));
        columns.getStyleClass().add("transparent");
        columns.setOrientation(Orientation.HORIZONTAL);
    }

    private void initTitle() {
        labelTitle = new EditableLabel(viewModelBoard.getTitleProperty(), "boardTitle", "labelClass", viewModelBoard);
        labelTitle.setPrefSize(400, 40);
    }

    private void initLayout() {
        this.columns.prefWidthProperty().bind(Main.getStage().widthProperty());
        this.initMenu();
        this.initTitle();
        this.initCol();
        this.getChildren().addAll(labelTitle, columns);
        this.configMenu();

    }

    private void initMenu() {
        menuBar.getMenus().add(folder);
        menuBar.getMenus().add(edition);
        folder.getItems().add(newColumn);
        folder.getItems().add(quit);
        undo.textProperty().bind(viewModelBoard.getUndoMessageProperty());
        redo.textProperty().bind(viewModelBoard.getRedoMessageProperty());
        undo.disableProperty().bind(viewModelBoard.getCanUndoProperty().not());
        redo.disableProperty().bind(viewModelBoard.getCanRedoProperty().not());
        edition.getItems().add(undo);
        edition.getItems().add(redo);
        this.getChildren().add(menuBar);
    }

    private void configMenu() {
        newColumn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                viewModelBoard.addNewColumn();
            }
        });

        quit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                main.getStage().close();
            }
        });
        undo.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                    viewModelBoard.undo();
            }
        });
        redo.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                    viewModelBoard.redo();
            }
        });
        this.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.isControlDown() && keyEvent.getCode() == KeyCode.Z && viewModelBoard.getCanUndoProperty().get()) {
                        viewModelBoard.undo();

                } else if (keyEvent.isControlDown() && keyEvent.getCode() == KeyCode.Y && viewModelBoard.getCanRedoProperty().get()) {
                        viewModelBoard.redo();
                }
            }
        });


    }

    private void beautify() {
        this.getStylesheets().add("style.css");
        this.getStyleClass().add("myBoard");
    }

}
