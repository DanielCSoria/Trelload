package view;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import model.Card;
import model.Mode;
import mvvm.ViewModelCard;

import java.sql.SQLException;
import java.util.Optional;


public class CardView extends BorderPane {
    public static final double CARD_HEIGHT = 85;
    private final String[] comboOptions = {"supprimer"};
    private final ComboBox box = new ComboBox(FXCollections.observableArrayList(comboOptions));
    private Card card;
    private ViewModelCard viewModelCard;
    private ImageButton l;
    private ImageButton r;
    private ImageButton u;
    private ImageButton d;


    private void initButtons() {
        r = new ImageButton("right-arrow.png", 16,16);
        l = new ImageButton("left-arrow.png", 16,16);
        u = new ImageButton("up-arrow.png", 16,16);
        d = new ImageButton("down-arrow.png", 16,16);
        this.setTop(u);
        this.setBottom(d);
        this.setLeft(l);
        this.setRight(r);
        this.initDeleteButton();
        configButtonsListener();
    }

    private void initDeleteButton() {
        box.setVisible(false);
        box.setMaxWidth(20);
        box.setMinWidth(0);
        StackPane stack = new StackPane();
        stack.getChildren().addAll(r,box);
        this.setRight(stack);
    }
    private void initComfirmationWindow() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confrmation");
        alert.setHeaderText("Confirmation");
        alert.setContentText("Are you sure you want to delete this card?");
        Optional<ButtonType> option = alert.showAndWait();
        if (option.get() == ButtonType.OK){
            viewModelCard.deleteCard();
        }
    }

    private void initBody() {
        EditableLabel l = new EditableLabel(viewModelCard.getContentProperty(),"cardBody","", viewModelCard);
        this.setCenter(l);
    }

    private void initLayout() {
        this.initBody();
        this.initButtons();
        this.beautify();
        this.configButtonsProperties();
        this.configMouseClicked();
    }

    private void beautify() {
        this.getStylesheets().add("style.css");
        this.getStyleClass().add("myClass");
    }

    protected CardView(Card card) {
        this.card = card;
        this.viewModelCard = new ViewModelCard(card);
        this.initLayout();
    }

    private void configButtonsListener() {
        u.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                    viewModelCard.moveTo(Mode.PREVIOUS);
            }
        });
        d.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                    viewModelCard.moveTo(Mode.NEXT);
            }
        });
        l.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                    viewModelCard.moveCardToLeft();
            }
        });
        r.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                    viewModelCard.moveCardToRight();
            }
        });
        box.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                    initComfirmationWindow();
            }
        });
    }

    private void configButtonsProperties() {
        this.u.disableProperty().bind(viewModelCard.getCanMoveUp().not());
        this.d.disableProperty().bind(viewModelCard.getCanMoveDown().not());
        this.r.disableProperty().bind(viewModelCard.getCanMoveRight().not());
        this.l.disableProperty().bind(viewModelCard.getGetCanMoveLeft().not());
    }

    private void configMouseClicked() {
        this.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                // consomme l'event de sorte a ce que le parent ne soit pas notifié
                if (mouseEvent.getButton() == MouseButton.SECONDARY) {
                    box.show();
                }
            }
        });

    }

}
