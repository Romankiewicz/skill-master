package de.iav.frontend.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {

    @FXML
    private TextField userName_TF;

    @FXML
    private PasswordField password_TF;

    @FXML
    private ComboBox userTypeSelector_CB;

    public void initialize() {

        System.out.println(userTypeSelector_CB.getValue());

        userTypeSelector_CB.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) ->
                {
                    System.out.println(newValue);
                }
        );
    }

}
