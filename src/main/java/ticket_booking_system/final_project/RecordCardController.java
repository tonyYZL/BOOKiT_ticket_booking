package ticket_booking_system.final_project;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.SVGPath;
import javafx.util.Duration;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class RecordCardController {
    @FXML
    private Pane recordOuterPane;
    @FXML
    private Pane detailPane;
    @FXML
    private Label AirlineName;
    @FXML
    private Label AirlineNumber;
    @FXML
    private Label Departure;
    @FXML
    private Label DepartureChinese;
    @FXML
    private Label DestinationChinese;
    @FXML
    private Label TimeConsuming;
    @FXML
    private Label arrivalTime;
    @FXML
    private Label departureTime;
    @FXML
    private Label Destination;
    @FXML
    private Label Date;
    @FXML
    private SVGPath triangle;
    @FXML
    private Label AirlineClass;
    @FXML
    private Label CreditCardNumber;
    @FXML
    private Label Name;
    @FXML
    private Label People;
    @FXML
    private Label PhoneNumber;
    @FXML
    private Label Price;
    @FXML
    private Label TotalCost;
    @FXML
    private Label TradeTime;
    @FXML
    private HBox iconPane;
    @FXML
    private Label btnText;

    public static ScrollPane modal;
    public static Pane titlePane;
    public Rectangle rectangle = new Rectangle(984, 208);
//    private int flightId;
    private int transactionId;


    public void initialize() {
        //hide detail pane
        rectangle.setLayoutX(-40);
        recordOuterPane.setClip(rectangle);

    }

    public void passData(ArrayList<String> card, ArrayList<String> detail) {
        // card
//        flightId = Integer.parseInt(card.get(0));
        AirlineName.setText(card.get(1));
        AirlineNumber.setText(card.get(2));
        //load icon
        Loader object = new Loader();
        Pane icon = object.getIcon(card.get(2).substring(0, 2));
        iconPane.getChildren().add(icon);

        DepartureChinese.setText(card.get(3).substring(0, card.get(3).length()-5));
        Departure.setText(card.get(3).substring(card.get(3).length()-4, card.get(3).length()-1));
        DestinationChinese.setText(card.get(4).substring(0, card.get(4).length()-5));
        Destination.setText(card.get(4).substring(card.get(4).length()-4, card.get(4).length()-1));
        Date.setText(PublicController.formatDate(card.get(5)));
        departureTime.setText(card.get(6));
        arrivalTime.setText(card.get(7));
        TimeConsuming.setText(PublicController.formatTime(card.get(8)));
        //detail
        transactionId = Integer.parseInt(detail.get(0));
        Name.setText(detail.get(1));
        CreditCardNumber.setText(detail.get(2).substring(12));
        PhoneNumber.setText(detail.get(3));
        TradeTime.setText(detail.get(4));
        AirlineClass.setText(detail.get(5));
        int price = Integer.parseInt(detail.get(6));
        Price.setText(NumberFormat.getNumberInstance(Locale.US).format(price));
        People.setText(detail.get(7));
        int total = Integer.parseInt(detail.get(8));
        TotalCost.setText(NumberFormat.getNumberInstance(Locale.US).format(total));
    }

    public boolean isOpened = false;
    public void openDetail() {
        if (isOpened) {
            isOpened = false;
            btnText.setText("訂單資訊");
            KeyValue value = new KeyValue(rectangle.heightProperty(), 208, new Interpolator() {
                @Override
                protected double curve(double t) {
                    return (t == 1) ? 1.0 : 1 - Math.pow(2.0, -10 * t);
                }
            });
            KeyValue value2 = new KeyValue(recordOuterPane.prefHeightProperty(), 168, new Interpolator() {
                @Override
                protected double curve(double t) {
                    return (t == 1) ? 1.0 : 1 - Math.pow(2.0, -10 * t);
                }
            });
            KeyValue value3 = new KeyValue(detailPane.opacityProperty(), 0, new Interpolator() {
                @Override
                protected double curve(double t) {
                    return (t == 1) ? 1.0 : 1 - Math.pow(2.0, -10 * t);
                }
            });
            KeyFrame frame = new KeyFrame(Duration.millis(500), value, value2, value3);
            Timeline animation = new Timeline(frame);
            animation.play();
            AnimFunction.arrowDown(triangle);
        }
        else {
            isOpened = true;
            btnText.setText("收起");
            KeyValue value = new KeyValue(rectangle.heightProperty(), 415, new Interpolator() {
                @Override
                protected double curve(double t) {
                    return (t == 1) ? 1.0 : 1 - Math.pow(2.0, -10 * t);
                }
            });
            KeyValue value2 = new KeyValue(recordOuterPane.prefHeightProperty(), 385, new Interpolator() {
                @Override
                protected double curve(double t) {
                    return (t == 1) ? 1.0 : 1 - Math.pow(2.0, -10 * t);
                }
            });
            KeyValue value3 = new KeyValue(detailPane.opacityProperty(), 1, new Interpolator() {
                @Override
                protected double curve(double t) {
                    return (t == 1) ? 1.0 : 1 - Math.pow(2.0, -10 * t);
                }
            });
            KeyFrame frame = new KeyFrame(Duration.millis(500), value, value2, value3);
            Timeline animation = new Timeline(frame);
            animation.play();
            AnimFunction.arrowUp(triangle);
        }
    }

    public void openModal() {
        PublicController.chosenTransactionId = transactionId;
        titlePane.setVisible(true);
        modal.setVisible(true);
        KeyValue value = new KeyValue(titlePane.opacityProperty(),1, new Interpolator() {
            @Override
            protected double curve(double t) {
                return (t == 1) ? 1.0 : 1 - Math.pow(2.0, -10 * t);
            }
        });
        KeyValue value2 = new KeyValue(modal.opacityProperty(), 1, new Interpolator() {
            @Override
            protected double curve(double t) {
                return (t == 1) ? 1.0 : 1 - Math.pow(2.0, -10 * t);
            }
        });

        KeyFrame frame = new KeyFrame(Duration.millis(500), value, value2);
        Timeline animation = new Timeline(frame);
        animation.play();
    }
}
