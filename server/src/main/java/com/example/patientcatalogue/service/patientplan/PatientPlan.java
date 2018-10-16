package com.example.patientcatalogue.service.patientplan;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@EqualsAndHashCode
public class PatientPlan {

    private Integer id;
    private String patientId;
    private String deviceId;
    private LocalDate planDate;
    private String planTime;

}
