package de.iav.frontend.controller;

import de.iav.frontend.model.Student;
import de.iav.frontend.service.SceneSwitchService;
import de.iav.frontend.service.StudentViewService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;

import java.io.IOException;
import java.util.List;

import static java.awt.SystemColor.text;


public class StudentViewController {

    @FXML
    private Button logout;
    @FXML
    private Button addCourse;
    @FXML
    private ComboBox<String> courseListe;
    @FXML
    private ListView<Student> listView;

    @FXML
    private final StudentViewService studentViewService = StudentViewService.getInstance();
    @FXML
    private final SceneSwitchService sceneSwitchService = SceneSwitchService.getInstance();


    public void initialize() {
        List<String> allCourses = studentViewService.getAllCourses();
        listView.getItems().add(allCourses);
        listView.getSelectionModel()
                .selectedItemProperty()
                .addListener(
                        (observableValue, course, t1) -> {
                            text.setText(listView.getSelectionModel().getSelectedItems()course());
                        }
                );
    }

    @FXML
    public void onLogoutButtonClick_switchToLoginView(ActionEvent event) throws IOException {
        SceneSwitchService.getInstance().switchToLoginView(event);
    }

    @FXML
    public void addCourseToStudentCourseList(ActionEvent event) throws IOException {

    }
}
