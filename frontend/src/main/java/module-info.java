module de.iav.frontend {
    requires javafx.controls;
    requires javafx.fxml;
            
                            
    opens de.iav.frontend to javafx.fxml;
    exports de.iav.frontend;

    opens de.iav.frontend.controller to javafx.fxml;
    exports de.iav.frontend.controller;
}