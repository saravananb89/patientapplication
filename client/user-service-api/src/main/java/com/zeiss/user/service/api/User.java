package com.zeiss.user.service.api;

import com.zeiss.role.service.api.Role;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDate;
import java.util.Locale;

public interface User {

    void setUserName(String userName);

    void setPassword(String password);

    void setPreferredLocale(String preferredLocale);

    void setLastLogin(String lastLogin);

    void setRole(String role);

    void setRoleType(Role roleType);

    String getUserName();

    StringProperty userNameProperty();

    String getPassword();

    StringProperty passwordProperty();

    Locale getPreferredLocale();

    ObjectProperty<Locale> preferredLocaleProperty();

    LocalDate getLastLogin();

    ObjectProperty<LocalDate> lastLoginProperty();

    String getRole();

    StringProperty roleProperty();

    Role getRoleType();

    ObjectProperty<Role> roleTypeProperty();

}
