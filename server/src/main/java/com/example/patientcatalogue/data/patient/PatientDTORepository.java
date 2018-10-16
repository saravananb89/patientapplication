package com.example.patientcatalogue.data.patient;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PatientDTORepository extends CrudRepository <PatientDTO,Integer> {

    List<PatientDTO> findByLastNameLikeIgnoreCase(String lastName);

    List<PatientDTO> findByFirstName(String firstName);

    List<PatientDTO> findByFirstNameAndLastNameLikeIgnoreCase(String firstName, String lastName);

    List<PatientDTO> findByFirstNameLikeIgnoreCase(String firstName);
}
