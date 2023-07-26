module de.iav.frontend {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.databind;
    requires java.net.http;

    opens de.iav.frontend;
    exports de.iav.frontend;

    opens de.iav.frontend.model;
    exports de.iav.frontend.model;

    opens de.iav.frontend.controller;
    exports de.iav.frontend.controller;

    opens de.iav.frontend.service;
    exports de.iav.frontend.service;

    opens de.iav.frontend.security;
    exports de.iav.frontend.security;
}