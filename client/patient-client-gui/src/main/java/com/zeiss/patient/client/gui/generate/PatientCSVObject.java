package com.zeiss.patient.client.gui.generate;

public class PatientCSVObject {
    private   String firstName = "";
    private  String lastName = "";

    public PatientCSVObject() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
