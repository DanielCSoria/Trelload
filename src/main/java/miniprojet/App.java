package miniprojet;

import ctrl.Ctrl;
import javafx.application.Application;
import javafx.stage.Stage;
import model.School;
import view.View;

/**
 * JavaFX App
 */
public class App extends Application {

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) {
        School school = new School();
        Ctrl ctrl = new Ctrl(school);
        View view = new View(primaryStage, ctrl);
        school.addObserver(view);
        school.notifyObservers(School.NotificationKind.INIT);
        primaryStage.show();

    }



}