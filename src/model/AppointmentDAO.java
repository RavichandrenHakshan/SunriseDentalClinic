package model;

import util.DBconnection;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class AppointmentDAO {
    
    public boolean saveAppointment(Appointment appt) {
        boolean isSuccess = false;
        try {
            java.sql.Connection con = util.DBconnection.getConnection();

            String sql = "INSERT INTO appointments (patient_name, contact_number, dentist, treatment, appointment_date) VALUES (?, ?, ?, ?, ?)";
            
            java.sql.PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, appt.getPatientName());
            pst.setString(2, appt.getContactNumber());
            pst.setString(3, appt.getDentist());
            pst.setString(4, appt.getTreatment());
            pst.setString(5, appt.getAppointmentDate());
            
            int rowsAffected = pst.executeUpdate();
            if (rowsAffected > 0) {
                isSuccess = true;
            }
        } catch (Exception e) {
            System.out.println("Error saving appointment: " + e);
        }
        return isSuccess;
    }
    
    public java.sql.ResultSet searchAppointments(String searchTerm) {
        java.sql.ResultSet rs = null;
        try {
            Connection con = DBconnection.getConnection();
            String sql;
            java.sql.PreparedStatement pst;

            if (searchTerm == null || searchTerm.isEmpty()) {
                sql = "SELECT * FROM appointments";
                pst = con.prepareStatement(sql);
            } else {
                sql = "SELECT * FROM appointments WHERE patient_name LIKE ?";
                pst = con.prepareStatement(sql);
                pst.setString(1, "%" + searchTerm + "%");
            }
            
            rs = pst.executeQuery();
            
        } catch (Exception e) {
            System.out.println("Error searching appointments: " + e);
        }
        return rs;
        
    }
    
    public java.sql.ResultSet getAppointmentById(int id) {
        java.sql.ResultSet rs = null;
        try {
            Connection con = DBconnection.getConnection();
            String sql = "SELECT * FROM appointments WHERE id = ?";
            java.sql.PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, id);
            rs = pst.executeQuery();
        } catch (Exception e) {
            System.out.println("Error fetching appointment by ID: " + e);
        }
        return rs;
    }
    
    public java.sql.ResultSet getAppointmentsByDentist(String dentistName) {
        java.sql.ResultSet rs = null;
        try {
            java.sql.Connection con = util.DBconnection.getConnection();
            String sql = "SELECT * FROM appointments WHERE dentist = ?";
            java.sql.PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, dentistName);
            rs = pst.executeQuery();
        } catch (Exception e) {
            System.out.println("Error fetching dentist schedule: " + e);
        }
        return rs;
    }
}