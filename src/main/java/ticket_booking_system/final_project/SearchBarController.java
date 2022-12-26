package ticket_booking_system.final_project;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.SVGPath;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Objects;

public class SearchBarController {
    @FXML
    private Pane location_left;
    @FXML
    private Pane location_right;
    @FXML
    private Label departure_label;
    @FXML
    private Label destination_label;
    @FXML
    private TextField Date;
    @FXML
    private String confirm_date;

    @FXML
    public SVGPath icon_left;
    public SVGPath icon_right;
    public Label btnGroup1;
    public Label btnGroup2;
    public Label btnGroup3;
    public Label btnGroup4;
    public Label btnGroup5;
    public Label btnGroup6;

    public static Pane outerView;
    public static VBox vbox;
    public static Label departureText;
    public static Label arriveText;
    public static Pane titlePane;
    private final Loader object = new Loader();
    private final HBox msg1 = object.getPopup("Failed_big", "出發地需與目的地不同");
    private final HBox msg2 = object.getPopup("Failed_big", "請填寫正確的地點和日期");
    private final HBox msg3 = object.getPopup("Failed_big", "系統錯誤");

    public static String from;
    public static String to;
    public Label currentSort = null;


    // Location pane show
    @FXML
    protected void showlocation_left(){
        if (location_left.isVisible()) {
            location_left.setVisible(false);
            AnimFunction.arrowDown(icon_left);
        }
        else {
            location_left.setVisible(true);
            AnimFunction.arrowUp(icon_left);
            if (location_right.isVisible()) {
                location_right.setVisible(false);
                AnimFunction.arrowDown(icon_right);
            }
        }
    }
    @FXML
    protected void showlocation_right(){
        if (location_right.isVisible()) {
            location_right.setVisible(false);
            AnimFunction.arrowDown(icon_right);
        }
        else {
            location_right.setVisible(true);
            AnimFunction.arrowUp(icon_right);
            if (location_left.isVisible()) {
                location_left.setVisible(false);
                AnimFunction.arrowDown(icon_left);
            }
        }
    }

    // Left pane location show
    @FXML
    protected void TaipeiClicked_left(){
        if (!Objects.equals(destination_label.getText(), "台北(TPE)")){
            departure_label.setText("台北(TPE)");
            departure_label.getStyleClass().add("text-to-black");
            location_left.setVisible(false);
            AnimFunction.arrowDown(icon_left);
        }
        else {
            AnimFunction.showTopPopup(outerView, msg1);
        }
    }
    @FXML
    protected void SydneyClicked_left(){
        if (!Objects.equals(destination_label.getText(), "雪梨(SYD)")){
            departure_label.setText("雪梨(SYD)");
            departure_label.getStyleClass().add("text-to-black");
            location_left.setVisible(false);
            AnimFunction.arrowDown(icon_left);
        }
        else {
            AnimFunction.showTopPopup(outerView, msg1);
        }
    }
    @FXML
    protected void LAClicked_left(){
        if (!Objects.equals(destination_label.getText(), "洛杉磯(LAX)")){
            departure_label.setText("洛杉磯(LAX)");
            departure_label.getStyleClass().add("text-to-black");
            location_left.setVisible(false);
            AnimFunction.arrowDown(icon_left);
        }
        else {
            AnimFunction.showTopPopup(outerView, msg1);
        }
    }
    @FXML
    protected void TokyoClicked_left(){
        if (!Objects.equals(destination_label.getText(), "東京(TYO)")){
            departure_label.setText("東京(TYO)");
            departure_label.getStyleClass().add("text-to-black");
            location_left.setVisible(false);
            AnimFunction.arrowDown(icon_left);
        }
        else {
            AnimFunction.showTopPopup(outerView, msg1);
        }
    }
    @FXML
    protected void DubaiClicked_left(){
        if (!Objects.equals(destination_label.getText(), "杜拜(DXB)")){
            departure_label.setText("杜拜(DXB)");
            departure_label.getStyleClass().add("text-to-black");
            location_left.setVisible(false);
            AnimFunction.arrowDown(icon_left);
        }
        else {
            AnimFunction.showTopPopup(outerView, msg1);
        }
    }
    @FXML
    protected void NewYorkClicked_left(){
        if (!Objects.equals(destination_label.getText(), "紐約(NYC)")){
            departure_label.setText("紐約(NYC)");
            departure_label.getStyleClass().add("text-to-black");
            location_left.setVisible(false);
            AnimFunction.arrowDown(icon_left);
        }
        else {
            AnimFunction.showTopPopup(outerView, msg1);
        }
    }
    @FXML
    protected void SeoulClicked_left(){
        if (!Objects.equals(destination_label.getText(), "首爾(SEL)")){
            departure_label.setText("首爾(SEL)");
            departure_label.getStyleClass().add("text-to-black");
            location_left.setVisible(false);
            AnimFunction.arrowDown(icon_left);
        }
        else {
            AnimFunction.showTopPopup(outerView, msg1);
        }
    }
    @FXML
    protected void ParisClicked_left(){
        if (!Objects.equals(destination_label.getText(), "巴黎(PAR)")){
            departure_label.setText("巴黎(PAR)");
            destination_label.getStyleClass().add("text-to-black");
            location_left.setVisible(false);
            AnimFunction.arrowDown(icon_left);
        }
        else {
            AnimFunction.showTopPopup(outerView, msg1);
        }
    }
    @FXML
    protected void LondonClicked_left(){
        if (!Objects.equals(destination_label.getText(), "倫敦(LON)")){
            departure_label.setText("倫敦(LON)");
            departure_label.getStyleClass().add("text-to-black");
            location_left.setVisible(false);
            AnimFunction.arrowDown(icon_left);
        }
        else {
            AnimFunction.showTopPopup(outerView, msg1);
        }
    }
    @FXML
    protected void MaldivesClicked_left(){
        if (!Objects.equals(destination_label.getText(), "馬爾地夫(MLE)")){
            departure_label.setText("馬爾地夫(MLE)");
            departure_label.getStyleClass().add("text-to-black");
            location_left.setVisible(false);
            AnimFunction.arrowDown(icon_left);
        }
        else {
            AnimFunction.showTopPopup(outerView, msg1);
        }
    }

