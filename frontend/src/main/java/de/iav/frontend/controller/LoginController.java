package de.iav.frontend.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    @FXML
    private TextField userName_TF;

    @FXML
    private PasswordField password_TF;

    @FXML
    private ComboBox<String> userTypeSelector_CB;

    public void initialize() {

        System.out.println(userTypeSelector_CB.getValue());

        userTypeSelector_CB.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) ->
                        System.out.println(newValue)
        );
    }

    @FXML
    public void onClick_switchToRegisterView(ActionEvent event) throws IOException {

        // load layout of scene
        FXMLLoader loaderRegView = new FXMLLoader(getClass().getResource("/de/iav/frontend/fxml/registration-view.fxml"));

        // set scene object which should display the content in scene2
        Scene sceneRegView = new Scene(loaderRegView.load());

        // set stage which should be shown (newly) on click
        Stage stage = (Stage) (((Node) event.getSource()).getScene().getWindow());

        // set scene2 to stage and show it
        stage.setScene(sceneRegView);
        stage.show();

    }

}
