package controller;

import model.Appointment;
import model.AppointmentDAO;

public class AppointmentController {

    public String registerAppointment(String patientName, String contactNumber, String dentist, String treatment) {
        
        // 1. Pack the data into the model
        Appointment appt = new Appointment();
        appt.setPatientName(patientName);
        appt.setContactNumber(contactNumber);
        appt.setDentist(dentist);
        appt.setTreatment(treatment);
        
        // 2. Send to the Data Access Object
        AppointmentDAO dao = new AppointmentDAO();
        boolean isSaved = dao.saveAppointment(appt);
        
        // 3. Return the result back to the View
        if (isSaved) {
            return "SUCCESS";
        } else {
            return "Failed to save to database.";
        }
    }
}