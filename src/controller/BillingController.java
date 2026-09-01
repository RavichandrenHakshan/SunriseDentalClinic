package controller;

import model.AppointmentDAO;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.io.File;
import java.io.FileWriter;
import java.awt.Desktop;

public class BillingController {

    public String generateReceipt(String patientIdString) {
        
        // 1. Validate that the ID is a valid number
        int id;
        try {
            id = Integer.parseInt(patientIdString);
        } catch (NumberFormatException e) {
            return "ERROR: Please enter a valid numerical Patient ID.";
        }

        // 2. Fetch the patient data from the Database
        AppointmentDAO dao = new AppointmentDAO();
        ResultSet rs = dao.getAppointmentById(id);

        try {
            if (rs != null && rs.next()) {
                String name = rs.getString("patient_name");
                String dentist = rs.getString("dentist");
                String treatment = rs.getString("treatment");
                
                // 3. Calculate Cost
                double cost = 0.0;
                switch (treatment) {
                    case "Cleaning": cost = 50.00; break;
                    case "Whitening": cost = 120.00; break;
                    case "Filling": cost = 150.00; break;
                    case "Root Canal": cost = 500.00; break;
                    case "Extraction": cost = 200.00; break;
                    default: cost = 0.0; break;
                }
                double total = cost + 50.00;


                String htmlContent = "<html><head><title>Receipt - Sunrise Dental</title>"
                        + "<style>"
                        + "body { font-family: Arial, sans-serif; padding: 40px; background-color: #f4f4f9; }"
                        + ".receipt-box { max-width: 600px; margin: auto; padding: 30px; border: 1px solid #ddd; box-shadow: 0 0 10px rgba(0,0,0,0.1); background-color: white; }"
                        + "h2 { text-align: center; color: #2c3e50; }"
                        + "table { width: 100%; border-collapse: collapse; margin-top: 20px; }"
                        + "th, td { padding: 12px; border-bottom: 1px solid #ddd; text-align: left; }"
                        + ".total { font-weight: bold; font-size: 1.2em; color: #e74c3c; }"
                        + ".footer { text-align: center; margin-top: 30px; font-size: 0.9em; color: #777; }"
                        + ".download-btn { display: block; width: 250px; margin: 30px auto 0; padding: 15px; background-color: #27ae60; color: white; text-align: center; text-decoration: none; font-size: 16px; font-weight: bold; border-radius: 5px; cursor: pointer; border: none; }"
                        + ".download-btn:hover { background-color: #2ecc71; }"
                        + "@media print { .no-print { display: none !important; } body { padding: 0; background-color: white; } .receipt-box { box-shadow: none; border: none; } }"
                        + "</style></head><body>"
                        + "<div class='receipt-box'>"
                        + "<h2>Sunrise Dental Clinic - Invoice</h2>"
                        + "<p><strong>Date:</strong> " + LocalDate.now() + "</p>"
                        + "<p><strong>Patient Name:</strong> " + name + " (ID: " + id + ")</p>"
                        + "<p><strong>Attending Dentist:</strong> " + dentist + "</p>"
                        + "<table>"
                        + "<tr><th>Description</th><th>Amount</th></tr>"
                        + "<tr><td>" + treatment + " Treatment</td><td>$" + String.format("%.2f", cost) + "</td></tr>"
                        + "<tr><td>Registration Fee</td><td>$50.00</td></tr>"
                        + "<tr class='total'><td>Total Amount Due</td><td>$" + String.format("%.2f", total) + "</td></tr>"
                        + "</table>"
                        + "<div class='footer'>Thank you for your visit!<br>Please present this invoice at the front desk.</div>"
                        + "<button class='download-btn no-print' onclick='window.print()'>Download PDF / Print</button>"
                        + "</div></body></html>";

                // 5. Save the HTML to a local file
                File file = new File("Sunrise_Receipt_" + id + ".html");
                FileWriter writer = new FileWriter(file);
                writer.write(htmlContent);
                writer.close();

                // 6. Force the computer to open the file in the default browser
                if (Desktop.isDesktopSupported()) {
                    Desktop.getDesktop().browse(file.toURI());
                }

                return "SUCCESS: Receipt generated and opened in browser.";
            } else {
                return "ERROR: No appointment found with ID " + id;
            }
        } catch (Exception e) {
            return "ERROR: Failed to generate or open receipt. " + e.getMessage();
        }
    }
}