module com.nelly.doctorpatientrelationshipmodel_frontend {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;

    opens com.nelly.doctorpatientrelationshipmodel_frontend to javafx.fxml;
    opens com.nelly.doctorpatientrelationshipmodel_frontend.controllers to javafx.fxml;

    exports com.nelly.doctorpatientrelationshipmodel_frontend;
    exports com.nelly.doctorpatientrelationshipmodel_frontend.controllers;
    exports com.nelly.doctorpatientrelationshipmodel_frontend.utils;
    exports com.nelly.doctorpatientrelationshipmodel_frontend.models;
}
