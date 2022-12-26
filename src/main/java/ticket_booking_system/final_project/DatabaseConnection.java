package ticket_booking_system.final_project;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {
    public Connection databaseLink;

    public Connection getConnection() {
        String databaseName = "system";
        String databaseUser = "root";
        String databasePassword = "tony920214";
        String url = "jdbc:mysql://localhost/" + databaseName;

        try {
//            Class.forName("com.mysql.cj.jdbc.Driver");
            databaseLink = DriverManager.getConnection(url, databaseUser, databasePassword);
        }
        catch (Exception e) {
            e.printStackTrace();
//            System.out.println("資料庫沒開啦北七");
        }
        return databaseLink;
    }
}
