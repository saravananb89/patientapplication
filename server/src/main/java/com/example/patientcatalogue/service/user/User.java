package com.example.patientcatalogue.service.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@EqualsAndHashCode
public class User {

    private String userName;
    private String password;
    private String preferredLocale;
    private LocalDate lastLogin;
}
