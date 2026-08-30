package model;

import util.DBconnection;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class AppointmentDAO {
    
    public boolean saveAppointment(Appointment appt) {
        boolean isSuccess = false;
        try {
            Connection con = DBconnection.getConnection();
            String sql = "INSERT INTO appointments (patient_name, contact_number, dentist, treatment) VALUES (?, ?, ?, ?)";
            
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, appt.getPatientName());
            pst.setString(2, appt.getContactNumber());
            pst.setString(3, appt.getDentist());
            pst.setString(4, appt.getTreatment());
            
            int rowsAffected = pst.executeUpdate();
            if (rowsAffected > 0) {
                isSuccess = true;
            }
            
        } catch (Exception e) {
            System.out.println("Error saving appointment: " + e);
        }
        return isSuccess;
    }
}