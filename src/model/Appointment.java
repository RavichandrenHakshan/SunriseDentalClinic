package model;

public class Appointment {
    private String patientName;
    private String contactNumber;
    private String dentist;
    private String treatment;

    public String getPatientName() { return patientName; }
    public void setPatientName(String patientName) { this.patientName = patientName; }

    public String getContactNumber() { return contactNumber; }
    public void setContactNumber(String contactNumber) { this.contactNumber = contactNumber; }

    public String getDentist() { return dentist; }
    public void setDentist(String dentist) { this.dentist = dentist; }

    public String getTreatment() { return treatment; }
    public void setTreatment(String treatment) { this.treatment = treatment; }
} 