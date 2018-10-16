package com.zeiss.plan.service.impl;

import com.zeiss.device.service.api.Device;
import com.zeiss.patient.service.api.Patient;
import com.zeiss.plan.service.api.PlanningUnit;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDate;
import java.util.Objects;

public class PlanningUnitImpl implements PlanningUnit {

    private ObjectProperty<Device> device = new SimpleObjectProperty<>();
    private ObjectProperty<Patient> patient = new SimpleObjectProperty<>();
    private ObjectProperty<LocalDate> planDate = new SimpleObjectProperty<>();
    private StringProperty planTime = new SimpleStringProperty();
    private StringProperty firstName = new SimpleStringProperty();
    private StringProperty lastName = new SimpleStringProperty();
    private StringProperty id = new SimpleStringProperty();

    public PlanningUnitImpl() {
    }

    public PlanningUnitImpl(ObjectProperty<Device> device, ObjectProperty<Patient> patient,
                            ObjectProperty<LocalDate> planDate, StringProperty planTime, StringProperty firstName,
                            StringProperty lastName, StringProperty id) {
        this.device = device;
        this.patient = patient;
        this.planDate = planDate;
        this.planTime = planTime;
        this.firstName = firstName;
        this.lastName = lastName;
        this.id = id;
    }

    @Override
    public Device getDevice() {
        return device.get();
    }

    @Override
    public ObjectProperty<Device> deviceProperty() {
        return device;
    }

    public void setDevice(Device device) {
        this.device.set(device);
    }

    @Override
    public Patient getPatient() {
        return patient.get();
    }

    public ObjectProperty<Patient> patientProperty() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient.set(patient);
    }

    @Override
    public LocalDate getPlanDate() {
        return planDate.get();
    }

    @Override
    public ObjectProperty<LocalDate> planDateProperty() {
        return planDate;
    }

    public void setPlanDate(LocalDate planDate) {
        this.planDate.set(planDate);
    }

    @Override
    public String getPlanTime() {
        return planTime.get();
    }

    @Override
    public StringProperty planTimeProperty() {
        return planTime;
    }

    public void setPlanTime(String planTime) {
        this.planTime.set(planTime);
    }

    @Override
    public String getFirstName() {
        return firstName.get();
    }

    public StringProperty firstNameProperty() {
        return firstName;
    }

    @Override
    public String getId() {
        return id.get();
    }

    @Override
    public StringProperty idProperty() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id.set(id);

    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    @Override
    public String getLastName() {
        return lastName.get();
    }

    public StringProperty lastNameProperty() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlanningUnitImpl that = (PlanningUnitImpl) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "PlanningUnitImpl{" +
                "device=" + device +
                ", patient=" + patient +
                ", planDate=" + planDate +
                ", planTime=" + planTime +
                '}';
    }
}
