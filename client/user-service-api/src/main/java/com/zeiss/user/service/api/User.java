package com.zeiss.patient.service.api;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDate;

public interface Patient {

     String getUserName();

     StringProperty userNameProperty();

     void setUserName(String userName);

}
