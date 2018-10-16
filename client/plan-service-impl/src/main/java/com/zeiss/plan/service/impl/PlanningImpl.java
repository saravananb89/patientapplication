package com.zeiss.plan.service.impl;

import com.zeiss.plan.service.api.Planning;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.Objects;

public class PlanningImpl implements Planning {

    private StringProperty id = new SimpleStringProperty();
    private StringProperty patientId = new SimpleStringProperty();
    private StringProperty deviceId = new SimpleStringProperty();
    private StringProperty planTime = new SimpleStringProperty();
    private StringProperty planDate = new SimpleStringProperty();

    PlanningImpl(String id, String patientId, String deviceId, String planTime, String planDate) {
        this.id.set(id);
        this.patientId.set(patientId);
        this.deviceId.set(deviceId);
        this.planTime.set(planTime);
        this.planDate.set(planDate);
    }

    public PlanningImpl() {
    }

    @Override
    public String getPatientId() {
        return patientId.get();
    }

    @Override
    public StringProperty patientIdProperty() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId.set(patientId);
    }

    @Override
    public String getDeviceId() {
        return deviceId.get();
    }

    @Override
    public StringProperty deviceIdProperty() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId.set(deviceId);
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
    public String getPlanDate() {
        return planDate.get();
    }

    @Override
    public StringProperty planDateProperty() {
        return planDate;
    }

    public void setPlanDate(String planDate) {
        this.planDate.set(planDate);
    }

    @Override
    public String getId() {
        return id.get();
    }

    @Override
    public StringProperty idProperty() {
        return id;
    }

    public void setId(String id) {
        this.id.set(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlanningImpl that = (PlanningImpl) o;
        return Objects.equals(deviceId, that.deviceId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(deviceId);
    }

    @Override
    public String toString() {
        return "PlanningImpl{" +
                "patientId=" + patientId +
                ", deviceId=" + deviceId +
                ", planTime=" + planTime +
                ", planDate=" + planDate +
                '}';
    }
}
