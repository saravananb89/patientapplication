package com.example.patientcatalogue.data.user;


import com.example.patientcatalogue.service.patient.Patient;
import com.example.patientcatalogue.service.user.User;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "usertable")
public class UserDTO {
    @Column(name = "userName", length = 20)
    @Id
    private String userName;
    @Column(name = "password")
    private String password;
    @Column(name = "preferredLocale")
    private String preferredLocale;
    @Column(name = "lastLogin")
    private LocalDate lastLogin;

    public UserDTO() {
    }

    public UserDTO(String userName, String password, String preferredLocale, LocalDate lastLogin) {
        this.userName = userName;
        this.password = password;
        this.preferredLocale = preferredLocale;
        this.lastLogin = lastLogin;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPreferredLocale() {
        return preferredLocale;
    }

    public void setPreferredLocale(String preferredLocale) {
        this.preferredLocale = preferredLocale;
    }

    public LocalDate getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(LocalDate lastLogin) {
        this.lastLogin = lastLogin;
    }

    public User getUser(){
        return new User(userName,password,preferredLocale,lastLogin);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDTO userDTO = (UserDTO) o;
        return Objects.equals(userName, userDTO.userName);
    }

    @Override
    public int hashCode() {

        return Objects.hash(userName);
    }
}
