package controller;

import model.AppointmentDAO;
import java.sql.ResultSet;
import java.time.LocalDate;

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
                
                // 3. Calculate Cost based on Treatment Type
                double cost = 0.0;
                switch (treatment) {
                    case "Cleaning": cost = 50.00; break;
                    case "Whitening": cost = 120.00; break;
                    case "Filling": cost = 150.00; break;
                    case "Root Canal": cost = 500.00; break;
                    case "Extraction": cost = 200.00; break;
                    default: cost = 0.0; break;
                }

                // 4. Format the final receipt string
                String receipt = "============================================\n";
                receipt += "           SUNRISE DENTAL CLINIC\n";
                receipt += "============================================\n";
                receipt += "Date: " + LocalDate.now() + "\n";
                receipt += "Invoice For: " + name + " (ID: " + id + ")\n";
                receipt += "Attending Dentist: " + dentist + "\n";
                receipt += "--------------------------------------------\n";
                receipt += "Treatment Provided: \t" + treatment + "\n";
                receipt += "Treatment Fee: \t\t$" + String.format("%.2f", cost) + "\n";
                receipt += "Registration Fee: \t$50.00\n";
                receipt += "--------------------------------------------\n";
                receipt += "TOTAL AMOUNT DUE: \t$" + String.format("%.2f", (cost + 50.00)) + "\n";
                receipt += "============================================\n";
                receipt += "      Thank you for your visit!\n";

                return receipt;
            } else {
                return "ERROR: No appointment found with ID " + id;
            }
        } catch (Exception e) {
            return "ERROR: Database connection failed.";
        }
    }
}