package com.zeiss.patient.service.api;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDate;

public interface Patient {

     String getId();

     StringProperty idProperty();

     void setId(String id);

     String getEmail();

     StringProperty emailProperty();

     void setEmail(String email);

     String getFirstName();

     StringProperty firstNameProperty();

     void setFirstName(String firstName);

     void setLastName(String lastName);

     void setAge(String age);

     void setDob(String dob);

     String getLastName();

     StringProperty lastNameProperty();

     String getAge();

     StringProperty ageProperty();

     LocalDate getDob();

     ObjectProperty<LocalDate> dobProperty();

}
