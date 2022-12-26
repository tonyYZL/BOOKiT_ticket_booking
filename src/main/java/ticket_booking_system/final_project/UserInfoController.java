package ticket_booking_system.final_project;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserInfoController {
    @FXML
    public VBox userInfoScreen;
    public Pane modal;
    public PasswordField oldPassword;
    public PasswordField newPassword;
    public PasswordField againNewPassword;
    public Pane outerView;

    PersonalInfoController personalInfoController = new PersonalInfoController();


    public void initialize() {
        personalInfoController.setScreen(userInfoScreen);
        personalInfoController.setModal(modal);
        personalInfoController.setOuterView(outerView);
        EditModeController.setOuterView(outerView);
        CreditCardInfoController.setOuterView(outerView);

        Loader object = new Loader();
        Pane personalInfoCard = object.getComponent("PersonalInfo");
        userInfoScreen.getChildren().add(personalInfoCard);
        Pane creditCardInfo = object.getComponent("CreditCardInfo");
        userInfoScreen.getChildren().add(creditCardInfo);
    }
    public void closeModal() {
        modal.setVisible(false);
    }
    public void changePasswordCheck() {
        Loader object = new Loader();
        HBox msg;

        if (oldPassword.getText().equals("") || newPassword.getText().equals("") || againNewPassword.getText().equals("")){
            msg = object.getPopup("Failed_big", "所有欄位皆須填寫");
        }
        else if (!(Validation.isPassword(oldPassword.getText()) && Validation.isPassword(newPassword.getText()) && Validation.isPassword(againNewPassword.getText()))) {
            msg = object.getPopup("Failed_big", "密碼格式不符");
        }
        else {
            DatabaseConnection connection = new DatabaseConnection();
            Connection connectDB = connection.getConnection();
            Statement statement = null;
            ResultSet resultSet = null;
            try {
                String command = "SELECT * FROM userData WHERE `userId`=" + UserData.getId();
                statement = connectDB.createStatement();
                resultSet = statement.executeQuery(command);
                resultSet.next();
                String oldHashedPassword = getHash(oldPassword.getText());
                if (!oldHashedPassword.equals(resultSet.getString("password"))) {
                    msg = object.getPopup("Failed_big", "舊密碼錯誤");
                }
                else {
                    if (!newPassword.getText().equals(againNewPassword.getText())) {
                        msg = object.getPopup("Failed_big", "兩次新密碼必須相同");
                    }
                    else {
                        String newHashedPassword = getHash(newPassword.getText());
                        if (oldHashedPassword.equals(newHashedPassword)) {
                            msg = object.getPopup("Failed_big", "新密碼無法與舊密碼相同");
                        }
                        else {
                            command = "UPDATE userData SET `password`='" + newHashedPassword + "' WHERE `userId`=" + UserData.getId();
                            int i = statement.executeUpdate(command);
                            if (i > 0) {
                                closeModal();
                                msg = object.getPopup("Success_big", "已更改為新密碼");
                            }
                            else {
                                msg = object.getPopup("Failed_big", "系統錯誤");
                            }
                        }
                    }
                }
            } catch (SQLException | NoSuchAlgorithmException e) {
                e.printStackTrace();
                msg = object.getPopup("Failed_big", "系統錯誤");
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
        outerView.getChildren().add(msg);
        showPopup(msg);
    }

    public String getHash(String str) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] bytes = md.digest(str.getBytes(StandardCharsets.UTF_8));
        BigInteger number = new BigInteger(1, bytes);
        return number.toString(16);
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
