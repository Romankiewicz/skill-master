package de.iav.frontend.controller;

import de.iav.frontend.model.Course;
import de.iav.frontend.model.Student;
import de.iav.frontend.security.AppUserRole;
import de.iav.frontend.service.SceneSwitchService;
import de.iav.frontend.service.StudentViewService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;

import java.io.IOException;
import java.util.List;


public class StudentViewController {

    @FXML
    private Button logout;
    @FXML
    private Button addCourse;
    @FXML
    private ListView<Course> registredCourses_LV;

    @FXML
    private ComboBox<Course> courseList_CB;

    @FXML
    private final StudentViewService studentViewService = StudentViewService.getInstance();
    @FXML
    private final SceneSwitchService sceneSwitchService = SceneSwitchService.getInstance();


    public void initialize() {

        Student loginStudent = StudentViewService.getInstance().getLoginStudent();
        List<Course> allCoursesOfStudent = loginStudent.courses();
        registredCourses_LV.getItems().addAll(allCoursesOfStudent);

        List<Course> allCourses = StudentViewService.getInstance().getAllCourses();
        Course chosenCourse = null;
        courseList_CB.getItems().addAll(allCourses);

        /*
        courseList_CB.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {

                    chosenCourse

                }
        );*/

    }

    @FXML
    public void onLogoutButtonClick_switchToLoginView(ActionEvent event) throws IOException {
        SceneSwitchService.getInstance().switchToLoginView(event);
    }

    @FXML
    public void addCourseToStudentCourseList(ActionEvent event) throws IOException {

    }
}
