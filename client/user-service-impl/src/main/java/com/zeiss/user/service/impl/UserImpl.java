package com.zeiss.user.service.impl;

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

    public UserImpl(String userName, String password, Locale preferredLocale, LocalDate lastLogin) {
        this.userName.setValue(userName);
        this.password.setValue(password);
        this.preferredLocale.setValue(preferredLocale);
        this.lastLogin.setValue(lastLogin);
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
