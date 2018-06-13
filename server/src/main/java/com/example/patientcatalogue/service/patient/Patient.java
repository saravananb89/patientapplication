package com.example.patientcatalogue.service.patient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@EqualsAndHashCode
public class Patient {

    private String id;
    private String firstName;
    private String lastName;
    private LocalDate dob;
    private String age;
    private String email;


}
