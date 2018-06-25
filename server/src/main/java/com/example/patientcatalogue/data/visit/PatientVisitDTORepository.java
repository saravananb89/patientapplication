package com.example.patientcatalogue.data.visit;

import com.example.patientcatalogue.data.patient.PatientDTO;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PatientVisitDTORepository extends CrudRepository<PatientVisitDTO,Integer> {
    List<PatientVisitDTO> findByLastName(String lastName);
    List<PatientVisitDTO> findByFirstNameAndLastName(String firstName, String lastName);
}
