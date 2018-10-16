package com.zeiss.user.service.impl;

import com.zeiss.role.service.api.Access;
import com.zeiss.role.service.api.Role;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDate;
import java.util.Locale;

public class NoUser extends UserImpl {

    private static final String USERNAME = "userName";
    private static final String PASSWORD = "";
    private static final Locale PREFERREDLOCALE = Locale.GERMAN;
    private static final LocalDate LASTLOGIN = LocalDate.now();
    private static final String ROLE = "user";
    private static final Role ROLETYPE = new Role() {
        @Override
        public void setRoleName(String roleName) {

        }

        @Override
        public void setPatientAccess(Access patientAccess) {

        }

        @Override
        public void setVisitAccess(Access visitAccess) {

        }

        @Override
        public void setUserAccess(Access userAccess) {

        }

        @Override
        public void setRoleAccess(Access roleAccess) {

        }

        @Override
        public void setDeviceAccess(Access roleAccess) {

        }

        @Override
        public String getRoleName() {
            return "";
        }

        @Override
        public StringProperty roleNameProperty() {
            return null;
        }

        @Override
        public Access getPatientAccess() {
            return Access.NO_ACCESS;
        }

        @Override
        public ObjectProperty<Access> patientAccessProperty() {
            return new SimpleObjectProperty<>(Access.NO_ACCESS);
        }

        @Override
        public Access getVisitAccess() {
            return Access.NO_ACCESS;
        }

        @Override
        public ObjectProperty<Access> visitAccessProperty() {
            return new SimpleObjectProperty<>(Access.NO_ACCESS);
        }

        @Override
        public Access getUserAccess() {
            return Access.NO_ACCESS;
        }

        @Override
        public ObjectProperty<Access> userAccessProperty() {
            return new SimpleObjectProperty<>(Access.NO_ACCESS);
        }

        @Override
        public Access getRoleAccess() {
            return Access.NO_ACCESS;
        }

        @Override
        public ObjectProperty<Access> roleAccessProperty() {
            return new SimpleObjectProperty<>(Access.NO_ACCESS);
        }

        @Override
        public Access getDeviceAccess() {
            return Access.NO_ACCESS;
        }

        @Override
        public ObjectProperty<Access> deviceAccessProperty() {
            return new SimpleObjectProperty<>(Access.NO_ACCESS);
        }
    };

    public NoUser() {
        super(USERNAME, PASSWORD, PREFERREDLOCALE, LASTLOGIN, ROLE, ROLETYPE);
    }
}
