package ticket_booking_system.final_project;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.io.FileNotFoundException;
import java.net.URL;

public class Loader {
    private Pane view;
    public Pane getScreen(String s) {
        try {
            URL fileUrl = Main.class.getResource("screens/" + s + ".fxml");
            if (fileUrl == null) {
                throw new FileNotFoundException();
            }
            view = FXMLLoader.load(fileUrl);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }

    public HBox getPopup(String type, String text) {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("popups/" + type + ".fxml"));
            PopupController popupController = new PopupController(text);
            loader.setController(popupController);
            view = loader.load();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return (HBox) view;
    }

    public Pane getComponent(String s) {
        try {
            URL fileUrl = Main.class.getResource("components/" + s + ".fxml");
            if (fileUrl == null) {
                throw new FileNotFoundException();
            }
            view = FXMLLoader.load(fileUrl);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }

    public Pane getAsset(String s) {
        try {
            URL fileUrl = Main.class.getResource("assets/" + s + ".fxml");
            if (fileUrl == null) {
                throw new FileNotFoundException();
            }
            view = FXMLLoader.load(fileUrl);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }

    public HBox getEditMode(String type) {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("popups/" + type + ".fxml"));
            view = loader.load();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return (HBox) view;
    }

    public Pane getIcon(String IATA) {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("assets/logos/" + IATA + ".fxml"));
            view = loader.load();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }
}
