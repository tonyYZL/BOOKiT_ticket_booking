package ticket_booking_system.final_project;

import javafx.application.HostServices;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class BoardingPassController {
    public static HostServices hostServices;
    public static String filePath = "";

    public void openFile() {
        String encodedURL = URLEncoder.encode(filePath, StandardCharsets.UTF_8);
        encodedURL = encodedURL.replaceAll("%2F", "/");
        encodedURL = encodedURL.replaceAll("[+]", "%20");
        hostServices.showDocument("file://" + encodedURL);
    }
}
