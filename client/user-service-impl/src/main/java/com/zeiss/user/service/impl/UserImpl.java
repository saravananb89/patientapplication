package com.zeiss.user.service.impl;

import com.zeiss.role.service.api.Role;
import com.zeiss.user.service.api.User;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDate;
import java.util.Locale;
import java.util.Objects;

public class UserImpl implements User {

    private final StringProperty userName = new SimpleStringProperty();
    private final StringProperty password = new SimpleStringProperty();
    private final ObjectProperty<Locale> preferredLocale = new SimpleObjectProperty<>();
    private final ObjectProperty<LocalDate> lastLogin = new SimpleObjectProperty<>();
    private final ObjectProperty<Role> roleType = new SimpleObjectProperty<>();
    private final StringProperty role = new SimpleStringProperty();

    public UserImpl(String userName, String password, Locale preferredLocale, LocalDate lastLogin, String role, Role roleType) {
        this.userName.setValue(userName);
        this.password.setValue(password);
        this.preferredLocale.setValue(preferredLocale);
        this.lastLogin.setValue(lastLogin);
        this.role.setValue(role);
        this.roleType.setValue(roleType);
    }

    public UserImpl() {
    }

    public String getUserName() {
        return userName.get();
    }

    public StringProperty userNameProperty() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName.set(userName);
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    public String getPassword() {
        return password.get();
    }

    public StringProperty passwordProperty() {
        return password;
    }

    public void setPreferredLocale(String preferredLocale) {
        this.preferredLocale.set(Locale.forLanguageTag(preferredLocale));
    }

    public Locale getPreferredLocale() {
        return preferredLocale.get();
    }

    public ObjectProperty<Locale> preferredLocaleProperty() {
        return preferredLocale;
    }

    public void setLastLogin(String lastLogin) {
        this.lastLogin.set(lastLogin == null ? null : LocalDate.parse(lastLogin));
    }

    public LocalDate getLastLogin() {
        return lastLogin.get();
    }

    public ObjectProperty<LocalDate> lastLoginProperty() {
        return lastLogin;
    }

    public String getRole() {
        return role.get();
    }

    public StringProperty roleProperty() {
        return role;
    }

    public void setRole(String role) {
        this.role.set(role);
    }

    public Role getRoleType() {
        return roleType.get();
    }

    public ObjectProperty<Role> roleTypeProperty() {
        return roleType;
    }

    public void setRoleType(Role roleType) {
        this.roleType.set(roleType);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserImpl user = (UserImpl) o;
        return Objects.equals(userName, user.userName);
    }

    @Override
    public int hashCode() {

        return Objects.hash(userName);
    }

    @Override
    public String toString() {
        return "UserImpl{" +
                "userName=" + userName +
                ", password=" + password +
                ", preferredLocale=" + preferredLocale +
                ", lastLogin=" + lastLogin +
                '}';
    }
}
