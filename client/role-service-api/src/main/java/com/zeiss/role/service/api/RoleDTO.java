package com.zeiss.role.service.api;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.Objects;

public class RoleDTO {

    private StringProperty roleName = new SimpleStringProperty();
    private IntegerProperty patientAccess = new SimpleIntegerProperty();
    private IntegerProperty visitAccess = new SimpleIntegerProperty();
    private IntegerProperty userAccess = new SimpleIntegerProperty();
    private IntegerProperty roleAccess = new SimpleIntegerProperty();
    private IntegerProperty deviceAccess = new SimpleIntegerProperty();

    public RoleDTO() {
    }

    public RoleDTO(String roleName, int patientAccess, int visitAccess, int userAccess, int roleAccess, int deviceAccess) {
        this.roleName.set(roleName);
        this.patientAccess.set(patientAccess);
        this.visitAccess.set(visitAccess);
        this.userAccess.set(userAccess);
        this.roleAccess.set(roleAccess);
        this.deviceAccess.set(deviceAccess);
    }

    public String getRoleName() {
        return roleName.get();
    }

    public StringProperty roleNameProperty() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName.set(roleName);
    }

    public int getPatientAccess() {
        return patientAccess.get();
    }

    public IntegerProperty patientAccessProperty() {
        return patientAccess;
    }

    public void setPatientAccess(int patientAccess) {
        this.patientAccess.set(patientAccess);
    }

    public int getVisitAccess() {
        return visitAccess.get();
    }

    public IntegerProperty visitAccessProperty() {
        return visitAccess;
    }

    public void setVisitAccess(int visitAccess) {
        this.visitAccess.set(visitAccess);
    }

    public int getUserAccess() {
        return userAccess.get();
    }

    public IntegerProperty userAccessProperty() {
        return userAccess;
    }

    public void setUserAccess(int userAccess) {
        this.userAccess.set(userAccess);
    }

    public int getRoleAccess() {
        return roleAccess.get();
    }

    public IntegerProperty roleAccessProperty() {
        return roleAccess;
    }

    public void setRoleAccess(int roleAccess) {
        this.roleAccess.set(roleAccess);
    }

    public int getDeviceAccess() {
        return deviceAccess.get();
    }

    public IntegerProperty deviceAccessProperty() {
        return deviceAccess;
    }

    public void setDeviceAccess(int deviceAccess) {
        this.deviceAccess.set(deviceAccess);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoleDTO roleDTO = (RoleDTO) o;
        return Objects.equals(roleName, roleDTO.roleName);
    }

    @Override
    public int hashCode() {

        return Objects.hash(roleName);
    }

    @Override
    public String toString() {
        return "RoleDTO{" +
                "roleName=" + roleName +
                ", patientAccess=" + patientAccess +
                ", visitAccess=" + visitAccess +
                ", userAccess=" + userAccess +
                ", roleAccess=" + roleAccess +
                ", deviceAccess=" + deviceAccess +
                '}';
    }
}
