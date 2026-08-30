package controller;

import model.AppointmentDAO;
import java.sql.ResultSet;
import javax.swing.table.DefaultTableModel;

public class SearchController {

    public DefaultTableModel getAppointmentTable(String searchTerm) {
        // 1. Define the table structure
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("ID");
        tableModel.addColumn("Patient Name");
        tableModel.addColumn("Contact");
        tableModel.addColumn("Dentist");
        tableModel.addColumn("Treatment");

        try {
            // 2. Get raw data from DAO
            AppointmentDAO dao = new AppointmentDAO();
            ResultSet rs = dao.searchAppointments(searchTerm);

            // 3. Loop through the data and add it to the table rows
            if (rs != null) {
                while (rs.next()) {
                    Object[] row = new Object[5];
                    row[0] = rs.getInt("id");
                    row[1] = rs.getString("patient_name");
                    row[2] = rs.getString("contact_number");
                    row[3] = rs.getString("dentist");
                    row[4] = rs.getString("treatment");
                    tableModel.addRow(row);
                }
            }
        } catch (Exception e) {
            System.out.println("Error building table model: " + e);
        }

        return tableModel;
    }
}