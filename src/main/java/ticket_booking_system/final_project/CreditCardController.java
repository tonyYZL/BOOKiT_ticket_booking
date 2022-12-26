package ticket_booking_system.final_project;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CreditCardController {
    @FXML
//    public static BorderPane mainPane;
    public static VBox homeScreen;
    public static Pane modal;
//    private static Pane currentScreen;

    @FXML
    public Pane creditCard;
    public Pane cardPane;
    public Text haveCardText;
    public Text noCardText;
    public Pane editBtn;
    public Text cardNumber;
    public Text expireDate;
    public Text username;


    public void setHomeScreen(VBox homeScreen) {
        CreditCardController.homeScreen = homeScreen;
    }
    public void setModal(Pane modal) {
        CreditCardController.modal = modal;
    }

    public void initialize() {
        username.setText(UserData.getUsername() + "，您好");
        DatabaseConnection connection = new DatabaseConnection();
        Connection connectDB = connection.getConnection();
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            String command = "SELECT * FROM creditCardData WHERE `userid`=" + UserData.getId();
            statement = connectDB.createStatement();
            resultSet = statement.executeQuery(command);
//            有無信用卡
            if (resultSet.next()) {
                UserData.setHaveCreditCard(true);
                Loader object = new Loader();
                Pane card = object.getAsset("credit_card");
                cardPane.getChildren().add(card);
                noCardText.setVisible(false);
                haveCardText.setVisible(true);
                editBtn.setVisible(true);
                cardNumber.setVisible(true);
                StringBuilder stringBuilder = new StringBuilder(resultSet.getString("cardNumber").substring(12));
                stringBuilder.insert(1, " ");
                stringBuilder.insert(3, " ");
                stringBuilder.insert(5, " ");
                String formattedNumber = stringBuilder.toString();
                cardNumber.setText(formattedNumber);
                expireDate.setVisible(true);
                stringBuilder.delete(0, stringBuilder.length());
                stringBuilder.append(resultSet.getString("expireDate"));
                stringBuilder.insert(2, "/");
                String formattedExpireDate = stringBuilder.toString();
                expireDate.setText(formattedExpireDate);
            }
            else {
                Loader object = new Loader();
                Pane card = object.getAsset("empty_card");
                cardPane.getChildren().add(card);
                haveCardText.setVisible(false);
                editBtn.setVisible(false);
                cardNumber.setVisible(false);
                expireDate.setVisible(false);
                noCardText.setVisible(true);
            }
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

    public void addCard() {
        if (!UserData.getHaveCreditCard()) {
//            System.out.println("Add");
            modal.setVisible(true);
        }
        else {
//            System.out.println("You are already have credit card");
        }
    }

    public void goToInfoScreen() {
        PublicController.goToInfoScreen();
    }
}
