package ticket_booking_system.final_project;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ChosenFlightInfoController {
    @FXML
    private Label departure;
    @FXML
    private Label destination;
    @FXML
    private Label date;
    @FXML
    private Label flight;
    @FXML
    private Label flightNo;
    @FXML
    private Label departtime;
    @FXML
    private Label arrivetime;
    @FXML
    private Label costtime;
    @FXML
    private Label departure_abbr;
    @FXML
    private Label destination_abbr;
    @FXML
    private Label flightLevel;
    @FXML
    private SVGPath ICON;
    @FXML
    private Pane leveloption;
    @FXML
    private Pane quantity;
    @FXML
    private Label quantity_label;
    @FXML
    private HBox iconPane;
    @FXML
    private Label firstClassBtn;
    @FXML
    private Label businessClassBtn;

    public static VBox vBox;
    public static Label number; //total passenger
    static public Pane pane;
    static public Label label;
    static public Label total;
    public static Pane outerView;
    public static Label unitprice;
    public static Label seatClassText;

    public static int economyClassPrice = 0;
    public static int businessClassPrice = 0;
    public static int firstClassPrice = 0;

    //state variables
    public boolean passengerListLoaded = false;
    /*=====*/

    //loader
    private final Loader object = new Loader();

    //popup message
    public HBox msg1 = object.getPopup("Failed_big", "系統錯誤");


    public void initialize() {
        passengerListLoaded = false;
        PublicController.numberOfPassenger = 1;
        DatabaseConnection connection = new DatabaseConnection();
        Connection connectDB = connection.getConnection();
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            String command = "SELECT * FROM `flightData` WHERE `id`=" + PublicController.bookId;
            statement = connectDB.createStatement();
            resultSet = statement.executeQuery(command);
            resultSet.next();
            String from = resultSet.getString("from");
            String to = resultSet.getString("to");
            departure.setText(from.substring(0, from.length()-5));
            destination.setText(to.substring(0, to.length()-5));
            String bookDate = PublicController.formatDate(PublicController.bookDate);
            date.setText(bookDate);
            Pane icon = object.getIcon(resultSet.getString("flightNo").substring(0, 2));
            iconPane.getChildren().add(icon);
            flight.setText(resultSet.getString("airline"));
            flightNo.setText(resultSet.getString("flightNo"));
            departtime.setText(resultSet.getString("departure"));
            arrivetime.setText(resultSet.getString("arrive"));
            departure_abbr.setText(from.substring(from.length()-4, from.length()-1));
            destination_abbr.setText(to.substring(to.length()-4, to.length()-1));
            String time = resultSet.getString("time");
            time = time.replaceAll("05", "5");
            if (time.charAt(0) == '0') {
                time = time.substring(1);
            }
            costtime.setText(time);
            economyClassPrice = resultSet.getInt("economyClassPrice");
            businessClassPrice = resultSet.getInt("businessClassPrice");
            firstClassPrice = resultSet.getInt("firstClassPrice");
        } catch (SQLException e) {
            AnimFunction.showTopPopup(outerView, msg1);
            e.printStackTrace();
        } finally {
            try {
                connectDB.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (firstClassPrice == 0) {
            firstClassBtn.setDisable(true);
        }
        if (businessClassPrice == 0) {
            businessClassBtn.setDisable(true);
        }
    }

    @FXML
    void OnPaneClick(){
        vBox.getChildren().get(0).setViewOrder(-1);
        if (leveloption.isVisible()) {
            leveloption.setVisible(false);
            AnimFunction.arrowDown(ICON);
        }
        else {
            leveloption.setVisible(true);
            AnimFunction.arrowUp(ICON);
        }
    }
    @FXML
    void firstclassClick() throws IOException {
        unitprice.setText("NT$" + PublicController.formatPrice(firstClassPrice));
        changeTotal(firstClassPrice);
        seatClassText.setText("頭等艙");
        leveloption.setVisible(false);
        AnimFunction.arrowDown(ICON);
        flightLevel.setText("頭等艙");
        flightLevel.setTextFill(Color.BLACK);
        if (!passengerListLoaded) {
            passengerListLoaded = true;
            quantity.setVisible(true);
            vBox.lookup("#pricelabel").setVisible(true);
            vBox.lookup("#finishbutton").setVisible(true);
            Pane title = object.getComponent("PassengerInfo_title");
            vBox.getChildren().add(1, title);
            FXMLLoader loader1 = new FXMLLoader(Main.class.getResource("components/PassengerInfo_content.fxml"));
            PassengerInfoContentController.topPaneId = 0;
            Pane pane1 = loader1.load();
            PassengerInfoContentController PC = loader1.getController();
            PC.changepassengerNo(1);
            vBox.getChildren().add(2,pane1);
            Pane payment = object.getComponent("Payment");
            vBox.getChildren().add(vBox.getChildren().size()-1,payment);
        }
    }
    @FXML
    void businessclassClick() throws IOException {
        unitprice.setText("NT$" + PublicController.formatPrice(businessClassPrice));
        changeTotal(businessClassPrice);
        seatClassText.setText("商務艙");
        leveloption.setVisible(false);
        AnimFunction.arrowDown(ICON);
        flightLevel.setText("商務艙");
        flightLevel.setTextFill(Color.BLACK);
        if (!passengerListLoaded) {
            passengerListLoaded = true;
            quantity.setVisible(true);
            vBox.lookup("#pricelabel").setVisible(true);
            vBox.lookup("#finishbutton").setVisible(true);
            Pane title = object.getComponent("PassengerInfo_title");
            vBox.getChildren().add(1, title);
            FXMLLoader loader1 = new FXMLLoader(Main.class.getResource("components/PassengerInfo_content.fxml"));
            Pane pane1 = loader1.load();
            PassengerInfoContentController PC = loader1.getController();
            PC.changepassengerNo(1);
            vBox.getChildren().add(2,pane1);
            Pane payment = object.getComponent("Payment");
            vBox.getChildren().add(vBox.getChildren().size()-1,payment);
        }
    }
    @FXML
    void economicclassClick() throws IOException {
        unitprice.setText("NT$" + PublicController.formatPrice(economyClassPrice));
        changeTotal(economyClassPrice);
        seatClassText.setText("經濟艙");
        leveloption.setVisible(false);
        AnimFunction.arrowDown(ICON);
        flightLevel.setText("經濟艙");
        flightLevel.setTextFill(Color.BLACK);
        if (!passengerListLoaded) {
            passengerListLoaded = true;
            quantity.setVisible(true);
            vBox.lookup("#pricelabel").setVisible(true);
            vBox.lookup("#finishbutton").setVisible(true);
            Pane title = object.getComponent("PassengerInfo_title");
            vBox.getChildren().add(1, title);
            FXMLLoader loader1 = new FXMLLoader(Main.class.getResource("components/PassengerInfo_content.fxml"));
            Pane pane1 = loader1.load();
            PassengerInfoContentController PC = loader1.getController();
            PC.changepassengerNo(1);
            vBox.getChildren().add(2,pane1);
            Pane payment = object.getComponent("Payment");
            vBox.getChildren().add(vBox.getChildren().size()-1,payment);
        }
    }
    @FXML
    void OnAddClick() throws IOException {
        if(PublicController.numberOfPassenger < 10) {
            PublicController.numberOfPassenger++;
            number.setText(PublicController.numberOfPassenger + "人");
            if (flightLevel.getText().equals("頭等艙")) {
                changeTotal(firstClassPrice);
            }
            else if (flightLevel.getText().equals("商務艙")) {
                changeTotal(businessClassPrice);
            }
            else {
                changeTotal(economyClassPrice);
            }
            quantity_label.setText(String.valueOf(PublicController.numberOfPassenger));
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("components/PassengerInfo_content.fxml"));
            Pane content = loader.load();
            PassengerInfoContentController tempcontroller = loader.getController();
            tempcontroller.changepassengerNo(PublicController.numberOfPassenger);
            vBox.getChildren().add(1+PublicController.numberOfPassenger, content);
        }
    }
    @FXML
    void OnMinusClick(){
        if(PublicController.numberOfPassenger > 1) {
            PublicController.numberOfPassenger--;
            number.setText(PublicController.numberOfPassenger + "人");
            if (flightLevel.getText().equals("頭等艙")) {
                changeTotal(firstClassPrice);
            }
            else if (flightLevel.getText().equals("商務艙")) {
                changeTotal(businessClassPrice);
            }
            else {
                changeTotal(economyClassPrice);
            }
            quantity_label.setText(String.valueOf(PublicController.numberOfPassenger));
            vBox.getChildren().remove(PublicController.numberOfPassenger+2);
        }
    }
    public void changeTotal(int price){
        total.setText("NT$" + PublicController.formatPrice(price * PublicController.numberOfPassenger));
    }
}
