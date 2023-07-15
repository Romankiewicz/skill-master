module de.iav.frontend {
    requires javafx.controls;
    requires javafx.fxml;
            
                            
    opens de.iav.frontend to javafx.fxml;
    exports de.iav.frontend;
}