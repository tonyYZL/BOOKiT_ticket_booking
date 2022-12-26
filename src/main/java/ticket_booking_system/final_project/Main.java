package ticket_booking_system.final_project;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        BoardingPassController.hostServices = getHostServices();
        FXMLLoader start = new FXMLLoader(Main.class.getResource("Start.fxml"));
        Scene scene1 = new Scene(start.load(), 700, 550);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene1);
        stage.show();
    }
    public static void main(String[] args) {
        launch();
    }
}