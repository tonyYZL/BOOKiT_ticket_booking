package ticket_booking_system.final_project;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.text.NumberFormat;
import java.util.Locale;

public class PublicController extends Main {

    // GUI Node
    public static BorderPane mainPane;
    public static Pane currentScreenIndicator;

    // State
    public static String bookDate; // 航班日期
    public static int bookId; // 航班id
    public static int numberOfPassenger; // 乘客數
    public static boolean buttonOn = false; // 訂單切換按鈕狀態
    public static boolean orderCreated = false;
    public static int chosenTransactionId; // 登機證資料


    //switch screen function
    public static void goToHomeScreen() {
        Loader object = new Loader();
        Pane view = object.getScreen("HomeScreen");
        mainPane.setCenter(view);
        AnimFunction.moveTo(170);
    }
    public static void goToSearchScreen() {
        Loader object = new Loader();
        Pane view = object.getScreen("SearchScreen");
        mainPane.setCenter(view);
        AnimFunction.moveTo(250);
    }
    public static void goToBookingScreen() {
        Loader object = new Loader();
        Pane view = object.getScreen("BookingScreen");
        mainPane.setCenter(view);
    }
    public static void goToRecordScreen() {
        Loader object = new Loader();
        Pane view = object.getScreen("RecordScreen");
        mainPane.setCenter(view);
        AnimFunction.moveTo(330);
    }
    public static void goToInfoScreen() {
        Loader object = new Loader();
        Pane view = object.getScreen("UserInfoScreen");
        mainPane.setCenter(view);
        AnimFunction.moveTo(410);
    }





    // animation


    //format text
    public static String formatTime(String time) {
        time = time.replaceAll("05", "5");
        if (time.charAt(0) == '0') {
            time = time.substring(1);
        }
        return time;
    }
    public static String formatDate(String date) {
        String[] splitDate = date.split("/");
        if (splitDate[1].charAt(0) == '0') {
            splitDate[1] = String.valueOf(splitDate[1].charAt(1));
        }
        if (splitDate[2].charAt(0) == '0') {
            splitDate[2] = String.valueOf(splitDate[2].charAt(1));
        }
        return splitDate[0] + "年" + splitDate[1] + "月" + splitDate[2] + "日";
    }
    public static String formatPrice(int price) {
        return NumberFormat.getNumberInstance(Locale.US).format(price);
    }
}
