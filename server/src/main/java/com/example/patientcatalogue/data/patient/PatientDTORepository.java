package com.example.patientcatalogue.data.patient;

import com.example.patientcatalogue.data.patient.PatientDTO;
import com.example.patientcatalogue.data.visit.PatientVisitDTO;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PatientDTORepository extends CrudRepository <PatientDTO,Integer>{

    List<PatientDTO> findByLastName(String lastName);

    List<PatientDTO> findByFirstNameAndLastName(String firstName, String lastName);

}
