package ticket_booking_system.final_project;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class FlightInfoTitleController {
    @FXML
    public Label departureText;
    public Label arriveText;
    public Pane titlePane;

    public void initialize() {
        SearchBarController.departureText = departureText;
        SearchBarController.arriveText = arriveText;
        SearchBarController.titlePane = titlePane;
    }
}
