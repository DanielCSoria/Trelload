package view;

import javafx.scene.control.ListCell;
import model.Column;

public class ColumnCellView extends ListCell<Column> {
    protected ColumnCellView() {
        this.getStyleClass().add("transparent");
    }

    @Override
    protected void updateItem(Column item, boolean empty) {
        super.updateItem(item, empty);
        if (item != null && !empty) { // <== test for null item and empty parameter
            ColumnView v = new ColumnView(item);
            setGraphic(v);
        } else {
            setGraphic(null);
        }
    }

}
