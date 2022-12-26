package ticket_booking_system.final_project;

public class UserData {
    private static int id;
    private static String username;
    private static boolean haveCreditCard;

    public static void setId(int id) {
        UserData.id = id;
    }
    public static int getId() {
        return UserData.id;
    }
    public static void setUsername(String username) {
        UserData.username = username;
    }
    public static String getUsername() {
        return UserData.username;
    }
    public static void setHaveCreditCard(boolean b) { UserData.haveCreditCard = b;}
    public static boolean getHaveCreditCard() { return UserData.haveCreditCard; }

    public static void clearAll() {
        UserData.id = -1;
        UserData.username = null;
        UserData.haveCreditCard = false;
    }
}
