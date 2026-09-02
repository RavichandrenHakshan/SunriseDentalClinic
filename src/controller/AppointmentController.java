package controller;

import model.Appointment;
import model.AppointmentDAO;

public class AppointmentController {

    public String registerAppointment(String patientName, String contactNumber, String dentist, String treatment, String appointmentDate) {

        String cleanDentist = dentist.trim();
        String cleanDate = appointmentDate.trim();
        
        AppointmentDAO dao = new AppointmentDAO();
        
        
        if (dao.checkConflict(cleanDentist, cleanDate)) {
            return "CONFLICT: " + cleanDentist + " is already booked at " + cleanDate + ". Please choose a different time.";
        }

        Appointment appt = new Appointment();
        appt.setPatientName(patientName.trim());
        appt.setContactNumber(contactNumber.trim());
        appt.setDentist(cleanDentist);
        appt.setTreatment(treatment.trim());
        appt.setAppointmentDate(cleanDate); 

        boolean isSaved = dao.saveAppointment(appt);
        
        if (isSaved) {
            return "SUCCESS";
        } else {
            return "Failed to save to database.";
        }
    }
}