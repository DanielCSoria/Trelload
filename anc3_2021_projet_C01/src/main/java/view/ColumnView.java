package view;

import javafx.beans.binding.DoubleBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.util.Callback;
import model.Card;
import model.Column;
import model.Mode;
import mvvm.ViewModelColumn;

import java.sql.SQLException;
import java.util.Optional;

public class ColumnView extends VBox {


    private ViewModelColumn viewModelColumn;
    private EditableLabel lbStatus;
    private final BorderPane header = new BorderPane();
    private ListView<Card> cards = new ListView();
    private ImageButton leftButton = new ImageButton("left-arrow.png", 16, 16);
    private ImageButton rightButton = new ImageButton("right-arrow.png", 16, 16);
    private final String[] comboOptions = {"supprimer"};
    private final ComboBox box = new ComboBox(FXCollections.observableArrayList(comboOptions));
    private final DoubleBinding sizeProperty;
    private Button addButton = new Button("  +  ");

    protected ColumnView(Column col) {
        this.viewModelColumn = new ViewModelColumn(col);
        this.sizeProperty = viewModelColumn.getSizeProperty();
        this.initLayout();
        this.beautify();
        this.configActions();
    }

    private void initLayout() {
        initTitle();
        initCards();
        initButtons();

    }

    private void initCards() {
        ObservableList<Card> arr = this.viewModelColumn.getCards();
        cards.setItems(arr);
        cards.setCellFactory(new Callback<ListView<Card>, ListCell<Card>>() {
            @Override
            public ListCell<Card> call(ListView<Card> cardListView) {
                return new CardCellView();
            }
        });
        cards.setPadding(new Insets(10,0,10,0));
        cards.prefHeightProperty().bind(sizeProperty);
        this.prefHeightProperty().bind(sizeProperty.add(75));
        this.getChildren().add(cards);
        this.getChildren().add(addButton);
    }

    private void initButtons() {
        configButtonsListener();
        header.setLeft(leftButton);
        this.initDeleteButton();

    }

    private void configButtonsListener() {
        leftButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                    viewModelColumn.moveTo(Mode.PREVIOUS);
            }
        });
        box.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                    initComfirmationWindow();
            }
        });
        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                viewModelColumn.addNewCard();
            }
        });

    }

    private void initTitle() {
        lbStatus = new EditableLabel(viewModelColumn.getStatusProperty(), "columnBody", "", viewModelColumn);
        header.setCenter(lbStatus);
        this.getChildren().add(header);
    }

    private void configActions() {
        this.configButtonsListener();
        this.configButtonsProperties();

    }

    private void configButtonsProperties() {
        this.leftButton.disableProperty().bind(viewModelColumn.getCanMoveLeft().not());
        this.rightButton.disableProperty().bind(viewModelColumn.getCanMoveRight().not());
    }

    private void beautify() {
        this.cards.getStyleClass().add("transparent");
        this.getStylesheets().add("style.css");
        this.getStyleClass().add("myColumn");
    }

    private void initDeleteButton() {
        box.setVisible(false);
        box.setMaxWidth(10);
        box.setMinWidth(10);
        StackPane stack = new StackPane();
        stack.getChildren().addAll(rightButton, box);
        stack.prefWidth(100);
        stack.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
            if (event.isPrimaryButtonDown() && !rightButton.disabledProperty().get()) {
                viewModelColumn.moveTo(Mode.NEXT);
            }
            else if (event.isSecondaryButtonDown())
                box.show();
        });
        header.setRight(stack);
    }

    private void initComfirmationWindow() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confrmation");
        alert.setHeaderText("Confirmation");
        alert.setContentText("Are you sure you want to delete this Column");
        Optional<ButtonType> option = alert.showAndWait();
        if (option.get() == ButtonType.OK) {
            viewModelColumn.deleteColumn();
        }
    }




}
