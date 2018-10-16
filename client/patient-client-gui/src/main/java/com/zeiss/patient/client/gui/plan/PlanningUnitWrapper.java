package com.zeiss.patient.client.gui.plan;

import com.zeiss.device.service.api.Device;
import com.zeiss.patient.service.api.Patient;
import com.zeiss.plan.service.api.PlanningUnit;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDate;

public class PlanningUnitWrapper implements PlanningUnit {

    private final PlanningUnit planningUnit;

    private ChangeType changeType;

    public PlanningUnitWrapper(PlanningUnit planningUnit, ChangeType changeType) {
        this.planningUnit = planningUnit;
        this.changeType = changeType;
    }

    public PlanningUnit getPlanningUnit() {
        return planningUnit;
    }

    public ChangeType getChangeType() {
        return changeType;
    }

    @Override
    public String getId() {
        return planningUnit.getId();
    }

    @Override
    public StringProperty idProperty() {
        return planningUnit.idProperty();
    }

    @Override
    public void setId(String id) {
        planningUnit.setId(id);
    }

    @Override
    public void setFirstName(String firstName) {
        planningUnit.setFirstName(firstName);
    }

    @Override
    public void setLastName(String lastName) {
        planningUnit.setLastName(lastName);
    }

    @Override
    public void setDevice(Device device) {
        planningUnit.setDevice(device);
    }

    @Override
    public void setPatient(Patient patient) {
        planningUnit.setPatient(patient);
    }

    @Override
    public void setPlanDate(LocalDate planDate) {
        planningUnit.setPlanDate(planDate);
    }

    @Override
    public void setPlanTime(String planTime) {
        planningUnit.setPlanTime(planTime);
    }

    @Override
    public Device getDevice() {
        return planningUnit.getDevice();
    }

    @Override
    public ObjectProperty<Device> deviceProperty() {
        return planningUnit.deviceProperty();
    }

    @Override
    public Patient getPatient() {
        return planningUnit.getPatient();
    }

    @Override
    public ObjectProperty<Patient> patientProperty() {
        return planningUnit.patientProperty();
    }

    @Override
    public LocalDate getPlanDate() {
        return planningUnit.getPlanDate();
    }

    @Override
    public ObjectProperty<LocalDate> planDateProperty() {
        return planningUnit.planDateProperty();
    }

    @Override
    public String getPlanTime() {
        return planningUnit.getPlanTime();
    }

    @Override
    public StringProperty planTimeProperty() {
        return planningUnit.planTimeProperty();
    }

    @Override
    public String getFirstName() {
        return planningUnit.getFirstName();
    }

    @Override
    public StringProperty firstNameProperty() {
        return planningUnit.firstNameProperty();
    }

    @Override
    public String getLastName() {
        return planningUnit.getLastName();
    }

    @Override
    public StringProperty lastNameProperty() {
        return planningUnit.lastNameProperty();
    }

    public void setChangeType(ChangeType changeType) {
        this.changeType = changeType;
    }
}
