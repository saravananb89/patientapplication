package com.zeiss.plan.service.api;

import com.zeiss.device.service.api.Device;
import com.zeiss.patient.service.api.Patient;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDate;

public interface PlanningUnit {

    String getId();

    StringProperty idProperty();

    void setId(String id);

    void setFirstName(String firstName);

    void setLastName(String lastName);

    void setDevice(Device device);

    void setPatient(Patient patient);

    void setPlanDate(LocalDate planDate);

    void setPlanTime(String planTime);

    Device getDevice();

    ObjectProperty<Device> deviceProperty();

    Patient getPatient();

    ObjectProperty<Patient> patientProperty();

    LocalDate getPlanDate();

    ObjectProperty<LocalDate> planDateProperty();

    String getPlanTime();

    StringProperty planTimeProperty();

    String getFirstName();

    StringProperty firstNameProperty();

    String getLastName();

    StringProperty lastNameProperty();

}
