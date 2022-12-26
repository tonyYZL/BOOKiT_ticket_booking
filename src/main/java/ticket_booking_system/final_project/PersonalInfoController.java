package ticket_booking_system.final_project;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PersonalInfoController {
    @FXML
    public TextField name;
    public TextField lastname;
    public TextField firstname;
    public TextField username;
    public PasswordField password;
    public TextField phoneNumber;
    public TextField emailAddress;

    public static VBox userInfoScreen;
    public static Pane modal;
    public static Pane outerView;

    public static String phone = "";
    public static String email = "";

    public void setScreen(VBox userInfoScreen) {
        PersonalInfoController.userInfoScreen = userInfoScreen;
    }
    public void setModal(Pane modal) {
        PersonalInfoController.modal = modal;
    }
    public void setOuterView(Pane outerView) {
        PersonalInfoController.outerView = outerView;
    }

    public void initialize() {
        EditModeController.setPhoneTextField(phoneNumber);
        EditModeController.setEmailTextField(emailAddress);

        DatabaseConnection connection = new DatabaseConnection();
        String command = "SELECT * FROM userData WHERE `userId`=" + UserData.getId();
        try (Connection connectDB = connection.getConnection(); Statement statement = connectDB.createStatement(); ResultSet resultSet = statement.executeQuery(command)) {
            resultSet.next();
            name.setText(resultSet.getString("chineseName"));
            lastname.setText(resultSet.getString("lastName"));
            firstname.setText(resultSet.getString("firstName"));
            username.setText(resultSet.getString("userName"));
            password.setText("password");
            phoneNumber.setText(resultSet.getString("phoneNumber"));
            emailAddress.setText(resultSet.getString("emailAddress"));
            phone = phoneNumber.getText();
            email = emailAddress.getText();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        /**/
        /**/
        /**/
    }

    public void changePassword() {
        modal.setVisible(true);
    }

    public static boolean editModeOn = false;
    public void checkEditMode() {
        if (!editModeOn) {
            Loader object = new Loader();
            HBox msg = object.getEditMode("EditMode");
            outerView.getChildren().add(msg);
            showPopup(msg);
            editModeOn = true;
        }
    }

    public void showPopup(HBox msg) {
        msg.setLayoutX(302);
        msg.setLayoutY(-80);
        moveYTo(msg, 30);
    }

    public void moveYTo(HBox hBox, int y) {
        KeyValue value = new KeyValue(hBox.layoutYProperty(), y);
        KeyFrame move = new KeyFrame(Duration.millis(250), value);

        Timeline animation = new Timeline(move);
        animation.play();
    }
}
