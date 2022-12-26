package ticket_booking_system.final_project;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class EditModeController {
    public static Pane outerView;
    public static TextField phoneNumber;
    public static TextField emailAddress;

    public static void setOuterView(Pane outerView) {
        EditModeController.outerView = outerView;
    }
    public static void setPhoneTextField(TextField phoneNumber) {
        EditModeController.phoneNumber = phoneNumber;
    }
    public static void setEmailTextField(TextField emailAddress) {
        EditModeController.emailAddress = emailAddress;
    }

    public void closeEditMode() {
        outerView.getChildren().remove(2);
        PersonalInfoController.editModeOn = false;
        phoneNumber.setText(PersonalInfoController.phone);
        emailAddress.setText(PersonalInfoController.email);
        outerView.requestFocus();

    }
    public void saveEdit() {
        Loader object = new Loader();
        HBox msg;
        if (!Validation.isValidPhoneNumber(phoneNumber.getText())) {
            msg = object.getPopup("Failed_big", "手機號碼格式有誤");
        }
        else if (!Validation.isEmail(emailAddress.getText())) {
            msg = object.getPopup("Failed_big", "信箱格式有誤");
        }
        else {
            DatabaseConnection connection = new DatabaseConnection();
            Connection connectDB = connection.getConnection();
            Statement statement = null;
            try {
                String command = "UPDATE userData SET `phoneNumber`='" + phoneNumber.getText() + "', `emailAddress`='" + emailAddress.getText() + "' WHERE `userId`=" + UserData.getId();
                statement = connectDB.createStatement();
                int i = statement.executeUpdate(command);
                if (i > 0) {
                    outerView.getChildren().remove(2);
                    outerView.requestFocus();
                    msg = object.getPopup("Success_big", "已修改");
                    PersonalInfoController.phone = phoneNumber.getText();
                    PersonalInfoController.email = emailAddress.getText();
                    PersonalInfoController.editModeOn = false;
                }
                else {
                    msg = object.getPopup("Failed_big", "系統錯誤");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                msg = object.getPopup("Failed_big", "系統錯誤");
            } finally {
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