    // Right pane location show
    @FXML
    protected void TaipeiClicked_right(){
        if (!Objects.equals(departure_label.getText(), "台北(TPE)")){
            destination_label.setText("台北(TPE)");
            destination_label.getStyleClass().add("text-to-black");
            location_right.setVisible(false);
            AnimFunction.arrowDown(icon_right);
        }
        else {
            AnimFunction.showTopPopup(outerView, msg1);
        }
    }
    @FXML
    protected void SydneyClicked_right(){
        if (!Objects.equals(departure_label.getText(), "雪梨(SYD)")){
            destination_label.setText("雪梨(SYD)");
            destination_label.getStyleClass().add("text-to-black");
            location_right.setVisible(false);
            AnimFunction.arrowDown(icon_right);
        }
        else {
            AnimFunction.showTopPopup(outerView, msg1);
        }
    }
    @FXML
    protected void LAClicked_right(){
        if (!Objects.equals(departure_label.getText(), "洛杉磯(LAX)")){
            destination_label.setText("洛杉磯(LAX)");
            destination_label.getStyleClass().add("text-to-black");
            location_right.setVisible(false);
            AnimFunction.arrowDown(icon_right);
        }
        else {
            AnimFunction.showTopPopup(outerView, msg1);
        }
    }
    @FXML
    protected void TokyoClicked_right(){
        if (!Objects.equals(departure_label.getText(), "東京(TYO)")){
            destination_label.setText("東京(TYO)");
            destination_label.getStyleClass().add("text-to-black");
            location_right.setVisible(false);
            AnimFunction.arrowDown(icon_right);
        }
        else {
            AnimFunction.showTopPopup(outerView, msg1);
        }
    }
    @FXML
    protected void DubaiClicked_right(){
        if (!Objects.equals(departure_label.getText(), "杜拜(DXB)")){
            destination_label.setText("杜拜(DXB)");
            destination_label.getStyleClass().add("text-to-black");
            location_right.setVisible(false);
            AnimFunction.arrowDown(icon_right);
        }
        else {
            AnimFunction.showTopPopup(outerView, msg1);
        }
    }
    @FXML
    protected void NewYorkClicked_right(){
        if (!Objects.equals(departure_label.getText(), "紐約(NYC)")){
            destination_label.setText("紐約(NYC)");
            destination_label.getStyleClass().add("text-to-black");
            location_right.setVisible(false);
            AnimFunction.arrowDown(icon_right);
        }
        else {
            AnimFunction.showTopPopup(outerView, msg1);
        }
    }
    @FXML
    protected void SeoulClicked_right(){
        if (!Objects.equals(departure_label.getText(), "首爾(SEL)")){
            destination_label.setText("首爾(SEL)");
            destination_label.getStyleClass().add("text-to-black");
            location_right.setVisible(false);
            AnimFunction.arrowDown(icon_right);
        }
        else {
            AnimFunction.showTopPopup(outerView, msg1);
        }
    }
    @FXML
    protected void ParisClicked_right(){
        if (!Objects.equals(departure_label.getText(), "巴黎(PAR)")){
            destination_label.setText("巴黎(PAR)");
            destination_label.getStyleClass().add("text-to-black");
            location_right.setVisible(false);
            AnimFunction.arrowDown(icon_right);
        }
        else {
            AnimFunction.showTopPopup(outerView, msg1);
        }
    }
    @FXML
    protected void LondonClicked_right(){
        if (!Objects.equals(departure_label.getText(), "倫敦(LON)")){
            destination_label.setText("倫敦(LON)");
            destination_label.getStyleClass().add("text-to-black");
            location_right.setVisible(false);
            AnimFunction.arrowDown(icon_right);
        }
        else {
            AnimFunction.showTopPopup(outerView, msg1);
        }
    }
    @FXML
    protected void MaldivesClicked_right(){
        if (!Objects.equals(departure_label.getText(), "馬爾地夫(MLE)")){
            destination_label.setText("馬爾地夫(MLE)");
            destination_label.getStyleClass().add("text-to-black");
            location_right.setVisible(false);
            AnimFunction.arrowDown(icon_right);
        }
        else {
            AnimFunction.showTopPopup(outerView, msg1);
        }
    }

