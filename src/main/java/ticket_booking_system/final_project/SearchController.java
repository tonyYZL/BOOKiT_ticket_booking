package ticket_booking_system.final_project;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class SearchController {
    @FXML
    private VBox vbox;

    @FXML
    public Pane outerView;


    // initialize
    public void initialize() throws IOException {
        SearchBarController.outerView = outerView;
        SearchBarController.vbox = vbox;
        FlightInfoCardController.vbox = vbox;

        Loader object = new Loader();
        Pane searchBar = object.getComponent("SearchBar");
        vbox.getChildren().add(searchBar);
        Pane title = object.getComponent("FlightInfoIcon");
        vbox.getChildren().add(title);
        title.setVisible(false);
    }
}
