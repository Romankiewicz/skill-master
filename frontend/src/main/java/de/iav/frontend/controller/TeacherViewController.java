package de.iav.frontend.controller;

import de.iav.frontend.service.SceneSwitchService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

import java.io.IOException;

public class TeacherViewController {
    @FXML
    private Button logoutButton;

    @FXML
    private Button newCourseButton;

    @FXML
    private ListView coursesOfTeacherListView;

    @FXML
    public void onLogoutButtonClickSwitchToLoginScene(ActionEvent event) throws IOException {
        SceneSwitchService.getInstance().switchToLoginView(event);
    }

    @FXML
    public void onNewCourseButtonClickSwitchToCourseDetailsScene(ActionEvent event) throws IOException{
        SceneSwitchService.getInstance().switchToLoginView(event);
    }
}