    // switch location
    @FXML
    protected void switch_location(){
        if (!Objects.equals(departure_label.getText(), "出發地") && !Objects.equals(destination_label.getText(), "目的地")){
            String departure = destination_label.getText();
            String destination = departure_label.getText();
            departure_label.setText(departure);
            destination_label.setText(destination);
        }
        else {
//            System.out.println("請輸入正確出發地或目的地");
        }
    }

    // input Date
    @FXML
    protected void set_date(){
        String s = Date.getText();
        Date.setText(s.replaceAll("[^0-9/]", ""));
        Date.positionCaret(s.length()+1);
        if(s.matches("\\d{4}") || s.matches("\\d{4}/\\d{2}")){
            s = s + "/";
            Date.setText(s);
            Date.positionCaret(s.length()+1);
        }
        if (s.length() == 10){
            confirm_date = s;
        }
        if (s.length() > 10){
            Date.setText(confirm_date);
            Date.positionCaret(s.length()+1);
        }
    }

    // Search button
    @FXML
    protected void search(){
        PublicController.bookDate = Date.getText();
        if (!departure_label.getText().equals("出發地") && !destination_label.getText().equals("目的地") && Validation.isValidDate(Date.getText())){
            departureText.setText(departure_label.getText().substring(0, departure_label.getText().length()-5));
            arriveText.setText(destination_label.getText().substring(0, destination_label.getText().length()-5));
            titlePane.setVisible(true);

            // clear previous search result
            if (vbox.getChildren().size() >= 2)
                clearAllCards();
            if (currentSort != null) {
                currentSort.getStyleClass().remove(currentSort.getStyleClass().size()-1);
                currentSort = null;
            }

            DatabaseConnection connection = new DatabaseConnection();
            Connection connectDB = connection.getConnection();
            Statement statement = null;
            ResultSet resultSet = null;
            try {
                String command = "SELECT * FROM flightData WHERE `from` = '" + departure_label.getText() + "' and `to`= '" + destination_label.getText() + "'";
                statement = connectDB.createStatement();
                resultSet = statement.executeQuery(command);
                if (resultSet.next()) {
                    //load data
                    do {
                        FXMLLoader loader = new FXMLLoader(Main.class.getResource("components/FlightInfoCard.fxml"));
                        ArrayList<String> data = new ArrayList<>();
                        data.add(resultSet.getString("id"));
                        data.add(resultSet.getString("airline"));
                        data.add(resultSet.getString("flightNo"));
                        data.add(resultSet.getString("departure"));
                        data.add(resultSet.getString("arrive"));
                        data.add(resultSet.getString("time"));
                        data.add(resultSet.getString("economyClassPrice"));
                        Pane card = loader.load();
                        FlightInfoCardController controller = loader.getController();
                        controller.passData(data);
                        vbox.getChildren().add(card);
                    }while(resultSet.next());
                    btnGroup1.setDisable(false);
                    btnGroup2.setDisable(false);
                    btnGroup3.setDisable(false);
                    btnGroup4.setDisable(false);
                    btnGroup5.setDisable(false);
                    btnGroup6.setDisable(false);
                }
                else {
                    //no data
                    FXMLLoader loader = new FXMLLoader(Main.class.getResource("components/FlightInfoCard.fxml"));
                    vbox.getChildren().add(loader.load());
//                    System.out.println("no data");
                    btnGroup1.setDisable(true);
                    btnGroup2.setDisable(true);
                    btnGroup3.setDisable(true);
                    btnGroup4.setDisable(true);
                    btnGroup5.setDisable(true);
                    btnGroup6.setDisable(true);
                }
            } catch (SQLException | IOException e) {
                e.printStackTrace();
                AnimFunction.showTopPopup(outerView, msg3);
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
        else {
            AnimFunction.showTopPopup(outerView, msg2);
        }
    }

    // six select button

    // lowest price

    @FXML
    protected void low_price(MouseEvent event){
        Label btn = (Label) event.getSource();
        if (currentSort != null && !currentSort.getId().equals(btn.getId())) {
            currentSort.getStyleClass().remove(currentSort.getStyleClass().size()-1);
        }
        currentSort = btn;
        btn.getStyleClass().add("text_pressed");
        sorting("economyClassPrice", "ASC");
    }
    // shortest flying time
    @FXML
    protected void shortest_flyingtime(MouseEvent event){
        Label btn = (Label) event.getSource();
        if (currentSort != null && !currentSort.getId().equals(btn.getId())) {
            currentSort.getStyleClass().remove(currentSort.getStyleClass().size()-1);
        }
        currentSort = btn;
        btn.getStyleClass().add("text_pressed");
        sorting("time", "ASC");
    }
    // early departure time
    @FXML
    protected void early_departuretime(MouseEvent event){
        Label btn = (Label) event.getSource();
        if (currentSort != null && !currentSort.getId().equals(btn.getId())) {
            currentSort.getStyleClass().remove(currentSort.getStyleClass().size()-1);
        }
        currentSort = btn;
        btn.getStyleClass().add("text_pressed");
        sorting("departure", "ASC");
    }
    // latest departure time
    @FXML
    protected void latest_departuretime(MouseEvent event){
        Label btn = (Label) event.getSource();
        if (currentSort != null && !currentSort.getId().equals(btn.getId())) {
            currentSort.getStyleClass().remove(currentSort.getStyleClass().size()-1);
        }
        currentSort = btn;
        btn.getStyleClass().add("text_pressed");
        sorting("departure", "DESC");
    }
    @FXML
    // early arrive time
    protected void early_arrivetime(MouseEvent event){
        Label btn = (Label) event.getSource();
        if (currentSort != null && !currentSort.getId().equals(btn.getId())) {
            currentSort.getStyleClass().remove(currentSort.getStyleClass().size()-1);
        }
        currentSort = btn;
        btn.getStyleClass().add("text_pressed");
        sorting("arrive", "ASC");
    }
    @FXML
    // latest arrive time
    protected void latest_arrivetime(MouseEvent event){
        Label btn = (Label) event.getSource();
        if (currentSort != null && !currentSort.getId().equals(btn.getId())) {
            currentSort.getStyleClass().remove(currentSort.getStyleClass().size()-1);
        }
        currentSort = btn;
        btn.getStyleClass().add("text_pressed");
        sorting("arrive", "DESC");
    }

    public void clearAllCards() {
        vbox.getChildren().remove(2, vbox.getChildren().size());
    }

    public void sorting(String columnName, String order) {
        if (vbox.getChildren().size() >= 2)
            clearAllCards();

        DatabaseConnection connection = new DatabaseConnection();
        Connection connectDB = connection.getConnection();
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            String command = "SELECT * FROM flightData WHERE `from` = '" + departure_label.getText() + "' and `to`= '" + destination_label.getText() + "' ORDER BY `" + columnName + "` " + order;
            statement = connectDB.createStatement();
            resultSet = statement.executeQuery(command);
            resultSet.next();
            //load data
            do {
                FXMLLoader loader = new FXMLLoader(Main.class.getResource("components/FlightInfoCard.fxml"));
                ArrayList<String> data = new ArrayList<>();
                data.add(resultSet.getString("id"));
                data.add(resultSet.getString("airline"));
                data.add(resultSet.getString("flightNo"));
                data.add(resultSet.getString("departure"));
                data.add(resultSet.getString("arrive"));
                data.add(resultSet.getString("time"));
                data.add(resultSet.getString("economyClassPrice"));
                Pane card = loader.load();
                FlightInfoCardController controller = loader.getController();
                controller.passData(data);
                vbox.getChildren().add(card);
            }while(resultSet.next());
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            AnimFunction.showTopPopup(outerView, msg3);
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
