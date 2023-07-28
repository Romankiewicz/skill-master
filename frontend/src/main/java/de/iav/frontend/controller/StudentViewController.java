package de.iav.frontend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import de.iav.frontend.model.Course;
import de.iav.frontend.model.Student;
import de.iav.frontend.security.AuthenticationService;
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

    }

    @FXML
    public void onLogoutButtonClick_switchToLoginView(ActionEvent event) throws IOException {
        SceneSwitchService.getInstance().switchToLoginView(event);
    }

    @FXML
    public void addCourseToStudentCourseList() throws JsonProcessingException {
        studentViewService.addCourseToStudentCourseList(studentViewService
                .getLoginStudent()
                .studentId()
                ,courseList_CB
                        .getSelectionModel()
                        .getSelectedItem()
                        .courseId()
                ,registredCourses_LV);

    }

    @FXML
    public void deleteCourseFromStudentCourseList() {
        studentViewService.deleteCourseFromStudentCourseList(studentViewService
                .getLoginStudent()
                .studentId()
                ,registredCourses_LV
                        .getSelectionModel()
                        .getSelectedItem()
                        .courseId()
                ,registredCourses_LV);
    }

}
