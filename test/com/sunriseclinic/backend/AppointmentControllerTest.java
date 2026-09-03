package com.sunriseclinic.backend;

import controller.AppointmentController;
import org.junit.Test;
import static org.junit.Assert.*;

public class AppointmentControllerTest {

    @Test
    public void testSuccessfulRegistration() {
        AppointmentController controller = new AppointmentController();
        
        String result = controller.registerAppointment(
            "Test Patient", 
            "0712223334", 
            "Dr. Perera", 
            "Whitening", 
            "2027-12-01 08:30 AM" 
        );
        

        assertEquals("The appointment should be saved successfully.", "SUCCESS", result);
    }

    @Test
    public void testDoubleBookingConflict() {
        AppointmentController controller = new AppointmentController();
        
   
        controller.registerAppointment(
            "Patient One", 
            "0711111111", 
            "Dr. Smith", 
            "Filling", 
            "2026-09-05 10:00 AM"
        );
        
 
        String conflictResult = controller.registerAppointment(
            "Patient Two", 
            "0722222222", 
            "Dr. Smith", 
            "Cleaning", 
            "2026-09-05 10:00 AM" 
        );
        

        assertTrue("The system must block double bookings.", conflictResult.startsWith("CONFLICT:"));
    }
}