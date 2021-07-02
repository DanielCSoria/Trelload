package view;

import javafx.beans.property.StringProperty;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import model.Board;
import model.Card;
import model.Column;
import mvvm.ViewModelBoard;
import mvvm.ViewModelCard;
import mvvm.ViewModelColumn;

import java.sql.SQLException;

public class

EditableLabel extends HBox {
    private static EditableLabel currentUpdating;

    private enum Mode {EDIT, READ}

    private final TextField updateText = new TextField();
    private final Label text = new Label();
    private String oldContent;
    private Board board;
    private Card card;
    private Column column;
    private ViewModelBoard viewModelBoard;
    private ViewModelCard viewModelCard;
    private ViewModelColumn viewModelColumn;

    private void changeMode(Mode m)  {
        if (this.getChildren().size() > 0)
            if (m == Mode.EDIT) {
                if (EditableLabel.currentUpdating != null)
                    EditableLabel.currentUpdating.changeMode(Mode.READ);
                EditableLabel.currentUpdating = this;
                this.getChildren().set(0, updateText);
                updateText.requestFocus();
                updateText.selectAll();

            } else {
                this.getChildren().set(0, text);
                if (board != null) {
                    viewModelBoard.editTitleHistory(board, oldContent);
                } else if (card != null) {
                    viewModelCard.editCardHistory(card, oldContent);
                } else if (column != null) {
                    viewModelColumn.editColumnHistory(column, oldContent);
                }
            }
    }

    public EditableLabel(StringProperty lab, String customTextFieldCSS, String customLabelCSS, ViewModelCard viewModelCard) {
        ConstructorBody(lab, customTextFieldCSS, customLabelCSS);
        this.card = viewModelCard.getCard();
        this.viewModelCard = viewModelCard;

    }

    public EditableLabel(StringProperty lab, String customTextFieldCSS, String customLabelCSS, ViewModelBoard viewModelBoard) {
        ConstructorBody(lab, customTextFieldCSS, customLabelCSS);
        this.board = viewModelBoard.getBoard();
        this.viewModelBoard = viewModelBoard;

    }

    public EditableLabel(StringProperty lab, String customTextFieldCSS, String customLabelCSS, ViewModelColumn viewModelColumn) {
        ConstructorBody(lab, customTextFieldCSS, customLabelCSS);
        this.column = viewModelColumn.getColumn();
        this.viewModelColumn = viewModelColumn;
    }

    public void ConstructorBody(StringProperty lab, String customTextFieldCSS, String customLabelCSS) {
        oldContent = lab.getValue();
        text.textProperty().bind(lab);
        text.setWrapText(true);
        updateText.textProperty().bindBidirectional(lab);
        updateText.getStyleClass().add(customTextFieldCSS);
        text.getStyleClass().add(customTextFieldCSS);
        text.getStyleClass().add(customLabelCSS);
        HBox.setHgrow(updateText, Priority.ALWAYS);
        HBox.setHgrow(text, Priority.ALWAYS);
        text.setMaxWidth(Double.MAX_VALUE);
        this.getChildren().add(text);
        initKeyAction();
    }


    private void initKeyAction() {

        this.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                        changeMode(Mode.EDIT);
                }
            }
        });
        this.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode().equals(KeyCode.ENTER)) {
                        changeMode(Mode.READ);

                }
                if (keyEvent.getCode().equals((KeyCode.ESCAPE))
                ) {
                        changeMode(Mode.READ);

                }
            }
        });
    }


}
