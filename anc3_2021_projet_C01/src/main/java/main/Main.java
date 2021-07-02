package main;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.dao.DbSetup;
import mvvm.ViewModelBoard;
import view.BoardView;


public class Main extends Application {
    private static Stage stage;
    public static Stage getStage() { return stage; }

    @Override
    public void start(Stage primaryStage) throws Exception {
        DbSetup.setupDatabase();
        stage = primaryStage;
        ViewModelBoard vm = new ViewModelBoard();
        BoardView view = new BoardView(vm);
        HBox root = new HBox(view);
        HBox.setHgrow(view, Priority.ALWAYS);


        Scene s = new Scene(root);
        stage.setTitle("TrelloAnc3");
        stage.setMinHeight(700);
        stage.setWidth(1200);
        s.getStylesheets().add("style.css");
        s.setFill(Color.TRANSPARENT);
        primaryStage.setScene(s);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}


