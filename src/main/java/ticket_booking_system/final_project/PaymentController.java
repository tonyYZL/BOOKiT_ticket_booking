package ticket_booking_system.final_project;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class PaymentController {
    int currentPosition;
    int currentPosition2;

    @FXML
    private TextField name;
    @FXML
    private TextField phonenumber;
    @FXML
    private TextField cardnum1;
    @FXML
    private TextField cardnum2;
    @FXML
    private TextField cardnum3;
    @FXML
    private TextField cardnum4;
    @FXML
    private TextField mm1;
    @FXML
    private TextField yy2;
    @FXML
    private TextField CVV3;
    @FXML
    private TextField mail;
    @FXML
    private Label label1;
    @FXML
    public ArrayList<TextField> cardnumList;
    @FXML
    public ArrayList<TextField> cardsetList;
    @FXML
    private Pane toggleBtnBackground;
    @FXML
    private Circle toggleBtn;

    @FXML
    private void OnDelClick(){
        name.clear();
        phonenumber.clear();
        cardnum1.clear();
        cardnum2.clear();
        cardnum3.clear();
        cardnum4.clear();
        mm1.clear();
        yy2.clear();
        CVV3.clear();
        mail.clear();
    }


    public void initialize() {
        if (!PublicController.buttonOn) {
            changeState();
        }
        if (UserData.getHaveCreditCard()) {
            autoFill();
            label1.setText("自動填入綁定資料");
        }
        else {
            label1.setText("將信用卡資料綁定於本帳戶");
        }
    }

    public void autoFill() {
        DatabaseConnection connection = new DatabaseConnection();
        Connection connectDB = connection.getConnection();
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            String command = "SELECT creditCardData.*, userData.chineseName, userData.emailAddress FROM `userData` INNER JOIN `creditCardData` ON userData.userId = creditCardData.userId WHERE userData.userId=" + UserData.getId();
            statement = connectDB.createStatement();
            resultSet = statement.executeQuery(command);
            resultSet.next();
            name.setText(resultSet.getString("chineseName"));
            mail.setText(resultSet.getString("emailAddress"));
            String cardNumber = resultSet.getString("cardNumber");
            String[] splitNumbers = cardNumber.split("(?<=\\G.{4})");
            for (int i = 0; i < 4; i++) {
                cardnumList.get(i).setText(splitNumbers[i]);
            }
            String expireDate = resultSet.getString("expireDate");
            mm1.setText(expireDate.substring(0, 2));
            yy2.setText(expireDate.substring(2));
            CVV3.setText(resultSet.getString("CVV"));
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {connectDB.close();}catch (SQLException ignored){}
            try {
                if (statement != null) {
                    statement.close();
                }
            }catch (SQLException ignored){}
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
            }catch (SQLException ignored){}
        }
    }

    @FXML
    public void changeState() {
        if (PublicController.buttonOn) {
            final Animation animation = new Transition() {

                {
                    setCycleDuration(Duration.millis(500));
                    setInterpolator(new Interpolator() {
                        @Override
                        protected double curve(double t) {
                            return (t == 1) ? 1.0 : 1 - Math.pow(2.0, -10 * t);
                        }
                    });
                }

                @Override
                protected void interpolate(double frac) {
                    Color vColor = new Color((127 + frac * 69)/255, (234 - frac * 38)/255, (93 + frac * 103)/255, 1);
                    toggleBtnBackground.setBackground(new Background(new BackgroundFill(vColor, new CornerRadii(50), Insets.EMPTY)));
                    toggleBtn.setTranslateX((1 - frac) * 26);
                }
            };
            animation.play();
            animation.setOnFinished(e -> {
                toggleBtnBackground.getStyleClass().remove(1);
//                System.out.println("remove");
            });
            PublicController.buttonOn = false;
        }
        else {
            final Animation animation = new Transition() {

                {
                    setCycleDuration(Duration.millis(500));
                    setInterpolator(new Interpolator() {
                        @Override
                        protected double curve(double t) {
                            return (t == 1) ? 1.0 : 1 - Math.pow(2.0, -10 * t);
                        }
                    });
                }

                @Override
                protected void interpolate(double frac) {
                    Color vColor = new Color((196 - frac * 69)/255, (196 + frac * 38)/255, (196 - frac * 103)/255, 1);
                    toggleBtnBackground.setBackground(new Background(new BackgroundFill(vColor, new CornerRadii(50), Insets.EMPTY)));
                    toggleBtn.setTranslateX(frac * 26);
                }
            };
            animation.play();
            animation.setOnFinished(e -> {
                toggleBtnBackground.getStyleClass().add("green-bg");
//                System.out.println("add");
            });
            PublicController.buttonOn = true;
            if (UserData.getHaveCreditCard()) {
                autoFill();
            }
        }
    }

    @FXML
    public void KeyTyped1(KeyEvent e){
        String s1 = cardnum1.getText();
        if(s1.length() >=4 ){
            s1 = s1.substring(0,4);
            cardnum1.setText(s1);
            cardnum1.positionCaret(4);
            TextField currentField = (TextField) e.getSource();
            String id = currentField.getId();
            currentPosition = id.charAt(id.length()-1)-'1';
            cardnumList.get(currentPosition+1).requestFocus();
        }
    }
    @FXML
    public void KeyTyped2(KeyEvent e){
        String s2 = cardnum2.getText();
        if(s2.length() >=4 ){
            s2 = s2.substring(0,4);
            cardnum2.setText(s2);
            cardnum2.positionCaret(4);
            TextField currentField = (TextField) e.getSource();
            String id = currentField.getId();
            currentPosition = id.charAt(id.length()-1)-'1';
            cardnumList.get(currentPosition+1).requestFocus();
        }
    }
    @FXML
    public void KeyTyped3(KeyEvent e){
        String s3 = cardnum3.getText();
        if(s3.length() >=4 ){
            s3 = s3.substring(0,4);
            cardnum3.setText(s3);
            cardnum3.positionCaret(4);
            TextField currentField = (TextField) e.getSource();
            String id = currentField.getId();
            currentPosition = id.charAt(id.length()-1)-'1';
            cardnumList.get(currentPosition+1).requestFocus();
        }
    }
    @FXML
    public void KeyTyped4(KeyEvent e){
        String s4 = cardnum4.getText();
        if(s4.length() >=4 ){
            s4 = s4.substring(0,4);
            cardnum4.setText(s4);
            cardnum4.positionCaret(4);
            TextField currentField = (TextField) e.getSource();
            String id = currentField.getId();
            currentPosition = id.charAt(id.length()-1)-'1';
            if(currentPosition != cardnumList.size()-1) {
                cardnumList.get(currentPosition + 1).requestFocus();
            }
        }
    }
    @FXML
    public void mmKeyTyped(KeyEvent e){
        String m = mm1.getText();
        if(m.length() >= 2){
            m = m.substring(0,2);
            mm1.setText(m);
            mm1.positionCaret(2);
            TextField currentField = (TextField) e.getSource();
            String id = currentField.getId();
            currentPosition2 = id.charAt(id.length()-1)-'1';
            cardsetList.get(currentPosition2+1).requestFocus();
        }
    }
    @FXML
    public void yyKeyTyped(KeyEvent e){
        String y = yy2.getText();
        if(y.length() >= 2){
            y = y.substring(0,2);
            yy2.setText(y);
            yy2.positionCaret(2);
            TextField currentField = (TextField) e.getSource();
            String id = currentField.getId();
            currentPosition2 = id.charAt(id.length()-1)-'1';
            cardsetList.get(currentPosition2+1).requestFocus();
        }
    }
    @FXML
    public void CVVKeyTyped(KeyEvent e){
        String cvv = CVV3.getText();
        if(cvv.length() >= 3){
            cvv = cvv.substring(0,3);
            CVV3.setText(cvv);
            CVV3.positionCaret(3);
            TextField currentField = (TextField) e.getSource();
            String id = currentField.getId();
            currentPosition2 = id.charAt(id.length()-1)-'1';
            if(currentPosition2 != cardsetList.size()-1) {
                cardsetList.get(currentPosition2 + 1).requestFocus();
            }
        }
    }
    @FXML
    public  void phoneTyped(){
        String temp = phonenumber.getText();
        if(temp.length()>=10){
            temp = temp.substring(0,10);
            phonenumber.setText(temp);
            phonenumber.positionCaret(10);
        }
    }
}