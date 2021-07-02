package view;


import ctrl.Ctrl;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Course;
import model.School;
import model.Student;

import java.util.Observable;
import java.util.Observer;

public class View implements Observer {

    // Utilitaires graphiques
    private static final double SPACING = 10;

    private static void spacing(Pane pane) {
        if (pane instanceof HBox) {
            HBox b = (HBox) pane;
            b.setSpacing(SPACING);
        } else if (pane instanceof VBox) {
            VBox b = (VBox) pane;
            b.setSpacing(SPACING);
        }
    }

    private static void padding(Pane pane) {
        pane.setPadding(new Insets(SPACING));
    }

    private static void paddingAndSpacing(Pane pane) {
        padding(pane);
        spacing(pane);
    }

    /****************************************************************/

    public View(Stage primaryStage, Ctrl ctrl) {
        makeComponentsHierarchy();
        configSelectionHandlers();
        configActions();
        configTextField();
        this.ctrl = ctrl;
        Scene scene = new Scene(hbMainPanel);
        primaryStage.setTitle("Gestion des inscriptions");
        primaryStage.setScene(scene);
    }

    private final Ctrl ctrl;

    private final HBox hbMainPanel = new HBox(),
            hbNewSubscription = new HBox(),
            hbNewStudent = new HBox();
    private final VBox vbCourses = new VBox(),
            vbStudents = new VBox(),
            vbSubscriptions = new VBox();
    private final BorderPane bpSubscribe = new BorderPane();
    private final ListView<Course> lvCourses = new ListView<>();
    private final ListView<Student> lvStudents = new ListView<>();

    private final Label lbCourses = new Label("Cours"),
            lbStudents = new Label("Etudiants"),
            lbNewSubscription = new Label("Nouvelle inscription");
    private final ComboBox<Student> cbStudents = new ComboBox<>();
    private final Button btSubscribe = new Button("Inscrire"),
            btNewStudent = new Button("Ajouter et inscrire"),
            btUnsubscribe = new Button("Désinscrire étudiant");
    private final TextField tfNewStudent = new TextField();

    private void makeComponentsHierarchy() {
        hbMainPanel.getChildren().addAll(vbCourses, vbStudents, bpSubscribe);
        vbCourses.getChildren().addAll(lbCourses, lvCourses);
        vbStudents.getChildren().addAll(lbStudents, lvStudents);
        hbNewSubscription.getChildren().addAll(cbStudents, btSubscribe);
        hbNewStudent.getChildren().addAll(tfNewStudent, btNewStudent);
        vbSubscriptions.getChildren().addAll(lbNewSubscription, hbNewSubscription, hbNewStudent);
        bpSubscribe.setTop(vbSubscriptions);
        bpSubscribe.setBottom(btUnsubscribe);
        componentsDecoration();
    }

    private void componentsDecoration() {
        paddingAndSpacing(hbMainPanel);
        paddingAndSpacing(vbCourses);
        paddingAndSpacing(vbStudents);
        padding(bpSubscribe);
        spacing(vbSubscriptions);
        spacing(hbNewSubscription);
        spacing(hbNewStudent);
    }

    private void configSelectionHandlers() {
        lvCourses.getSelectionModel().selectedIndexProperty()
                .addListener(o -> updateCourseStudents());
        lvStudents.getSelectionModel().selectedIndexProperty()
                .addListener(o -> btUnsubscribe.setDisable(getSelectedCourseStudent() == null));
        cbStudents.getSelectionModel().selectedIndexProperty()
                .addListener(o -> configDisabledButtons());
    }

    private void updateCourseStudents() {
        lvStudentsUpdate();
        configDisabledButtons();
    }

    private void configActions() {
        btSubscribe.setOnAction(e ->
                ctrl.addStudentToCourse(getSelectedStudent(), getSelectedCourse()));
        btUnsubscribe.setOnAction(e ->
                ctrl.removeStudentFromCourse(getSelectedCourseStudent(), getSelectedCourse()));
        btNewStudent.setOnAction(e ->
                ctrl.createStudentAndAddToCourse(tfNewStudent.getText(), getSelectedCourse()));
    }

    @Override
    public void update(Observable o, Object arg) {
        School school = (School) o;
        School.NotificationKind typeNotif = (School.NotificationKind) arg;
        switch (typeNotif) {
            case INIT:
                makeCbStudents(school);
                lvCourses.getItems().addAll(school.getCourses());
                break;
            case STUDENT_ADDED:
                makeCbStudents(school);
                tfNewStudent.clear();
                break;
            case STUDENT_ADDED_TO_COURSE:
            case STUDENT_REMOVED_FROM_COURSE:
                lvStudentsUpdate();
                break;
        }
        configDisabledButtons();
    }

    private void makeCbStudents(School school) {
        cbStudents.getItems().clear();
        cbStudents.getItems().addAll(school.getStudents());
    }

    private void lvStudentsUpdate() {
        lvStudents.getItems().clear();
        Course c = getSelectedCourse();
        if (c != null) {
            lvStudents.getItems().addAll(c.getStudents());
        }
    }

    private void configTextField() {
        tfNewStudent.setOnKeyTyped(e ->
                btNewStudent.setDisable(ctrl.btNewStudentHasToBeDisabled(tfNewStudent.getText(), getSelectedCourse())));
        tfNewStudent.setOnAction(ae ->
                ctrl.createStudentAndAddToCourse(tfNewStudent.getText(), getSelectedCourse()));
    }

    private void configDisabledButtons() {
        btSubscribe.setDisable(ctrl.btSubscribeHasToBeDisabled(getSelectedStudent(), getSelectedCourse()));
        btUnsubscribe.setDisable(getSelectedCourseStudent() == null);
        btNewStudent.setDisable(ctrl.btNewStudentHasToBeDisabled(tfNewStudent.getText(), getSelectedCourse()));
    }

    private Course getSelectedCourse() {
        return lvCourses.getSelectionModel().getSelectedItem();
    }

    private Student getSelectedCourseStudent() {
        return lvStudents.getSelectionModel().getSelectedItem();
    }

    private Student getSelectedStudent() {
        return cbStudents.getSelectionModel().getSelectedItem();
    }

}
