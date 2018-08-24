package com.zeiss.role.service.impl;

import com.zeiss.role.service.api.Role;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.Objects;

public class RoleImpl implements Role {

    private final StringProperty roleName = new SimpleStringProperty();
    private final IntegerProperty patientTabAccess = new SimpleIntegerProperty();
    private final IntegerProperty visitTabAccess = new SimpleIntegerProperty();
    private final IntegerProperty userTabAccess = new SimpleIntegerProperty();
    private final IntegerProperty roleTabAccess = new SimpleIntegerProperty();

    public RoleImpl(String roleName, Integer patientTabAccess, Integer visitTabAccess, Integer userTabAccess, Integer roleTabAccess) {
        this.roleName.setValue(roleName);
        this.patientTabAccess.setValue(patientTabAccess);
        this.visitTabAccess.setValue(visitTabAccess);
        this.userTabAccess.setValue(userTabAccess);
        this.roleTabAccess.setValue(roleTabAccess);
    }

    public RoleImpl() {
    }

    @Override
    public String getRoleName() {
        return roleName.get();
    }

    @Override
    public StringProperty roleNameProperty() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName.set(roleName);
    }

    @Override
    public Integer getPatientTabAccess() {
        return patientTabAccess.get();
    }

    @Override
    public IntegerProperty patientTabAccessProperty() {
        return patientTabAccess;
    }

    public void setPatientTabAccess(Integer patientTabAccess) {
        this.patientTabAccess.set(patientTabAccess);
    }

    @Override
    public Integer getVisitTabAccess() {
        return visitTabAccess.get();
    }

    @Override
    public IntegerProperty visitTabAccessProperty() {
        return visitTabAccess;
    }

    public void setVisitTabAccess(Integer visitTabAccess) {
        this.visitTabAccess.set(visitTabAccess);
    }

    @Override
    public Integer getUserTabAccess() {
        return userTabAccess.get();
    }

    @Override
    public IntegerProperty userTabAccessProperty() {
        return userTabAccess;
    }

    public void setUserTabAccess(Integer userTabAccess) {
        this.userTabAccess.set(userTabAccess);
    }

    @Override
    public Integer getRoleTabAccess() {
        return roleTabAccess.get();
    }

    @Override
    public IntegerProperty roleTabAccessProperty() {
        return roleTabAccess;
    }

    public void setRoleTabAccess(Integer roleTabAccess) {
        this.roleTabAccess.set(roleTabAccess);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoleImpl role = (RoleImpl) o;
        return Objects.equals(roleName, role.roleName);
    }

    @Override
    public int hashCode() {

        return Objects.hash(roleName);
    }

    @Override
    public String toString() {
        return "RoleImpl{" +
                "roleName=" + roleName +
                ", patientTabAccess=" + patientTabAccess +
                ", visitTabAccess=" + visitTabAccess +
                ", userTabAccess=" + userTabAccess +
                ", roleTabAccess=" + roleTabAccess +
                '}';
    }
}
