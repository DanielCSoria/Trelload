package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

class ImageButton extends Button {

    private final String STYLE_NORMAL = "-fx-background-color: transparent; -fx-padding: 2, 2, 2, 2;";
    private final String STYLE_PRESSED = "-fx-background-color: transparent; -fx-padding: 3 1 1 3;";

    protected ImageButton(String imagePath, double h, double w) {

        ImageView image = new ImageView(new Image(imagePath));
        image.setFitHeight(h);
        image.setFitHeight(w);
        image.setPreserveRatio(true);
        setGraphic(image);
        setStyle(STYLE_NORMAL);
        setOnMousePressed(event -> setStyle(STYLE_PRESSED));
        setOnMouseReleased(event -> setStyle(STYLE_NORMAL));
        beautify();
    }

    public void beautify() {
        BorderPane.setAlignment(this, Pos.CENTER);
        BorderPane.setMargin(this, new Insets(2));
        this.getStylesheets().add("style.css");
        this.getStyleClass().add("myButton");
    }


}
