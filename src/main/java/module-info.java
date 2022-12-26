module ticket_booking_system.final_project {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires kernel;
    requires layout;

    opens ticket_booking_system.final_project to javafx.fxml;
    exports ticket_booking_system.final_project;
}