package ticket_booking_system.final_project;

import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class PopupController {
    @FXML
    public Text message;
    public String str;

    public PopupController(String str) {
        this.str = str;
    }

    public void initialize() {
        message.setText(str);
    }
}
