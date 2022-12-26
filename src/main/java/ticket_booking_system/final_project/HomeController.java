package ticket_booking_system.final_project;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class HomeController {

    @FXML
    public Pane outerView;
    public VBox homeScreen;
    public HBox allRecordBtn;
    public Pane modal;
    public ArrayList<TextField> cardNumberList;
    public ArrayList<TextField> expireDateList;
    public TextField securityCode;

    public int currentPosition = 0;
    CreditCardController creditCardController = new CreditCardController();


    public void initialize() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDateTime now = LocalDateTime.now();
        String date = dtf.format(now);

        creditCardController.setModal(modal);
        creditCardController.setHomeScreen(homeScreen);

        Loader object = new Loader();
        Pane creditCardPane = object.getComponent("CreditCard");
        Pane recentTitlePane = object.getComponent("Recent_title");
        homeScreen.getChildren().add(homeScreen.getChildren().size()-1, creditCardPane);
        homeScreen.getChildren().add(homeScreen.getChildren().size()-1, recentTitlePane);

        DatabaseConnection connection = new DatabaseConnection();
        Connection connectDB = connection.getConnection();
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            String command = "SELECT flightData.*, orderData.date FROM orderData INNER JOIN flightData ON orderData.id = flightData.id WHERE orderData.userId =" + UserData.getId() + " AND orderData.date >= '" + date + "' ORDER BY orderData.date ASC LIMIT 3";
            statement = connectDB.createStatement();
            resultSet = statement.executeQuery(command);

            if (resultSet.next()) {
                allRecordBtn.setVisible(true);
                do {
                    //read data from database

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("components/Recent_content.fxml"));
                    ArrayList<String> info = new ArrayList<>();
                    info.add(resultSet.getString("flightNo"));
                    info.add(resultSet.getString("airline"));
                    info.add(resultSet.getString("from"));
                    info.add(resultSet.getString("to"));
                    info.add(resultSet.getString("departure"));
                    info.add(resultSet.getString("arrive"));
                    info.add(resultSet.getString("time"));
                    info.add(resultSet.getString("date"));

                    Pane recentContentPane = loader.load();
                    RecentContentController controller = loader.getController();
                    controller.writeData(info);
                    homeScreen.getChildren().add(homeScreen.getChildren().size()-1, recentContentPane);
                }while (resultSet.next());
            }
            else {
                allRecordBtn.setVisible(false);
                Pane recentContentPane = object.getComponent("Recent_content");
                homeScreen.getChildren().add(homeScreen.getChildren().size()-1, recentContentPane);
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            HBox msg = object.getPopup("Failed_big", "無法載入資料");
            showPopup(msg);
        } finally {
            try { connectDB.close();} catch (Exception e) {/**/}
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {/**/}
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (Exception e) {/**/}
        }
    }

    public void closeModal() {
        modal.setVisible(false);
    }

    public void addCardCheck() {
        boolean wrongFormat = false;
        for (TextField textField: cardNumberList) {
            if (textField.getText().length() != 4) {
                wrongFormat = true;
                break;
            }
        }
        for (TextField textField: expireDateList) {
            if (textField.getText().length() != 2) {
                wrongFormat = true;
                break;
            }
        }
        if (securityCode.getText().length() != 3)
            wrongFormat = true;

        Loader object = new Loader();
        HBox msg;
        if (wrongFormat) {
            msg = object.getPopup("Failed_big", "格式有誤");
        }
        else {
            DatabaseConnection connection = new DatabaseConnection();
            Connection connectDB = connection.getConnection();
            Statement statement = null;

            StringBuilder stringBuilder= new StringBuilder();
            for (TextField textField: cardNumberList) {
                stringBuilder.append(textField.getText());
            }
            String formattedCardNumber = stringBuilder.toString();
            stringBuilder.delete(0, stringBuilder.length());
            for (TextField textField: expireDateList) {
                stringBuilder.append(textField.getText());
            }
            String formattedExpireDate = stringBuilder.toString();
            String command = "INSERT INTO creditCardData (userId, cardNumber, expireDate, CVV) VALUES ('" + UserData.getId() + "','" + formattedCardNumber + "','" + formattedExpireDate + "','" + securityCode.getText() + "')";
            try {
                statement = connectDB.createStatement();
                int i = statement.executeUpdate(command);
                if (i > 0) {
                    msg = object.getPopup("Success_big", "已新增");
                    UserData.setHaveCreditCard(true);
                    Pane creditCardPane = object.getComponent("CreditCard");
                    homeScreen.getChildren().remove(0);
                    homeScreen.getChildren().add(0, creditCardPane);
                    closeModal();
                }
                else {
                    msg = object.getPopup("Failed_big", "系統錯誤");
                }
            }catch (SQLException e) {
                e.printStackTrace();
                msg = object.getPopup("Failed_big", "系統錯誤");
            }finally {
                try { connectDB.close();} catch (SQLException e) {/**/}
                try {
                    if (statement != null) {
                        statement.close();
                    }
                } catch (SQLException e) {/**/}
            }
        }
        outerView.getChildren().add(msg);
        showPopup(msg);
    }

    public void checkLength4(KeyEvent event) {
        TextField currentField = (TextField) event.getSource();
        String id = currentField.getId();
        currentPosition = id.charAt(id.length()-1)-'1';
        cardNumberList.get(currentPosition).setText(cardNumberList.get(currentPosition).getText().replaceAll("[^\\d]", ""));
        cardNumberList.get(currentPosition).positionCaret(cardNumberList.get(currentPosition).getText().length());
        if (cardNumberList.get(currentPosition).getText().length() == 4) {
            if (currentPosition < 3) {
                currentPosition++;
                cardNumberList.get(currentPosition).requestFocus();
                cardNumberList.get(currentPosition).positionCaret(cardNumberList.get(currentPosition).getText().length());
            }
        }
        else if (cardNumberList.get(currentPosition).getText().length() > 4) {
            cardNumberList.get(currentPosition).setText(cardNumberList.get(currentPosition).getText(0, 4));
            cardNumberList.get(currentPosition).positionCaret(4);
        }
    }

    public void checkLength2(KeyEvent event) {
        TextField currentField = (TextField) event.getSource();
        String id = currentField.getId();
        currentPosition = id.charAt(id.length()-1)-'1';
        expireDateList.get(currentPosition).setText(expireDateList.get(currentPosition).getText().replaceAll("[^\\d]", ""));
        expireDateList.get(currentPosition).positionCaret(expireDateList.get(currentPosition).getText().length());
        if (expireDateList.get(currentPosition).getText().length() == 2) {
            if (currentPosition < 1) {
                currentPosition++;
                expireDateList.get(currentPosition).requestFocus();
                expireDateList.get(currentPosition).positionCaret(expireDateList.get(currentPosition).getText().length());
            }
        }
        else if (expireDateList.get(currentPosition).getText().length() > 2) {
            expireDateList.get(currentPosition).setText(expireDateList.get(currentPosition).getText(0, 2));
            expireDateList.get(currentPosition).positionCaret(4);
        }
    }
    public void checkLength3() {
        securityCode.setText(securityCode.getText().replaceAll("[^\\d]", ""));
        securityCode.positionCaret(securityCode.getText().length());
        if (securityCode.getText().length() > 3) {
            securityCode.setText(securityCode.getText(0, 3));
            securityCode.positionCaret(3);
        }
    }

    public void moveYTo(HBox hBox, int y) {
        KeyValue value = new KeyValue(hBox.layoutYProperty(), y);
        KeyFrame move = new KeyFrame(Duration.millis(250), value);
        KeyFrame delay = new KeyFrame(Duration.millis(1500));

        Timeline animation = new Timeline(move, delay);
        animation.setAutoReverse(true);
        animation.setCycleCount(2);
        animation.play();
        animation.setOnFinished(e -> outerView.getChildren().remove(hBox));
    }

    public void showPopup(HBox msg) {
        msg.setLayoutX(302);
        msg.setLayoutY(-80);
        moveYTo(msg, 30);
    }

    public void goToRecordScreen() {
        PublicController.goToRecordScreen();
    }
}
