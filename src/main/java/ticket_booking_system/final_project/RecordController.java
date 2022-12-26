package ticket_booking_system.final_project;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class RecordController {

    @FXML
    public Pane outerView;
    public ScrollPane scroll;
    public Pane titlePane;
    public VBox modal;
    public VBox loadingPane;
    public VBox recordScreen;

    public Loader object = new Loader();
    public HBox msg1 = object.getPopup("Failed_big", "系統錯誤");
    public HBox msg3 = object.getPopup("Success_big", "訂單已成立");

    public static String currentChoice = null; //boarding pass type
    public void initialize() {
        if (PublicController.orderCreated) {
            AnimFunction.showTopPopup(outerView, msg3);
            PublicController.orderCreated = false;
        }
        currentChoice = null;

        RecordCardController.modal = scroll;
        RecordCardController.titlePane = titlePane;
        //load record data
        DatabaseConnection connection = new DatabaseConnection();
        Connection connectDB = connection.getConnection();
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            String command = "SELECT `flightData`.*, `orderData`.* FROM `flightData` INNER JOIN `orderData` ON `flightData`.id = `orderData`.id WHERE `userId` = " + UserData.getId() + " ORDER BY `eventTime` DESC";
            statement = connectDB.createStatement();
            resultSet = statement.executeQuery(command);
            if (resultSet.next()) {
                do {
                    FXMLLoader loader = new FXMLLoader(Main.class.getResource("components/RecordCard.fxml"));
                    Pane card = loader.load();
                    RecordCardController controller = loader.getController();
                    recordScreen.getChildren().add(card);
                    //card
                    ArrayList<String> toCard = new ArrayList<>();
                    toCard.add(resultSet.getString("id"));
                    toCard.add(resultSet.getString("airline"));
                    toCard.add(resultSet.getString("flightNo"));
                    toCard.add(resultSet.getString("from"));
                    toCard.add(resultSet.getString("to"));
                    toCard.add(resultSet.getString("date"));
                    toCard.add(resultSet.getString("departure"));
                    toCard.add(resultSet.getString("arrive"));
                    toCard.add(resultSet.getString("time"));

                    //detail
                    ArrayList<String> toDetail = new ArrayList<>();
                    toDetail.add(resultSet.getString("transactionId"));
                    toDetail.add(resultSet.getString("name"));
                    toDetail.add(resultSet.getString("cardNumber"));
                    toDetail.add(resultSet.getString("phoneNumber"));
                    toDetail.add(resultSet.getString("eventTime"));
                    toDetail.add(resultSet.getString("cabinClass"));
                    toDetail.add(resultSet.getString("price"));
                    toDetail.add(resultSet.getString("quantity"));
                    toDetail.add(resultSet.getString("totalPrice"));

                    //pass data
                    controller.passData(toCard, toDetail);

//                    System.out.println(resultSet.getString("transactionId"));
                } while (resultSet.next());
            }
        } catch (SQLException | IOException e) {
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

    public void getBoardingPass() {
        Loader object = new Loader();
        if (currentChoice != null) {
            loadingPane.setVisible(true);
            HBox msg = object.getEditMode("Processing");
            msg.setTranslateY(-50);
            loadingPane.getChildren().add(msg);
            KeyValue value = new KeyValue(msg.translateYProperty(), 0);
            KeyFrame move = new KeyFrame(Duration.millis(250), value);

            Timeline animation = new Timeline(move);
            animation.play();
            animation.setOnFinished(e -> {
                int result = CreateBoardingPass.create();
                if (result == 1) {
                    loadingPane.setVisible(false);
                    loadingPane.getChildren().remove(0);
                    titlePane.setVisible(false);
                    scroll.setVisible(false);
                    HBox msg1 = object.getEditMode("SeeMyBoardingPass");
                    outerView.getChildren().add(msg1);
                    showPopup(msg1);
                }
                else if (result == -1) {
                    loadingPane.getChildren().remove(0);
                    HBox msg1 = object.getPopup("Failed_big", "處理過程中發生問題");
                    loadingPane.getChildren().add(msg1);

                    KeyFrame delay = new KeyFrame(Duration.millis(2000));
                    Timeline animation2 = new Timeline(delay);
                    animation2.play();
                    animation2.setOnFinished(e2 -> {
                        loadingPane.getChildren().remove(msg1);
                        loadingPane.setVisible(false);
                    });
                }
                else {
                    loadingPane.setVisible(false);
                    loadingPane.getChildren().remove(0);
                }
            });
        }
        else {
            HBox msg = object.getPopup("Failed_big", "請先選擇一種樣式");
            outerView.getChildren().add(msg);
            showPopup(msg);
        }
    }

    public void cancel() {
        KeyValue value = new KeyValue(titlePane.opacityProperty(),0, new Interpolator() {
            @Override
            protected double curve(double t) {
                return (t == 1) ? 1.0 : 1 - Math.pow(2.0, -10 * t);
            }
        });
        KeyValue value2 = new KeyValue(scroll.opacityProperty(), 0, new Interpolator() {
            @Override
            protected double curve(double t) {
                return (t == 1) ? 1.0 : 1 - Math.pow(2.0, -10 * t);
            }
        });

        KeyFrame frame = new KeyFrame(Duration.millis(500), value, value2);
        Timeline animation = new Timeline(frame);
        animation.play();
        animation.setOnFinished(e -> {
            scroll.setVisible(false);
            scroll.setVvalue(0);
            titlePane.setVisible(false);
        });
        if (currentChoice != null) {
            StackPane lastChoice = (StackPane) modal.lookup("#" + currentChoice);
            lastChoice.getStyleClass().remove("hover-border-show");
            KeyValue value3 = new KeyValue(lastChoice.scaleXProperty(), 1);
            KeyValue value4 = new KeyValue(lastChoice.scaleYProperty(), 1);
            KeyValue value5 = new KeyValue(lastChoice.getChildren().get(1).scaleXProperty(), 0);
            KeyValue value6 = new KeyValue(lastChoice.getChildren().get(1).scaleYProperty(), 0);
            KeyValue value7 = new KeyValue(lastChoice.getChildren().get(2).scaleXProperty(), 0);
            KeyValue value8 = new KeyValue(lastChoice.getChildren().get(2).scaleYProperty(), 0);

            KeyFrame frame2 = new KeyFrame(Duration.millis(150), value3, value4, value5, value6, value7, value8);
            Timeline animation2 = new Timeline(frame2);
            animation2.play();
        }
        currentChoice = null;
    }

    public void hover(MouseEvent event) {
        StackPane node = (StackPane) event.getSource();
        if (!node.getId().equals(currentChoice)) {
            KeyValue value = new KeyValue(node.scaleXProperty(), 1.05);
            KeyValue value2 = new KeyValue(node.scaleYProperty(), 1.05);

            KeyFrame frame = new KeyFrame(Duration.millis(150), value, value2);
            Timeline animation = new Timeline(frame);
            animation.play();
        }
    }

    public void mouseOut(MouseEvent event) {
        StackPane node = (StackPane) event.getSource();
        if (!node.getId().equals(currentChoice)) {
            KeyValue value = new KeyValue(node.scaleXProperty(), 1);
            KeyValue value2 = new KeyValue(node.scaleYProperty(), 1);

            KeyFrame frame = new KeyFrame(Duration.millis(150), value, value2);
            Timeline animation = new Timeline(frame);
            animation.play();
        }
    }

    public void chosen(MouseEvent event) {
        StackPane node = (StackPane) event.getSource();
        if (currentChoice != null && !currentChoice.equals(node.getId())) {
            StackPane lastChoice = (StackPane) modal.lookup("#" + currentChoice);
            lastChoice.getStyleClass().remove("hover-border-show");
            KeyValue value = new KeyValue(lastChoice.scaleXProperty(), 1);
            KeyValue value2 = new KeyValue(lastChoice.scaleYProperty(), 1);
            KeyValue value3 = new KeyValue(lastChoice.getChildren().get(1).scaleXProperty(), 0);
            KeyValue value4 = new KeyValue(lastChoice.getChildren().get(1).scaleYProperty(), 0);
            KeyValue value5 = new KeyValue(lastChoice.getChildren().get(2).scaleXProperty(), 0);
            KeyValue value6 = new KeyValue(lastChoice.getChildren().get(2).scaleYProperty(), 0);

            KeyFrame frame = new KeyFrame(Duration.millis(150), value, value2, value3, value4, value5, value6);
            Timeline animation = new Timeline(frame);
            animation.play();
        }
        currentChoice = node.getId();
        node.getStyleClass().add("hover-border-show");
        node.getChildren().get(1).setVisible(true);
        node.getChildren().get(2).setVisible(true);
        KeyValue value = new KeyValue(node.getChildren().get(1).scaleXProperty(), 1, new Interpolator() {
            @Override
            protected double curve(double t) {
                final float s = 1.70158f;
                t--;
                return (t * t * ((s + 1f) * t + s) + 1f);
            }
        });
        KeyValue value2 = new KeyValue(node.getChildren().get(1).scaleYProperty(), 1, new Interpolator() {
            @Override
            protected double curve(double t) {
                final float s = 1.70158f;
                t--;
                return (t * t * ((s + 1f) * t + s) + 1f);
            }
        });
        KeyValue value3 = new KeyValue(node.getChildren().get(2).scaleXProperty(), 1, new Interpolator() {
            @Override
            protected double curve(double t) {
                final float s = 1.70158f;
                t--;
                return (t * t * ((s + 1f) * t + s) + 1f);
            }
        });
        KeyValue value4 = new KeyValue(node.getChildren().get(2).scaleYProperty(), 1, new Interpolator() {
            @Override
            protected double curve(double t) {
                final float s = 1.70158f;
                t--;
                return (t * t * ((s + 1f) * t + s) + 1f);
            }
        });

        KeyFrame frame = new KeyFrame(Duration.millis(350), value, value2, value3, value4);
        Timeline animation = new Timeline(frame);
        animation.play();
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
