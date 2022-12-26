package ticket_booking_system.final_project;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class RecentContentController {
    @FXML
    public HBox noRecord;
    public Pane recordInfo;

    @FXML
    public HBox logo;
    public ArrayList<Label> infoList;
    public Label airlineName;
    public Label departTime;
    public Label from;
    public Label totalTime;
    public Label arriveTime;
    public Label to;
    public Label date;
    public Text goToSearchBtn;

    public String logoName;


    public void writeData(ArrayList<String> data) {
        logoName = data.get(0).substring(0, 2);
        Loader object = new Loader();
        Pane icon = object.getIcon(logoName);
        logo.getChildren().add(icon);

        airlineName.setText(data.get(1));
        from.setText(data.get(2));
        to.setText(data.get(3));
        departTime.setText(data.get(4));
        arriveTime.setText(data.get(5));
        totalTime.setText(data.get(6));
        date.setText(PublicController.formatDate(data.get(7)));

        noRecord.setVisible(false);
        recordInfo.setVisible(true);
    }

    public void mouseOver() {

    }
    public void mouseOut() {

    }

    public void goToSearchScreen() {
        PublicController.goToSearchScreen();
    }
}
