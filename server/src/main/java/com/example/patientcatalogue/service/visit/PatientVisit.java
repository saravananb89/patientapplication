package com.example.patientcatalogue.service.visit;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@EqualsAndHashCode
public class PatientVisit {

    private String id;
    private String visitPatientFirstName;
    private String visitPatientLastName;
    private LocalDate patientVisitDate;

}
