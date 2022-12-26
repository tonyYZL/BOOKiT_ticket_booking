package ticket_booking_system.final_project;

import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.utils.PdfMerger;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

public class CreateBoardingPass {
//    public static int transactionId;


    private static final String[] MONTH = {"JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"};
    public static int create() {
        //db
        DatabaseConnection connection = new DatabaseConnection();
        Connection connectDB = connection.getConnection();
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            //initialize data
            int page = 1;
            String templateNumber = RecordController.currentChoice;
            Random random = new Random();
            int seqNum = random.nextInt(200); //%04d
            // 0012

            char letter = 'A';
            String gate = (char)(letter + random.nextInt(0, 4)) + Integer.toString(random.nextInt(1, 11));
            // D7

            int zone = random.nextInt(1, 6);
            // 3
            int seatNum = random.nextInt(1, 80);
            char seatZone = (char)(letter + random.nextInt(0, 8));
            // 82H


            String command = "SELECT orderData.id, orderData.date, orderData.cabinClass, flightData.* FROM flightData INNER JOIN orderData ON orderData.id=flightData.id WHERE orderData.transactionId=" + PublicController.chosenTransactionId;
            statement = connectDB.createStatement();
            resultSet = statement.executeQuery(command);
            resultSet.next();
            String flight = resultSet.getString("flightNo");
            String cabinClass = resultSet.getString("cabinClass");
            String time = resultSet.getString("departure").replaceAll(":", "");
            String date = simplifyDate(resultSet.getString("date"));
            String from = resultSet.getString("from").substring(resultSet.getString("from").length()-4, resultSet.getString("from").length()-1);
            String to = resultSet.getString("to").substring(resultSet.getString("to").length()-4, resultSet.getString("to").length()-1);
            String boardingTime = getBoardingTime(time);

            FileChooser fileChooser = new FileChooser();
            fileChooser.setInitialFileName(from + "-" + to + " " + date);
            try {
                File file = fileChooser.showSaveDialog(null);
                fileChooser.setInitialDirectory(new File(System.getProperty("user.home") + "/Desktop/"));
                String dest = file.getAbsolutePath() + ".pdf";
                BoardingPassController.filePath = dest;
                PdfFont pdfFont = PdfFontFactory.createFont("Ticketing.ttf");

                PdfDocument mergedDoc = new PdfDocument(new PdfWriter(dest));
                PdfMerger merger = new PdfMerger(mergedDoc);
                Document document = new Document(mergedDoc);
                document.setMargins(0, 0, 0, 0);

                command = "SELECT * FROM passengerData WHERE passengerData.transactionId=" + PublicController.chosenTransactionId;
                statement = connectDB.createStatement();
                resultSet = statement.executeQuery(command);

                while (resultSet.next()) {
                    PdfDocument sourcePdf = new PdfDocument(new PdfReader(String.valueOf(Main.class.getResource("assets/templates/" + templateNumber + ".pdf"))));

                    merger.merge(sourcePdf, 1, 1).setCloseSourceDocuments(true);

                    String name = resultSet.getString("lastName") + " " + resultSet.getString("firstName");

                    Paragraph PassengerName = new Paragraph(name);
                    PassengerName.setFont(pdfFont).setFontSize(20);
                    PassengerName.setFixedPosition(page, 60, 150, PassengerName.getWidth());

                    Paragraph CabinClasses = new Paragraph();
                    if (cabinClass.equals("經濟艙")) {
                        CabinClasses.add("ECONOMY CLASS");
                    }
                    else if (cabinClass.equals("商務艙")) {
                        CabinClasses.add("BUSINESS CLASS");
                    }
                    else {
                        CabinClasses.add("FIRST CLASS");
                    }
                    CabinClasses.setFontSize(20);
                    CabinClasses.setFixedPosition(page, 264, 200, CabinClasses.getWidth());

                    Paragraph FlightDateTime = new Paragraph(flight + "  " + date + "  " + time);
                    FlightDateTime.setFont(pdfFont).setFontSize(20);
                    FlightDateTime.setFixedPosition(page, 254, 140, FlightDateTime.getWidth());

                    Paragraph From = new Paragraph(from);
                    From.setFont(pdfFont).setFontSize(20);
                    From.setFixedPosition(page, 87, 125, From.getWidth());

                    Paragraph To = new Paragraph(to);
                    To.setFont(pdfFont).setFontSize(20);
                    To.setFixedPosition(page, 87, 105, To.getWidth());


                    String seq = String.format("%04d", seqNum);
                    Paragraph TimeGateSeq = new Paragraph(boardingTime + "   " + gate + "   " + seq);
                    TimeGateSeq.setFont(pdfFont).setFontSize(20);
                    TimeGateSeq.setFixedPosition(page, 221, 84, TimeGateSeq.getWidth());

                    Paragraph Seat = new Paragraph(seatNum + String.valueOf(seatZone));
                    Seat.setFont(pdfFont).setFontSize(20);
                    Seat.setFixedPosition(page, 409, 87, Seat.getWidth());

                    Paragraph Zone = new Paragraph(String.valueOf(zone));
                    Zone.setFont(pdfFont).setFontSize(20);
                    Zone.setFixedPosition(page, 435, 64, Zone.getWidth());

                    String etkt = Long.toString(random.nextLong(1000000000000L, 9999999999999L));
                    Paragraph ETKT = new Paragraph("ETKT " + etkt);
                    ETKT.setFont(pdfFont).setFontSize(15);
                    ETKT.setFixedPosition(page, 100, 60, ETKT.getWidth());


                    Paragraph PassengerName2 = new Paragraph(name + "\n" + from + "  " + to + "\n\n" + flight + "  " + date);
                    PassengerName2.setFont(pdfFont).setFontSize(16);
                    PassengerName2.setFixedPosition(page, 481, 137, PassengerName2.getWidth());

                    Paragraph Seq = new Paragraph(seq);
                    Seq.setFont(pdfFont).setFontSize(20);
                    Seq.setFixedPosition(page, 484, 97, Seq.getWidth());

                    Paragraph Seat2 = new Paragraph(seatNum + String.valueOf(seatZone));
                    Seat2.setFont(pdfFont).setFontSize(20);
                    Seat2.setFixedPosition(page, 548, 97, Seat2.getWidth());

                    Paragraph Zone2 = new Paragraph(String.valueOf(zone));
                    Zone2.setFont(pdfFont).setFontSize(20);
                    Zone2.setFixedPosition(page, 574, 74, Zone2.getWidth());

                    document.add(PassengerName);
                    document.add(CabinClasses);
                    document.add(FlightDateTime);
                    document.add(From);
                    document.add(To);
                    document.add(TimeGateSeq);
                    document.add(Seat);
                    document.add(Zone);
                    document.add(ETKT);

                    document.add(PassengerName2);
                    document.add(Seq);
                    document.add(Seat2);
                    document.add(Zone2);

                    page++;
                    seqNum++;
                    seatNum++;
                }
                merger.close();
                mergedDoc.close();
//                sourcePdf.close();
                document.close();
                return 1;
            } catch (IOException e) {
                e.printStackTrace();
                return -1;
            } catch (NullPointerException e) {
                e.printStackTrace();
                return 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {connectDB.close();}catch (SQLException ignored) {}
            try {statement.close();}catch (SQLException ignored) {}
            try {resultSet.close();}catch (SQLException ignored) {}
        }
        return 0;
    }

    public static String simplifyDate(String date) {
        String[] split = date.split("/");
        String month = MONTH[Integer.parseInt(split[1])-1];
        String day = "";
        if (split[2].charAt(0) == '0') {
            day += split[1].charAt(1);
        }
        else {
            day = split[2];
        }
        return day + month;
    }
    public static String getBoardingTime(String time) {
        int hours = Integer.parseInt(time.substring(0, 2));
        int minutes = Integer.parseInt(time.substring(2));
        if (minutes < 40) {
            hours = (hours + 23) % 24;
            minutes += 60;
        }
        minutes -= 40;
        return String.format("%02d%02d", hours, minutes);
    }
}
