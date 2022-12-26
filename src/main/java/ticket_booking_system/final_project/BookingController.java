package ticket_booking_system.final_project;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BookingController {
    @FXML
    private Pane outerView;
    @FXML
    private VBox vBox;
    @FXML
    private Label number;
    @FXML
    private Label seatClassText;
    @FXML
    private Label unitprice;
    @FXML
    private Label total;


    public Loader object = new Loader();
    public HBox msg1 = object.getPopup("Failed_big", "系統錯誤");
    public HBox msg2 = object.getPopup("Failed_big", "有欄位為空或格式有誤");
    public HBox msg3 = object.getPopup("Success_big", "訂單已成立");



    public void initialize() {
        ChosenFlightInfoController.outerView  = outerView;
        ChosenFlightInfoController.vBox = vBox;
        ChosenFlightInfoController.number = number;
        ChosenFlightInfoController.unitprice = unitprice;
        ChosenFlightInfoController.total = total;
        ChosenFlightInfoController.seatClassText = seatClassText;

        PassengerInfoContentController.vBox = vBox;
        Pane info = object.getComponent("ChosenFlightInfo");
        vBox.getChildren().add(0, info);
    }

    public void checkOrder() {
        if (passengerInfoAllSet() && creditCardInfoAllSet()) {

            String command;

            if (PublicController.buttonOn) {
                Pane card = (Pane) vBox.getChildren().get(vBox.getChildren().size()-2);

                if (!UserData.getHaveCreditCard()) {

                    TextField cardnum1 = (TextField) card.lookup("#cardnum1");
                    TextField cardnum2 = (TextField) card.lookup("#cardnum2");
                    TextField cardnum3 = (TextField) card.lookup("#cardnum3");
                    TextField cardnum4 = (TextField) card.lookup("#cardnum4");
                    String formattedCardNumber = cardnum1.getText() + cardnum2.getText() + cardnum3.getText() + cardnum4.getText();

                    TextField expireDateM = (TextField) card.lookup("#mm1");
                    TextField expireDateY = (TextField) card.lookup("#yy2");
                    String formattedExpireDate = expireDateM.getText() + expireDateY.getText();

                    TextField CVV = (TextField) card.lookup("#CVV3");

                    command = "INSERT INTO creditCardData (userId, cardNumber, expireDate, CVV) VALUES ('" + UserData.getId() + "','" + formattedCardNumber + "','" + formattedExpireDate + "','" + CVV.getText() + "')";
                    try {
                        DatabaseConnection connection = new DatabaseConnection();
                        Connection connectDB = connection.getConnection();
                        Statement statement = null;
                        statement = connectDB.createStatement();
                        int i = statement.executeUpdate(command);
                        if (i > 0) {
                            UserData.setHaveCreditCard(true);
                        }
                        else {
                            AnimFunction.showTopPopup(outerView, msg1);
                        }
                    }catch (SQLException e) {
                        e.printStackTrace();
                        AnimFunction.showTopPopup(outerView, msg1);
                    }
                }
            }
            //send order
            boolean finished = true;
            Pane info = (Pane) vBox.getChildren().get(vBox.getChildren().size()-3);
            Pane card = (Pane) vBox.getChildren().get(vBox.getChildren().size()-2);
            Label cabinClass = (Label) info.lookup("#seatClassText");
            Label price = (Label) info.lookup("#unitprice");
            int priceToInt = Integer.parseInt(price.getText().substring(3).replaceAll(",", ""));
            Label passenger = (Label) info.lookup("#number");
            int passengerToInt = Integer.parseInt(passenger.getText().replaceAll("人", ""));
            Label total = (Label) info.lookup("#total");
            int totalToInt = Integer.parseInt(total.getText().substring(3).replaceAll(",", ""));
            TextField name = (TextField) card.lookup("#name");
            TextField phoneNumber = (TextField) card.lookup("#phonenumber");
            TextField cardNum1 = (TextField) card.lookup("#cardnum1");
            TextField cardNum2 = (TextField) card.lookup("#cardnum2");
            TextField cardNum3 = (TextField) card.lookup("#cardnum3");
            TextField cardNum4 = (TextField) card.lookup("#cardnum4");
            String cardNumber = cardNum1.getText() + cardNum2.getText() + cardNum3.getText() + cardNum4.getText();


            command = "INSERT INTO orderData (userId, name, cardNumber, phoneNumber, id, date, cabinClass, price, quantity, totalPrice) VALUES (" + UserData.getId() + ",'" + name.getText() + "','" + cardNumber + "','" + phoneNumber.getText() + "','" + PublicController.bookId + "','" + PublicController.bookDate + "','" + cabinClass.getText() + "'," + priceToInt + "," + passengerToInt + "," + totalToInt + ")";
            try {
                DatabaseConnection connection = new DatabaseConnection();
                Connection connectDB = connection.getConnection();
                Statement statement = null;
                statement = connectDB.createStatement();
                int i = statement.executeUpdate(command);
                if (i > 0) {
                    AnimFunction.showTopPopup(outerView, msg3);
                }
                else {
                    AnimFunction.showTopPopup(outerView, msg1);
                    finished = false;
                }
            }catch (SQLException e) {
                e.printStackTrace();
                AnimFunction.showTopPopup(outerView, msg1);
                finished = false;
            }
            int transactionId = -1;
            command = "SELECT * FROM `orderData` ORDER BY `transactionId` DESC";
            try {
                DatabaseConnection connection = new DatabaseConnection();
                Connection connectDB = connection.getConnection();
                Statement statement = null;
                ResultSet resultSet = null;
                statement = connectDB.createStatement();
                resultSet = statement.executeQuery(command);
                resultSet.next();
                transactionId = resultSet.getInt("transactionId");
            }catch (SQLException e) {
                e.printStackTrace();
                AnimFunction.showTopPopup(outerView, msg1);
                finished = false;
            }

            for (int i = 2; i < 2 + PublicController.numberOfPassenger; i++) {
                Pane passengerCard = (Pane) vBox.getChildren().get(i);
                Pane inner = (Pane) passengerCard.getChildren().get(0);
                Pane genderSelector = (Pane) inner.getChildren().get(3);
                TextField lastname = (TextField) inner.getChildren().get(1);
                TextField firstname = (TextField) inner.getChildren().get(2);
                Label gender = (Label) genderSelector.getChildren().get(0);
                command = "INSERT INTO passengerData (transactionId, lastname, firstname, gender) VALUES (" + transactionId + ",'" + lastname.getText() + "','" + firstname.getText() + "','" + gender.getText() + "')";
                try {
                    DatabaseConnection connection = new DatabaseConnection();
                    Connection connectDB = connection.getConnection();
                    Statement statement = null;
                    statement = connectDB.createStatement();
                    int j = statement.executeUpdate(command);
                    if (j <= 0) {
                        AnimFunction.showTopPopup(outerView, msg1);
                        finished = false;
                    }
                }catch (SQLException e) {
                    e.printStackTrace();
                    AnimFunction.showTopPopup(outerView, msg1);
                    finished = false;
                }
            }
            if (finished) {
                PublicController.buttonOn = false;
                PublicController.orderCreated = true;
                PublicController.goToRecordScreen();
            }
        }
        else {
//            System.out.println("EMPTY FIELD");
            AnimFunction.showTopPopup(outerView, msg2);
        }
    }

    public boolean passengerInfoAllSet() {
        int start = 2;
        for (int i = start; i < 2 + PublicController.numberOfPassenger; i++) {
            Pane passenger = (Pane) vBox.getChildren().get(i);
            Pane inner = (Pane) passenger.getChildren().get(0);
            Pane genderSelector = (Pane) inner.getChildren().get(3);
            TextField lastname = (TextField) inner.getChildren().get(1);
            TextField firstname = (TextField) inner.getChildren().get(2);
            Label gender = (Label) genderSelector.getChildren().get(0);
            if (!Validation.isUpperAlpha(firstname.getText()) || !Validation.isUpperAlpha(lastname.getText()) || gender.getText().equals("性別（必填）")) {
                return false;
            }
        }
        return true;
    }
    public boolean creditCardInfoAllSet() {
        Pane card = (Pane) vBox.getChildren().get(vBox.getChildren().size()-2);
        TextField name = (TextField) card.lookup("#name");
        if (!Validation.isChinese(name.getText())) {
            return false;
        }
        TextField phoneNumber = (TextField) card.lookup("#phonenumber");
        if (!Validation.isValidPhoneNumber(phoneNumber.getText())) {
            return false;
        }
        TextField cardnum1 = (TextField) card.lookup("#cardnum1");
        if (cardnum1.getText().length() != 4) {
            return false;
        }
        TextField cardnum2 = (TextField) card.lookup("#cardnum2");
        if (cardnum2.getText().length() != 4) {
            return false;
        }
        TextField cardnum3 = (TextField) card.lookup("#cardnum3");
        if (cardnum3.getText().length() != 4) {
            return false;
        }
        TextField cardnum4 = (TextField) card.lookup("#cardnum4");
        if (cardnum4.getText().length() != 4) {
            return false;
        }
        TextField expireDateM = (TextField) card.lookup("#mm1");
        if (Integer.parseInt(expireDateM.getText()) > 12) {
            return false;
        }
        TextField expireDateY = (TextField) card.lookup("#yy2");
        if (Integer.parseInt(expireDateY.getText()) < 22 || Integer.parseInt(expireDateY.getText()) > 27) {
            return false;
        }
        TextField CVV = (TextField) card.lookup("#CVV3");
        if (CVV.getText().length() != 3) {
            return false;
        }
        TextField email = (TextField) card.lookup("#mail");
        return Validation.isEmail(email.getText());
    }
}
