package com.example.patientcatalogue.service.patient;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface PatientService {

    /**
     * @param id
     * @return the patient with the id
     */
    Optional<Patient> get(String id);

    /**
     * @return all the patients
     */
    Set<Patient> all();

    /**
     *
     * @param firstName
     * @param lastName
     * @param dob
     * @param age
     * @param email
     * @return
     */
    String create(String firstName, String lastName, LocalDate dob, String age, String email);

    /**
     *
     * @param id
     * @param firstName
     * @param lastName
     * @param dob
     * @param age
     * @param email
     * @return
     */
    boolean update(String id, String firstName, String lastName, LocalDate dob, String age, String email);

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
    List<Patient> findByLastNameLikeIgnoreCase(String lastName);

    List<Patient> findByFirstName(String firstName);

    List<Patient> findByFirstNameLikeIgnoreCase(String firstName);

    List<Patient> findByFirstNameAndLastNameLikeIgnoreCase(String firstName, String lastName);

}
