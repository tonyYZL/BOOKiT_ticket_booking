package ticket_booking_system.final_project;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

public class Validation {
    public static int[] day = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    public static boolean isChinese(String str){
        String pattern = "[\u4e00-\u9fff]+";
        return Pattern.matches(pattern, str);
    }

    public static boolean isUpperAlpha(String str){
        String pattern = "[A-z]+";
        return Pattern.matches(pattern, str);
    }

    public static boolean isPassword(String str){
        String pattern = "(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d!@#$%&*_]{6,}";
        return Pattern.matches(pattern, str);
    }

    public static boolean isValidUsername(String str){
        String pattern = "(?=.*[A-Za-z\\d])[A-Za-z\\d_.]+";
        return Pattern.matches(pattern, str);
    }

    public static boolean isValidPhoneNumber(String str){
        String pattern = "(?=09[\\d]+)[\\d]{10}";
        return Pattern.matches(pattern, str);
    }

    public static boolean isEmail(String str) {
        String pattern = "\\w+([.-]?\\w+)*@\\w+([.-]?\\w+)*(\\.\\w{2,3})+";
        return Pattern.matches(pattern, str);
    }

    public static boolean isValidDate(String str) {
        String pattern = "\\d{4}/\\d{2}/\\d{2}";
        if (!Pattern.matches(pattern, str)) {
            return false;
        }

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDateTime now = LocalDateTime.now();
        String date = dtf.format(now);

        String[] split = str.split("/");
        int yyyy = Integer.parseInt(split[0]);
        int mm = Integer.parseInt(split[1]);
        int dd = Integer.parseInt(split[2]);

        String[] nowSplit = date.split("/");
        int nowYYYY = Integer.parseInt(nowSplit[0]);
        int nowMM = Integer.parseInt(nowSplit[1]);
        int nowDD = Integer.parseInt(nowSplit[2]);

        if (mm > 12) {
            return false;
        }
        if (dd > day[mm-1]) {
            return false;
        }

        if (yyyy - nowYYYY < 0 || yyyy - nowYYYY > 1) {
            return false;
        }
        else if (yyyy - nowYYYY == 0) {
            if (mm > nowMM) {
                return true;
            }
            else if (mm < nowMM) {
                return false;
            }
            else {
                return dd >= nowDD;
            }
        }
        else {
            if (mm < nowMM) {
                return true;
            }
            else if (mm > nowMM) {
                return false;
            }
            else {
                return dd < nowDD;
            }
        }
    }
}
