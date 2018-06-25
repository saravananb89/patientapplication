package com.example.patientcatalogue.service.visit;


import com.example.patientcatalogue.service.patient.Patient;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface PatientVisitRepository {

    /**
     * @param id
     * @return the patientVisit with the id
     */
    Optional<PatientVisit> get(String id);

    /**
     * @return all the patients
     */
    Set<PatientVisit> all();

    /**
     *
     * @param firstName
     * @param lastName
     * @param visitDate
     * @return
     */
    String create(String firstName, String lastName, LocalDate visitDate);

    /**
     *
     * @param id
     * @param firstName
     * @param lastName
     * @param visitDate
     * @return
     */
    boolean update(String id, String firstName, String lastName, LocalDate visitDate);

    /**
     * @param id
     * @@return true whether the patient deleted, otherwise false
     */
    boolean delete(String id);

    /**
     *
     */
    void clear();

    /**
     * Search by lastName
     * @param lastName
     * @return
     */
    List<PatientVisit> findByLastName(String lastName);

    List<PatientVisit> findByFirstNameAndLastName(String firstName, String lastName);

}
