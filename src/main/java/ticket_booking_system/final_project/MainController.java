package ticket_booking_system.final_project;

import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;

public class MainController {

    @FXML
    public BorderPane mainPane;
    public Pane currentScreen;
    public Pane logOut;
    public Pane navPane;
    public Pane animationPane;


    public void initialize() {
        PublicController.mainPane = mainPane;
        PublicController.currentScreenIndicator = currentScreen;
        PublicController.goToHomeScreen();

        SVGPath path = new SVGPath();
        path.setContent("M1 21.5C1 10.1782 10.1782 1 21.5 1H137.5C148.822 1 158 10.1782 158 21.5C158 32.8218 148.822 42 137.5 42H21.5C10.1782 42 1 32.8218 1 21.5ZM0 21.5C0 9.62588 9.62588 0 21.5 0H137.5C149.374 0 159 9.62588 159 21.5C159 33.3741 149.374 43 137.5 43H21.5C9.62588 43 0 33.3741 0 21.5Z");
        logOut.setClip(path);
    }

    @FXML
    public void goToHomeScreen() {
        PublicController.goToHomeScreen();
    }
    public void goToSearchScreen() {
        PublicController.goToSearchScreen();
    }
    public void goToRecordScreen() {
        PublicController.goToRecordScreen();
    }
    public void goToInfoScreen() {
        PublicController.goToInfoScreen();
    }

    public void mouseOver(MouseEvent event) {
        Pane pane = (Pane) event.getSource();
        AnimFunction.mouseOverBackgroundChange(pane);
    }
    public void mouseOut(MouseEvent event) {
        Pane pane = (Pane) event.getSource();
        AnimFunction.mouseOutBackgroundChange(pane);
    }
    public void fadeIn() {
        AnimFunction.fadeIn(animationPane);
    }
    public void fadeOut() {
        AnimFunction.fadeOut(animationPane);
    }

    public void goToStart(MouseEvent event) throws IOException {
        Node source = (Node)  event.getSource();
        Stage oldStage  = (Stage) source.getScene().getWindow();
        oldStage.close();
        UserData.clearAll();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Start.fxml"));
        Scene scene2 = new Scene(fxmlLoader.load(), 700, 550);
        Stage stage = new Stage();
        stage.setScene(scene2);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
    }
}