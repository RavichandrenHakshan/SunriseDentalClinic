package controller;

import model.Appointment;
import model.AppointmentDAO;

public class AppointmentController {

    public String registerAppointment(String patientName, String contactNumber, String dentist, String treatment, String appointmentDate) {
        
        Appointment appt = new Appointment();
        appt.setPatientName(patientName);
        appt.setContactNumber(contactNumber);
        appt.setDentist(dentist);
        appt.setTreatment(treatment);
        appt.setAppointmentDate(appointmentDate); 
       
        AppointmentDAO dao = new AppointmentDAO();
        boolean isSaved = dao.saveAppointment(appt);
        
        if (isSaved) {
            return "SUCCESS";
        } else {
            return "Failed to save to database.";
        }
    }
}