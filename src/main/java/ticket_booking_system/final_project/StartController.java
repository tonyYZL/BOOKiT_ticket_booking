package ticket_booking_system.final_project;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class StartController {
    @FXML
    public Pane startPane;
    public Pane toggleBg;
    public Text loginText;
    public Text signupText;
    public Pane loginField;
    public TextField username;
    public PasswordField password;
    public TextField unmaskPassword;
    public Pane signupField;
    public TextField lastname;
    public TextField surname;
    public TextField chineseName;
    public TextField tempUsername;
    public PasswordField tempPassword;
    public TextField tempUnmaskPassword;
    public TextField emailAddress;

    public String currentScreen = "login";

    public void goToHomeScreen(ActionEvent actionEvent) throws IOException{
        Node source = (Node)  actionEvent.getSource();
        Stage oldStage  = (Stage) source.getScene().getWindow();
        oldStage.close();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Main.fxml"));
        Scene scene2 = new Scene(fxmlLoader.load(), 1220, 720);
        Stage stage = new Stage();
        stage.setScene(scene2);
        stage.setResizable(false);
        stage.setTitle("BOOKiT");
        stage.show();
    }

    public void loginCheck(ActionEvent actionEvent) {
        Loader object = new Loader();
        if (!Validation.isValidUsername(username.getText())) {
            HBox msg = object.getPopup("Failed_small", "使用者名稱格式不符");
            showPopup(msg);
        }
        else if (!Validation.isPassword(password.getText())) {
            HBox msg = object.getPopup("Failed_small", "密碼格式不符");
            showPopup(msg);
        }
        else {
            DatabaseConnection connection = new DatabaseConnection();
            Connection connectDB = connection.getConnection();
            Statement statement = null;
            ResultSet resultSet = null;
            try {
                String command = "SELECT * FROM userData WHERE `username`='" + username.getText() + "'";
                statement = connectDB.createStatement();
                resultSet = statement.executeQuery(command);
                if (resultSet.next()) {
                    String hashed = getHash(password.getText());
                    if (hashed.equals(resultSet.getString("password"))) {
                        UserData.setId(resultSet.getInt("userId"));
                        UserData.setUsername(resultSet.getString("username"));
                        goToHomeScreen(actionEvent);
                    }
                    else {
                        HBox msg = object.getPopup("Failed_small", "使用者名稱或密碼錯誤");
                        showPopup(msg);
                    }
                }
                else {
                    HBox msg = object.getPopup("Failed_small", "查無此用戶");
                    showPopup(msg);
                }
            } catch (Exception e) {
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
    }

    public void signupCheck() {
        Loader object = new Loader();
        if (!Validation.isUpperAlpha(surname.getText())) {
            HBox msg = object.getPopup("Failed_small", "英文名字不可為空");
            showPopup(msg);
        } else if (!Validation.isUpperAlpha(lastname.getText())) {
            HBox msg = object.getPopup("Failed_small", "英文姓氏不可為空");
            showPopup(msg);
        } else if (!Validation.isChinese(chineseName.getText())) {
            HBox msg = object.getPopup("Failed_small", "請輸入正確的中文姓名");
            showPopup(msg);
        } else if (!Validation.isValidUsername(tempUsername.getText())) {
            HBox msg = object.getPopup("Failed_small", "使用者名稱格式不符");
            showPopup(msg);
        } else if (!Validation.isPassword(tempPassword.getText())) {
            HBox msg = object.getPopup("Failed_small", "密碼格式不符");
            showPopup(msg);
        } else if (!Validation.isEmail(emailAddress.getText())) {
            HBox msg = object.getPopup("Failed_small", "信箱格式錯誤");
            showPopup(msg);
        } else {
            DatabaseConnection connection = new DatabaseConnection();
            Connection connectDB = connection.getConnection();
            Statement statement = null;
            ResultSet resultSet = null;
            try {
                String command = "SELECT COUNT(*) FROM userData WHERE `username`='" + tempUsername.getText() + "'";
                statement = connectDB.createStatement();
                resultSet = statement.executeQuery(command);
                resultSet.next();
                if (resultSet.getInt("count(*)") == 1) {
                    HBox msg = object.getPopup("Failed_small", "使用者名稱已被使用");
                    showPopup(msg);
                }
                else {
                    String hashed = getHash(tempPassword.getText());
                    command = "INSERT INTO userData (username, lastname, firstname, chineseName, password, emailAddress) VALUES ('" + tempUsername.getText() + "','" + lastname.getText() + "','" + surname.getText() + "','" + chineseName.getText() + "','" + hashed + "','" + emailAddress.getText() + "')";
                    statement = connectDB.createStatement();
                    int i = statement.executeUpdate(command);
                    if (i > 0) {
                        HBox msg = object.getPopup("Success_small", "註冊成功");
                        showPopup(msg);
                        goToLogin();
                    } else {
                        HBox msg = object.getPopup("Failed_small", "系統錯誤");
                        showPopup(msg);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                HBox msg = object.getPopup("Failed_small", "系統錯誤");
                showPopup(msg);
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
    }

    public void exit(MouseEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    public void goToSignup() {
        if (currentScreen.equals("login")) {
//            System.out.println("Signup");
            currentScreen = "signup";
            loginText.getStyleClass().set(0, "font");
            signupText.getStyleClass().set(0, "selected-font");
            loginField.setVisible(false);
            signupField.setVisible(true);
            moveXTo(532);
        }
    }

    public void goToLogin() {
        if (currentScreen.equals("signup")) {
//            System.out.println("Login");
            currentScreen = "login";
            signupText.getStyleClass().set(0, "font");
            loginText.getStyleClass().set(0, "selected-font");
            signupField.setVisible(false);
            loginField.setVisible(true);
            moveXTo(411);
        }
    }

    public void moveXTo(int x) {
        KeyValue value = new KeyValue(toggleBg.layoutXProperty(), x, new Interpolator() {
            @Override
            protected double curve(double t) {
                return (t == 1) ? 1.0 : 1 - Math.pow(2.0, -10 * t);
            }
        });
        KeyFrame frame = new KeyFrame(Duration.millis(550), value);
        Timeline animation = new Timeline(frame);
        animation.play();
    }

    public void moveYTo(Pane pane, int y) {
        KeyValue value = new KeyValue(pane.layoutYProperty(), y);
        KeyFrame move = new KeyFrame(Duration.millis(250), value);
        KeyFrame delay = new KeyFrame(Duration.millis(1500));

        Timeline animation = new Timeline(move, delay);
        animation.setAutoReverse(true);
        animation.setCycleCount(2);
        animation.play();
        animation.setOnFinished(e -> startPane.getChildren().remove(pane));
    }

    public void showPopup(HBox msg) {
        startPane.getChildren().add(msg);
        msg.setLayoutX(441);
        msg.setLayoutY(550);
        moveYTo(msg, 493);
    }

    public String getHash(String str) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] bytes = md.digest(str.getBytes(StandardCharsets.UTF_8));
        BigInteger number = new BigInteger(1, bytes);
        return number.toString(16);
    }

    public void toUpper(KeyEvent event) {
        TextField textField = (TextField) event.getSource();
        textField.setText(textField.getText().replaceAll("[^A-Za-z]", ""));

        String text = textField.getText();
        textField.setText(text.toUpperCase());
        textField.positionCaret(textField.getText().length());
    }
}
