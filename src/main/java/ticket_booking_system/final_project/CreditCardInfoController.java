package ticket_booking_system.final_project;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class CreditCardInfoController {
    public static Pane outerView;

    public static void setOuterView(Pane outerView) {
        CreditCardInfoController.outerView = outerView;
    }


    @FXML
    private ArrayList<TextField> cardNumberList;
    @FXML
    private ArrayList<TextField> expireDateList;
    public TextField securityCode;
    public Text cardNumber;
    public Text expireDate;
    public HBox addBtn;
    public HBox deleteBtn;

    public int currentPosition = 0;

    public void initialize() {
        DatabaseConnection connection = new DatabaseConnection();
        Connection connectDB = connection.getConnection();
        Statement statement = null;
        ResultSet resultSet = null;
        if (UserData.getHaveCreditCard()) {
            try {
                String command = "SELECT * FROM creditCardData WHERE `userid`=" + UserData.getId();
                statement = connectDB.createStatement();
                resultSet = statement.executeQuery(command);
                resultSet.next();
                for (int i = 0; i < 4; i++) {
                    cardNumberList.get(i).setText(resultSet.getString("cardNumber").substring(i * 4, i*4+4));
                }
                for (int i = 0; i < 2; i++) {
                    expireDateList.get(i).setText(resultSet.getString("expireDate").substring(i*2, i*2+2));
                }
                securityCode.setText(resultSet.getString("CVV"));
                StringBuilder stringBuilder = new StringBuilder(resultSet.getString("cardNumber").substring(12));
                stringBuilder.insert(1, " ");
                stringBuilder.insert(3, " ");
                stringBuilder.insert(5, " ");
                String formattedNumber = stringBuilder.toString();
                cardNumber.setText(formattedNumber);
                stringBuilder.delete(0, stringBuilder.length());
                stringBuilder.append(resultSet.getString("expireDate"));
                stringBuilder.insert(2, "/");
                String formattedExpireDate = stringBuilder.toString();
                expireDate.setText(formattedExpireDate);
                addBtn.setVisible(false);
                deleteBtn.setVisible(true);
                cardNumber.setVisible(true);
                expireDate.setVisible(true);

                cardNumberList.forEach(box -> {
                    box.getStyleClass().add("disabled-input");
                    box.setEditable(false);
                    box.setCursor(Cursor.DEFAULT);
                });
                expireDateList.forEach(box -> {
                    box.getStyleClass().add("disabled-input");
                    box.setEditable(false);
                    box.setCursor(Cursor.DEFAULT);
                });
                securityCode.getStyleClass().add("disabled-input");
                securityCode.setEditable(false);
                securityCode.setCursor(Cursor.DEFAULT);
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try { connectDB.close();} catch (SQLException e) {/**/}
                try {
                    if (statement != null) {
                        statement.close();
                    }
                } catch (SQLException e) {/**/}
                try {
                    if (resultSet != null) {
                        resultSet.close();
                    }
                } catch (SQLException e) {/**/}
            }
        }
        else {
            cardNumber.setVisible(false);
            expireDate.setVisible(false);
            deleteBtn.setVisible(false);
            addBtn.setVisible(true);

            cardNumberList.forEach(box -> {
                box.getStyleClass().remove("disabled-input");
                box.setEditable(true);
                box.setCursor(Cursor.TEXT);
            });
            expireDateList.forEach(box -> {
                box.getStyleClass().remove("disabled-input");
                box.setEditable(true);
                box.setCursor(Cursor.TEXT);
            });
            securityCode.getStyleClass().remove("disabled-input");
            securityCode.setEditable(true);
            securityCode.setCursor(Cursor.TEXT);
        }
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

    public void deleteCard() {
        DatabaseConnection connection = new DatabaseConnection();
        Connection connectDB = connection.getConnection();
        Statement statement = null;
        Loader object = new Loader();
        HBox msg;
        try {
            String command = "DELETE FROM `creditCardData` WHERE `userId`=" + UserData.getId();
            statement = connectDB.createStatement();
            int i = statement.executeUpdate(command);
            if (i > 0) {
                msg = object.getPopup("Success_big", "已解除綁定信用卡");
                UserData.setHaveCreditCard(false);
                cardNumber.setVisible(false);
                expireDate.setVisible(false);
                deleteBtn.setVisible(false);
                addBtn.setVisible(true);
                for (TextField textField: cardNumberList) {
                    textField.setText("");
                }
                for (TextField textField: expireDateList) {
                    textField.setText("");
                }
                securityCode.setText("");

                cardNumberList.forEach(box -> {
                    box.getStyleClass().remove("disabled-input");
                    box.setEditable(true);
                    box.setCursor(Cursor.TEXT);
                });
                expireDateList.forEach(box -> {
                    box.getStyleClass().remove("disabled-input");
                    box.setEditable(true);
                    box.setCursor(Cursor.TEXT);
                });
                securityCode.getStyleClass().remove("disabled-input");
                securityCode.setEditable(true);
                securityCode.setCursor(Cursor.TEXT);
            }
            else {
                msg = object.getPopup("Failed_big", "系統錯誤");
            }
        } catch (SQLException e) {
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
        outerView.getChildren().add(msg);
        showPopup(msg);
    }

    public void addCard() {
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
                    UserData.setHaveCreditCard(true);

                    stringBuilder = new StringBuilder(formattedCardNumber.substring(12));
                    stringBuilder.insert(1, " ");
                    stringBuilder.insert(3, " ");
                    stringBuilder.insert(5, " ");
                    String formattedNumber = stringBuilder.toString();
                    cardNumber.setText(formattedNumber);
                    stringBuilder.delete(0, stringBuilder.length());
                    stringBuilder.append(formattedExpireDate);
                    stringBuilder.insert(2, "/");
                    formattedExpireDate = stringBuilder.toString();
                    expireDate.setText(formattedExpireDate);
                    addBtn.setVisible(false);
                    deleteBtn.setVisible(true);
                    cardNumber.setVisible(true);
                    expireDate.setVisible(true);

                    cardNumberList.forEach(box -> {
                        box.getStyleClass().add("disabled-input");
                        box.setEditable(false);
                        box.setCursor(Cursor.DEFAULT);
                    });
                    expireDateList.forEach(box -> {
                        box.getStyleClass().add("disabled-input");
                        box.setEditable(false);
                        box.setCursor(Cursor.DEFAULT);
                    });
                    securityCode.getStyleClass().add("disabled-input");
                    securityCode.setEditable(false);
                    securityCode.setCursor(Cursor.DEFAULT);
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

    public void showPopup(HBox msg) {
        msg.setLayoutX(302);
        msg.setLayoutY(-80);
        moveYTo(msg, 30);
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
}
