package de.iav.frontend.controller;

import de.iav.frontend.service.SceneSwitchService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.io.IOException;

public class CourseDetailViewController {
    @FXML
    private TextField courseNameTextField;

    @FXML
    private TextField courseContentTextField;

    @FXML
    private ListView studentsOfCourseListView;

    @FXML
    private Button cancelButton;

    @FXML
    private Button submitButton;

    @FXML
    public void onCancelButtonClickSwitchToTeacherScene(ActionEvent event) throws IOException {
        SceneSwitchService.getInstance().switchToLoginView(event);
    }

    @FXML
    public void onSubmitButtonClickSwitchToTeacherSceneAndSaveCourse(ActionEvent event) throws IOException{
        SceneSwitchService.getInstance().switchToLoginView(event);
    }
}
