package de.iav.frontend.controller;
import de.iav.frontend.model.CourseDTO_RequestBody;
import de.iav.frontend.model.Student;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;


public class StudentViewController {

    @FXML
    private Button logout;
    @FXML
    private Button addCourse;
    @FXML
    private ComboBox<CourseDTO_RequestBody> courseListe;
    @FXML
    private ListView<Student>courseListOfStudent;




}
