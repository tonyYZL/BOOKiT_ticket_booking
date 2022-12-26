package ticket_booking_system.final_project;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.SVGPath;
import javafx.util.Duration;

import java.util.ArrayList;

public class FlightInfoCardController {
    @FXML
    public Label airlineName;
    public Label flightNumber;
    public Label departTime;
    public Label arriveTime;
    public Label totalTime;
    public Label price;
    public HBox logoPane;
    public Pane haveDataPane;
    public HBox noDataPane;
    public SVGPath btnArrow;

    public static VBox vbox;

    private int id;

    public void passData(ArrayList<String> data) {
        id = Integer.parseInt(data.get(0));
        airlineName.setText(data.get(1));
        flightNumber.setText(data.get(2));
        departTime.setText(data.get(3));
        arriveTime.setText(data.get(4));
        String time = data.get(5).replaceAll("05", "5");
        if (time.charAt(0) == '0') {
            time = time.substring(1);
        }
        totalTime.setText(time);
        int num = Integer.parseInt(data.get(6));
        price.setText("NT$" + PublicController.formatPrice(num));
        Loader object = new Loader();
        Pane logo = object.getIcon(data.get(2).substring(0, 2));
        logoPane.getChildren().add(logo);
        noDataPane.setVisible(false);
        haveDataPane.setVisible(true);
    }

    public void goToBooking() {
        PublicController.bookId = id;
        PublicController.goToBookingScreen();
    }

    public void hoverBtn() {
        KeyValue rotate = new KeyValue(btnArrow.translateXProperty(), 8, new Interpolator() {
            @Override
            protected double curve(double t) {
                return (t == 1) ? 1.0 : 1 - Math.pow(2.0, -10 * t);
            }
        });
        KeyFrame frame = new KeyFrame(Duration.millis(500), rotate);
        Timeline animation = new Timeline(frame);
        animation.play();
    }
    public void outBtn() {
        KeyValue rotate = new KeyValue(btnArrow.translateXProperty(), 0, new Interpolator() {
            @Override
            protected double curve(double t) {
                return (t == 1) ? 1.0 : 1 - Math.pow(2.0, -10 * t);
            }
        });
        KeyFrame frame = new KeyFrame(Duration.millis(500), rotate);
        Timeline animation = new Timeline(frame);
        animation.play();
    }
}















