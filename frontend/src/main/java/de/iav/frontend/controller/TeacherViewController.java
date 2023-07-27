package de.iav.frontend.controller;

import de.iav.frontend.model.Course;
import de.iav.frontend.model.Teacher;
import de.iav.frontend.service.SceneSwitchService;
import de.iav.frontend.service.TeacherViewService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

import java.io.IOException;
import java.util.List;

public class TeacherViewController {
    @FXML
    private Button logoutButton;

    @FXML
    private Button newCourseButton;



    @FXML
    private ListView<Course> coursesOfTeacherListView;

    @FXML
    private final TeacherViewService teacherViewService = TeacherViewService.getInstance();

    public void initialize() {
        Teacher loginTeacher = TeacherViewService.getInstance().getLoginTeacher();
        List<Course> allCourses = loginTeacher.courses();
        coursesOfTeacherListView.getItems().addAll(allCourses);
    }



    @FXML
    public void onLogoutButtonClickSwitchToLoginScene(ActionEvent event) throws IOException {
        SceneSwitchService.getInstance().switchToLoginView(event);
    }

    @FXML
    public void onNewCourseButtonClickSwitchToCourseDetailsScene(ActionEvent event) throws IOException{
        SceneSwitchService.getInstance().switchToLoginView(event);
    }

    @FXML
    public void deleteSelectedCourse(){
        teacherViewService.deleteCourse(coursesOfTeacherListView
                .getSelectionModel()
                .getSelectedItem()
                .courseId()
        ,coursesOfTeacherListView);
    }

}
