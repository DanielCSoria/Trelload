package view;

import javafx.geometry.Insets;
import javafx.scene.control.ListCell;
import model.Card;

public class CardCellView  extends ListCell<Card> {
    protected CardCellView(){
        this.getStyleClass().add("transparent");
        this.setPrefHeight(CardView.CARD_HEIGHT);
    }

    @Override
    protected void updateItem(Card item, boolean empty) {
        super.updateItem(item, empty);
        if (item != null && !empty) { // <== test for null item and empty parameter
            setGraphic(new CardView(item));
            this.setPadding(new Insets(10,10,10,10));
        } else {
            setGraphic(null);
        }
    }
}
