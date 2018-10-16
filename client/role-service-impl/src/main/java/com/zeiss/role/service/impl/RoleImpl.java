package com.zeiss.role.service.impl;

import com.zeiss.role.service.api.Access;
import com.zeiss.role.service.api.Role;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.Objects;

public class RoleImpl implements Role {

    private final StringProperty roleName = new SimpleStringProperty("");
    private final ObjectProperty<Access> patientAccess = new SimpleObjectProperty<>(Access.NO_ACCESS);
    private final ObjectProperty<Access> visitAccess = new SimpleObjectProperty<>(Access.NO_ACCESS);
    private final ObjectProperty<Access> userAccess = new SimpleObjectProperty<>(Access.NO_ACCESS);
    private final ObjectProperty<Access> roleAccess = new SimpleObjectProperty<>(Access.NO_ACCESS);
    private final ObjectProperty<Access> deviceAccess = new SimpleObjectProperty<>(Access.NO_ACCESS);

    RoleImpl(String roleName, Access patientAccess, Access visitAccess, Access userAccess, Access roleAccess,
             Access deviceAccess) {
        this.roleName.setValue(roleName);
        this.patientAccess.setValue(patientAccess);
        this.visitAccess.setValue(visitAccess);
        this.userAccess.setValue(userAccess);
        this.roleAccess.setValue(roleAccess);
        this.deviceAccess.setValue(deviceAccess);
    }

    public RoleImpl() {
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

    public Access getPatientAccess() {
        return patientAccess.get();
    }

    public ObjectProperty<Access> patientAccessProperty() {
        return patientAccess;
    }

    public void setPatientAccess(Access patientAccess) {
        this.patientAccess.set(patientAccess);
    }

    public Access getVisitAccess() {
        return visitAccess.get();
    }

    public ObjectProperty<Access> visitAccessProperty() {
        return visitAccess;
    }

    public void setVisitAccess(Access visitAccess) {
        this.visitAccess.set(visitAccess);
    }

    public Access getUserAccess() {
        return userAccess.get();
    }

    @Override
    public ObjectProperty<Access> userAccessProperty() {
        return userAccess;
    }

    public void setUserAccess(Access userAccess) {
        this.userAccess.set(userAccess);
    }

    public Access getRoleAccess() {
        return roleAccess.get();
    }

    public ObjectProperty<Access> roleAccessProperty() {
        return roleAccess;
    }

    public void setRoleAccess(Access roleAccess) {
        this.roleAccess.set(roleAccess);
    }

    @Override
    public Access getDeviceAccess() {
        return deviceAccess.get();
    }

    @Override
    public ObjectProperty<Access> deviceAccessProperty() {
        return deviceAccess;
    }

    public void setDeviceAccess(Access deviceAccess) {
        this.deviceAccess.set(deviceAccess);
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
                ", patientAccess=" + patientAccess +
                ", visitAccess=" + visitAccess +
                ", userAccess=" + userAccess +
                ", roleAccess=" + roleAccess +
                ", deviceAccess=" + deviceAccess +
                '}';
    }
}
