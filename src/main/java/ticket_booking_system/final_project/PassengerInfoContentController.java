package ticket_booking_system.final_project;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;

import java.security.Key;

public class PassengerInfoContentController {
    @FXML
    private Pane sexual_pane;
    @FXML
    private Label sexual_labal;
    @FXML
    private SVGPath icon;
    @FXML
    private Label passengerNo;

    //get access to BookingScreen
    public static VBox vBox;

    //record current top object
    public static int topPaneId = 0;

    // unique number for each pane
    public int paneId;


    @FXML
    void OnMouseClick(){
        if (topPaneId != 0) {
            // close the box which is opened and send it to back
            Pane previous = (Pane) vBox.getChildren().get(1+topPaneId);
            previous.setViewOrder(0);
            previous.lookup("#sexual_pane").setVisible(false);
            AnimFunction.arrowDown(icon);
        }

        // bring the object which is chosen to front and set sexual box visible
        Pane current = (Pane) vBox.getChildren().get(1+paneId);
        if (topPaneId != paneId) { // check if the previous one equals to the current one
            current.setViewOrder(-1);
            current.lookup("#sexual_pane").setVisible(true);
            AnimFunction.arrowUp(icon);
            topPaneId = paneId; // set top pane to the current one
        }
        else {
            topPaneId = 0;
        }
    }
    @FXML
    void OnFemaleClick(){
        sexual_pane.setVisible(false);
        AnimFunction.arrowDown(icon);
        sexual_labal.setText("女");
        sexual_labal.setTextFill(Color.BLACK);
    }
    @FXML
    void OnMaleClick(){
        sexual_pane.setVisible(false);
        AnimFunction.arrowDown(icon);
        sexual_labal.setText("男");
        sexual_labal.setTextFill(Color.BLACK);
    }
    public void changepassengerNo (int i) {
        paneId = i;
        passengerNo.setText("乘客"+ i);
    }

    public void toUpper(KeyEvent event) {
        TextField textField = (TextField) event.getSource();
        textField.setText(textField.getText().replaceAll("[^A-Za-z]", ""));

        String text = textField.getText();
        textField.setText(text.toUpperCase());
        textField.positionCaret(textField.getText().length());
    }
}