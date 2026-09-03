package controller;

import model.AppointmentDAO;
import java.sql.ResultSet;
import javax.swing.table.DefaultTableModel;

public class SearchController {

    public DefaultTableModel getAppointmentTable(String searchTerm) {
        // 1. Define the table structure (Added Date & Time)
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("ID");
        tableModel.addColumn("Patient Name");
        tableModel.addColumn("Contact");
        tableModel.addColumn("Dentist");
        tableModel.addColumn("Treatment");
        tableModel.addColumn("Date & Time"); 

        try {
            // 2. Get raw data from DAO
            AppointmentDAO dao = new AppointmentDAO();
            
            // Handle null searches safely by converting to empty string
            if (searchTerm == null) {
                searchTerm = "";
            }
            
            ResultSet rs = dao.searchAppointments(searchTerm);

            // 3. Loop through the data and add it to the table rows
            if (rs != null) {
                while (rs.next()) {
                    Object[] row = new Object[6];
                    row[0] = rs.getString("id");
                    row[1] = rs.getString("patient_name");
                    row[2] = rs.getString("contact_number");
                    row[3] = rs.getString("dentist");
                    row[4] = rs.getString("treatment");
                    row[5] = rs.getString("appointment_date"); 
                    tableModel.addRow(row);
                }
            }
        } catch (Exception e) {
            System.out.println("Error building table model: " + e);
        }

        return tableModel;
    }
}